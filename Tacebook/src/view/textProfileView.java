/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.ProfileController;
import model.Profile;
import model.Message;
import model.Post;
import model.Comment;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * Encárgase de mostrar as opcións do menú principal
 *
 * @author alejandro.fernandezregueiro
 */
public class textProfileView implements ProfileView{

    private int postsShowed = 10;  // Número de publicacions mostradas
    private ProfileController profileControler;  // Controlador do perfil
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy 'ás' HH:mm:ss");// Pºara formatar as datas

    /**
     * Constructor da clase ProfileView.
     *
     * @param profileControler
     */
    public textProfileView(ProfileController profileControler) {
        this.profileControler = profileControler;
    }

    /**
     * Obtén o número de publicacions mostradas
     *
     * @return postsShowed
     */
    public int getPostsShowed() {
        return postsShowed;
    }

    /**
     * Método set do atributo postsShowed
     *
     * @param postsShowed
     */
    public void setPostsShowed(int postsShowed) {
        this.postsShowed = postsShowed;
    }

    /**
     * Método get do atributo formatter
     *
     * @return formatter
     */
    public SimpleDateFormat getFormatter() {
        return formatter;
    }

    /**
     * Método set do atributo formatter
     *
     * @param formatter
     */
    public void setFormatter(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }

    /**
     * Encárgase de mostrar toda a información do perfil
     *
     * @param ownProfile
     * @param profile
     */
    public void showProfileInfo(boolean ownProfile, Profile profile) {
        System.out.println("tacebook - Perfil de usuario: " + profile.getName());
        System.out.println("Estado actual: " + profile.getStatus());

        System.out.println("A túa biografía (10 últimas publicacións): ");
        for (int i = 0; i < profile.getPosts().size(); i++) {
            Post post = profile.getPosts().get(i);
            System.out.println(i + ".0" + " " + formatter.format(post.getDate()) + " " + post.getAutor().getName() + " escribiu: " + post.getText());
            System.out.println("Likes da publicación: " + post.getProfileLikes().size());

            for (Comment comment : post.getComments()) {
                System.out.println("Comentario: " + comment.getText());
                System.out.println("Data do comentario " + formatter.format(comment.getDate()));
            }
        }
        System.out.println("Solicitudes de amizade: " + profile.getFriendshipRequests().size());
        System.out.println("Lista de amigos: ");
        for (int i = 0; i < profile.getFriends().size(); i++) {
            System.out.println(i + ". " + profile.getFriends().get(i).getName() + "-" + profile.getFriends().get(i).getStatus());
        }

        for (int i = 0; i < profile.getMessages().size(); i++) {
            Message message = profile.getMessages().get(i);
            System.out.println(i + ". " + "Mensaxe de " + message.getSourceProfile().getName() + ": " + message.getText());
            System.out.println("Data da mensaxe: " + formatter.format(message.getDate()));
        }
        System.out.println(" ");
    }

    /**
     * Cambia o estado do perfil
     *
     * @param ownProfile
     * @param scanner
     * @param profile
     */
    public void changeStatus(boolean ownProfile, Scanner scanner, Profile profile) {
        if (ownProfile) {
            System.out.println("Introduce un novo estado: ");
            String status = scanner.next();
            profileControler.updateProfileStatus(status);
            System.out.println(" ");
        } else {
            System.out.println("Opción só válida para a propia biografía");
            showProfileMenu(profile);
            System.out.println(" ");
        }
    }

