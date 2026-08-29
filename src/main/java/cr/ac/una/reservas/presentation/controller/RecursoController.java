package cr.ac.una.reservas.presentation.controller;

import cr.ac.una.reservas.logic.Recurso;
import cr.ac.una.reservas.logic.ServiceModel;
import cr.ac.una.reservas.presentation.view.RecursosView;
import cr.ac.una.reservas.presentation.view.VistaUtil;

import java.util.List;

public class RecursoController {
    private ServiceModel service;
    private RecursosView view;

    public RecursoController(RecursosView view) {
        this.view = view;
        this.service = ServiceModel.getInstance();
        view.getBtnFiltrar().addActionListener(e -> filtrarPorCategoria(view.getCategoriaFiltro()));
        view.getBtnGuardar().addActionListener(e -> guardarFormulario());
        view.getBtnEliminar().addActionListener(e -> eliminarSeleccionado());
        view.getBtnLimpiar().addActionListener(e -> view.limpiar());
        view.getBtnPdf().addActionListener(e -> generarPdf("recursos.pdf"));
        view.getTabla().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarSeleccion();
            }
        });
        view.actualizarTabla(service.buscarRecursosPorCategoria(""));
    }

    public List<Recurso> filtrarPorCategoria(String catId) {
        List<Recurso> lista = service.buscarRecursosPorCategoria(catId);
        view.actualizarTabla(lista);
        return lista;
    }

    public boolean guardar(Recurso r) {
        Recurso existente = null;
        for (Recurso rec : service.buscarRecursosPorCategoria("")) {
            if (rec.getId().equals(r.getId())) {
                existente = rec;
                break;
            }
        }
        boolean ok;
        if (existente == null) {
            ok = service.agregarRecurso(r);
        } else {
            ok = service.modificarRecurso(r);
        }
        if (ok) {
            view.cargarCategorias();
            view.actualizarTabla(service.buscarRecursosPorCategoria(""));
            view.limpiar();
        }
        return ok;
    }

    public boolean eliminar(String id) {
        boolean ok = service.eliminarRecurso(id);
        if (ok) {
            view.actualizarTabla(service.buscarRecursosPorCategoria(""));
            view.limpiar();
        }
        return ok;
    }

    public void generarPdf(String ruta) {
        String elegido = VistaUtil.elegirRutaPdf(view, "recursos.pdf");
        if (elegido == null) {
            return;
        }
        service.exportarPDF("Recursos", VistaUtil.columnas(view.getModelo()),
                VistaUtil.tablaADatos(view.getModelo()), elegido);
        VistaUtil.mensaje(view, "PDF generado.");
    }

    private void guardarFormulario() {
        if (view.getTxtId().isBlank() || view.getTxtDescripcion().isBlank() || view.getCategoriaSeleccionadaForm() == null) {
            VistaUtil.error(view, "Complete id, descripcion y categoria.");
            return;
        }
        Recurso r = new Recurso(view.getTxtId(), view.getTxtDescripcion(), view.getCategoriaSeleccionadaForm());
        if (guardar(r)) {
            VistaUtil.mensaje(view, "Recurso guardado.");
        } else {
            VistaUtil.error(view, "No se pudo guardar. El id puede estar repetido.");
        }
    }

    private void eliminarSeleccionado() {
        int fila = view.getTabla().getSelectedRow();
        if (fila < 0) {
            VistaUtil.error(view, "Seleccione un recurso.");
            return;
        }
        String id = String.valueOf(view.getModelo().getValueAt(fila, 0));
        if (eliminar(id)) {
            VistaUtil.mensaje(view, "Recurso eliminado.");
        } else {
            VistaUtil.error(view, "No se puede eliminar porque esta en una reserva activa.");
        }
    }

    private void cargarSeleccion() {
        int fila = view.getTabla().getSelectedRow();
        if (fila < 0) {
            return;
        }
        String id = String.valueOf(view.getModelo().getValueAt(fila, 0));
        Recurso rec = null;
        for (Recurso r : service.buscarRecursosPorCategoria("")) {
            if (r.getId().equals(id)) {
                rec = r;
                break;
            }
        }
        if (rec != null) {
            String catId = rec.getCategoria() == null ? "" : rec.getCategoria().getId();
            view.cargarFormulario(rec.getId(), rec.getDescripcion(), catId);
        }
    }
}
