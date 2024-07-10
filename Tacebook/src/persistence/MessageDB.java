/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Message;

/**
 * Clase que implementa a persistencia dos obxectos da clase Message
 *
 * @author alexf
 */
public class MessageDB {

    /**
     * Almacena unha nova mensaxe
     *
     * @param message
     * @throws persistence.PersistenceException
     */
    public static void save(Message message) throws PersistenceException {
        Connection c = TacebookDB.getConnection();
        java.util.Date utilDate = message.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        String sql;
        sql = "INSERT INTO Message (text,date,isRead,source,destination) VALUES(?,?,?,?,?)";
        PreparedStatement pst;
        try {
            pst = c.prepareStatement(sql);
            pst.setString(1, message.getText());
            pst.setDate(2, sqlDate);
            pst.setBoolean(3, message.isRead());
            pst.setString(4, message.getSourceProfile().getName());
            pst.setString(5, message.getDestProfile().getName());
            pst.executeUpdate();
            pst.close();
            String sql2 = "SELECT * FROM Message WHERE text=? and date=? and isRead=? and source=? and destination=?";
            PreparedStatement st = c.prepareStatement(sql2);
            st.setString(1, message.getText());
            st.setDate(2, sqlDate);
            st.setBoolean(3, message.isRead());
            st.setString(4, message.getSourceProfile().getName());
            st.setString(5, message.getDestProfile().getName());
            ResultSet rst = st.executeQuery();
            while (rst.next()) {
                message.setId(rst.getInt(1));
            }
            message.getDestProfile().getMessages().add(0, message);
        } catch (SQLException ex) {
            Logger.getLogger(ProfileDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Actualiza a información dunha mensaxe
     *
     * @param message
     * @throws persistence.PersistenceException
     */
    public static void update(Message message) throws PersistenceException {
        Connection c = TacebookDB.getConnection();
        try {
            String sql = "UPDATE Message SET isRead=? WHERE id=?";
            PreparedStatement st = c.prepareStatement(sql);
            st.setBoolean(1, message.isRead());
            st.setInt(2, message.getId());
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProfileDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Borra unha mensaxe
     *
     * @param message
     */
    public static void remove(Message message) throws PersistenceException {
        Connection c = TacebookDB.getConnection();
        try {
            String sql = "DELETE FROM Message WHERE id =?";
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, message.getId());
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProfileDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        message.getDestProfile().getMessages().remove(message);
    }
}
