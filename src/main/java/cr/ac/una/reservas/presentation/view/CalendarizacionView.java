package cr.ac.una.reservas.presentation.view;

import cr.ac.una.reservas.logic.Categoria;
import cr.ac.una.reservas.logic.Recurso;
import cr.ac.una.reservas.logic.ServiceModel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.util.List;

public class CalendarizacionView extends JPanel {
    private JPanel panelNorte;
    private JTextField txtFecha;
    private JComboBox<Categoria> cmbCategoria;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnCargar;
    private JButton btnPdf;

    public CalendarizacionView() {
        $$$setupUI$$$();
        cargarCategorias();
    }

    private void createUIComponents() {
        modelo = new DefaultTableModel();
        tabla = new JTable(modelo);
    }

    public void cargarCategorias() {
        cmbCategoria.removeAllItems();
        for (Categoria c : ServiceModel.getInstance().buscarCategorias("")) {
            cmbCategoria.addItem(c);
        }
    }

    public LocalDate getFechaSeleccionada() {
        return LocalDate.parse(txtFecha.getText().trim());
    }

    public String getCategoriaSeleccionada() {
        Categoria c = (Categoria) cmbCategoria.getSelectedItem();
        return c == null ? "" : c.getId();
    }

    public void mostrarMatrizHorarios(Object[][] matriz) {
        List<Recurso> recursos = ServiceModel.getInstance().recursosDeCategoria(getCategoriaSeleccionada());
        String[] cols = new String[recursos.size() + 1];
        cols[0] = "Hora";
        for (int i = 0; i < recursos.size(); i++) {
            cols[i + 1] = recursos.get(i).getDescripcion();
        }
        modelo.setColumnIdentifiers(cols);
        modelo.setRowCount(0);
        if (matriz != null) {
            for (Object[] fila : matriz) {
                modelo.addRow(fila);
            }
        }
    }

    public JTable getTabla() {
        return tabla;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JButton getBtnCargar() {
        return btnCargar;
    }

    public JButton getBtnPdf() {
        return btnPdf;
    }

    private void $$$setupUI$$$() {
        createUIComponents();
        setLayout(new BorderLayout(8, 8));
        setBorder(new EmptyBorder(8, 8, 8, 8));

        txtFecha = new JTextField(LocalDate.now().toString(), 12);
        cmbCategoria = new JComboBox<>();
        btnCargar = new JButton("Mostrar");
        btnPdf = new JButton("PDF");

        panelNorte = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 6));
        panelNorte.add(new JLabel("Fecha (yyyy-MM-dd):"));
        panelNorte.add(txtFecha);
        panelNorte.add(new JLabel("Categoria:"));
        panelNorte.add(cmbCategoria);
        panelNorte.add(btnCargar);
        panelNorte.add(btnPdf);

        add(panelNorte, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }
}
