import javax.swing.*;
import java.awt.*;

public class UpdatingWindow extends JWindow {

    public UpdatingWindow() {
        super();
    }

    public void prompt() {
        JPanel panel = new JPanel(new GridLayout(0,1));
        JLabel label = new JLabel("Updating...", SwingConstants.CENTER);
        panel.add(label);
        this.add(panel);
        this.setSize(200,100);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void close() {
        this.setVisible(false);
        this.dispose();
        return;
    }
}
