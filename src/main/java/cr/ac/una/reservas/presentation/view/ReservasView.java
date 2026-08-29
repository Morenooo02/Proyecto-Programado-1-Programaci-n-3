package cr.ac.una.reservas.presentation.view;

import cr.ac.una.reservas.logic.Categoria;
import cr.ac.una.reservas.logic.Reserva;
import cr.ac.una.reservas.logic.ReservaDTO;
import cr.ac.una.reservas.logic.ServiceModel;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservasView extends JPanel {
    private JPanel panelNorte;
    private JPanel panelIA;
    private JPanel panelForm;
    private JPanel panelBotones;
    private JLabel lblFrase;
    private JTextArea txtFrase;
    private JTextField txtActividad;
    private JTextField txtFecha;
    private JTextField txtHoraInicio;
    private JTextField txtHoraFin;
    private JPanel panelCategorias;
    private List<JCheckBox> checksCategorias = new ArrayList<>();
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnUsarIA;
    private JButton btnAplicar;
    private JButton btnCancelar;
    private JButton btnPdf;

    public ReservasView() {
        $$$setupUI$$$();
        cargarChecksCategorias();
    }

    private void createUIComponents() {
        modelo = new DefaultTableModel(new Object[]{"Id", "Actividad", "Fecha", "Inicio", "Fin", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
    }

    public void cargarChecksCategorias() {
        panelCategorias.removeAll();
        checksCategorias.clear();
        for (Categoria c : ServiceModel.getInstance().buscarCategorias("")) {
            JCheckBox cb = new JCheckBox(c.getDescripcion());
            cb.putClientProperty("catId", c.getId());
            checksCategorias.add(cb);
            panelCategorias.add(cb);
        }
        panelCategorias.revalidate();
        panelCategorias.repaint();
    }

    public String getTxtFrase() {
        return txtFrase.getText();
    }

    public String getTxtActividad() {
        return txtActividad.getText().trim();
    }

    public String getTxtFecha() {
        return txtFecha.getText().trim();
    }

    public String getTxtHoraInicio() {
        return txtHoraInicio.getText().trim();
    }

    public String getTxtHoraFin() {
        return txtHoraFin.getText().trim();
    }

    public List<String> getCategoriasSeleccionadas() {
        List<String> ids = new ArrayList<>();
        for (JCheckBox cb : checksCategorias) {
            if (cb.isSelected()) {
                ids.add((String) cb.getClientProperty("catId"));
            }
        }
        return ids;
    }

    public void LlenarFormularioConDTO(ReservaDTO dto) {
        if (dto.getActividad() != null) {
            txtActividad.setText(dto.getActividad());
        }
        if (dto.getFecha() != null) {
            txtFecha.setText(dto.getFecha().toString());
        }
        if (dto.getHoraInicio() != null) {
            txtHoraInicio.setText(dto.getHoraInicio().toString());
        }
        if (dto.getHoraFin() != null) {
            txtHoraFin.setText(dto.getHoraFin().toString());
        }
        for (JCheckBox cb : checksCategorias) {
            String id = (String) cb.getClientProperty("catId");
            cb.setSelected(dto.getCategoriasExtraidas() != null && dto.getCategoriasExtraidas().contains(id));
        }
    }

    public void actualizarTablaMisReservas(List<Reserva> reservas) {
        modelo.setRowCount(0);
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");
        for (Reserva r : reservas) {
            modelo.addRow(new Object[]{
                    r.getId(),
                    r.getActividad(),
                    r.getFecha(),
                    r.getHoraInicio() == null ? "" : r.getHoraInicio().format(tf),
                    r.getHoraFin() == null ? "" : r.getHoraFin().format(tf),
                    r.getEstado()
            });
        }
    }

    public JTable getTabla() {
        return tabla;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JButton getBtnUsarIA() {
        return btnUsarIA;
    }

    public JButton getBtnAplicar() {
        return btnAplicar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JButton getBtnPdf() {
        return btnPdf;
    }

    private void $$$setupUI$$$() {
        createUIComponents();
        setLayout(new BorderLayout(8, 8));
        setBorder(new EmptyBorder(8, 8, 8, 8));

        panelNorte = new JPanel(new BorderLayout(6, 6));
        panelIA = new JPanel(new BorderLayout(6, 6));
        lblFrase = new JLabel("Frase (IA):");
        txtFrase = new JTextArea(3, 40);
        txtFrase.setLineWrap(true);
        btnUsarIA = new JButton("Usar IA");
        panelIA.add(lblFrase, BorderLayout.NORTH);
        panelIA.add(new JScrollPane(txtFrase), BorderLayout.CENTER);
        panelIA.add(btnUsarIA, BorderLayout.EAST);

        panelForm = new JPanel(new GridLayout(4, 2, 6, 6));
        txtActividad = new JTextField();
        txtFecha = new JTextField();
        txtHoraInicio = new JTextField();
        txtHoraFin = new JTextField();
        panelForm.add(new JLabel("Actividad:"));
        panelForm.add(txtActividad);
        panelForm.add(new JLabel("Fecha (yyyy-MM-dd):"));
        panelForm.add(txtFecha);
        panelForm.add(new JLabel("Hora inicio (HH:mm):"));
        panelForm.add(txtHoraInicio);
        panelForm.add(new JLabel("Hora fin (HH:mm):"));
        panelForm.add(txtHoraFin);

        panelCategorias = new JPanel(new GridLayout(0, 2, 4, 4));
        panelNorte.add(panelIA, BorderLayout.NORTH);
        panelNorte.add(panelForm, BorderLayout.CENTER);
        panelNorte.add(new JScrollPane(panelCategorias), BorderLayout.SOUTH);

        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAplicar = new JButton("Aplicar reserva");
        btnCancelar = new JButton("Cancelar reserva");
        btnPdf = new JButton("PDF");
        panelBotones.add(btnAplicar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnPdf);

        add(panelNorte, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
}
