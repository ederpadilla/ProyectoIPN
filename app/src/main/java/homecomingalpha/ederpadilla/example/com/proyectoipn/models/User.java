package homecomingalpha.ederpadilla.example.com.proyectoipn.models;

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
    private String contraseña;
    private int tipoDeUuario;
    private RealmList<Alumnos> alumnosRealmList;
    @PrimaryKey
    String id;

    public User() {
    }

    public User(String nombre, String telefono, String email, String contraseña, int tipoDeUuario, RealmList<Alumnos> alumnosRealmList, String id) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.contraseña = contraseña;
        this.tipoDeUuario = tipoDeUuario;
        this.alumnosRealmList = alumnosRealmList;
        this.id = id;
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

    public RealmList<Alumnos> getAlumnosRealmList() {
        return alumnosRealmList;
    }

    public void setAlumnosRealmList(RealmList<Alumnos> alumnosRealmList) {
        this.alumnosRealmList = alumnosRealmList;
    }

    @Override
    public String toString() {
        return "User{" +
                "nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", tipoDeUuario=" + tipoDeUuario +
                ", alumnosRealmList=" + alumnosRealmList +
                ", id='" + id + '\'' +
                '}';
    }
}
