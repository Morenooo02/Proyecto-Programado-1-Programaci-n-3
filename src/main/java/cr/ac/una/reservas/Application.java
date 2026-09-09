package cr.ac.una.reservas;

import cr.ac.una.reservas.presentation.controller.LoginController;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            new LoginController().setVisible(true);
        });
    }
}
