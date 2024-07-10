/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import model.Profile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Comment;
import model.Message;
import model.Post;

/**
 * Clase que implementa a persistencia dos obxectos da clase Profile
 */
public class ProfileDB {

    /**
     * Busca un perfil polo nome
     *
     * @param name
     * @param numberOfPosts
     * @return Perfil encontrado o null se non existe
     * @throws persistence.PersistenceException
     */
    public static Profile findByName(String name, int numberOfPosts) throws PersistenceException {
        Connection c = TacebookDB.getConnection();
        Profile profile = null;
        try {
            String sql = "SELECT * FROM Profile WHERE name=?";
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rst = st.executeQuery();
            if (rst.next()) {
                profile = new Profile(name, rst.getString(2), rst.getString(3));
            }
            return profile;
        } catch (SQLException ex) {
            Logger.getLogger(ProfileDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Recibe como parámetros o nome dun usuario, o contrasinal e o número de
     * publicacións dese perfil que queremos recuperar
     *
     * @param name
     * @param password
     * @param numberOfPosts
     * @return Perfil encontrado o null si no existe
     * @throws persistence.PersistenceException
     */
    public static Profile findByNameAndPassword(String name, String password, int numberOfPosts) throws PersistenceException {
        Connection c = TacebookDB.getConnection();
        Profile profile = null;
        try {
            String sql = "SELECT * FROM Profile WHERE name=? AND password =?";
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, name);
            try {
                st.setString(2, getPasswordHash(password));
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(ProfileDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            ResultSet rst = st.executeQuery();
            if (rst.next()) {
                profile = new Profile(name, password, rst.getString(3));
            }
            return profile;
        } catch (SQLException ex) {
            Logger.getLogger(ProfileDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Carga un perfil
     *
     * @param name
     * @param profile
     */
    public static void loadProfile(String name, Profile profile) {
        try {
            Connection c = TacebookDB.getConnection();
            String sql3 = "SELECT * FROM FriendRequest WHERE destinationProfile=?";
            PreparedStatement st3 = c.prepareStatement(sql3);
            st3.setString(1, name);
            ResultSet rst3 = st3.executeQuery();
            while (rst3.next()) {
                String nameSourceProfile = rst3.getString(1);
                Profile sourceProfile = findByName(nameSourceProfile, 0);
                if (!(profile.getFriendshipRequests().contains(sourceProfile))) {
                    profile.getFriendshipRequests().add(sourceProfile);
                }
            }
            String sql4 = "SELECT * FROM Friend WHERE profile1=? or profile2=?";
            PreparedStatement st4 = c.prepareStatement(sql4);
            st4.setString(1, name);
            st4.setString(2, name);
            ResultSet rst4 = st4.executeQuery();
            while (rst4.next()) {
                String nameFriend;
                if (rst4.getString(1).equals(name)) {
                    nameFriend = rst4.getString(2);
                } else {
                    nameFriend = rst4.getString(1);
                }
                Profile friend = findByName(nameFriend, 0);
                profile.getFriends().add(friend);
            }
            String sql = "SELECT * FROM Message WHERE destination=?";
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rst = st.executeQuery();
            while (rst.next()) {
                String nameSourceProfile = rst.getString(5);
                Profile sourceProfile = findByName(nameSourceProfile, 0);
                Message message = new Message(profile, sourceProfile, rst.getInt(1), rst.getString(2), rst.getDate(3), rst.getBoolean(4));
                profile.getMessages().add(message);
            }
            String sql1 = "SELECT * FROM Post WHERE profile=?";
            PreparedStatement st1 = c.prepareStatement(sql1);
            st1.setString(1, name);
            ResultSet rst1 = st1.executeQuery();
            while (rst1.next()) {
                String nameAutorPost = rst1.getString(5);
                Profile autorPost = findByName(nameAutorPost, 0);
                Post post = new Post(profile, autorPost, rst1.getInt(1), rst1.getDate(3), rst1.getString(2));
                profile.getPosts().add(post);
                String sql2 = "SELECT * FROM Comment WHERE idPost=?";
                PreparedStatement st2 = c.prepareStatement(sql2);
                st2.setInt(1, rst1.getInt(1));
                ResultSet rst2 = st2.executeQuery();
                while (rst2.next()) {
                    String nameAutorComment = rst2.getString(4);
                    Profile autorComment = findByName(nameAutorComment, 0);
                    Comment comment = new Comment(post, autorComment, rst2.getInt(1), rst2.getDate(3), rst2.getString(2));
                    post.getComments().add(comment);
                }
                String sql5 = "SELECT * FROM ProfileLikesPost WHERE idPost=?";
                PreparedStatement st5 = c.prepareStatement(sql5);
                st5.setInt(1, rst1.getInt(1));
                ResultSet rst5 = st5.executeQuery();
                while (rst5.next()) {
                    String nameProfileLiked = rst5.getString(2);
                    Profile profileLiked = findByName(nameProfileLiked, 0);
                    post.getProfileLikes().add(profileLiked);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (PersistenceException ex) {
            Logger.getLogger(ProfileDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Almacena un novo perfil
     *
     * @param profile
     * @throws persistence.PersistenceException
     */
    public static void save(Profile profile) throws PersistenceException {
        Connection c = TacebookDB.getConnection();
        String sql;
        sql = "INSERT INTO Profile VALUES(?,?,?)";
        PreparedStatement pst;
        try {
            pst = c.prepareStatement(sql);
            pst.setString(1, profile.getName());
            try {
                pst.setString(2, getPasswordHash(profile.getPassword()));
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(ProfileDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            pst.setString(3, profile.getStatus());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProfileDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Actualiza o perfil no almacemento
     *
     * @param profile
     * @throws persistence.PersistenceException
     */
    public static void update(Profile profile) throws PersistenceException {
        Connection c = TacebookDB.getConnection();
        try {
            String sql = "UPDATE Profile SET status=? WHERE name=?";
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, profile.getStatus());
            st.setString(2, profile.getName());
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProfileDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Almacena unha nova solicitude de amizade
     *
     * @param destProfile
     * @param sourceProfile
     * @throws persistence.PersistenceException
     */
    public static void saveFriendshipRequest(Profile destProfile, Profile sourceProfile) throws PersistenceException {
        Connection c = TacebookDB.getConnection();
        String sql;
        sql = "INSERT INTO FriendRequest VALUES(?,?)";
        PreparedStatement pst;
        try {
            pst = c.prepareStatement(sql);
            pst.setString(1, sourceProfile.getName());
            pst.setString(2, destProfile.getName());
            pst.executeUpdate();
            pst.close();
            destProfile.getFriendshipRequests().add(sourceProfile);
        } catch (SQLException ex) {
            Logger.getLogger(ProfileDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Borra unha solicitude de amizade
     *
     * @param destProfile
     * @param sourceProfile
     */
    public static void removeFriendshipRequest(Profile destProfile, Profile sourceProfile) throws PersistenceException {
        Connection c = TacebookDB.getConnection();

        String sql;
        sql = "DELETE FROM FriendRequest WHERE sourceProfile =? and destinationProfile=?";
        PreparedStatement pst;
        try {
            pst = c.prepareStatement(sql);
            pst.setString(1, sourceProfile.getName());
            pst.setString(2, destProfile.getName());
            pst.executeUpdate();
            pst.close();
            destProfile.getFriendshipRequests().remove(sourceProfile);
        } catch (SQLException ex) {
            Logger.getLogger(ProfileDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Almacena unha amizade entre dous perfís
     *
     * @param profile1
     * @param profile2
     */
    public static void saveFriendship(Profile profile1, Profile profile2) throws PersistenceException {
        Connection c = TacebookDB.getConnection();
        String sql;
        sql = "INSERT INTO Friend VALUES(?,?)";
        PreparedStatement pst;
        try {
            pst = c.prepareStatement(sql);
            pst.setString(1, profile1.getName());
            pst.setString(2, profile2.getName());
            pst.executeUpdate();
            pst.close();
            profile1.getFriends().add(profile2);
            profile2.getFriends().add(profile1);
        } catch (SQLException ex) {
            Logger.getLogger(ProfileDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para almacenamiento seguro dun contrasinal
     *
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static String getPasswordHash(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(password.getBytes());
        return new String(messageDigest.digest());
    }
}
