import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Form extends JFrame {
    private String name = "Admin";

    public Form(String title, String name) {
        setTitle(title);
        this.name = name;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(0, 0, 600, 500);

        JPanel chatPanel = new JPanel(new BorderLayout());
        JTextArea charTextArea = new JTextArea();
        charTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(charTextArea);
        chatPanel.add(scrollPane);

        add(chatPanel);

        JPanel controlPanel = new JPanel(new BorderLayout());

        JTextField inputField = new JTextField();
        inputField.addActionListener(new ActionListener() {
            private StringBuilder sb = new StringBuilder(charTextArea.getText());

            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputField.getText().isBlank()) {
                    return;
                }

                sb.append(charTextArea.getText())
                        .append("\n")
                        .append(addHeader())
                        .append(inputField.getText());
                charTextArea.setText(sb.toString());
                inputField.setText("");
                sb.setLength(0);
            }
        });
        controlPanel.add(inputField);

        JComboBox<String> roomsOptions = new JComboBox<>(new String[]{"Room 1", "Room 2", "Admin"});
        controlPanel.add(roomsOptions, BorderLayout.WEST);

        JButton submitBtn = new JButton("Submit");
        submitBtn.addActionListener(new SubmitButtonListener(this, inputField, charTextArea));
        controlPanel.add(submitBtn, BorderLayout.EAST);

        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    String addHeader() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, HH:mm");
        return dateFormat.format(new Date()) + " " + name + "\n";
    }
}
