package homecomingalpha.ederpadilla.example.com.proyectoipn.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ederpadilla on 10/10/16.
 */

public class Constantes {
    public static final String STORAGE_DB_URL="https://homecoming-alpha.firebaseio.com/";
    public static final String USERS="users";



    public static final int INPUT_EMPTY_EMAIL = 0;
    public static final int INPUT_INVALID_EMAIL = -1;
    public static final int INPUT_OK = 1;
    public static final int INPUT_EMPTY_PASSWORD = 0;
    public static final int INPUT_INVALID_PHONE = 0;
    public static final int USUARIO_PADRE_MADRE=0;
    public static final int USUARIO_PROFESOR=1;
    public static final int USUARIO_SIN_DEFINIR=3;
    public static final String LLAVE_LOGIN="LLAVE_LOGIN";
    public static final String LLAVE_LOG="LLAVE_LOG";
    public static final String LLAVE_NOMBRE="NOMBRE";
    public static final String LLAVE_TELEFONO="TELEFONO";
    public static final String LLAVE_EMAIL="EMAIL";
    public static final String LLAVE_TIPO_DE_USUARIO="TIPO DE USUARIO";
    public static final String LLAVE_CONTRASEÑA="PASS";
    public static final String LLAVE_ALUMNO_CODIGO="codigoAlumno";
    public static final String LLAVE_LOGIN_MAIL="email";
    public static final String LLAVE_USUARIO_ID="id";
    public static final String LLAVE_USUARIO_IMAGE_URL = "imageUrl";

    public static final String EMPTYFIELD="CAMPO VACIO";
    public static final int REQUEST_CAMERA = 20;
    public static final int SELECT_FILE = 30;

    public static final String FIREBASE_DB_USERS = "Users";
    public static final String FIREBASE_DB_USER_NOMBRE = "nombre";
    public static final String FIREBASE_DB_USEr_TELEFONO = "telefono";
    public static final String FIREBASE_DB_USER_EMAIL = "email";
    public static final String FIREBASE_DB_USER_TIPO_DE_URUASIO = "tipoDeUuario";
    public static final String FIREBASE_DB_USER_PHOTO_URL = "imageUrl";
    public static final String FIREBASE_DB_USER_LIST = "alumnosRealmList";
    public static final String FIREBASE_DB_USER_ID = "id";
    public static final String FIREBASE_DB_USER_PORFILE_PICTURE_URL = "gs://proyectoipn-a83a9.appspot.com";

    public static final String FIREBASE_DB_STUDENTS_PORFILE_PICTURE_URL = "gs://proyectoipn-a83a9.appspot.com/estudiantes/";

    public static final String FIREBASE_DB_STUDENTS = "Estudiantes";
    public static final String FIREBASE_DB_STUDENTS_NAME = "nombreCompletoAlumno";
    public static final String FIREBASE_DB_STUDENTS_AGE = "edadAlumno";
    public static final String FIREBASE_DB_STUDENTS_BIRTHDATE = "fechaNacimientoAlumno";
    public static final String FIREBASE_DB_STUDENTS_BLODTYPE = "tipoDeSangreAlumno";
    public static final String FIREBASE_DB_STUDENTS_PHONE = "telefonoAlumno";
    public static final String FIREBASE_DB_STUDENTS_GROUP = "grupoAlumno";
    public static final String FIREBASE_DB_STUDENTS_PHOTO_URL = "fotoAlumnoUrl";
    public static final String FIREBASE_DB_STUDENTS_USERID= "idDelProfesor";
    public static final String FIREBASE_DB_STUDENTS_STATE= "estado";
    public static final String FIREBASE_DB_STUDENTS_PERSON_WHOS_GONNA_GET_IT= "personaQueRecogera";
    public static final String FIREBASE_DB_STUDENTS_DATE= "fecha";
    public static final String FIREBASE_DB_STUDENTS_HOUR= "hora";
    public static final String FIREBASE_DB_STUDENTS_WHO_GET_IT= "personaQueRecogio";
    public static final String FIREBASE_DB_STUDENTS_USERLIST= "usersList";
    public static final String FIREBASE_DB_STUDENTS_CODE= "codigoAlumno";


    public static final String NOMBRE ="nombre" ;
    public static final String LLAVE_KEEP = "KEEP";
    public static final int STUDENT_STATE_NOT_IN_SCHOOL = 0;
    public static final int STUDET_STATE_SOMEONE_ELSE = 1;
    public static final int STUDENT_STATE_LATE = 2;
    public static final int STUDENT_STATE_ON_MY_WAY = 3;
    public static final int STUDENT_STATE_NOT_YET = 4;
}
