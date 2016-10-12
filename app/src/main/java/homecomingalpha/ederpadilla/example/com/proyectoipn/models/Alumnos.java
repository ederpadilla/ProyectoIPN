package homecomingalpha.ederpadilla.example.com.proyectoipn.models;

/**
 * Created by ederpadilla on 12/10/16.
 */

public class Alumnos {
    private String nombreCompleto;
    private String edad;
    private String fechaNacimiento;

    public Alumnos() {
    }

    public Alumnos(String nombreCompleto, String edad, String fechaNacimiento) {
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
