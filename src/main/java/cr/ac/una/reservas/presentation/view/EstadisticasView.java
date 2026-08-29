package cr.ac.una.reservas.presentation.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

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
import java.time.LocalDate;
import java.util.Map;

public class EstadisticasView extends JPanel {
    private JPanel panelRecursos;
    private JPanel panelActividades;
    private JPanel panelFiltroRecursos;
    private JPanel panelFiltroActividades;
    private JPanel panelCentroRecursos;
    private JPanel panelCentroActividades;
    private JPanel panelPdf;
    private JTextField txtDesdeRecursos;
    private JTextField txtHastaRecursos;
    private JTextField txtDesdeActividades;
    private JTextField txtHastaActividades;
    private DefaultTableModel modeloRecursos;
    private DefaultTableModel modeloActividades;
    private JTable tablaRecursos;
    private JTable tablaActividades;
    private JPanel panelGraficoRecursos;
    private JPanel panelGraficoActividades;
    private JButton btnRecursos;
    private JButton btnActividades;
    private JButton btnPdf;

    public EstadisticasView() {
        $$$setupUI$$$();
    }

    private void createUIComponents() {
        modeloRecursos = new DefaultTableModel(new Object[]{"Categoria", "Cantidad"}, 0);
        modeloActividades = new DefaultTableModel(new Object[]{"Semana", "Cantidad"}, 0);
        tablaRecursos = new JTable(modeloRecursos);
        tablaActividades = new JTable(modeloActividades);
    }

    public LocalDate getDesdeRecursos() {
        return LocalDate.parse(txtDesdeRecursos.getText().trim());
    }

    public LocalDate getHastaRecursos() {
        return LocalDate.parse(txtHastaRecursos.getText().trim());
    }

    public LocalDate getDesdeActividades() {
        return LocalDate.parse(txtDesdeActividades.getText().trim());
    }

    public LocalDate getHastaActividades() {
        return LocalDate.parse(txtHastaActividades.getText().trim());
    }

    public void renderizarGraficoBarrasRecursos(Map<String, Integer> datos) {
        llenarTablaYGrafico(modeloRecursos, panelGraficoRecursos, datos, "Recursos");
    }

    public void renderizarGraficoBarrasActividades(Map<String, Integer> datos) {
        llenarTablaYGrafico(modeloActividades, panelGraficoActividades, datos, "Actividades");
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

    public DefaultTableModel getModeloRecursos() {
        return modeloRecursos;
    }

    public DefaultTableModel getModeloActividades() {
        return modeloActividades;
    }

    public JButton getBtnRecursos() {
        return btnRecursos;
    }

    public JButton getBtnActividades() {
        return btnActividades;
    }

    public JButton getBtnPdf() {
        return btnPdf;
    }

    private void $$$setupUI$$$() {
        createUIComponents();
        setLayout(new GridLayout(1, 2, 8, 8));
        setBorder(new EmptyBorder(8, 8, 8, 8));

        LocalDate hoy = LocalDate.now();
        txtDesdeRecursos = new JTextField(hoy.minusMonths(1).toString(), 10);
        txtHastaRecursos = new JTextField(hoy.toString(), 10);
        txtDesdeActividades = new JTextField(hoy.minusMonths(1).toString(), 10);
        txtHastaActividades = new JTextField(hoy.toString(), 10);
        btnRecursos = new JButton("Calcular recursos");
        btnActividades = new JButton("Calcular actividades");
        btnPdf = new JButton("PDF");
        panelGraficoRecursos = new JPanel(new BorderLayout());
        panelGraficoActividades = new JPanel(new BorderLayout());

        panelFiltroRecursos = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 6));
        panelFiltroRecursos.add(new JLabel("Recursos reservados"));
        panelFiltroRecursos.add(new JLabel("Desde:"));
        panelFiltroRecursos.add(txtDesdeRecursos);
        panelFiltroRecursos.add(new JLabel("Hasta:"));
        panelFiltroRecursos.add(txtHastaRecursos);
        panelFiltroRecursos.add(btnRecursos);

        panelCentroRecursos = new JPanel(new GridLayout(2, 1, 4, 4));
        panelCentroRecursos.add(new JScrollPane(tablaRecursos));
        panelCentroRecursos.add(panelGraficoRecursos);

        panelRecursos = new JPanel(new BorderLayout(6, 6));
        panelRecursos.add(panelFiltroRecursos, BorderLayout.NORTH);
        panelRecursos.add(panelCentroRecursos, BorderLayout.CENTER);

        panelFiltroActividades = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 6));
        panelFiltroActividades.add(new JLabel("Actividades calendarizadas"));
        panelFiltroActividades.add(new JLabel("Desde:"));
        panelFiltroActividades.add(txtDesdeActividades);
        panelFiltroActividades.add(new JLabel("Hasta:"));
        panelFiltroActividades.add(txtHastaActividades);
        panelFiltroActividades.add(btnActividades);

        panelCentroActividades = new JPanel(new GridLayout(2, 1, 4, 4));
        panelCentroActividades.add(new JScrollPane(tablaActividades));
        panelCentroActividades.add(panelGraficoActividades);

        panelPdf = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelPdf.add(btnPdf);

        panelActividades = new JPanel(new BorderLayout(6, 6));
        panelActividades.add(panelFiltroActividades, BorderLayout.NORTH);
        panelActividades.add(panelCentroActividades, BorderLayout.CENTER);
        panelActividades.add(panelPdf, BorderLayout.SOUTH);

        add(panelRecursos);
        add(panelActividades);
    }
}
