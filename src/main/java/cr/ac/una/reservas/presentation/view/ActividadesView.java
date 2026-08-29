package cr.ac.una.reservas.presentation.view;

import javax.swing.JButton;
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

public class ActividadesView extends JPanel {
    private JPanel panelNorte;
    private JTextField txtSemana;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnCargar;
    private JButton btnPdf;

    public ActividadesView() {
        $$$setupUI$$$();
    }

    private void createUIComponents() {
        modelo = new DefaultTableModel(
                new Object[]{"Hora", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"}, 0);
        tabla = new JTable(modelo);
    }

    public LocalDate getSemanaReferencia() {
        return LocalDate.parse(txtSemana.getText().trim());
    }

    public void mostrarMatrizSemanal(Object[][] matriz) {
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

        txtSemana = new JTextField(LocalDate.now().toString(), 12);
        btnCargar = new JButton("Mostrar semana");
        btnPdf = new JButton("PDF");

        panelNorte = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 6));
        panelNorte.add(new JLabel("Fecha de la semana (yyyy-MM-dd):"));
        panelNorte.add(txtSemana);
        panelNorte.add(btnCargar);
        panelNorte.add(btnPdf);

        add(panelNorte, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }
}
