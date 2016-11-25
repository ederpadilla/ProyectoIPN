package homecomingalpha.ederpadilla.example.com.proyectoipn.models;

import java.util.Arrays;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ederpadilla on 10/10/16.
 */

public class User extends RealmObject {
    private String nombre;
    private String telefono;
    private String email;
    private int tipoDeUuario;
    private byte[]bytes;
    private RealmList<Alumnos> alumnosRealmList;
    private String imageUrl;
    @PrimaryKey
    private String id;

    public User() {
    }

    public User(String nombre, String id, String imageUrl,int tipoDeUuario) {
        this.nombre = nombre;
        this.id = id;
        this.tipoDeUuario=tipoDeUuario;
        this.imageUrl = imageUrl;
    }

    public User(String nombre, String telefono, String email, String contraseña, int tipoDeUuario, byte[] bytes, RealmList<Alumnos> alumnosRealmList, String id) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.tipoDeUuario = tipoDeUuario;
        this.bytes = bytes;
        this.alumnosRealmList = alumnosRealmList;
        this.id = id;
    }

    public User(String nombre, String telefono, String email, String contraseña, int tipoDeUuario, RealmList<Alumnos> alumnosRealmList, String id) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.tipoDeUuario = tipoDeUuario;
        this.alumnosRealmList = alumnosRealmList;
        this.id = id;
    }

    public User(String nombre, String telefono, String email, String contraseña, int tipoDeUuario, String id) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.tipoDeUuario = tipoDeUuario;
        this.id = id;
    }

    public User(String nombre, String telefono, String email, String contraseña, int tipoDeUuario) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.tipoDeUuario = tipoDeUuario;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
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

    public RealmList<Alumnos> getAlumnosRealmList() {
        return alumnosRealmList;
    }

    public void setAlumnosRealmList(RealmList<Alumnos> alumnosRealmList) {
        this.alumnosRealmList = alumnosRealmList;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", tipoDeUuario=" + tipoDeUuario +
                ", alumnosRealmList=" + alumnosRealmList +
                ", imageUrl='" + imageUrl + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
