package cr.ac.una.reservas.presentation.controller;

import cr.ac.una.reservas.logic.Rol;
import cr.ac.una.reservas.logic.ServiceModel;
import cr.ac.una.reservas.presentation.view.CambiarClaveDialog;
import cr.ac.una.reservas.presentation.view.MainFrame;
import cr.ac.una.reservas.presentation.view.VistaUtil;

public class MainController {
    public MainController(MainFrame main) {
        main.getItemCambiarClave().addActionListener(e -> {
            CambiarClaveDialog dialog = new CambiarClaveDialog(main);
            dialog.getBtnGuardar().addActionListener(ev -> {
                boolean ok = ServiceModel.getInstance().cambiarClave(
                        main.getUsuarioAutenticado().getId(),
                        dialog.getTxtClaveActual(),
                        dialog.getTxtClaveNueva());
                if (ok) {
                    VistaUtil.mensaje(dialog, "Clave actualizada.");
                    dialog.dispose();
                } else {
                    VistaUtil.error(dialog, "No se pudo cambiar la clave. Verifique la clave actual.");
                }
            });
            dialog.setVisible(true);
        });

        CalendarizacionController calCtrl = new CalendarizacionController(main.getCalendarizacionView());
        calCtrl.conectarActividades(main.getActividadesView());
        new EstadisticasController(main.getEstadisticasView());

        if (main.getUsuarioAutenticado().getRol() == Rol.FUNCIONARIO) {
            new ReservaController(main.getReservasView(), main.getUsuarioAutenticado().getId());
        }
        if (main.getUsuarioAutenticado().getRol() == Rol.ADMINISTRADOR) {
            new FuncionarioController(main.getFuncionariosView());
            new CategoriaController(main.getCategoriasView());
            new RecursoController(main.getRecursosView());
        }
    }
}
