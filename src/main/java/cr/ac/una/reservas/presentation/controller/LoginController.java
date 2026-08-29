package cr.ac.una.reservas.presentation.controller;

import cr.ac.una.reservas.logic.ServiceModel;
import cr.ac.una.reservas.logic.Usuario;
import cr.ac.una.reservas.presentation.view.LoginView;
import cr.ac.una.reservas.presentation.view.MainFrame;
import cr.ac.una.reservas.presentation.view.VistaUtil;

public class LoginController {
    private ServiceModel service;
    private LoginView view;

    public LoginController(LoginView view) {
        this.view = view;
        this.service = ServiceModel.getInstance();
        this.view.getBtnIngresar().addActionListener(e -> intentarLogin());
    }

    public boolean login(String id, String clave) {
        return service.login(id, clave) != null;
    }

    public boolean cambiarClave(String id, String actual, String nueva) {
        return service.cambiarClave(id, actual, nueva);
    }

    private void intentarLogin() {
        try {
            String id = view.getTxtId();
            String clave = view.getTxtClave();
            if (id.isBlank() || clave.isBlank()) {
                VistaUtil.error(view, "Debe indicar id y clave.");
                return;
            }
            Usuario usuario = service.login(id, clave);
            if (usuario == null) {
                VistaUtil.error(view, "Credenciales incorrectas.");
                return;
            }
            view.dispose();
            MainFrame main = new MainFrame(usuario);
            new MainController(main);
            main.setVisible(true);
        } catch (Exception ex) {
            VistaUtil.error(view, "Error al ingresar: " + ex.getMessage());
        }
    }
}
