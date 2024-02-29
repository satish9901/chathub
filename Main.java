import java.io.*;
import java.net.*;

public class Main {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            ChatClient client = new ChatClient(socket);
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
