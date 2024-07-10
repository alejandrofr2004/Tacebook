/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Comment;

/**
 * Clase que implementa a persistencia dos obxectos da clase Post
 *
 * @author alexf
 */
public class CommentDB {

    /**
     * Almacena un novo comentario
     *
     * @param comment
     */
    public static void save(Comment comment) throws PersistenceException {
        Connection c = TacebookDB.getConnection();
        java.util.Date utilDate = comment.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        String sql;
        sql = "INSERT INTO Comment (text,date,author,idPost) VALUES(?,?,?,?)";
        PreparedStatement pst;
        try {
            pst = c.prepareStatement(sql);
            pst.setString(1, comment.getText());
            pst.setDate(2, sqlDate);
            pst.setString(3, comment.getSourceProfile().getName());
            pst.setInt(4, comment.getPost().getId());
            pst.executeUpdate();
            pst.close();
            comment.getPost().getComments().add(0, comment);
        } catch (SQLException ex) {
            Logger.getLogger(ProfileDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
