import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class UpdatePromptPanel extends JPanel {

    public static boolean prompt() {
        JPanel panel = new JPanel(new GridLayout(0,1));
        JLabel label = new JLabel("Es gibt eine neuen üpdate für Das Blöckchen!!!!!");
        JLabel label2 = new JLabel("Würdest du üpdaten?");
        panel.add(label);
        panel.add(label2);
        String[] options = new String[]{"Ja", "Nein"};
        int result = JOptionPane.showOptionDialog(null, panel, "Test", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        return (result == 0);
    }
}
