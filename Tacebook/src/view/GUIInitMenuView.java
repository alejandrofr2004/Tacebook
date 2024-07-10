/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.InitMenuController;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import persistence.PersistenceException;
import persistence.TacebookDB;

/**
 * Encárgase de mostrar as opcións do menú inicial e recoller os datos de
 * entrada
 *
 * @author alejandro.fernandezregueiro
 */
public class GUIInitMenuView implements InitMenuView {

    /**
     * Atributo de referencia a clase InitMenuController
     */
    private InitMenuController initMenuController;

    /**
     * Constructor que inicializa o atributo initMenuController e colle como
     * LookAndFeel Nimbus
     *
     * @param initMenuController
     */
    public GUIInitMenuView(InitMenuController initMenuController) {
        this.initMenuController = initMenuController;

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIProfileView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para loguear a un usuario
     *
     * @return
     */
    @Override
    public boolean showLoginMenu() {
        Object[] options = new Object[]{"Saír", "Rexistrarse", "Iniciar sesión"};
        GridLayout gridLayout = new GridLayout(2, 2);
        JPanel jPanel = new JPanel(gridLayout);

        JLabel JL_name = new JLabel("Nome de usuario:");
        JL_name.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JLabel JL_contrasinal = new JLabel("Contrasinal:");
        JL_contrasinal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        JTextField user = new JTextField();
        JPasswordField contrasinal = new JPasswordField();
        jPanel.add(JL_name);
        jPanel.add(user);
        jPanel.add(JL_contrasinal);
        jPanel.add(contrasinal);
        String title = "Entrar en tacebook";
        int result = JOptionPane.showOptionDialog(null, jPanel, title,
                0, 3, null, options, this);
        switch (result) {
            case 0:
                try {
                if (TacebookDB.getConnection() != null) {
                    TacebookDB.close();
                }
            } catch (PersistenceException ex) {
                Logger.getLogger(GUIInitMenuView.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);

            case 1:
                initMenuController.register();
                break;
            case 2:
                String name = user.getText();
                String password = new String(contrasinal.getPassword());
                initMenuController.login(name, password);
                break;
        }
        return false;
    }

    /**
     *
     */
    @Override
    public void showLoginErrorMessage() {
        Object[] options = new Object[]{"Aceptar"};
        JOptionPane.showOptionDialog(null, "Nome de usuario e contrasinal incorrectos", "Erro nos datos", 0, 2, null, options, 0);

        showLoginMenu();
    }

    /**
     * Mensaje de error en la conexión
     */
    @Override
    public void showConnectionErrorMessage() {
        Object[] options = new Object[]{"Aceptar"};
        JOptionPane.showOptionDialog(null, "Erro na conexión co almacén de datos!", "Erro nos datos", 0, 2, null, options, 0);

    }

    /**
     * Mensaje de error en la lectura de datos
     */
    @Override
    public void showReadErrorMessage() {
        Object[] options = new Object[]{"Aceptar"};
        JOptionPane.showOptionDialog(null, "Erro na lectura de datos!", "Erro nos datos", 0, 2, null, options, 0);

    }

    /**
     * Mensaje de error en la escritura de datos
     */
    @Override
    public void showWriteErrorMessage() {
        Object[] options = new Object[]{"Aceptar"};
        JOptionPane.showOptionDialog(null, "Erro na escritura dos datos!", "Erro nos datos", 0, 2, null, options, 0);

    }

    /**
     * Método para cambiar o nome de usuario cando é igual ao nome doutro perfil
     * xa creado
     *
     * @return novo nome
     */
    @Override
    public String showNewNameMenu() {
        String name;
        do {
            name = JOptionPane.showInputDialog(null, "O nome de"
                    + " usuario xa está collido, debes introducir outro",
                    "Usuario xa existente",
                    1);
            if (name != null) {
                JOptionPane.showMessageDialog(null, "Debes introducir un nome");
            }
        } while (name != null);
        if (name == null) {
            showRegisterMenu();
            return null;
        }
        return name;
    }

    /**
     * Método para registrarse
     */
    @Override
    public void showRegisterMenu() {
        Object[] options = new Object[]{"Aceptar", "Cancelar"};
        GridLayout gridLayout = new GridLayout(4, 2);
        JPanel jPanel = new JPanel(gridLayout);
        JLabel JL_name = new JLabel("Nome de usuario:");
        JL_name.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JLabel JL_contrasinal = new JLabel("Contrasinal:");
        JL_contrasinal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JLabel JL_contrasinalRepeat = new JLabel("Repite o contrasinal:");
        JL_contrasinalRepeat.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JLabel JL_state = new JLabel("Estado:");
        JL_state.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JTextField jTextField_name = new JTextField();
        JPasswordField jPasswordField_password = new JPasswordField();
        JPasswordField password_2 = new JPasswordField();
        JTextField jTextField_state = new JTextField();
        jPanel.add(JL_name);
        jPanel.add(jTextField_name);
        jPanel.add(JL_contrasinal);
        jPanel.add(jPasswordField_password);
        jPanel.add(JL_contrasinalRepeat);
        jPanel.add(password_2);
        jPanel.add(JL_state);
        jPanel.add(jTextField_state);
        String title = "Rexistrarse";
        int type = JOptionPane.PLAIN_MESSAGE;

        String contrasinal1 = new String(jPasswordField_password.getPassword());
        String contrasinal2 = null;
        String name = null;
        int result = JOptionPane.showOptionDialog(null, jPanel, title,
                0, type, null, options, this);
        if (result == 0) {
            do {
                name = jTextField_name.getText();
                contrasinal1 = new String(jPasswordField_password.getPassword());
                contrasinal2 = new String(password_2.getPassword());
                if (!contrasinal1.equals(contrasinal2)) {
                    JOptionPane.showMessageDialog(null,
                            "As contrasinais non coinciden, introduce outra vez a contrasinal");
                    result = JOptionPane.showOptionDialog(null, jPanel, title,
                            0, type, null, options, this);
                    if (result == 0) {
                        name = jTextField_name.getText();
                        contrasinal1 = new String(jPasswordField_password.getPassword());
                        contrasinal2 = new String(password_2.getPassword());
                    }
                } else if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Debes introducir un nome");
                    result = JOptionPane.showOptionDialog(null, jPanel, title,
                            0, type, null, options, this);
                    if (result == 0) {
                        name = jTextField_name.getText();
                        contrasinal1 = new String(jPasswordField_password.getPassword());
                        contrasinal2 = new String(password_2.getPassword());
                    }
                }
            } while (!contrasinal1.equals(contrasinal2) || name.isEmpty());
            initMenuController.createProfile(jTextField_name.getText(), contrasinal1, jTextField_state.getText());
        } else {
            showLoginMenu();
        }
    }
}
