/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 * Clase que representa un perfil de usuario na aplicación
 * @author alejandro.fernandezregueiro
 */
public class Profile {

    // Atributos da clase Profile
    private String name; 
    private String password; 
    private String status;

    private ArrayList<Post> posts = new ArrayList<>();
    private ArrayList<Profile> friends = new ArrayList<>();
    private ArrayList<Profile> friendshipRequests = new ArrayList<>(); 
    private ArrayList<Message> messages = new ArrayList<>(); 

    /**
     * Constructor da clase Profile
     * @param name 
     * @param password 
     * @param status 
     */
    public Profile(String name, String password, String status) {
        this.name = name;
        this.password = password;
        this.status = status;
    }

    // Métodos de acceso para os atributos

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Profile> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<Profile> friends) {
        this.friends = friends;
    }

    public ArrayList<Profile> getFriendshipRequests() {
        return friendshipRequests;
    }

    public void setFriendshipRequests(ArrayList<Profile> friendshipRequests) {
        this.friendshipRequests = friendshipRequests;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return getName();
    }
    
}

