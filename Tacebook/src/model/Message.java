/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 * Clase que representa un mensaje entre perfiles en la aplicación.
 *
 * @author alejandro.fernandezregueiro
 */
public class Message {

    private Profile destProfile; // Atributo da clase Profile
    private Profile sourceProfile; // Atributo da clase Profile
    private int id; // Id do mensaxe
    private String text; // Contido do mensaxe
    private Date date; // Fecha e hora do mensaxe
    private boolean read; // Indica se o mensaxe foi leído

    /**
     * Constructor da clase Message
     * @param destProfile
     * @param sourceProfile
     * @param id
     * @param text
     * @param date
     * @param read 
     */
    public Message(Profile destProfile, Profile sourceProfile, int id, String text, Date date, boolean read) {
        this.destProfile = destProfile;
        this.sourceProfile = sourceProfile;
        this.id = id;
        this.text = text;
        this.date = date;
        this.read = read;
    }

    // Métodos de acceso para os atributos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Profile getDestProfile() {
        return destProfile;
    }

    public void setDestProfile(Profile destProfile) {
        this.destProfile = destProfile;
    }

    public Profile getSourceProfile() {
        return sourceProfile;
    }

    public void setSourceProfile(Profile sourceProfile) {
        this.sourceProfile = sourceProfile;
    }
}