    /**
     * Mostra o menú para o usuario
     *
     * @param profile
     */
    @Override
    public void showProfileMenu(Profile profile) {
        boolean ownProfile;

        ownProfile = profileControler.getSessionProfile().getName().equals(profile.getName());

        showProfileInfo(ownProfile, profile);

        if (ownProfile) {
            System.out.println("Introduce qué queres facer:");
            System.out.println("1. Añadir publicación");
            System.out.println("2. Comentar unha publicación");
            System.out.println("3. Facer me gusta sobre unha publicación");
            System.out.println("4. Ver biografía dun amigo");
            System.out.println("5. Enviar unha solicitude de amistade");
            System.out.println("6. Aceptar unha solicitude de amizade");
            System.out.println("7. Rexeitar unha solicitude de amizade");
            System.out.println("8. Mandar unha mensaxe privada a un amigo");
            System.out.println("9. Ler unha mensaxe privada");
            System.out.println("10. Eliminar unha mensaxe privada");
            System.out.println("11. Ver publicacións anteriores");
            System.out.println("12. Cambiar o estado do perfil");
            System.out.println("13. Pechar a sesión");
            System.out.println(" ");

        } else {
            System.out.println("Introduce qué queres facer:");
            System.out.println("1. Añadir publicación");
            System.out.println("2. Comentar unha publicación");
            System.out.println("3. Facer me gusta sobre unha publicación");
            System.out.println("4. Volver a miña biografía");
            System.out.println("8. Mandar unha mensaxe privada a este amigo");
            System.out.println("11. Ver publicacións anteriores");
            System.out.println("13. Pechar a sesión");
            System.out.println(" ");
        }

        int action;

        do {
            Scanner scanner = new Scanner(System.in);
            action = scanner.nextInt();

            if (action < 1 || action > 13) {
                System.out.println("Opción inválida, introduce unha opción entre 1 e 13");
            }

            switch (action) {
                case 1:
                    String text = scanner.nextLine();
                    writeNewPost(scanner, profile);
                    break;
                case 2:
                    commentPost(scanner, profile);
                    break;
                case 3:
                    addLike(scanner, profile);
                    break;
                case 4:
                    showBiography(ownProfile, scanner, profile);
                    break;
                case 5:
                    sendFriendshipRequest(true, scanner, profile);
                    break;
                case 6:
                    proccessFriendshipRequest(true, scanner, profile, true);
                    break;
                case 7:
                    proccessFriendshipRequest(true, scanner, profile, false);
                    break;
                case 8:
                    sendPrivateMessage(true, scanner, profile);
                    break;
                case 9:
                    readPrivateMessage(true, scanner, profile);
                    break;
                case 10:
                    deletePrivateMessage(true, scanner, profile);
                    break;
                case 11:
                    showOldPosts(scanner, profile);
                    break;
                case 12:
                    changeStatus(true, scanner, profile);
                    break;
                case 13:
                    break;
            }
        } while (action < 1 || action > 13);
    }

    /**
     * Este método utilizarase cando se pida ao usuario que introduza un número
     * para seleccionar un elemento dunha lista (de publicacións, de mensaxes,
     * de amizades, etc.).
     *
     * @param text
     * @param maxNumber
     * @param scanner
     * @return
     */
    private int selectElement(String text, int maxNumber, Scanner scanner) {
        int number;

        do {
            System.out.println(text);
            number = scanner.nextInt();
            if (number < 0 || number > maxNumber - 1) {
                System.out.println("Introduce outra vez o número: ");
                number = scanner.nextInt();
            }
        } while (number < 0 || number > maxNumber - 1);

        return number;

    }

    /**
     * Pide o texto para crear unha nova publicación e chama ao controlador para
     * creala.
     *
     * @param scanner
     * @param profile
     */
    private void writeNewPost(Scanner scanner, Profile profile) {
        System.out.println("Introduce texto para nova publicación: ");
        String text = scanner.nextLine();
        profileControler.newPost(text, profile);
    }

    /**
     * Pide ao usuario que seleccione unha publicación e que introduza un texto,
     * e chama ao controlador para crear un comentario con ese texto
     *
     * @param scanner
     * @param profile
     */
    private void commentPost(Scanner scanner, Profile profile) {
        int selectedIndex = selectElement("Escolle a publicación que queres comentar: ", postsShowed, scanner);

        scanner.nextLine();

        System.out.println("Introduce o comentario: ");
        String text = scanner.nextLine();

        Post post = profile.getPosts().get(selectedIndex);
        profileControler.newComment(post, text);
    }

    /**
     * Pide ao usuario que seleccione unha publicación e chama ao controlador
     * para facer like sobre ela.
     *
     * @param scanner
     * @param profile
     */
    private void addLike(Scanner scanner, Profile profile) {
        int selectedIndex = selectElement("Introduce a publicación a que lle queres dar like", postsShowed, scanner);

        Post post = profile.getPosts().get(selectedIndex);
        if (post.getAutor().equals(profileControler.getSessionProfile())) {
            showCannotLikeOwnPostMessage();
        } else if (post.getProfileLikes().contains(profileControler.getSessionProfile())) {
            showAlreadyLikedPostMessage();
        } else {
            profileControler.newLike(post);
        }
    }

