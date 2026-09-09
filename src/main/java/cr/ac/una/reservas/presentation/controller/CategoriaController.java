package cr.ac.una.reservas.presentation.controller;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import cr.ac.una.reservas.logic.Categoria;
import cr.ac.una.reservas.logic.ServiceModel;
import cr.ac.una.reservas.presentation.view.VistaUtil;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess", "FieldCanBeLocal"})
public class CategoriaController extends JPanel {
    public JPanel rootPanel;
    public JPanel panelNorte;
    public JPanel panelBusqueda;
    public JPanel panelForm;
    public JPanel panelBotones;
    public JTextField txtBusqueda;
    public JTextField txtId;
    public JTextField txtDescripcion;
    public JTable tabla;
    private DefaultTableModel modelo;
    public JButton btnBuscar;
    public JButton btnGuardar;
    public JButton btnEliminar;
    public JButton btnLimpiar;
    public JButton btnPdf;
    private ServiceModel service;
    private final PropertyChangeListener modeloListener = this::onModeloCambio;



    public CategoriaController() {
        $$$setupUI$$$();
        service = ServiceModel.getInstance();
        setLayout(new BorderLayout());
        add(rootPanel, BorderLayout.CENTER);
        btnBuscar.addActionListener(e -> actualizarTabla(service.buscarCategorias(txtBusqueda.getText().trim())));
        btnGuardar.addActionListener(e -> guardarFormulario());
        btnEliminar.addActionListener(e -> eliminarSeleccionado());
        btnLimpiar.addActionListener(e -> limpiar());
        btnPdf.addActionListener(e -> generarPdf());
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarSeleccion();
            }
        });
        service.addPropertyChangeListener(modeloListener);
        actualizarTabla(service.buscarCategorias(""));
    }

    private void onModeloCambio(PropertyChangeEvent evt) {
        if ("categorias".equals(evt.getPropertyName())) {
            actualizarTabla(service.buscarCategorias(txtBusqueda.getText().trim()));
        }
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        service.removePropertyChangeListener(modeloListener);
    }

    private void guardarFormulario() {
        if (txtDescripcion.getText().isBlank()) {
            VistaUtil.error(this, "La descripcion es obligatoria.");
            return;
        }
        String id = txtId.getText().trim();
        Categoria c = new Categoria(id.isBlank() ? null : id, txtDescripcion.getText().trim());
        boolean ok = (c.getId() == null || c.getId().isBlank()) ? service.agregarCategoria(c) : service.modificarCategoria(c);
        if (ok) {
            actualizarTabla(service.buscarCategorias(""));
            limpiar();
            VistaUtil.mensaje(this, "Categoria guardada.");
        } else {
            VistaUtil.error(this, "No se pudo guardar la categoria.");
        }
    }

    private void eliminarSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            VistaUtil.error(this, "Seleccione una categoria.");
            return;
        }
        String id = String.valueOf(modelo.getValueAt(fila, 0));
        if (service.eliminarCategoria(id)) {
            actualizarTabla(service.buscarCategorias(""));
            limpiar();
            VistaUtil.mensaje(this, "Categoria eliminada.");
        } else {
            VistaUtil.error(this, "No se puede eliminar porque tiene recursos asociados.");
        }
    }

    private void generarPdf() {
        String elegido = VistaUtil.elegirRutaPdf(this, "categorias.pdf");
        if (elegido == null) {
            return;
        }
        service.exportarPDF("Categorias", VistaUtil.columnas(modelo), VistaUtil.tablaADatos(modelo), elegido);
        VistaUtil.mensaje(this, "PDF generado.");
    }

    private void actualizarTabla(List<Categoria> lista) {
        modelo.setRowCount(0);
        for (Categoria c : lista) {
            modelo.addRow(new Object[]{c.getId(), c.getDescripcion()});
        }
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtId.setText(String.valueOf(modelo.getValueAt(fila, 0)));
            txtDescripcion.setText(String.valueOf(modelo.getValueAt(fila, 1)));
        }
    }

    private void limpiar() {
        txtId.setText("");
        txtDescripcion.setText("");
        tabla.clearSelection();
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


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(3, 1, new Insets(8, 8, 8, 8), -1, -1));
        panelNorte = new JPanel();
        panelNorte.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(panelNorte, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panelNorte.add(panelBusqueda, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Descripcion:");
        panelBusqueda.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtBusqueda = new JTextField();
        panelBusqueda.add(txtBusqueda, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnBuscar = new JButton();
        btnBuscar.setText("Buscar");
        panelBusqueda.add(btnBuscar, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelForm = new JPanel();
        panelForm.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panelNorte.add(panelForm, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Id:");
        panelForm.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtId = new JTextField();
        txtId.setEnabled(false);
        panelForm.add(txtId, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Descripcion:");
        panelForm.add(label3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtDescripcion = new JTextField();
        panelForm.add(txtDescripcion, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        rootPanel.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(tabla);
        panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(panelBotones, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnGuardar = new JButton();
        btnGuardar.setText("Guardar");
        panelBotones.add(btnGuardar, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnEliminar = new JButton();
        btnEliminar.setText("Eliminar");
        panelBotones.add(btnEliminar, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnLimpiar = new JButton();
        btnLimpiar.setText("Limpiar");
        panelBotones.add(btnLimpiar, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnPdf = new JButton();
        btnPdf.setText("PDF");
        panelBotones.add(btnPdf, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

}
