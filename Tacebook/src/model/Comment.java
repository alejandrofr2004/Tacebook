/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 * Clase que representa un comentario
 */
public class Comment {

// Atributos da clase Comment
    private Post post;
    private Profile sourceProfile;

    private int id;
    private Date date;
    private String text;

    /**
     * Constructor da clase Comment.
     *
     * @param post 
     * @param sourceProfile 
     * @param id 
     * @param date 
     * @param text
     */
    public Comment(Post post, Profile sourceProfile, int id, Date date, String text) {
        this.post = post;
        this.sourceProfile = sourceProfile;
        this.id = id;
        this.date = date;
        this.text = text;
    }

// Métodos getters e setters dos atributos da clase
    /**
     * Método get do atributo id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Método set do atributo id
     *
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Método get do atributo date
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Método set do atributo date
     *
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Método get do atributo date
     *
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * Método set do atributo date
     *
     * @param text El nuevo texto del comentario.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Método get do atributo post
     *
     * @return post
     */
    public Post getPost() {
        return post;
    }

    /**
     * Método set do atributo post
     *
     * @param post
     */
    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * Método get do atributo sourceProfile
     *
     * @return sourceProfile
     */
    public Profile getSourceProfile() {
        return sourceProfile;
    }

    /**
     * Método get do atributo sourceProfile
     *
     * @param sourceProfile 
     */
    public void setSourceProfile(Profile sourceProfile) {
        this.sourceProfile = sourceProfile;
    }
}
