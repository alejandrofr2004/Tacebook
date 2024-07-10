/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.InitMenuController;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Encárgase de mostrar as opcións do menú inicial e recoller os datos de
 * entrada
 *
 * @author alejandro.fernandezregueiro
 */
public class textInitMenuView implements InitMenuView {

    /**
     * Controlador asociado a vista
     */
    private InitMenuController initMenuController;

    /**
     * Constructor da clase InitMenuView
     *
     * @param initMenuController
     */
    public textInitMenuView(InitMenuController initMenuController) {
        this.initMenuController = initMenuController;
    }

    /**
     * Mostra o menú de inicio de sesión
     *
     * @return true se o usuario quere saír, false en caso contrario
     */
    public boolean showLoginMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------");
        System.out.println("|TACEBOOK|");
        System.out.println("----------");
        System.out.println(" ");
        System.out.println("Escolle unha opción:");
        System.out.println("1. Iniciar sesión: ");
        System.out.println("2. Crear un novo perfil: ");
        System.out.println("3. Saír da aplicación:");
        int leer = readNumber();

        switch (leer) {
            case 1:
                System.out.println("Introduce o nome: ");
                String name = scanner.nextLine();
                System.out.println("Introduce a contrasinal: ");
                String password = scanner.next();
                initMenuController.login(name, password);
                break;
            case 2:
                initMenuController.register();
                break;
            case 3:
                return true;
            default:
                System.out.println("Opción no válida, seleccione unha opción correcta");
        }
        return false;
    }

    /**
     * Mostra unha mensaxe de usuario e contrasinal incorrectos
     */
    @Override
    public void showLoginErrorMessage() {
        System.out.println("Usuario e contrasinal incorrectos");
    }

    /**
     * Mostra o menú para rexistrarse
     */
    public void showRegisterMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce o nome do usuario: ");
        String name = scanner.nextLine();
        String contrasinal;
        String contrasinal2;
        do {
            System.out.println("Introduce a contrasinal: ");
            contrasinal = scanner.nextLine();
            System.out.println("Introduce outra vez a contraseña: ");
            contrasinal2 = scanner.nextLine();
            if (!contrasinal.equals(contrasinal2)) {
                System.out.println("As contrasinais non coinciden, introduce outra vez as contrasinais");
                System.out.println(" ");
            }
        } while (!contrasinal.equals(contrasinal2));
        System.out.println("Introduce un estado: ");
        String status = scanner.nextLine();
        System.out.println(" ");
        initMenuController.createProfile(name, contrasinal, status);
    }

    /**
     * Mostra unha mensaxe indicando que o nome de usuario introducido xa estaba
     * en uso e pedido un novo nome para o usuario
     *
     * @return o novo nome
     */
    public String showNewNameMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("O nome xa está en uso");
        System.out.println("Introduce un nome");
        String nombre = scanner.nextLine();
        System.out.println(" ");
        return nombre;
    }

    /**
     * Este método lerá un dato por teclado usando o método "nextInt()" do
     * scanner recibido como parámetro, e se se produce a excepción
     * "NoSuchElementException" mostrará unha mensaxe indicando que se debe
     * introducir un número e volverá a chamarse a si mesmo para volver a pedir
     * o dato
     *
     * @return
     */
    private int readNumber() {
        int number = 0;
        boolean numberIsValid = false;
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                System.out.println("Introduce un número: ");
                number = scanner.nextInt();
                numberIsValid = true;
            } catch (NoSuchElementException e) {
                System.out.println("Tes que poñer un número");
                scanner.nextLine();
            }
        } while (!numberIsValid);

        scanner.nextLine();
        return number;
    }

    /**
     * Amosa a mensaxe de erro "Erro na conexión co almacén de datos!".
     */
    public void showConnectionErrorMessage() {
        System.out.println("Erro na conexión co almacén de datos!");
    }

    /**
     * Amosa a mensaxe de erro "Erro na lectura de datos!".
     */
    public void showReadErrorMessage() {
        System.out.println("Erro na lectura de datos!");
    }

    /**
     * Amosa a mensaxe de erro "Erro na escritura dos datos!".
     */
    public void showWriteErrorMessage() {
        System.out.println("Erro na escritura dos datos!");
    }

}