    /**
     * Pide ao usuario que seleccione unha publicación e chama ao controlador
     * para facer like sobre ela. private void showBiography(boolean ownProfile,
     * Scanner scanner, Profile profile): Se estamos vendo o propio perfil, pide
     * ao usuario seleccionar unha amizade para establecer ese perfil como
     * perfil a mostrar (chamando ao controlador), e senón volve a poñer o
     * perfil da sesión como perfil a mostrar. O parámetro "ownProfile" é o que
     * indica se estamos vendo o propio perfil da sesión ou o perfil dunha
     * amizade
     *
     * @param ownProfile
     * @param scanner
     * @param profile
     */
    private void showBiography(boolean ownProfile, Scanner scanner, Profile profile) {
        if (ownProfile) {
            int selectedIndex = selectElement("Selecciona unha amizade para o seu perfil:",
                    profile.getFriends().size(), scanner);
            Profile friendProfile = profile.getFriends().get(selectedIndex);
            profileControler.setShownProfile(friendProfile);
        } else {
            profileControler.openSession(profileControler.getSessionProfile());
        }
    }

    /**
     * Pide o nome dun perfil e chama ao controlador para enviarlle unha
     * solicitude de amizade.
     *
     * @param ownProfile
     * @param scanner
     * @param profile
     */
    private void sendFriendshipRequest(boolean ownProfile, Scanner scanner, Profile profile) {
        if (ownProfile) {
            System.out.println("Introduce un nome do perfil ");
            scanner.nextLine();
            String nameProfile = scanner.nextLine();
            profileControler.newFriendshipRequest(nameProfile);

        } else {
            System.out.println("Non podes enviar unha solicitude dende outro perfil que non sexa o teu");
            profileControler.setShownProfile(profile);
        }
    }

    /**
     * Pide o número dunha solicitude de amizade e chama ao controlador para
     * aceptala ou rexeitala, en función do que se indique no parámetro
     * "accept".
     *
     * @param ownProfile
     * @param scanner
     * @param profile
     * @param accept
     */
    private void proccessFriendshipRequest(boolean ownProfile, Scanner scanner, Profile profile, boolean accept) {
        for (int i = 0; i < profile.getFriendshipRequests().size(); i++) {
            Profile friendRequest = profile.getFriendshipRequests().get(i);
            System.out.println(i + " Nome do perfil: " + friendRequest.getName());
        }
        int selectedIndex = selectElement("Introduce o número da solicitude "
                + "de amistade", postsShowed, scanner);
        Profile selectProfile = profile.getFriendshipRequests().get(selectedIndex);
        if (ownProfile) {
            if (accept) {
                profileControler.acceptFriendshipRequest(selectProfile);
            } else {
                profileControler.rejectFriendshipRequest(selectProfile);
            }
        } else {
            System.out.println("Non podes aceptar ou rexeitar unha solicitude "
                    + "dende outro perfil que non sexa o teu");
            profileControler.setShownProfile(profile);
        }
    }

    /**
     * Se estamos vendo o propio perfil, pide ao usuario selecciona un amigo e o
     * texto da mensaxe e chama ao controlador para enviar unha mensaxe. Se
     * estamos vendo o perfil dunha amizade, pide o texto para enviarlle unha
     * mensaxe a ese perfil.
     *
     * @param ownProfile
     * @param scanner
     * @param profile
     */
    private void sendPrivateMessage(boolean ownProfile, Scanner scanner, Profile profile) {
        if (ownProfile) {
            int profileSelected = selectElement("Selecciona un amigo: ", profile.getFriends().size(), scanner);
            Profile friendProfile = profile.getFriends().get(profileSelected);
            scanner.nextLine();
            System.out.println("Introduce o texto da mensaxe que lle queres enviar: ");
            String text = scanner.nextLine();
            profileControler.newMessage(friendProfile, text);
        } else {
            System.out.println("Introduce o texto da mensaxe que lle queres enviar: ");
            String text = scanner.nextLine();
            profileControler.newMessage(profile, text);
        }
    }

