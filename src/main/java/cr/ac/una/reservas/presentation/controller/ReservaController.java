package cr.ac.una.reservas.presentation.controller;

import cr.ac.una.reservas.logic.Funcionario;
import cr.ac.una.reservas.logic.Reserva;
import cr.ac.una.reservas.logic.ReservaDTO;
import cr.ac.una.reservas.logic.ServiceModel;
import cr.ac.una.reservas.presentation.view.ReservasView;
import cr.ac.una.reservas.presentation.view.VistaUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservaController {
    private ServiceModel service;
    private ReservasView view;
    private String funcionarioId;

    public ReservaController(ReservasView view, String funcionarioId) {
        this.view = view;
        this.funcionarioId = funcionarioId;
        this.service = ServiceModel.getInstance();
        view.getBtnUsarIA().addActionListener(e -> procesarFraseIA(view.getTxtFrase()));
        view.getBtnAplicar().addActionListener(e -> aplicar());
        view.getBtnCancelar().addActionListener(e -> cancelar());
        view.getBtnPdf().addActionListener(e -> generarPdf("reportes/reservas.pdf"));
        refrescar();
    }

    public ReservaDTO procesarFraseIA(String frase) {
        ReservaDTO dto = service.extraerDatosIA(frase);
        view.LlenarFormularioConDTO(dto);
        return dto;
    }

    public boolean solicitarReserva(String actividad, LocalDate fecha, LocalTime inicio, LocalTime fin, List<String> catIds) {
        Reserva r = new Reserva();
        r.setActividad(actividad);
        r.setFecha(fecha);
        r.setHoraInicio(inicio);
        r.setHoraFin(fin);
        r.setFuncionario(service.buscarFuncionario(funcionarioId));
        boolean ok = service.registrarReserva(r, catIds);
        if (!ok) {
            List<String> noDisp = service.categoriasNoDisponibles(fecha, inicio, fin, catIds);
            if (!noDisp.isEmpty()) {
                VistaUtil.error(view, "No hay disponibilidad en: " + String.join(", ", noDisp));
            } else {
                VistaUtil.error(view, "No se pudo registrar la reserva. Revise los datos.");
            }
            return false;
        }
        VistaUtil.mensaje(view, "Reserva registrada con exito.");
        refrescar();
        return true;
    }

    public boolean cancelarReserva(String reservaId) {
        return service.cancelarReserva(reservaId);
    }

    public List<Reserva> consultarMisReservas(String funcId) {
        return service.obtenerReservasPorFuncionario(funcId);
    }

    public void generarPdf(String ruta) {
        String elegido = VistaUtil.elegirRutaPdf(view, "reservas.pdf");
        if (elegido == null) {
            return;
        }
        service.exportarPDF("Mis reservas", VistaUtil.columnas(view.getModelo()),
                VistaUtil.tablaADatos(view.getModelo()), elegido);
        VistaUtil.mensaje(view, "PDF generado.");
    }

    private void aplicar() {
        try {
            if (view.getTxtActividad().isBlank() || view.getTxtFecha().isBlank()
                    || view.getTxtHoraInicio().isBlank() || view.getTxtHoraFin().isBlank()) {
                VistaUtil.error(view, "Complete actividad, fecha y horas.");
                return;
            }
            List<String> cats = view.getCategoriasSeleccionadas();
            if (cats.isEmpty()) {
                VistaUtil.error(view, "Seleccione al menos una categoria.");
                return;
            }
            solicitarReserva(view.getTxtActividad(),
                    LocalDate.parse(view.getTxtFecha()),
                    LocalTime.parse(normalizarHora(view.getTxtHoraInicio())),
                    LocalTime.parse(normalizarHora(view.getTxtHoraFin())),
                    cats);
        } catch (Exception ex) {
            VistaUtil.error(view, "Datos invalidos: " + ex.getMessage());
        }
    }

    private void cancelar() {
        int fila = view.getTabla().getSelectedRow();
        if (fila < 0) {
            VistaUtil.error(view, "Seleccione una reserva.");
            return;
        }
        String id = String.valueOf(view.getModelo().getValueAt(fila, 0));
        if (cancelarReserva(id)) {
            VistaUtil.mensaje(view, "Reserva cancelada.");
            refrescar();
        } else {
            VistaUtil.error(view, "Solo se pueden cancelar reservas futuras activas.");
        }
    }

    private void refrescar() {
        view.cargarChecksCategorias();
        view.actualizarTablaMisReservas(consultarMisReservas(funcionarioId));
    }

    private String normalizarHora(String hora) {
        String[] p = hora.split(":");
        return String.format("%02d:%02d", Integer.parseInt(p[0]), Integer.parseInt(p[1]));
    }
}
