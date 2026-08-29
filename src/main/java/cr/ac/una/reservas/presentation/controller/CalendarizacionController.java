package cr.ac.una.reservas.presentation.controller;

import cr.ac.una.reservas.logic.ServiceModel;
import cr.ac.una.reservas.presentation.view.ActividadesView;
import cr.ac.una.reservas.presentation.view.CalendarizacionView;
import cr.ac.una.reservas.presentation.view.VistaUtil;

import java.time.LocalDate;

public class CalendarizacionController {
    private ServiceModel service;
    private CalendarizacionView calView;
    private ActividadesView actView;

    public CalendarizacionController(CalendarizacionView calView) {
        this(calView, null);
    }

    public CalendarizacionController(CalendarizacionView calView, ActividadesView actView) {
        this.service = ServiceModel.getInstance();
        this.calView = calView;
        this.actView = actView;
        if (calView != null) {
            calView.getBtnCargar().addActionListener(e -> cargar());
            calView.getBtnPdf().addActionListener(e -> generarPdfCalendarizacion("calendarizacion.pdf"));
        }
    }

    public void conectarActividades(ActividadesView view) {
        this.actView = view;
        view.getBtnCargar().addActionListener(e -> cargarActividades());
        view.getBtnPdf().addActionListener(e -> generarPdfActividades("actividades.pdf"));
    }

    public Object[][] cargarCalendarizacionRecursos(LocalDate fecha, String catId) {
        return service.obtenerMatrizCalendarizacion(fecha, catId);
    }

    public Object[][] cargarProgramacionActividades(LocalDate semanaRef) {
        return service.obtenerMatrizActividades(semanaRef);
    }

    public void generarPdfCalendarizacion(String ruta) {
        String elegido = VistaUtil.elegirRutaPdf(calView, "calendarizacion.pdf");
        if (elegido == null) {
            return;
        }
        service.exportarPDF("Calendarizacion de recursos", VistaUtil.columnas(calView.getModelo()),
                VistaUtil.tablaADatos(calView.getModelo()), elegido);
        VistaUtil.mensaje(calView, "PDF generado.");
    }

    public void generarPdfActividades(String ruta) {
        String elegido = VistaUtil.elegirRutaPdf(actView, "actividades.pdf");
        if (elegido == null) {
            return;
        }
        service.exportarPDF("Programacion de actividades", VistaUtil.columnas(actView.getModelo()),
                VistaUtil.tablaADatos(actView.getModelo()), elegido);
        VistaUtil.mensaje(actView, "PDF generado.");
    }

    private void cargar() {
        try {
            Object[][] matriz = cargarCalendarizacionRecursos(calView.getFechaSeleccionada(),
                    calView.getCategoriaSeleccionada());
            calView.mostrarMatrizHorarios(matriz);
        } catch (Exception ex) {
            VistaUtil.error(calView, "Fecha o categoria invalida.");
        }
    }

    private void cargarActividades() {
        try {
            Object[][] matriz = cargarProgramacionActividades(actView.getSemanaReferencia());
            actView.mostrarMatrizSemanal(matriz);
        } catch (Exception ex) {
            VistaUtil.error(actView, "Fecha invalida.");
        }
    }
}
