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
import java.awt.GridLayout;
import java.util.List;

public class RecursosView extends JPanel {
    private JPanel panelNorte;
    private JPanel panelFiltro;
    private JPanel panelForm;
    private JPanel panelBotones;
    private JComboBox<Categoria> cmbFiltro;
    private JComboBox<Categoria> cmbCategoria;
    private JTextField txtId;
    private JTextField txtDescripcion;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnFiltrar;
    private JButton btnGuardar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnPdf;

    public RecursosView() {
        $$$setupUI$$$();
        cargarCategorias();
    }

    private void createUIComponents() {
        modelo = new DefaultTableModel(new Object[]{"Id", "Descripcion", "Categoria"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
    }

    public void cargarCategorias() {
        cmbFiltro.removeAllItems();
        cmbCategoria.removeAllItems();
        Categoria todas = new Categoria("", "Todas");
        cmbFiltro.addItem(todas);
        for (Categoria c : ServiceModel.getInstance().buscarCategorias("")) {
            cmbFiltro.addItem(c);
            cmbCategoria.addItem(c);
        }
    }

    public String getCategoriaFiltro() {
        Categoria c = (Categoria) cmbFiltro.getSelectedItem();
        return c == null ? "" : c.getId();
    }

    public Categoria getCategoriaSeleccionadaForm() {
        return (Categoria) cmbCategoria.getSelectedItem();
    }

    public String getTxtId() {
        return txtId.getText().trim();
    }

    public String getTxtDescripcion() {
        return txtDescripcion.getText().trim();
    }

    public void actualizarTabla(List<Recurso> lista) {
        modelo.setRowCount(0);
        for (Recurso r : lista) {
            String cat = r.getCategoria() == null ? "" : r.getCategoria().getDescripcion();
            modelo.addRow(new Object[]{r.getId(), r.getDescripcion(), cat});
        }
    }

    public void cargarFormulario(String id, String descripcion, String categoriaId) {
        txtId.setText(id);
        txtDescripcion.setText(descripcion);
        txtId.setEnabled(false);
        for (int i = 0; i < cmbCategoria.getItemCount(); i++) {
            if (cmbCategoria.getItemAt(i).getId().equals(categoriaId)) {
                cmbCategoria.setSelectedIndex(i);
                break;
            }
        }
    }

    public void limpiar() {
        txtId.setText("");
        txtDescripcion.setText("");
        txtId.setEnabled(true);
        tabla.clearSelection();
    }

    public JTable getTabla() {
        return tabla;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JButton getBtnFiltrar() {
        return btnFiltrar;
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

        cmbFiltro = new JComboBox<>();
        cmbCategoria = new JComboBox<>();
        txtId = new JTextField();
        txtDescripcion = new JTextField();
        btnFiltrar = new JButton("Filtrar");
        btnGuardar = new JButton("Guardar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnPdf = new JButton("PDF");

        panelNorte = new JPanel(new GridLayout(2, 1, 6, 6));
        panelFiltro = new JPanel(new BorderLayout(6, 6));
        panelFiltro.add(new JLabel("Categoria:"), BorderLayout.WEST);
        panelFiltro.add(cmbFiltro, BorderLayout.CENTER);
        panelFiltro.add(btnFiltrar, BorderLayout.EAST);

        panelForm = new JPanel(new GridLayout(1, 6, 6, 6));
        panelForm.add(new JLabel("Id/Activo:"));
        panelForm.add(txtId);
        panelForm.add(new JLabel("Descripcion:"));
        panelForm.add(txtDescripcion);
        panelForm.add(new JLabel("Categoria:"));
        panelForm.add(cmbCategoria);
        panelNorte.add(panelFiltro);
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
