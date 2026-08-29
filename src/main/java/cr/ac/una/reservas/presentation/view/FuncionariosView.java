package cr.ac.una.reservas.presentation.view;

import cr.ac.una.reservas.logic.Funcionario;

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

public class FuncionariosView extends JPanel {
    private JPanel panelNorte;
    private JPanel panelBusqueda;
    private JPanel panelForm;
    private JPanel panelBotones;
    private JTextField txtBusquedaId;
    private JTextField txtBusquedaNombre;
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnBuscar;
    private JButton btnGuardar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnPdf;

    public FuncionariosView() {
        $$$setupUI$$$();
    }

    private void createUIComponents() {
        modelo = new DefaultTableModel(new Object[]{"Id", "Nombre", "Telefono"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
    }

    public String getTxtBusquedaId() {
        return txtBusquedaId.getText().trim();
    }

    public String getTxtBusquedaNombre() {
        return txtBusquedaNombre.getText().trim();
    }

    public String getTxtId() {
        return txtId.getText().trim();
    }

    public String getTxtNombre() {
        return txtNombre.getText().trim();
    }

    public String getTxtTelefono() {
        return txtTelefono.getText().trim();
    }

    public void actualizarTabla(List<Funcionario> lista) {
        modelo.setRowCount(0);
        for (Funcionario f : lista) {
            modelo.addRow(new Object[]{f.getId(), f.getNombre(), f.getTelefono()});
        }
    }

    public void cargarFormulario(String id, String nombre, String telefono) {
        txtId.setText(id);
        txtNombre.setText(nombre);
        txtTelefono.setText(telefono);
        txtId.setEnabled(false);
    }

    public void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtId.setEnabled(true);
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

        txtBusquedaId = new JTextField();
        txtBusquedaNombre = new JTextField();
        txtId = new JTextField();
        txtNombre = new JTextField();
        txtTelefono = new JTextField();
        btnBuscar = new JButton("Buscar");
        btnGuardar = new JButton("Guardar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnPdf = new JButton("PDF");

        panelNorte = new JPanel(new GridLayout(2, 1, 6, 6));
        panelBusqueda = new JPanel(new GridLayout(1, 5, 6, 6));
        panelBusqueda.add(new JLabel("Id:"));
        panelBusqueda.add(txtBusquedaId);
        panelBusqueda.add(new JLabel("Nombre:"));
        panelBusqueda.add(txtBusquedaNombre);
        panelBusqueda.add(btnBuscar);

        panelForm = new JPanel(new GridLayout(1, 6, 6, 6));
        panelForm.add(new JLabel("Id:"));
        panelForm.add(txtId);
        panelForm.add(new JLabel("Nombre:"));
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Telefono:"));
        panelForm.add(txtTelefono);
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
