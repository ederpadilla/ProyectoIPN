package homecomingalpha.ederpadilla.example.com.proyectoipn.models;

import java.util.Arrays;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ederpadilla on 12/10/16.
 */

public class Alumnos extends RealmObject {
    private String nombreCompletoAlumno;
    private String edadAlumno;
    private String fechaNacimientoAlumno;
    private String tipoDeSangreAlumno;
    private String telefonoAlumno;
    private String grupoAlumno;
    private String fotoAlumnoUrl;
    private byte[]bytes;
    private String idDelProfesor;
    @PrimaryKey
    private String codigoAlumno;

    public Alumnos() {
    }

    public Alumnos(String nombreCompleto, String edad, String fechaNacimiento) {
        this.nombreCompletoAlumno = nombreCompleto;
        this.edadAlumno = edad;
        this.fechaNacimientoAlumno = fechaNacimiento;
    }

    public Alumnos(String nombreCompletoAlumno, String edadAlumno, String fechaNacimientoAlumno, String tipoDeSangreAlumno, String telefonoAlumno, String grupoAlumno, String fotoAlumnoUrl, byte[] bytes, String idDelProfesor, String codigoAlumno) {
        this.nombreCompletoAlumno = nombreCompletoAlumno;
        this.edadAlumno = edadAlumno;
        this.fechaNacimientoAlumno = fechaNacimientoAlumno;
        this.tipoDeSangreAlumno = tipoDeSangreAlumno;
        this.telefonoAlumno = telefonoAlumno;
        this.grupoAlumno = grupoAlumno;
        this.fotoAlumnoUrl = fotoAlumnoUrl;
        this.bytes = bytes;
        this.idDelProfesor = idDelProfesor;
        this.codigoAlumno = codigoAlumno;
    }

    public Alumnos(String nombreCompletoAlumno, String edadAlumno, String fechaNacimientoAlumno, String tipoDeSangreAlumno, String telefonoAlumno, String grupoAlumno, String idDelProfesor, String codigoAlumno) {
        this.nombreCompletoAlumno = nombreCompletoAlumno;
        this.edadAlumno = edadAlumno;
        this.fechaNacimientoAlumno = fechaNacimientoAlumno;
        this.tipoDeSangreAlumno = tipoDeSangreAlumno;
        this.telefonoAlumno = telefonoAlumno;
        this.grupoAlumno = grupoAlumno;
        this.idDelProfesor = idDelProfesor;
        this.codigoAlumno = codigoAlumno;
    }

    public Alumnos(String nombreCompletoAlumno, String edadAlumno, String fechaNacimientoAlumno, String tipoDeSangreAlumno, String telefonoAlumno, String grupoAlumno, byte[] bytes, String idDelProfesor, String codigoAlumno) {
        this.nombreCompletoAlumno = nombreCompletoAlumno;
        this.edadAlumno = edadAlumno;
        this.fechaNacimientoAlumno = fechaNacimientoAlumno;
        this.tipoDeSangreAlumno = tipoDeSangreAlumno;
        this.telefonoAlumno = telefonoAlumno;
        this.grupoAlumno = grupoAlumno;
        this.bytes = bytes;
        this.idDelProfesor = idDelProfesor;
        this.codigoAlumno = codigoAlumno;
    }

    public Alumnos(String nombreCompletoAlumno, String edadAlumno, String fechaNacimientoAlumno, String tipoDeSangreAlumno, String telefonoAlumno, String grupoAlumno, String codigoAlumno) {
        this.nombreCompletoAlumno = nombreCompletoAlumno;
        this.edadAlumno = edadAlumno;
        this.fechaNacimientoAlumno = fechaNacimientoAlumno;
        this.tipoDeSangreAlumno = tipoDeSangreAlumno;
        this.telefonoAlumno = telefonoAlumno;
        this.grupoAlumno = grupoAlumno;
        this.codigoAlumno = codigoAlumno;
    }

    public Alumnos(String nombreCompletoAlumno, String edadAlumno, String fechaNacimientoAlumno, String tipoDeSangreAlumno, String telefonoAlumno, String grupoAlumno, byte[] bytes, String codigoAlumno) {
        this.nombreCompletoAlumno = nombreCompletoAlumno;
        this.edadAlumno = edadAlumno;
        this.fechaNacimientoAlumno = fechaNacimientoAlumno;
        this.tipoDeSangreAlumno = tipoDeSangreAlumno;
        this.telefonoAlumno = telefonoAlumno;
        this.grupoAlumno = grupoAlumno;
        this.bytes = bytes;
        this.codigoAlumno = codigoAlumno;
    }

    public String getIdDelProfesor() {
        return idDelProfesor;
    }

    public void setIdDelProfesor(String idDelProfesor) {
        this.idDelProfesor = idDelProfesor;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getNombreCompletoAlumno() {
        return nombreCompletoAlumno;
    }

    public void setNombreCompletoAlumno(String nombreCompletoAlumno) {
        this.nombreCompletoAlumno = nombreCompletoAlumno;
    }

    public String getEdadAlumno() {
        return edadAlumno;
    }

    public void setEdadAlumno(String edadAlumno) {
        this.edadAlumno = edadAlumno;
    }

    public String getFechaNacimientoAlumno() {
        return fechaNacimientoAlumno;
    }

    public void setFechaNacimientoAlumno(String fechaNacimientoAlumno) {
        this.fechaNacimientoAlumno = fechaNacimientoAlumno;
    }

    public String getTipoDeSangreAlumno() {
        return tipoDeSangreAlumno;
    }

    public void setTipoDeSangreAlumno(String tipoDeSangreAlumno) {
        this.tipoDeSangreAlumno = tipoDeSangreAlumno;
    }

    public String getTelefonoAlumno() {
        return telefonoAlumno;
    }

    public void setTelefonoAlumno(String telefonoAlumno) {
        this.telefonoAlumno = telefonoAlumno;
    }

    public String getGrupoAlumno() {
        return grupoAlumno;
    }

    public void setGrupoAlumno(String grupoAlumno) {
        this.grupoAlumno = grupoAlumno;
    }

    public String getCodigoAlumno() {
        return codigoAlumno;
    }

    public void setCodigoAlumno(String codigoAlumno) {
        this.codigoAlumno = codigoAlumno;
    }

    public String getFotoAlumnoUrl() {
        return fotoAlumnoUrl;
    }

    public void setFotoAlumnoUrl(String fotoAlumnoUrl) {
        this.fotoAlumnoUrl = fotoAlumnoUrl;
    }

    @Override
    public String toString() {
        return "Alumnos{" +
                "nombreCompletoAlumno='" + nombreCompletoAlumno + '\'' +
                ", edadAlumno='" + edadAlumno + '\'' +
                ", fechaNacimientoAlumno='" + fechaNacimientoAlumno + '\'' +
                ", tipoDeSangreAlumno='" + tipoDeSangreAlumno + '\'' +
                ", telefonoAlumno='" + telefonoAlumno + '\'' +
                ", grupoAlumno='" + grupoAlumno + '\'' +
                ", fotoAlumnoUrl='" + fotoAlumnoUrl + '\'' +
                ", bytes=" + Arrays.toString(bytes) +
                ", idDelProfesor='" + idDelProfesor + '\'' +
                ", codigoAlumno='" + codigoAlumno + '\'' +
                '}';
    }
}
