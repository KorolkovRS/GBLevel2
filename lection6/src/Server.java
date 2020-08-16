import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.Adler32;

public class Server extends Chat{

    public Server() {
        super("Server");
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is ready. Waiting for clients...");
            clientSocket = serverSocket.accept();
            System.out.println(String.format("Client %s is connect", clientSocket.getLocalSocketAddress()));

            setStreams();
            getMessage();
            sendMessage();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
