package com.example.chesstournaments.requests;
/**
 * Clase que representa la solicitud de inicio de sesión de usuario.
 * Contiene los campos necesarios para autenticar a un usuario mediante su nombre de usuario y contraseña.
 * Se utiliza principalmente para recibir datos de entrada desde el cliente durante el proceso de inicio de sesión.
 *
 * Esta clase proporciona getters y setters para los campos de nombre de usuario y contraseña.
 * También incluye constructores vacíos y completos para facilitar la creación de instancias de la clase.
 *
 * @author Juan Cabello Rodríguez
 */
public class UserLoginRequest {
    private String username;
    private String password;

    /**
     * Constructor vacío de la clase UserLoginRequest.
     * Se utiliza principalmente para instanciar la clase sin parámetros iniciales.
     */
    public UserLoginRequest() {
    }

    /**
     * Constructor completo de la clase UserLoginRequest.
     * Se utiliza para instanciar la clase con un nombre de usuario y una contraseña específicos.
     *
     * @param username El nombre de usuario del usuario que intenta iniciar sesión.
     * @param password La contraseña del usuario que intenta iniciar sesión.
     */
    public UserLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
