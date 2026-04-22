package agency.ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public final class DialogUtil {
    private DialogUtil() {
    }

    public static void info(JFrame parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void error(JFrame parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
