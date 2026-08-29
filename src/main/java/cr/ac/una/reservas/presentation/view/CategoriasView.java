package cr.ac.una.reservas.presentation.view;

import cr.ac.una.reservas.logic.Categoria;

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
import java.awt.GridLayout;
import java.util.List;

public class CategoriasView extends JPanel {
    private JPanel panelNorte;
    private JPanel panelBusqueda;
    private JPanel panelForm;
    private JPanel panelBotones;
    private JTextField txtBusqueda;
    private JTextField txtId;
    private JTextField txtDescripcion;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnBuscar;
    private JButton btnGuardar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnPdf;

    public CategoriasView() {
        $$$setupUI$$$();
    }

    private void createUIComponents() {
        modelo = new DefaultTableModel(new Object[]{"Id", "Descripcion"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
    }

    public String getTxtDescripcion() {
        return txtDescripcion.getText().trim();
    }

    public String getTxtBusqueda() {
        return txtBusqueda.getText().trim();
    }

    public String getTxtId() {
        return txtId.getText().trim();
    }

    public void actualizarTabla(List<Categoria> lista) {
        modelo.setRowCount(0);
        for (Categoria c : lista) {
            modelo.addRow(new Object[]{c.getId(), c.getDescripcion()});
        }
    }

    public void cargarFormulario(String id, String descripcion) {
        txtId.setText(id);
        txtDescripcion.setText(descripcion);
    }

    public void limpiar() {
        txtId.setText("");
        txtDescripcion.setText("");
        tabla.clearSelection();
    }

    public JTable getTabla() {
        return tabla;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JButton getBtnPdf() {
        return btnPdf;
    }

    private void $$$setupUI$$$() {
        createUIComponents();
        setLayout(new BorderLayout(8, 8));
        setBorder(new EmptyBorder(8, 8, 8, 8));

        txtBusqueda = new JTextField();
        txtId = new JTextField();
        txtId.setEnabled(false);
        txtDescripcion = new JTextField();
        btnBuscar = new JButton("Buscar");
        btnGuardar = new JButton("Guardar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnPdf = new JButton("PDF");

        panelNorte = new JPanel(new GridLayout(2, 1, 6, 6));
        panelBusqueda = new JPanel(new BorderLayout(6, 6));
        panelBusqueda.add(new JLabel("Descripcion:"), BorderLayout.WEST);
        panelBusqueda.add(txtBusqueda, BorderLayout.CENTER);
        panelBusqueda.add(btnBuscar, BorderLayout.EAST);

        panelForm = new JPanel(new GridLayout(1, 4, 6, 6));
        panelForm.add(new JLabel("Id:"));
        panelForm.add(txtId);
        panelForm.add(new JLabel("Descripcion:"));
        panelForm.add(txtDescripcion);
        panelNorte.add(panelBusqueda);
        panelNorte.add(panelForm);

        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnPdf);

        add(panelNorte, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
}
