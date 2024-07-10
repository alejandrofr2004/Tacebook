/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Clase que representa unha publicación na aplicación
 *
 * @author alejandro.fernandezregueiro
 */
public class Post {

    // Atributos da clase post
    private Profile profile; 
    private Profile autor; 
    private ArrayList<Profile> profileLikes = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();

    private int id; 
    private Date date; 
    private String text; 

    /**
     * Constructor da clase Post.
     *
     * @param profile 
     * @param autor
     * @param id 
     * @param date 
     * @param text 
     */
    public Post(Profile profile, Profile autor, int id, Date date, String text) {
        this.profile = profile;
        this.autor = autor;
        this.id = id;
        this.date = date;
        this.text = text;
    }

    // Métodos de acceso para os atributos
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getAutor() {
        return autor;
    }

    public void setAutor(Profile autor) {
        this.autor = autor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Profile> getProfileLikes() {
        return profileLikes;
    }

    public void setProfileLikes(ArrayList<Profile> profileLikes) {
        this.profileLikes = profileLikes;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
    
    
}
