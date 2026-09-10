package cr.ac.una.reservas.logic;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServiceModelCalendarizacionTest {

    @Test
    void obtenerMatrizCalendarizacionMuestraReservaEnHorarioYRecursoCorrecto() {
        ServiceModel service = ServiceModel.getInstance();
        LocalDate fecha = LocalDate.now().plusDays(40);
        Reserva r = new Reserva();
        r.setActividad("Reunion calendarizacion");
        r.setFecha(fecha);
        r.setHoraInicio(LocalTime.of(9, 0));
        r.setHoraFin(LocalTime.of(10, 0));
        r.setFuncionario(service.buscarFuncionario("100"));
        assertTrue(service.registrarReserva(r, List.of("1")));

        Object[][] matriz = service.obtenerMatrizCalendarizacion(fecha, "1");
        List<Recurso> recursos = service.recursosDeCategoria("1");
        assertEquals(recursos.size() + 1, matriz[0].length);

        int filaNueve = 9 - 7;
        assertEquals("09:00", matriz[filaNueve][0]);
        boolean encontrada = false;
        for (int j = 1; j < matriz[filaNueve].length; j++) {
            if ("Reunion calendarizacion - Ana Perez".equals(matriz[filaNueve][j])) {
                encontrada = true;
            }
        }
        assertTrue(encontrada);

        service.cancelarReserva(r.getId());
    }

    @Test
    void obtenerMatrizCalendarizacionSlotSinReservaQuedaVacio() {
        ServiceModel service = ServiceModel.getInstance();
        LocalDate fecha = LocalDate.now().plusDays(41);
        Object[][] matriz = service.obtenerMatrizCalendarizacion(fecha, "1");
        int filaOnce = 11 - 7;
        for (int j = 1; j < matriz[filaOnce].length; j++) {
            assertEquals("", matriz[filaOnce][j]);
        }
    }
}
