package cr.ac.una.reservas.presentation.controller;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import cr.ac.una.reservas.logic.ServiceModel;
import cr.ac.una.reservas.presentation.view.VistaUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused", "WeakerAccess", "FieldCanBeLocal"})
public class EstadisticasController extends JPanel {
    public JPanel rootPanel;
    public JPanel panelRecursos;
    public JPanel panelActividades;
    public JPanel panelFiltroRecursos;
    public JPanel panelFiltroActividades;
    public JPanel panelCentroRecursos;
    public JPanel panelCentroActividades;
    public JPanel panelPdf;
    public JTextField txtDesdeRecursos;
    public JTextField txtHastaRecursos;
    public JTextField txtDesdeActividades;
    public JTextField txtHastaActividades;
    private DefaultTableModel modeloRecursos;
    private DefaultTableModel modeloActividades;
    public JTable tablaRecursos;
    public JTable tablaActividades;
    public JPanel panelGraficoRecursos;
    public JPanel panelGraficoActividades;
    public JButton btnRecursos;
    public JButton btnActividades;
    public JButton btnPdf;
    private ServiceModel service;

    public EstadisticasController() {
        $$$setupUI$$$();
        service = ServiceModel.getInstance();
        setLayout(new BorderLayout());
        add(rootPanel, BorderLayout.CENTER);
        btnRecursos.addActionListener(e -> cargarRecursos());
        btnActividades.addActionListener(e -> cargarActividades());
        btnPdf.addActionListener(e -> generarPdf());
    }

    private void cargarRecursos() {
        try {
            LocalDate desde = LocalDate.parse(txtDesdeRecursos.getText().trim());
            LocalDate hasta = LocalDate.parse(txtHastaRecursos.getText().trim());
            if (desde.isAfter(hasta)) {
                VistaUtil.error(this, "El rango de recursos es invalido.");
                return;
            }
            llenarTablaYGrafico(modeloRecursos, panelGraficoRecursos,
                    service.obtenerEstadisticasRecursos(desde, hasta), "Recursos");
        } catch (Exception ex) {
            VistaUtil.error(this, "Fechas invalidas.");
        }
    }

    private void cargarActividades() {
        try {
            LocalDate desde = LocalDate.parse(txtDesdeActividades.getText().trim());
            LocalDate hasta = LocalDate.parse(txtHastaActividades.getText().trim());
            if (desde.isAfter(hasta)) {
                VistaUtil.error(this, "El rango de actividades es invalido.");
                return;
            }
            llenarTablaYGrafico(modeloActividades, panelGraficoActividades,
                    service.obtenerEstadisticasActividades(desde, hasta), "Actividades");
        } catch (Exception ex) {
            VistaUtil.error(this, "Fechas invalidas.");
        }
    }

    private void generarPdf() {
        String elegido = VistaUtil.elegirRutaPdf(this, "estadisticas.pdf");
        if (elegido == null) {
            return;
        }
        List<Object[]> filas = new ArrayList<>();
        for (int i = 0; i < modeloRecursos.getRowCount(); i++) {
            filas.add(new Object[]{modeloRecursos.getValueAt(i, 0), modeloRecursos.getValueAt(i, 1)});
        }
        for (int i = 0; i < modeloActividades.getRowCount(); i++) {
            filas.add(new Object[]{modeloActividades.getValueAt(i, 0), modeloActividades.getValueAt(i, 1)});
        }
        service.exportarPDF("Estadisticas", new String[]{"Descripcion", "Cantidad"},
                filas.toArray(new Object[0][0]), elegido);
        VistaUtil.mensaje(this, "PDF generado.");
    }

    private void llenarTablaYGrafico(DefaultTableModel modelo, JPanel panel, Map<String, Integer> datos, String titulo) {
        modelo.setRowCount(0);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (datos != null) {
            for (Map.Entry<String, Integer> e : datos.entrySet()) {
                modelo.addRow(new Object[]{e.getKey(), e.getValue()});
                dataset.addValue(e.getValue(), titulo, e.getKey());
            }
        }
        JFreeChart chart = ChartFactory.createBarChart(titulo, "", "Cantidad", dataset);
        panel.removeAll();
        panel.add(new ChartPanel(chart), BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }

    private void createUIComponents() {
        modeloRecursos = new DefaultTableModel(new Object[]{"Categoria", "Cantidad"}, 0);
        modeloActividades = new DefaultTableModel(new Object[]{"Semana", "Cantidad"}, 0);
        tablaRecursos = new JTable(modeloRecursos);
        tablaActividades = new JTable(modeloActividades);
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
        rootPanel.setLayout(new GridLayoutManager(1, 2, new Insets(8, 8, 8, 8), -1, -1, true, false));
        panelRecursos = new JPanel();
        panelRecursos.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(panelRecursos, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelFiltroRecursos = new JPanel();
        panelFiltroRecursos.setLayout(new GridLayoutManager(1, 6, new Insets(0, 0, 0, 0), -1, -1));
        panelRecursos.add(panelFiltroRecursos, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Recursos reservados");
        panelFiltroRecursos.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Desde:");
        panelFiltroRecursos.add(label2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtDesdeRecursos = new JTextField();
        panelFiltroRecursos.add(txtDesdeRecursos, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Hasta:");
        panelFiltroRecursos.add(label3, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtHastaRecursos = new JTextField();
        panelFiltroRecursos.add(txtHastaRecursos, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnRecursos = new JButton();
        btnRecursos.setText("Calcular recursos");
        panelFiltroRecursos.add(btnRecursos, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelCentroRecursos = new JPanel();
        panelCentroRecursos.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelRecursos.add(panelCentroRecursos, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panelCentroRecursos.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(tablaRecursos);
        panelGraficoRecursos = new JPanel();
        panelGraficoRecursos.setLayout(new BorderLayout(0, 0));
        panelCentroRecursos.add(panelGraficoRecursos, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelActividades = new JPanel();
        panelActividades.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(panelActividades, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelFiltroActividades = new JPanel();
        panelFiltroActividades.setLayout(new GridLayoutManager(1, 6, new Insets(0, 0, 0, 0), -1, -1));
        panelActividades.add(panelFiltroActividades, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Actividades calendarizadas");
        panelFiltroActividades.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Desde:");
        panelFiltroActividades.add(label5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtDesdeActividades = new JTextField();
        panelFiltroActividades.add(txtDesdeActividades, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Hasta:");
        panelFiltroActividades.add(label6, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtHastaActividades = new JTextField();
        panelFiltroActividades.add(txtHastaActividades, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnActividades = new JButton();
        btnActividades.setText("Calcular actividades");
        panelFiltroActividades.add(btnActividades, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelCentroActividades = new JPanel();
        panelCentroActividades.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelActividades.add(panelCentroActividades, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panelCentroActividades.add(scrollPane2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane2.setViewportView(tablaActividades);
        panelGraficoActividades = new JPanel();
        panelGraficoActividades.setLayout(new BorderLayout(0, 0));
        panelCentroActividades.add(panelGraficoActividades, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelPdf = new JPanel();
        panelPdf.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelActividades.add(panelPdf, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnPdf = new JButton();
        btnPdf.setText("PDF");
        panelPdf.add(btnPdf, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

}
