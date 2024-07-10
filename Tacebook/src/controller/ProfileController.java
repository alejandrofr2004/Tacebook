/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Profile;
import model.Message;
import model.Post;
import model.Comment;
import java.util.Date;
import persistence.CommentDB;
import persistence.MessageDB;
import persistence.PersistenceException;
import persistence.PostDB;
import persistence.ProfileDB;
import view.GUIProfileView;
import view.ProfileView;
import view.textProfileView;

/**
 * Esta clase controlará as accións do menú principal
 */
public class ProfileController {

    /**
     * Atributo da clase ProfileView
     */
    private ProfileView profileView;
    /**
     * Atributo da clase Profile
     */
    private Profile sessionProfile;

    /**
     * Atributo que almacena o perfil que se está visualizando
     */
    private Profile shownProfile;

    /**
     * Atributo pra saber se é modo texto
     */
    private boolean textMode;

    /**
     * Método get do atributo shownProfile
     *
     * @return shownProfile
     */
    public Profile getShownProfile() {
        return shownProfile;
    }

    /**
     * Método set do atributo shownProfile
     *
     * @param shownProfile
     */
    public void setShownProfile(Profile shownProfile) {
        this.shownProfile = shownProfile;
        reloadProfile();
    }

    /**
     * Contructor da clase ProfileController
     *
     * @param sessionProfile
     * @param textMode
     */
    public ProfileController(Profile sessionProfile, Boolean textMode) {
        this.sessionProfile = sessionProfile;
        this.textMode = textMode;
        if (textMode) {
            profileView = new textProfileView(this);
        } else {
            profileView = new GUIProfileView(null, true, this);
        }
    }

    /**
     * Método get do atributo sessionProfile
     *
     * @return sessionProfile
     */
    public Profile getSessionProfile() {
        return sessionProfile;
    }

    /**
     * Obtén o número de publicacións a mostrar
     *
     * @return o método profileView.getPostsShowed()
     */
    public int getPostsShowed() {
        return profileView.getPostsShowed();
    }

    /**
     * Obtén o obxecto do perfil da sesión usando a clase "ProfileDB" e mostra o
     * menú do perfil para el
     */
    public void reloadProfile() {
        try {
            shownProfile = ProfileDB.findByName(shownProfile.getName(), getPostsShowed());
        } catch (PersistenceException ex) {
            processPersistenceException(ex);
        }
        profileView.showProfileMenu(shownProfile);
    }

    /**
     * Abre unha sesión con un perfil
     *
     * @param sessionProfile
     */
    public void openSession(Profile sessionProfile) {
        this.sessionProfile = sessionProfile;
        this.shownProfile = sessionProfile;
        profileView.showProfileMenu(sessionProfile);
    }

    /**
     * Actualiza o estado do perfil
     *
     * @param newStatus
     */
    public void updateProfileStatus(String newStatus) {
        if (newStatus != null) {
            sessionProfile.setStatus(newStatus);
            try {
                ProfileDB.update(sessionProfile);
            } catch (PersistenceException ex) {
                processPersistenceException(ex);
            }
            reloadProfile();
        }
    }

    /**
     * Crea un novo obxecto "Post", chama á clase "PostDB" para gardalo e chama
     * ao método "reloadProfile" para refrescar a información do perfil
     *
     * @param text
     * @param destProfile
     */
    public void newPost(String text, Profile destProfile) {
        Post post = new Post(destProfile, sessionProfile, 0, new Date(), text);
        try {
            PostDB.save(post);
        } catch (PersistenceException ex) {
            processPersistenceException(ex);
        }
        reloadProfile();
    }

    /**
     * Crea un novo obxecto "Comment", chama á clase "CommentDB" para gardalo e
     * chama ao método "reloadProfile" para refrescar a información do perfil
     *
     * @param post
     * @param commentText
     */
    public void newComment(Post post, String commentText) {
        Comment comment = new Comment(post, sessionProfile, 0, new Date(), commentText);
        try {
            CommentDB.save(comment);
        } catch (PersistenceException ex) {
            processPersistenceException(ex);
        }
        reloadProfile();
    }

    /**
     * Comproba que o perfil da sesión non sexa o autor da publicación e non
     * fixera xa Like sobre a publicación recibida
     *
     * @param post
     */
    public void newLike(Post post) {
        if (!post.getAutor().equals(sessionProfile) && !post.getProfileLikes().contains(sessionProfile)) {
            try {
                PostDB.saveLike(post, sessionProfile);
            } catch (PersistenceException ex) {
                processPersistenceException(ex);
            }
        }
        reloadProfile();
    }

