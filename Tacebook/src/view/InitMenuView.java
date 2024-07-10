/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 * Interface que define os métodos relacionados coa vista inicial do menú.
 * @author alex f
 */
public interface InitMenuView {

    /**
     * Amosa o menú de inicio de sesión.
     *
     * @return True se o usuario inicia sesión correctamente, False en caso contrario.
     */
    public boolean showLoginMenu();

    /**
     * Amosa a mensaxe de erro de inicio de sesión.
     */
    public void showLoginErrorMessage();

    /**
     * Amosa a mensaxe de erro na conexión co almacén de datos.
     */
    public void showConnectionErrorMessage();

    /**
     * Amosa a mensaxe de erro na lectura de datos.
     */
    public void showReadErrorMessage();

    /**
     * Amosa a mensaxe de erro na escritura dos datos.
     */
    public void showWriteErrorMessage();

    /**
     * Amosa o menú para introducir un novo nome.
     *
     * @return O novo nome introducido polo usuario.
     */
    public String showNewNameMenu();

    /**
     * Amosa o menú de rexistro de usuario.
     */
    public void showRegisterMenu();
}