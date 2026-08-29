package cr.ac.una.reservas.presentation.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class CambiarClaveDialog extends JDialog {
    private JPanel contentPane;
    private JLabel lblActual;
    private JLabel lblNueva;
    private JPasswordField txtClaveActual;
    private JPasswordField txtClaveNueva;
    private JButton btnGuardar;

    public CambiarClaveDialog(JFrame owner) {
        super(owner, "Cambiar clave", true);
        $$$setupUI$$$();
        setContentPane(contentPane);
        setSize(420, 200);
        setLocationRelativeTo(owner);
    }

    public String getTxtClaveActual() {
        return new String(txtClaveActual.getPassword());
    }

    public String getTxtClaveNueva() {
        return new String(txtClaveNueva.getPassword());
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    private void $$$setupUI$$$() {
        contentPane = new JPanel(new BorderLayout(10, 10));
        JPanel form = new JPanel(new GridLayout(2, 2, 8, 8));
        lblActual = new JLabel("Clave actual:");
        txtClaveActual = new JPasswordField();
        lblNueva = new JLabel("Clave nueva:");
        txtClaveNueva = new JPasswordField();
        form.add(lblActual);
        form.add(txtClaveActual);
        form.add(lblNueva);
        form.add(txtClaveNueva);
        btnGuardar = new JButton("Guardar");
        JPanel sur = new JPanel();
        sur.add(btnGuardar);
        contentPane.add(form, BorderLayout.CENTER);
        contentPane.add(sur, BorderLayout.SOUTH);
    }
}
