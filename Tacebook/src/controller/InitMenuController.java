/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Profile;
import persistence.PersistenceException;
import view.textInitMenuView;
import persistence.ProfileDB;
import persistence.TacebookDB;
import view.GUIInitMenuView;
import view.InitMenuView;

/**
 * Contén o main da aplicación
 *
 * @author alejandro.fernandezregueiro
 */
public class InitMenuController {

    /**
     * Atributo que fai referencia a clase InitMenuView
     */
    private InitMenuView initMenuView;
    /**
     * Atribyuto booleano para saber si estas en modo texto
     */
    private boolean textMode;

    /**
     * Constructor da clase InitMenuController
     *
     * @param textMode
     */
    public InitMenuController(boolean textMode) {
        this.textMode = textMode;
        if (textMode) {
            this.initMenuView = new textInitMenuView(this);
        } else {
            this.initMenuView = new GUIInitMenuView(this);
        }
    }

    /**
     * Inicia a aplicación
     */
    private void init() {
        while (!initMenuView.showLoginMenu());
    }

    /**
     * Intenta iniciar sesión na aplicación cun usuario e contrasinal
     *
     * @param name
     * @param password
     */
    public void login(String name, String password) {
        Profile profile = null;
        try {
            profile = ProfileDB.findByNameAndPassword(name, password, 10);
        } catch (PersistenceException ex) {
            processPersistenceException(ex);
        }

        if (profile != null) {
            ProfileController profileController = new ProfileController(profile, textMode);
            profileController.openSession(profile);
        } else {
            initMenuView.showLoginErrorMessage();
        }
    }

    /**
     * Registra os perfis
     */
    public void register() {
        initMenuView.showRegisterMenu();
    }

    /**
     * Crea un novo perfil
     *
     * @param name
     * @param password
     * @param status
     */
    public void createProfile(String name, String password, String status) {
        String newName = null;
        try {
            if (ProfileDB.findByName(name, 0) != null) {
                do {
                    newName = initMenuView.showNewNameMenu();
                } while (ProfileDB.findByName(newName, 0) != null);
                name = newName;
            }
            Profile newProfile = new Profile(name, password, status);
            ProfileDB.save(newProfile);
            ProfileController profileController = new ProfileController(newProfile, textMode);
            profileController.openSession(newProfile);
        } catch (PersistenceException ex) {
            processPersistenceException(ex);
        }
    }

    /**
     * Método principal
     *
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        boolean textMode = false;
        if (args.length > 0 && args[0].equals("text")) {
            textMode = true;
        }
        InitMenuController initMenuControler = new InitMenuController(textMode);
        initMenuControler.init();
        TacebookDB.close();
    }

    /**
     * Método privado que procesa unha excepción de persistencia. Dependendo do
     * código da excepción, chama a un dos tres métodos engadidos nas vistas no
     * punto anterior para amosar mensaxes de erro específicas.
     *
     * @param ex A excepción de persistencia a ser procesada.
     */
    private void processPersistenceException(PersistenceException ex) {
        switch (ex.getCode()) {
            case PersistenceException.CONNECTION_ERROR:
                initMenuView.showConnectionErrorMessage();
                break;
            case PersistenceException.CANNOT_READ:
                initMenuView.showReadErrorMessage();
                break;
            case PersistenceException.CANNOT_WRITE:
                initMenuView.showWriteErrorMessage();
                break;
        }
    }
}
