import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Viewer {
    private JTextField textField;

    public Viewer() {
    	Controller controller = new Controller(this);

        textField = new JTextField();
        textField.setFont(new Font("Roboto", Font.BOLD, 40));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(false);
        textField.setBounds(10, 10, 330, 70);

        JPanel buttonPanel = new JPanel(null);
        buttonPanel.setBounds(10, 90, 330, 360);

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "\u2190", "CA"
        };

        int x = 10;
        int y = 10;
        int buttonWidth = 65;
        int buttonHeight = 60;
        int horizontalGap = 10;
        int verticalGap = 10;

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Roboto", Font.PLAIN, 22));
            button.setBounds(x, y, buttonWidth, buttonHeight);
            button.addActionListener(controller);
            buttonPanel.add(button);

            x += buttonWidth + horizontalGap;
            if (x + buttonWidth + horizontalGap > buttonPanel.getWidth()) {
                x = 10;
                y += buttonHeight + verticalGap;
            }
        }

        JFrame frame = new JFrame("RPN Calculator MVC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocation(680, 250);
        frame.setSize(335, 510);
        frame.add(textField);
        frame.add(buttonPanel);
        frame.setVisible(true);
    }

    public void updateTextField(String result) {
        textField.setText(result);
    }

}
