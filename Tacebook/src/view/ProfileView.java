/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import model.Profile;

/**
 * Interface que define os métodos relacionados coa vista do perfil do usuario.
 * @author alexf
 */
public interface ProfileView {

    /**
     * Obten o número de posts mostrados no perfil.
     *
     * @return O número de posts mostrados.
     */
    public int getPostsShowed();

    /**
     * Amosa o menú do perfil do usuario.
     *
     * @param profile O perfil do usuario.
     */
    public void showProfileMenu(Profile profile);

    /**
     * Amosa a mensaxe de perfil non encontrado.
     */
    public void showProfileNotFoundMessage();

    /**
     * Amosa a mensaxe de non se pode dar "like" ao propio post.
     */
    public void showCannotLikeOwnPostMessage();

    /**
     * Amosa a mensaxe de que o post xa foi marcado como "gustado".
     */
    public void showAlreadyLikedPostMessage();

    /**
     * Amosa a mensaxe de que o usuario xa é amigo do perfil indicado.
     *
     * @param profileName O nome do perfil.
     */
    public void showIsAlreadyFriendMessage(String profileName);

    /**
     * Amosa a mensaxe de solicitude de amizade existente.
     *
     * @param profileName O nome do perfil.
     */
    public void showExistsFriendshipRequestMessage(String profileName);

    /**
     * Amosa a mensaxe de solicitude de amizade duplicada.
     *
     * @param profileName O nome do perfil.
     */
    public void showDuplicateFriendshipRequestMessage(String profileName);

    /**
     * Amosa a mensaxe de erro na conexión co almacén de datos.
     */
    public void showConnectionErrorMessage();

    /**
     * Amosa a mensaxe de erro na lectura de datos.
     */
    public void showReadErrorMessage();

    /**
     * Amosa a mensaxe de erro na escritura dos datos.
     */
    public void showWriteErrorMessage();
}
