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
import model.Profile;
import model.Post;

/**
 * Clase que implementa a persistencia dos obxectos da clase Post
 *
 * @author alexf
 */
public class PostDB {

    /**
     * Almacena unha nova publicación
     *
     * @param post
     * @throws persistence.PersistenceException
     */
    public static void save(Post post) throws PersistenceException {
        Connection c = TacebookDB.getConnection();
        java.util.Date utilDate = post.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        String sql;
        sql = "INSERT INTO Post (text,date,profile,author) VALUES(?,?,?,?)";
        PreparedStatement pst;
        try {
            pst = c.prepareStatement(sql);
            pst.setString(1, post.getText());
            pst.setDate(2, sqlDate);
            pst.setString(3, post.getProfile().getName());
            pst.setString(4, post.getAutor().getName());
            pst.executeUpdate();
            pst.close();
            String sql2 = "SELECT * FROM Post WHERE text=? and date=? and profile=? and author=?";
            try (PreparedStatement st = c.prepareStatement(sql2)) {
                st.setString(1, post.getText());
                st.setDate(2, sqlDate);
                st.setString(3, post.getProfile().getName());
                st.setString(4, post.getAutor().getName());
                try (ResultSet rst = st.executeQuery()) {
                    if (rst.next()) {
                        post.setId(rst.getInt(1));
                    }
                    post.getProfile().getPosts().add(post);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfileDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Garda un Like sobre unha publicación
     *
     * @param post
     * @param profile
     * @throws persistence.PersistenceException
     */
    public static void saveLike(Post post, Profile profile) throws PersistenceException {
        Connection c = TacebookDB.getConnection();
        java.util.Date utilDate = post.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        String sql;
        sql = "INSERT INTO ProfileLikesPost (idPost,profile) VALUES(?,?)";
        PreparedStatement pst;
        try {
            String sql2 = "SELECT * FROM Post WHERE text=? and date=? and profile=? and author=?";
            PreparedStatement st2 = c.prepareStatement(sql2);
            st2.setString(1, post.getText());
            st2.setDate(2, sqlDate);
            st2.setString(3, post.getProfile().getName());
            st2.setString(4, post.getAutor().getName());
            ResultSet rst2 = st2.executeQuery();
            while (rst2.next()) {
                pst = c.prepareStatement(sql);
                pst.setInt(1, rst2.getInt(1));
                pst.setString(2, profile.getName());
                pst.executeUpdate();
                pst.close();
            }
            String sql3 = "SELECT * FROM ProfileLikesPost WHERE idPost=?";
            PreparedStatement st3 = c.prepareStatement(sql3);
            st3.setString(1, post.getAutor().getName());
            ResultSet rst3 = st3.executeQuery();
            if (rst3.next()) {
                post.getProfileLikes().add(profile);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProfileDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
