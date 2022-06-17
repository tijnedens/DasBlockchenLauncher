import javax.swing.*;
import java.awt.*;

public class ErrorPromptPanel {

    public static void prompt() {
        JPanel panel = new JPanel(new GridLayout(0,1));
        JLabel label = new JLabel("Etwas ist schief gelaufen :(");
        JLabel label2 = new JLabel("(Zuviel verkehr?)");
        panel.add(label);
        panel.add(label2);
        String[] options = new String[]{"Schade"};
        JOptionPane.showOptionDialog(null, panel, "Test", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }
}
