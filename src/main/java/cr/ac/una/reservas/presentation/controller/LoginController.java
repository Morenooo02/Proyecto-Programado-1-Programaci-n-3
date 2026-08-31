package cr.ac.una.reservas.presentation.controller;

import cr.ac.una.reservas.logic.ServiceModel;
import cr.ac.una.reservas.logic.Usuario;
import cr.ac.una.reservas.presentation.view.LoginView;
import cr.ac.una.reservas.presentation.view.MainFrame;
import cr.ac.una.reservas.presentation.view.VistaUtil;
import cr.ac.una.reservas.presentation.view.CambiarClaveDialog;
import javax.swing.JOptionPane;

public class LoginController {
    private ServiceModel service;
    private LoginView view;

    public LoginController(LoginView view) {
        this.view = view;
        this.service = ServiceModel.getInstance();
        this.view.getBtnIngresar().addActionListener(e -> intentarLogin());
        this.view.getBtnCancelar().addActionListener(e->view.dispose());
        this.view.getBtnCambiarClave().addActionListener(e -> abrirCambiarClave());
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
    private void abrirCambiarClave() {
        try {
            String id = view.getTxtId();

            if (id.isBlank()) {
                VistaUtil.error(view, "Debe indicar su ID para cambiar la clave.");
                return;
            }

            CambiarClaveDialog dialog = new CambiarClaveDialog(view);

            dialog.getBtnCancelar().addActionListener(e -> dialog.dispose());

            dialog.getBtnGuardar().addActionListener(e -> {
                try {
                    String actual = dialog.getTxtClaveActual();
                    String nueva = dialog.getTxtClaveNueva();
                    String confirmacion = dialog.getTxtClaveNuevaConfirmacion();

                    if (actual.isBlank() || nueva.isBlank() || confirmacion.isBlank()) {
                        VistaUtil.error(dialog, "Debe completar todos los campos.");
                        return;
                    }

                    if (!nueva.equals(confirmacion)) {
                        VistaUtil.error(dialog, "Las nuevas claves no coinciden.");
                        return;
                    }

                    if (cambiarClave(id, actual, nueva)) {
                        JOptionPane.showMessageDialog(dialog, "Clave cambiada correctamente.", "Cambio de clave", JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                    } else {
                        VistaUtil.error(dialog, "La clave actual es incorrecta o no se pudo cambiar.");
                    }

                } catch (Exception ex) {
                    VistaUtil.error(dialog, "Error al cambiar la clave: " + ex.getMessage());
                }
            });

            dialog.setVisible(true);

        } catch (Exception ex) {
            VistaUtil.error(view, "Error al abrir cambio de clave: " + ex.getMessage());
        }
    }
}