    /**
     * Pide ao usuario que seleccione unha mensaxe e a mostra completa, dando as
     * opcións de respondela, eliminala ou simplemente volver á biografia
     * marcando a mensaxe como lida, chamando ao controlador para executar as
     * distintas accións.
     *
     * @param ownProfile
     * @param scanner
     * @param profile
     */
    private void readPrivateMessage(boolean ownProfile, Scanner scanner, Profile profile) {
        if (ownProfile) {
            int selectedMessage = selectElement("Selecciona unha mensaxe", profile.getMessages().size(), scanner);
            int action;
            do {
                System.out.println("Que queres facer: ");
                System.out.println("1. Respondela");
                System.out.println("2. Eliminala");
                System.out.println("3. Volver ao perfil marcandoa como leída");
                System.out.println(" ");

                scanner.nextLine();

                action = scanner.nextInt();
                scanner.nextLine();
                Message message = profile.getMessages().get(selectedMessage);
                switch (action) {
                    case 1:
                        System.out.println("Introduce o mensaxe de resposta");
                        String text = scanner.nextLine();
                        profileControler.replyMessage(message, text);
                        break;
                    case 2:
                        profileControler.deleteMessage(message);
                        break;
                    case 3:
                        profileControler.markMessageAsRead(message);
                        break;
                    default:
                        System.out.println("Non escolliste ningunha opción válida, volve a escoller outra opción.");
                        break;
                }
            } while (action < 1 || action > 3);
        } else {
            System.out.println("Non podes responder mensaxes dende este perfil");
        }
    }

    /**
     * Pide ao usuario que seleccione unha mensaxe e chama ao controlador para
     * borrala.
     *
     * @param ownProfile
     * @param scanner
     * @param profile
     */
    private void deletePrivateMessage(boolean ownProfile, Scanner scanner, Profile profile) {
        if (ownProfile) {
            int selectedMessage = selectElement("Selecciona unha mensaxe para borrar", profile.getMessages().size(), scanner);
            Message message = profile.getMessages().get(selectedMessage);
            profileControler.deleteMessage(message);

        } else {
            System.out.println("Non podes responder mensaxes dende este perfil");
        }
    }

    /**
     * Pide o número de publicacións que se queren visualizar e chamar ao
     * controlador para recargar o perfil.
     *
     * @param scanner
     * @param profile
     */
    private void showOldPosts(Scanner scanner, Profile profile) {
        int selectedNumberofPosts = selectElement("Selecciona o número de publicacions que queres ver", postsShowed, scanner);
        setPostsShowed(selectedNumberofPosts);
        profileControler.reloadProfile();
    }

    /**
     * Informa que un perfil non se atopou
     */
    public void showProfileNotFoundMessage() {
        System.out.println("O perfil non se atopou");
    }

    /**
     * Informa de que non se pode facer like sobre unha publicación propia.
     */
    public void showCannotLikeOwnPostMessage() {
        System.out.println("Non se pode facer like nunha publicación propia");

    }

    /**
     * Informa de que non se pode facer like sobre unha publicación sobre a que
     * xa se fixo like.
     */
    public void showAlreadyLikedPostMessage() {
        System.out.println("Non se pode facer like sobre unha publicación que xa lle diches like");
    }

    /**
     * Informa de que xa tes amizade con ese perfil.
     *
     * @param profileName
     */
    public void showIsAlreadyFriendMessage(String profileName) {
        System.out.println("Xa tes amizade con " + profileName);
    }

    /**
     * Informa de que ese perfil xa ten unha solicitude de amizade contigo.
     *
     * @param profileName
     */
     public void showExistsFriendshipRequestMessage(String profileName) {
        System.out.println(profileName + "  xa ten unha solicitude de amistade contigo");
    }

    /**
     * Informa de que xa tes unha solicitude de amizade con ese perfil.
     *
     * @param profileName
     */
    public void showDuplicateFriendshipRequestMessage(String profileName) {
        System.out.println("Xa tes unha solicitude de amizade con " + profileName);
    }

    /**
     * Amosa a mensaxe de erro "Erro na conexión co almacén de datos!".
     */
    public void showConnectionErrorMessage() {
        System.out.println("Erro na conexión co almacén de datos!");
    }

    /**
     * Amosa a mensaxe de erro "Erro na lectura de datos!".
     */
    public void showReadErrorMessage() {
        System.out.println("Erro na lectura de datos!");
    }

    /**
     * Amosa a mensaxe de erro "Erro na escritura dos datos!".
     */
    public void showWriteErrorMessage() {
        System.out.println("Erro na escritura dos datos!");
    }

}