    /**
     * Comproba que exista un perfil co nome recibido como parámetro, que ese
     * perfil non teña xa amizade co perfil da sesión, e que non haxa xa unha
     * solicitude dese perfil ao perfil da sesión e viceversa
     *
     * @param profileName
     */
    public void newFriendshipRequest(String profileName) {
        Profile destProfile = null;
        try {
            destProfile = ProfileDB.findByName(profileName, getPostsShowed());
        } catch (PersistenceException ex) {
            processPersistenceException(ex);
        }

        if (destProfile != null) {
            if (sessionProfile.getFriends().contains(destProfile)) {
                profileView.showIsAlreadyFriendMessage(profileName);
                profileView.showProfileMenu(sessionProfile);
            } else if (sessionProfile.getFriendshipRequests().contains(destProfile)) {
                profileView.showExistsFriendshipRequestMessage(profileName);
                profileView.showProfileMenu(sessionProfile);
            } else if (destProfile.getFriendshipRequests().contains(sessionProfile)) {
                profileView.showDuplicateFriendshipRequestMessage(profileName);
                profileView.showProfileMenu(sessionProfile);
            } else {
                try {
                    ProfileDB.saveFriendshipRequest(destProfile, sessionProfile);
                } catch (PersistenceException ex) {
                    processPersistenceException(ex);
                }
                reloadProfile();
            }
        } else {
            profileView.showProfileNotFoundMessage();
            profileView.showProfileMenu(sessionProfile);
        }
    }

    /**
     * Para grabar unha amizade
     *
     * @param sourceProfile
     */
    public void acceptFriendshipRequest(Profile sourceProfile) {
        try {
            ProfileDB.removeFriendshipRequest(sessionProfile, sourceProfile);
        } catch (PersistenceException ex) {
            processPersistenceException(ex);
        }
        try {
            ProfileDB.saveFriendship(sourceProfile, sessionProfile);
        } catch (PersistenceException ex) {
            processPersistenceException(ex);
        }
        reloadProfile();
    }

    /**
     * Chama á clase "ProfileDB" para borrar a solicitude de amizade
     *
     * @param sourceProfile
     */
    public void rejectFriendshipRequest(Profile sourceProfile) {
        try {
            ProfileDB.removeFriendshipRequest(sessionProfile, sourceProfile);
        } catch (PersistenceException ex) {
            processPersistenceException(ex);
        }
        reloadProfile();
    }

    /**
     * Crea un novo obxecto "Message", chama á clase "MessageDB" para gardalo e
     * chama ao método "reloadProfile" para refrescar a información do perfil
     *
     * @param destProfile
     * @param text
     */
    public void newMessage(Profile destProfile, String text) {
        Message message = new Message(destProfile, sessionProfile, 0, text, new Date(), false);
        try {
            MessageDB.save(message);
        } catch (PersistenceException ex) {
            processPersistenceException(ex);
        }
        reloadProfile();
    }

    /**
     * Chama á clase "MessageDB" para borrar a mensaxe e chama ao método
     * "reloadProfile" para refrescar a información do perfil
     *
     * @param message
     */
    public void deleteMessage(Message message) {
        try {
            MessageDB.remove(message);
        } catch (PersistenceException ex) {
            processPersistenceException(ex);
        }
        reloadProfile();
    }

    /**
     * Cambia o valor do atributo "read" da mensaxe e true, chama á clase
     * "MessageDB" para actualizar a mensaxe e chama ao método "reloadProfile"
     * para refrescar a información do perfil
     *
     * @param message
     */
    public void markMessageAsRead(Message message) {
        message.setRead(true);
        try {
            MessageDB.update(message);
        } catch (PersistenceException ex) {
            processPersistenceException(ex);
        }
        reloadProfile();
    }

    /**
     * Cambia o valor do atributo "read" da mensaxe e true, chama á clase
     * "MessageDB" para actualizar a mensaxe e chama ao método "newMessage" para
     * enviar a mensaxe de resposta e recargar o perfil
     *
     * @param message
     * @param text
     */
    public void replyMessage(Message message, String text) {
        message.setRead(true);
        try {
            MessageDB.update(message);
        } catch (PersistenceException ex) {
            processPersistenceException(ex);
        }
        newMessage(message.getSourceProfile(), text);
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
                profileView.showConnectionErrorMessage();
                break;
            case PersistenceException.CANNOT_READ:
                profileView.showReadErrorMessage();
                break;
            case PersistenceException.CANNOT_WRITE:
                profileView.showWriteErrorMessage();
                break;
        }
    }
}
