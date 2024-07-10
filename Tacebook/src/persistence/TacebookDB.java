/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import model.Profile;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que contén os perfiles cun ArrayList
 *
 * @author alejandro.fernandezregueiro
 */
public class TacebookDB {

    /**
     * ArrayList de perfiles
     */
    private static ArrayList<Profile> profiles = new ArrayList<>();

    /**
     * Constante que define la ruta de la conexión
     */
    public final static String URL_DB = "jdbc:sqlite:tacebook.db";

    // Referencia á conexión coa BD
    private static Connection connection = null;

    /**
     * Atributo get de los perfiles
     *
     * @return
     */
    public static ArrayList<Profile> getProfiles() {
        return profiles;
    }

    /**
     * Atributo set de los perfiles
     *
     * @param profiles
     */
    public static void setProfiles(ArrayList<Profile> profiles) {
        TacebookDB.profiles = profiles;
    }

    /**
     * Obtén unha única conexión coa base de datos, abríndoa se é necesario
     *
     * @return Conexión coa base de datos aberta
     * @throws PersistenceException Se se produce un erro ao conectar coa BD
     */
    public static Connection getConnection() throws PersistenceException {
        String url = null;
        // Declaramos o reader e o writer con buffer
        BufferedReader in = null;
        try {
            // Abrimos o reader e o writer
            in = new BufferedReader(new FileReader("resources\\db.properties"));
            // Usamos os métodos readLine, write e writeLine para ler e escribir
            // liña a liña
            String line;
            while ((line = in.readLine()) != null) {
                url = line;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TacebookDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TacebookDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // En calquera caso, producirase ou non unha excepción, pechamos o
            // reader e o writer se están abertos
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(TacebookDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        // Obtemos unha conexión coa base de datos
        try {

            if (connection == null) {
                connection = DriverManager.getConnection(url, "", "");
            }
            return connection;
        } catch (SQLException e) {
            throw new PersistenceException(PersistenceException.CONNECTION_ERROR, e.getMessage());
        }
    }

    /**
     * Método para pechar a conexión coa base de datos
     */
    public static void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(TacebookDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
