package cr.ac.una.reservas.presentation.controller;

import cr.ac.una.reservas.logic.Categoria;
import cr.ac.una.reservas.logic.ServiceModel;
import cr.ac.una.reservas.presentation.view.CategoriasView;
import cr.ac.una.reservas.presentation.view.VistaUtil;

import java.util.List;

public class CategoriaController {
    private ServiceModel service;
    private CategoriasView view;

    public CategoriaController(CategoriasView view) {
        this.view = view;
        this.service = ServiceModel.getInstance();
        view.getBtnBuscar().addActionListener(e -> buscar(view.getTxtBusqueda()));
        view.getBtnGuardar().addActionListener(e -> guardarFormulario());
        view.getBtnEliminar().addActionListener(e -> eliminarSeleccionado());
        view.getBtnLimpiar().addActionListener(e -> view.limpiar());
        view.getBtnPdf().addActionListener(e -> generarPdf("categorias.pdf"));
        view.getTabla().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarSeleccion();
            }
        });
        view.actualizarTabla(service.buscarCategorias(""));
    }

    public List<Categoria> buscar(String descripcion) {
        List<Categoria> lista = service.buscarCategorias(descripcion);
        view.actualizarTabla(lista);
        return lista;
    }

    public boolean guardar(Categoria c) {
        boolean ok;
        if (c.getId() == null || c.getId().isBlank()) {
            ok = service.agregarCategoria(c);
        } else {
            ok = service.modificarCategoria(c);
        }
        if (ok) {
            view.actualizarTabla(service.buscarCategorias(""));
            view.limpiar();
        }
        return ok;
    }

    public boolean eliminar(String id) {
        boolean ok = service.eliminarCategoria(id);
        if (ok) {
            view.actualizarTabla(service.buscarCategorias(""));
            view.limpiar();
        }
        return ok;
    }

    public void generarPdf(String ruta) {
        String elegido = VistaUtil.elegirRutaPdf(view, "categorias.pdf");
        if (elegido == null) {
            return;
        }
        service.exportarPDF("Categorias", VistaUtil.columnas(view.getModelo()),
                VistaUtil.tablaADatos(view.getModelo()), elegido);
        VistaUtil.mensaje(view, "PDF generado.");
    }

    private void guardarFormulario() {
        if (view.getTxtDescripcion().isBlank()) {
            VistaUtil.error(view, "La descripcion es obligatoria.");
            return;
        }
        Categoria c = new Categoria(view.getTxtId().isBlank() ? null : view.getTxtId(), view.getTxtDescripcion());
        if (guardar(c)) {
            VistaUtil.mensaje(view, "Categoria guardada.");
        } else {
            VistaUtil.error(view, "No se pudo guardar la categoria.");
        }
    }

    private void eliminarSeleccionado() {
        int fila = view.getTabla().getSelectedRow();
        if (fila < 0) {
            VistaUtil.error(view, "Seleccione una categoria.");
            return;
        }
        String id = String.valueOf(view.getModelo().getValueAt(fila, 0));
        if (eliminar(id)) {
            VistaUtil.mensaje(view, "Categoria eliminada.");
        } else {
            VistaUtil.error(view, "No se puede eliminar porque tiene recursos asociados.");
        }
    }

    private void cargarSeleccion() {
        int fila = view.getTabla().getSelectedRow();
        if (fila >= 0) {
            view.cargarFormulario(
                    String.valueOf(view.getModelo().getValueAt(fila, 0)),
                    String.valueOf(view.getModelo().getValueAt(fila, 1)));
        }
    }
}
