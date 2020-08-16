import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Chat {
    private int connectionCount = 1;

    public Client(String name) {
        super(name);
        connection();
        System.out.println(String.format("Connect to %s", clientSocket.getLocalSocketAddress()));
        setStreams();
        getMessage();
        sendMessage();
    }

    public void connection() {
        if (connectionCount <= 5) {
            try {
                System.out.println("Connection attempt " + connectionCount);
                clientSocket = new Socket("localhost", PORT);
            } catch (IOException e) {
                System.out.println("Connection error");
                connectionCount++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                connection();
            }
        } else {
            System.out.println("Connection timeout error");
            System.exit(1);
        }
    }
}
