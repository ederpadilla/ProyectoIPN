package homecomingalpha.ederpadilla.example.com.proyectoipn.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import homecomingalpha.ederpadilla.example.com.proyectoipn.models.User;

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
    public static int randomInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
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

    public static void saveSharedPreferences(Context context,User usuario){
        SharedPreferences mSharedPreferences =  context.getSharedPreferences(Constantes.LLAVE_LOGIN,0);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constantes.LLAVE_NOMBRE,usuario.getNombre());
        editor.putString(Constantes.LLAVE_TELEFONO,usuario.getTelefono());
        editor.putString(Constantes.LLAVE_EMAIL,usuario.getEmail());
        editor.putInt(Constantes.LLAVE_TIPO_DE_USUARIO,usuario.getTipoDeUuario());
        editor.putString(Constantes.LLAVE_USUARIO_ID,usuario.getId());
        editor.putString(Constantes.LLAVE_USUARIO_IMAGE_URL,usuario.getImageUrl());
        editor.commit();
    }
    public static String getSharerPreferencesUserName(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.LLAVE_LOGIN,0);
        return sharedPreferences.getString(Constantes.LLAVE_NOMBRE,"");
    }
    public static String getSharerPreferencesUsePhone(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.LLAVE_LOGIN,0);
        return sharedPreferences.getString(Constantes.LLAVE_TELEFONO,"");
    }
    public static String getSharerPreferencesUserMail(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.LLAVE_LOGIN,0);
        return sharedPreferences.getString(Constantes.LLAVE_EMAIL,"");
    }
    public static String getSharerPreferencesUserPass(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.LLAVE_LOGIN,0);
        return sharedPreferences.getString(Constantes.LLAVE_CONTRASEÑA,"");
    }
    public static int getSharerPreferencesUserType(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.LLAVE_LOGIN,0);
        return sharedPreferences.getInt(Constantes.LLAVE_TIPO_DE_USUARIO,3);
    }

    public static String getSharerPreferencesUserId(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.LLAVE_LOGIN,0);
        return sharedPreferences.getString(Constantes.LLAVE_USUARIO_ID,"");
    }
    public static User getUserInSharedPreferences(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.LLAVE_LOGIN,0);
        User userSharedPreferences = new User();
        userSharedPreferences.setNombre(sharedPreferences.getString(Constantes.LLAVE_NOMBRE,""));
        userSharedPreferences.setTelefono(sharedPreferences.getString(Constantes.LLAVE_TELEFONO,""));
        userSharedPreferences.setEmail(sharedPreferences.getString(Constantes.LLAVE_EMAIL,""));
        userSharedPreferences.setTipoDeUuario(sharedPreferences.getInt(Constantes.LLAVE_TIPO_DE_USUARIO,3));
        userSharedPreferences.setImageUrl(sharedPreferences.getString(Constantes.LLAVE_USUARIO_IMAGE_URL,""));
        userSharedPreferences.setId(sharedPreferences.getString(Constantes.LLAVE_USUARIO_ID,""));
        return userSharedPreferences;
    }
    public static void borrarSharedPreferences(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constantes.LLAVE_LOGIN, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
