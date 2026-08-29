package cr.ac.una.reservas;

import cr.ac.una.reservas.presentation.controller.LoginController;
import cr.ac.una.reservas.presentation.view.LoginView;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            LoginView login = new LoginView();
            new LoginController(login);
            login.setVisible(true);
        });
    }
}
