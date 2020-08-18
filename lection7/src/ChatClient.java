import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClient extends JFrame {
    private int connectionCount = 1;
    private String host = "localhost";
    private int port = Server.PORT;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    JTextField inputField;
    JTextArea charTextArea;

    public ChatClient() {
        setTitle("ChatClient v0.1");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(0, 0, 600, 500);

        JPanel chatPanel = new JPanel(new BorderLayout());
        charTextArea = new JTextArea();
        charTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(charTextArea);
        chatPanel.add(scrollPane);

        add(chatPanel);

        JPanel controlPanel = new JPanel(new BorderLayout());

        inputField = new JTextField();

        JButton submitBtn = new JButton("Submit");
        submitBtn.addActionListener(new SendButtonListener());
        controlPanel.add(submitBtn, BorderLayout.EAST);

        controlPanel.add(inputField);
        add(controlPanel, BorderLayout.SOUTH);
        setVisible(true);

        connection();
    }

    public void connection() {
        charTextArea.setText("Enter [/auth <login> <password>] for authorization\n");
        if(connectionCount < 6) {
            try {
                socket = new Socket(host, port);
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                readMessage();
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

    public void readMessage() {

        Thread thread = new Thread(new Runnable() {
            private StringBuilder sb = new StringBuilder();
            @Override
            public void run() {
                boolean isAuth = false;
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        String message = in.readUTF();
                        displayMessageOnGUI(message);

                        if(!isAuth) {
                            if (message.startsWith("/authok")) {
                                System.out.println("Authorized");
                                isAuth = true;
                            }
                        } else {
                            if (message.equals("/end")) {
                                break;
                            }
                        }
                    } catch (IOException e) {
                        Thread.currentThread().interrupt();
                        unsubscribe();
                        displayMessageOnGUI("Session closed. Goodbye!");
                        connection();
                        return;
                    }
                }
            }

            private void displayMessageOnGUI(String message) {
                sb.append(charTextArea.getText())
                        .append(message).append("\n");

                System.out.println("sb: " + sb.toString());
                charTextArea.setText(sb.toString());

                sb.setLength(0);
            }
        });
        thread.start();
    }

    private void unsubscribe() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class SendButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (inputField.getText().isBlank()) {
                return;
            }
            try {
                out.writeUTF(inputField.getText());
            } catch (IOException ioException) {
                System.out.println("Sending message error");

            }
            inputField.setText("");
        }
    }
}
