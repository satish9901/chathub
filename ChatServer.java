import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static Set<String> userNames = new HashSet<>();
    private static Set<UserThread> userThreads = new HashSet<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Chat server is running on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                UserThread newUser = new UserThread(socket);
                userThreads.add(newUser);
                newUser.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void broadcast(String message, UserThread excludeUser) {
        for (UserThread user : userThreads) {
            if (user != excludeUser) {
                user.sendMessage(message);
            }
        }
    }

    static void addUserName(String userName) {
        userNames.add(userName);
    }

    static void removeUser(String userName, UserThread userThread) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(userThread);
            System.out.println("The user " + userName + " has left the chat.");
        }
    }

    static Set<String> getUserNames() {
        return userNames;
    }

    static boolean hasUsers() {
        return !userNames.isEmpty();
    }

    public static ChatServer getServer() {
        return null;
    }
}
