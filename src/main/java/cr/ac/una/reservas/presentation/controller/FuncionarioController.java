package cr.ac.una.reservas.presentation.controller;

import cr.ac.una.reservas.logic.Funcionario;
import cr.ac.una.reservas.logic.ServiceModel;
import cr.ac.una.reservas.presentation.view.FuncionariosView;
import cr.ac.una.reservas.presentation.view.VistaUtil;

import java.util.List;

public class FuncionarioController {
    private ServiceModel service;
    private FuncionariosView view;

    public FuncionarioController(FuncionariosView view) {
        this.view = view;
        this.service = ServiceModel.getInstance();
        view.getBtnBuscar().addActionListener(e -> buscar(view.getTxtBusquedaId(), view.getTxtBusquedaNombre()));
        view.getBtnGuardar().addActionListener(e -> guardarFormulario());
        view.getBtnEliminar().addActionListener(e -> eliminarSeleccionado());
        view.getBtnLimpiar().addActionListener(e -> view.limpiar());
        view.getBtnPdf().addActionListener(e -> generarPdf("funcionarios.pdf"));
        view.getTabla().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarSeleccion();
            }
        });
        view.actualizarTabla(service.buscarFuncionarios("", ""));
    }

    public List<Funcionario> buscar(String id, String nombre) {
        List<Funcionario> lista = service.buscarFuncionarios(id, nombre);
        view.actualizarTabla(lista);
        return lista;
    }

    public boolean guardar(Funcionario f) {
        Funcionario existente = service.buscarFuncionario(f.getId());
        boolean ok;
        if (existente == null) {
            ok = service.agregarFuncionario(f);
        } else {
            ok = service.modificarFuncionario(f);
        }
        if (ok) {
            view.actualizarTabla(service.buscarFuncionarios("", ""));
            view.limpiar();
        }
        return ok;
    }

    public boolean eliminar(String id) {
        boolean ok = service.eliminarFuncionario(id);
        if (ok) {
            view.actualizarTabla(service.buscarFuncionarios("", ""));
            view.limpiar();
        }
        return ok;
    }

    public void generarPdf(String ruta) {
        String elegido = VistaUtil.elegirRutaPdf(view, "funcionarios.pdf");
        if (elegido == null) {
            return;
        }
        service.exportarPDF("Funcionarios", VistaUtil.columnas(view.getModelo()),
                VistaUtil.tablaADatos(view.getModelo()), elegido);
        VistaUtil.mensaje(view, "PDF generado.");
    }

    private void guardarFormulario() {
        if (view.getTxtId().isBlank() || view.getTxtNombre().isBlank() || view.getTxtTelefono().isBlank()) {
            VistaUtil.error(view, "Complete id, nombre y telefono.");
            return;
        }
        Funcionario f = new Funcionario(view.getTxtId(), view.getTxtNombre(), view.getTxtTelefono());
        if (guardar(f)) {
            VistaUtil.mensaje(view, "Funcionario guardado. La clave inicial es igual al id.");
        } else {
            VistaUtil.error(view, "No se pudo guardar. El id puede estar repetido o los datos son invalidos.");
        }
    }

    private void eliminarSeleccionado() {
        int fila = view.getTabla().getSelectedRow();
        if (fila < 0) {
            VistaUtil.error(view, "Seleccione un funcionario.");
            return;
        }
        String id = String.valueOf(view.getModelo().getValueAt(fila, 0));
        if (eliminar(id)) {
            VistaUtil.mensaje(view, "Funcionario eliminado.");
        } else {
            VistaUtil.error(view, "No se puede eliminar. Verifique que no tenga reservas futuras.");
        }
    }

    private void cargarSeleccion() {
        int fila = view.getTabla().getSelectedRow();
        if (fila >= 0) {
            view.cargarFormulario(
                    String.valueOf(view.getModelo().getValueAt(fila, 0)),
                    String.valueOf(view.getModelo().getValueAt(fila, 1)),
                    String.valueOf(view.getModelo().getValueAt(fila, 2)));
        }
    }
}
