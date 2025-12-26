import javax.swing.SwingUtilities;
import view.LoginFrame;

public class EducoreApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
