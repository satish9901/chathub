import java.io.*;
import java.net.*;

public class ChatClient {
    private String userName;
    private BufferedReader reader;
    private PrintWriter writer;

    public ChatClient(String userName, Socket socket) throws IOException {
        this.userName = userName;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public ChatClient(Socket socket) {

    }


    public void start() {
        Thread inputThread = new Thread(() -> {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        inputThread.start();

        try (BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {
            String userMessage;
            while (true) {
                userMessage = userInput.readLine();
                if (userMessage.equals("/quit")) {
                    writer.println("/quit");
                    break;
                } else {
                    writer.println(userName + ": " + userMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
