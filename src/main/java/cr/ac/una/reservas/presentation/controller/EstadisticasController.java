package cr.ac.una.reservas.presentation.controller;

import cr.ac.una.reservas.logic.ServiceModel;
import cr.ac.una.reservas.presentation.view.EstadisticasView;
import cr.ac.una.reservas.presentation.view.VistaUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EstadisticasController {
    private ServiceModel service;
    private EstadisticasView view;

    public EstadisticasController(EstadisticasView view) {
        this.view = view;
        this.service = ServiceModel.getInstance();
        view.getBtnRecursos().addActionListener(e -> cargarRecursos());
        view.getBtnActividades().addActionListener(e -> cargarActividades());
        view.getBtnPdf().addActionListener(e -> generarPdfEstadisticas("estadisticas.pdf"));
    }

    public Map<String, Integer> obtenerDatosRecursos(LocalDate desde, LocalDate hasta) {
        return service.obtenerEstadisticasRecursos(desde, hasta);
    }

    public Map<String, Integer> obtenerDatosActividades(LocalDate desde, LocalDate hasta) {
        return service.obtenerEstadisticasActividades(desde, hasta);
    }

    public void generarPdfEstadisticas(String ruta) {
        String elegido = VistaUtil.elegirRutaPdf(view, "estadisticas.pdf");
        if (elegido == null) {
            return;
        }
        List<Object[]> filas = new ArrayList<>();
        for (int i = 0; i < view.getModeloRecursos().getRowCount(); i++) {
            filas.add(new Object[]{
                    view.getModeloRecursos().getValueAt(i, 0),
                    view.getModeloRecursos().getValueAt(i, 1)
            });
        }
        for (int i = 0; i < view.getModeloActividades().getRowCount(); i++) {
            filas.add(new Object[]{
                    view.getModeloActividades().getValueAt(i, 0),
                    view.getModeloActividades().getValueAt(i, 1)
            });
        }
        service.exportarPDF("Estadisticas", new String[]{"Descripcion", "Cantidad"},
                filas.toArray(new Object[0][0]), elegido);
        VistaUtil.mensaje(view, "PDF generado.");
    }

    private void cargarRecursos() {
        try {
            if (view.getDesdeRecursos().isAfter(view.getHastaRecursos())) {
                VistaUtil.error(view, "El rango de recursos es invalido.");
                return;
            }
            view.renderizarGraficoBarrasRecursos(obtenerDatosRecursos(view.getDesdeRecursos(), view.getHastaRecursos()));
        } catch (Exception ex) {
            VistaUtil.error(view, "Fechas invalidas.");
        }
    }

    private void cargarActividades() {
        try {
            if (view.getDesdeActividades().isAfter(view.getHastaActividades())) {
                VistaUtil.error(view, "El rango de actividades es invalido.");
                return;
            }
            view.renderizarGraficoBarrasActividades(
                    obtenerDatosActividades(view.getDesdeActividades(), view.getHastaActividades()));
        } catch (Exception ex) {
            VistaUtil.error(view, "Fechas invalidas.");
        }
    }
}
