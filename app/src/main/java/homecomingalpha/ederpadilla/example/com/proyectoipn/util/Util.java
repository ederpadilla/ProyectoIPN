package homecomingalpha.ederpadilla.example.com.proyectoipn.util;

import android.content.Context;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ederpadilla on 10/10/16.
 */

public class Util {
    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=\\S+$).{6,20})";
    //    "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    private static int permissionLevel = -1;
    private static final String DATA_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";


    public static int isValidEmail(String text) {
        if (text.trim().equals("")) {
            return Constantes.INPUT_EMPTY_EMAIL;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            return Constantes.INPUT_INVALID_EMAIL;
        }
        return Constantes.INPUT_OK;
    }

    public static int isEmptyText(String text) {
        if (text == null || text.trim().equals("")) {
            return Constantes.INPUT_EMPTY_PASSWORD;
        }
        return Constantes.INPUT_OK;
    }

    public static boolean isEmptyField(EditText editText, String s){
        if (s.isEmpty()||s.trim().equals("")){
            editText.setError(Constantes.EMPTYFIELD);
            return false;
        }else
            return true;
    }

    public static int isValidPhoneNumber(String phone) {
        if (phone.length() <= 8) {
            return Constantes.INPUT_INVALID_PHONE;
        } else if (!Patterns.PHONE.matcher(phone).matches()) {
            return Constantes.INPUT_INVALID_PHONE;
        }
        return Constantes.INPUT_OK;
    }

    public static Boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static int emptyField(EditText editText){
        int STATUS_OF_FIELD=0;
        String value = editText.getText().toString();
        if (value.equals("")||value.isEmpty()){
            editText.setError(Constantes.EMPTYFIELD);
            STATUS_OF_FIELD=0;
        }else {
            STATUS_OF_FIELD=1;
        }
        return STATUS_OF_FIELD;

    }
    public static void showToast(Context context , String message) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
    public static void showLog(String log){
        Log.e("MyLog for Debbugging",log);
    }
}
