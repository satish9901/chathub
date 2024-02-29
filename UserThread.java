import java.io.*;
import java.net.*;

public class UserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;

    public UserThread(Socket socket) {
        this.socket = socket;
        this.server = ChatServer.getServer();
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            String userName;
            do {
                writer.println("Enter your name:");
                userName = reader.readLine().trim();
            } while (userName.isEmpty() || server.getUserNames().contains(userName));

            writer.println("Welcome, " + userName + "!");
            server.addUserName(userName);
            server.broadcast(userName + " has joined the chat.", this);

            String clientMessage;
            while (true) {
                clientMessage = reader.readLine();
                if (clientMessage == null || clientMessage.equals("/quit")) {
                    break;
                }
                server.broadcast(userName + ": " + clientMessage, this);
            }

            server.removeUser(userName, this);
            socket.close();
            server.broadcast(userName + " has left the chat.", this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMessage(String message) {
        writer.println(message);
    }
}

