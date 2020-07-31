import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubmitButtonListener implements ActionListener {
    private final StringBuilder sb = new StringBuilder();
    private final JTextField textField;
    private final JTextArea textArea;

    public SubmitButtonListener(JTextField textField, JTextArea textArea) {
        this.textField = textField;
        this.textArea = textArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (textField.getText().isBlank()) {
            return;
        }
        sb.append(textArea.getText())
                .append("\n")
                .append(Form.addHeader())
                .append(textField.getText());
        textArea.setText(sb.toString());
        textField.setText("");
        sb.setLength(0);
        Form.addHeader();
    }
}