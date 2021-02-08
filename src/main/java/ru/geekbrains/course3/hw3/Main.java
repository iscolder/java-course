package ru.geekbrains.course3.hw3;

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {


	public static void main(String[] args) throws InterruptedException {
		ChatApplication application = ChatApplication.getInstance();
		MessageQueue messageQueue = MessageQueue.getInstance();
		DisplayChat display = DisplayChat.getInstance(messageQueue);

		User user1 = new User(1, "Kostya", messageQueue);
		User user2 = new User(2, "Ura", messageQueue);
		User user3 = new User(3, "Sergey", messageQueue);
		User user4 = new User(4, "Anna", messageQueue);

		application.addUser(user1);
		application.addUser(user2);
		application.addUser(user3);

		Thread displayThread = new Thread(display);
		Thread userThread1 = new Thread(user1);
		Thread userThread2 = new Thread(user2);
		Thread userThread3 = new Thread(user3);
		Thread userThread4 = new Thread(user4);

		displayThread.start();
		userThread1.start();
		userThread2.start();
		userThread3.start();
		userThread4.start();

		Thread.sleep(1000);

		application.addUser(user4);

		try {
			displayThread.join();
			userThread1.join();
			userThread2.join();
			userThread3.join();
			userThread4.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}


	private static class ChatApplication {

		private static final Logger logger = Logger.getLogger(ChatApplication.class.getName());

		private volatile static ChatApplication instance;

		private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

		private final List<User> users = new ArrayList<>();

		private ChatApplication() {
		}

		public static ChatApplication getInstance() {
			if (instance == null) {
				synchronized (ChatApplication.class) {
					if (instance == null) {
						instance = new ChatApplication();
						logger.log(Level.INFO, "Chat is launched");
					}
				}
			}
			return instance;
		}

		public void addUser(User user) {
			Lock lock = reentrantReadWriteLock.writeLock();
			lock.lock();
			try {
				users.add(user);
				user.active = true;
				logger.log(Level.INFO, user.name + " joined");
			} finally {
				lock.unlock();
			}
		}

		public void removeUser(User user) {
			Lock lock = reentrantReadWriteLock.writeLock();
			lock.lock();
			try {
				users.remove(user);
				user.active = false;
				logger.log(Level.INFO, user.name + " left");
			} finally {
				lock.unlock();
			}
		}

		public List<User> getUsers() {
			Lock lock = reentrantReadWriteLock.readLock();
			lock.lock();
			try {
				return users;
			} finally {
				lock.unlock();
			}
		}

	}

	private static class DisplayChat implements Runnable {

		private static final Logger logger = Logger.getLogger(DisplayChat.class.getName());

		private static final String CHAT_FILE = System.getProperty("java.io.tmpdir") + File.separator + "chat.ser";

		private volatile static DisplayChat instance;

		private static final List<Function<String, String>> censor = new ArrayList<>();

		static {
			censor.add(String::toUpperCase);
			censor.add(message -> new Date().toString() + " " + message);
		}

		public static DisplayChat getInstance(MessageQueue messageQueue) {
			if (instance == null) {
				synchronized (ChatApplication.class) {
					if (instance == null) {
						instance = new DisplayChat(messageQueue);
						logger.info("Chat is visible");
					}
				}
			}
			return instance;
		}

		private final MessageQueue messageQueue;

		private List<String> buffer = Collections.synchronizedList(new ArrayList<>());

		private DisplayChat(MessageQueue messageQueue) {
			this.messageQueue = messageQueue;
		}

		@Override
		public void run() {
			logger.info("CHAT START");

			downloadChatFromFile();
			buffer.forEach(logger::info);

			logger.info("***************************************");
			logger.info("***************************************");
			logger.info("***************************************");
			logger.info("***************************************");

			int count = 0;
			while (count != 20) {
				Message message = messageQueue.getMessages().poll();

				if (message != null) {
					for (Function<String, String> c : censor) {
						message.setContent(c.apply(message.getContent()));
					}
					buffer.add(message.toString());
					logger.info(message.toString());
				}
				message = messageQueue.getMessages().poll();
				if (message != null) {
					for (Function<String, String> c : censor) {
						message.setContent(c.apply(message.getContent()));
					}
					buffer.add(message.toString());
					logger.info(message.toString());
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				count++;
			}

			saveChatToFile();
			logger.info("CHAT OVER");
		}

		@SuppressWarnings("unchecked")
		private void downloadChatFromFile() {
			if (new File(CHAT_FILE).exists()) {
				logger.info("Download Chat History");
				try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream(CHAT_FILE))) {
					buffer = (List<String>) ios.readObject();
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		private void saveChatToFile() {
			File file = new File(CHAT_FILE);
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.getAbsolutePath()))){
				oos.writeObject(buffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static class MessageQueue {

		private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<>(5);

		private static volatile MessageQueue instance;

		private MessageQueue() {
		}

		public static MessageQueue getInstance() {
			if (instance == null) {
				synchronized (ChatApplication.class) {
					if (instance == null) {
						instance = new MessageQueue();
					}
				}
			}
			return instance;
		}

		public ArrayBlockingQueue<Message> getMessages() {
			return messages;
		}
	}


	private static class User implements Runnable {

		private static final Logger logger = Logger.getLogger(User.class.getName());

		private final int id;
		private final String name;
		private final MessageQueue messageQueue;

		private volatile boolean active = false;
		private final Random random = new Random();

		public User(int id, String name, MessageQueue messageQueue) {
			this.id = id;
			this.name = name;
			this.messageQueue = messageQueue;
		}

		@Override
		public void run() {
			int count = 0;
			while (count != 10) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (active) {
					sendMessage("Message " + random.nextInt(10));
				}
				count++;
			}

			logger.info(name + " LEAVES THE CHAT");
		}

		public void sendMessage(String content) {
			try {
				messageQueue.getMessages().put(new Message(id, name, content));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof User)) return false;
			User user = (User) o;
			return id == user.id;
		}

		@Override
		public int hashCode() {
			return Objects.hash(id);
		}
	}

	private static final class Message {
		private int userId;
		private String userName;
		private String content;

		private Message(int userId, String userName, String content) {
			this.userId = userId;
			this.userName = userName;
			this.content = content;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		@Override
		public String toString() {
			return "Message{" +
					"userId=" + userId +
					", userName=" + userName +
					", content='" + content + '\'' +
					'}';
		}
	}
}
