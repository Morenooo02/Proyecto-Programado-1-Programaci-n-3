package cr.ac.una.reservas.data;

import cr.ac.una.reservas.logic.Categoria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class XmlDataStoreIT {

    @TempDir
    Path tempDir;

    @Test
    void guardarYCargarPersistenciaXml() {
        Path xml = tempDir.resolve("datos.xml");
        XmlDataStore store = new XmlDataStore(xml.toString());
        DataContainer datos = store.cargarDatos();
        int original = datos.getCategorias().size();
        datos.getCategorias().add(new Categoria("99", "Categoria IT"));
        store.guardarDatos(datos);

        DataContainer recargado = store.cargarDatos();
        assertEquals(original + 1, recargado.getCategorias().size());
        assertFalse(recargado.getUsuarios().isEmpty());
    }
}
