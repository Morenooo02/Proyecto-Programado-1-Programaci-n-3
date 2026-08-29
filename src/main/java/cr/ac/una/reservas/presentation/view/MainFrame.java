package cr.ac.una.reservas.presentation.view;

import cr.ac.una.reservas.logic.Rol;
import cr.ac.una.reservas.logic.Usuario;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;

public class MainFrame extends JFrame {
    private JPanel contentPane;
    private JTabbedPane tabs;
    private Usuario usuarioAutenticado;
    private JMenuItem itemCambiarClave;

    private ReservasView reservasView;
    private FuncionariosView funcionariosView;
    private CategoriasView categoriasView;
    private RecursosView recursosView;
    private CalendarizacionView calendarizacionView;
    private ActividadesView actividadesView;
    private EstadisticasView estadisticasView;

    public MainFrame(Usuario usuarioAutenticado) {
        this.usuarioAutenticado = usuarioAutenticado;
        setTitle("Sistema de Reserva de Recursos - " + usuarioAutenticado.getId());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        inicializarComponentes();
        mostrarTabsSegunRol(usuarioAutenticado.getRol());
    }

    public void inicializarComponentes() {
        $$$setupUI$$$();
        setContentPane(contentPane);

        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Cuenta");
        itemCambiarClave = new JMenuItem("Cambiar clave");
        menu.add(itemCambiarClave);
        bar.add(menu);
        setJMenuBar(bar);

        reservasView = new ReservasView();
        funcionariosView = new FuncionariosView();
        categoriasView = new CategoriasView();
        recursosView = new RecursosView();
        calendarizacionView = new CalendarizacionView();
        actividadesView = new ActividadesView();
        estadisticasView = new EstadisticasView();
    }

    public void mostrarTabsSegunRol(Rol rol) {
        tabs.removeAll();
        if (rol == Rol.FUNCIONARIO) {
            tabs.addTab("Reservas", reservasView);
        }
        if (rol == Rol.ADMINISTRADOR) {
            tabs.addTab("Funcionarios", funcionariosView);
            tabs.addTab("Categorias", categoriasView);
            tabs.addTab("Recursos", recursosView);
        }
        tabs.addTab("Calendarizacion", calendarizacionView);
        tabs.addTab("Actividades", actividadesView);
        tabs.addTab("Estadisticas", estadisticasView);
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public JMenuItem getItemCambiarClave() {
        return itemCambiarClave;
    }

    public ReservasView getReservasView() {
        return reservasView;
    }

    public FuncionariosView getFuncionariosView() {
        return funcionariosView;
    }

    public CategoriasView getCategoriasView() {
        return categoriasView;
    }

    public RecursosView getRecursosView() {
        return recursosView;
    }

    public CalendarizacionView getCalendarizacionView() {
        return calendarizacionView;
    }

    public ActividadesView getActividadesView() {
        return actividadesView;
    }

    public EstadisticasView getEstadisticasView() {
        return estadisticasView;
    }

    private void $$$setupUI$$$() {
        contentPane = new JPanel(new BorderLayout());
        tabs = new JTabbedPane();
        contentPane.add(tabs, BorderLayout.CENTER);
    }
}
