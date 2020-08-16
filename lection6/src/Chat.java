import java.io.*;
import java.net.Socket;

public abstract class Chat {
    public static final int PORT = 8888;
    protected String name;
    protected DataInputStream in;
    protected DataOutputStream out;
    protected Socket clientSocket;

    public Chat(String name) {
        this.name = name;
    }

    public void sendMessage() {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String text;

        while (true) {
            try {
                text = console.readLine();
                if (!text.isBlank()) {
                    out.writeUTF(String.format("[%s]: %s", name, text));
                }
            } catch (IOException e) {
                break;
            }
        }
    }

    public void getMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        System.out.println(in.readUTF());

                    } catch (IOException e) {
                        System.out.println("The connection is lost. Please restart application");
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }).start();
    }

    public void setStreams() {
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
