package homecomingalpha.ederpadilla.example.com.proyectoipn.models;

import io.realm.annotations.PrimaryKey;

/**
 * Created by ederpadilla on 10/10/16.
 */

public class User {
    private String nombre;
    private String telefono;
    private String email;
    private String contraseña;
    private int tipoDeUuario;
    @PrimaryKey
    String id;

    public User() {
    }

    public User(String nombre, String telefono, String email, String contraseña, int tipoDeUuario, String id) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.contraseña = contraseña;
        this.tipoDeUuario = tipoDeUuario;
        this.id = id;
    }

    public User(String nombre, String telefono, String email, String contraseña, int tipoDeUuario) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.contraseña = contraseña;
        this.tipoDeUuario = tipoDeUuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getTipoDeUuario() {
        return tipoDeUuario;
    }

    public void setTipoDeUuario(int tipoDeUuario) {
        this.tipoDeUuario = tipoDeUuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", tipoDeUuario=" + tipoDeUuario +
                ", id='" + id + '\'' +
                '}';
    }
}
