package cr.ac.una.reservas.presentation.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class LoginView extends JFrame {
    private JPanel contentPane;
    private JLabel lblId;
    private JLabel lblClave;
    private JTextField txtId;
    private JPasswordField txtClave;
    private JButton btnIngresar;

    public LoginView() {
        $$$setupUI$$$();
        setContentPane(contentPane);
        setTitle("Ingreso al sistema");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 200);
        setLocationRelativeTo(null);
    }

    public String getTxtId() {
        return txtId.getText().trim();
    }

    public String getTxtClave() {
        return new String(txtClave.getPassword());
    }

    public JButton getBtnIngresar() {
        return btnIngresar;
    }

    /**
     * Metodo del GUI Designer. Si edita el .form, IntelliJ puede regenerarlo.
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel(new BorderLayout(10, 10));
        JPanel form = new JPanel(new GridLayout(2, 2, 8, 8));
        lblId = new JLabel("Id:");
        txtId = new JTextField();
        lblClave = new JLabel("Clave:");
        txtClave = new JPasswordField();
        form.add(lblId);
        form.add(txtId);
        form.add(lblClave);
        form.add(txtClave);
        btnIngresar = new JButton("Ingresar");
        JPanel sur = new JPanel();
        sur.add(btnIngresar);
        contentPane.add(form, BorderLayout.CENTER);
        contentPane.add(sur, BorderLayout.SOUTH);
    }
}
