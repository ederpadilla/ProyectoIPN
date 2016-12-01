package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.github.silvestrpredko.dotprogressbar.DotProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.FacebookUserResponse;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.User;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Constantes;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Util;
import io.realm.Realm;

public class FaceboolLoginActivity extends AppCompatActivity {
    @BindView(R.id.login_et_mail)
    TextInputEditText et_mail;
    @BindView(R.id.login_et_password)
    TextInputEditText et_password;
    Realm realm;
    @BindView(R.id.fb_login_button)
    LoginButton loginButton;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.dot_progress_bar_login)
    DotProgressBar dot_progress_bar_login;
    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;
    int PERMISSION_ALL = 1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser firebaseUser;
    private User facebookUser;
    private AccessToken accessToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebool_login);
        ButterKnife.bind(this);
        getPermition();
        sharedPreferences =this.getSharedPreferences(Constantes.LLAVE_LOGIN,0);
        checkForUserLogIn();
    }
    private void authListernerSettings(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Util.showLog("onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Util.showLog("onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    private void checkForUserLogIn(){
        mAuth = FirebaseAuth.getInstance();
        if (sharedPreferences.contains(Constantes.LLAVE_NOMBRE)){
            callbackManager= CallbackManager.Factory.create();
            facebook();
            Util.borrarSharedPreferences(getApplicationContext());
        }else {
            Intent intent = new Intent(FaceboolLoginActivity.this,SplashActivity.class);
            startActivity(intent);
            FaceboolLoginActivity.this.finish();
        }
    }

    private void facebook() {
        loginButton.setReadPermissions("public_profile","email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Util.showLog("On succes!!!!!");
                accessToken = loginResult.getAccessToken();
                GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Gson gson = new Gson();
                        FacebookUserResponse facebookUserResponse =
                                gson.fromJson(object.toString(),FacebookUserResponse.class);
                         facebookUser = new User(facebookUserResponse.getName(),facebookUserResponse.getId(),
                         facebookUserResponse.getPicture().getData().getUrl(),3);
                        handleFacebookAccessToken(accessToken);

                    }
                });
                Bundle parametros = new Bundle();
                parametros.putString("fields","id,email,picture,name");
                graphRequest.setParameters(parametros);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                Util.showLog("Se Cancelo!!!!!");

            }

            @Override
            public void onError(FacebookException error) {
                Util.showLog("Bailo berta");

            }
        });
    }
    private void handleFacebookAccessToken(AccessToken token) {
        Util.showLog( "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Util.showLog("signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Util.showLog("signInWithCredential"+ task.getException());
                            Util.showToast(getApplicationContext(), "Facebook Authentication failed.");
                        }else {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            DatabaseReference mFirebaseDatabase = database.getReference(Constantes.FIREBASE_DB_USERS).child(firebaseUser.getUid());
                            Map<String, Object> map = new HashMap<>();
                            map.put(Constantes.FIREBASE_DB_USER_NOMBRE,facebookUser.getNombre());
                            map.put(Constantes.FIREBASE_DB_USEr_TELEFONO,facebookUser.getTelefono());
                            map.put(Constantes.FIREBASE_DB_USER_EMAIL,facebookUser.getEmail());
                            map.put(Constantes.FIREBASE_DB_USER_TIPO_DE_URUASIO,facebookUser.getTipoDeUuario());
                            map.put(Constantes.FIREBASE_DB_USER_ID,facebookUser.getId());
                            map.put(Constantes.FIREBASE_DB_USER_PHOTO_URL,facebookUser.getImageUrl());
                            map.put(Constantes.FIREBASE_DB_USER_LIST,facebookUser.getAlumnosRealmList());
                            mFirebaseDatabase.updateChildren(map);
                            Util.showLog("signInWithCredential:onComplete:" + task.isSuccessful());
                            Intent intent = new Intent(FaceboolLoginActivity.this,PerfilActivity.class);
                            Util.saveSharedPreferences(getApplicationContext(),facebookUser);
                            startActivity(intent);
                            finish();
                        }

                        // ...
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
        Util.showLog("Entra a este result");
    }
    @OnClick(R.id.btn_login)
    public void entrar(){
        validarCamposVacios();
    }
    private void validarCamposVacios() {
        if (Util.emptyField(et_mail)==0&&Util.emptyField(et_password)==0){
            Util.showLog("Los dos");
            Util.showToast(getApplicationContext(),getString(R.string.camposvacios));
        }
        else if(Util.emptyField(et_mail)==0){
            Util.showLog("El mail");
            et_mail.setError(getString(R.string.campo_vacio));
        }
        else if(Util.emptyField(et_password)==0){
            Util.showLog("password");
            et_password.setError(getString(R.string.campo_vacio));
        }
        else {
            if (Util.isValidEmail(et_mail.getText().toString())!=1){
                Util.showToast(getApplicationContext(),getString(R.string.invalid_mail));
            }else{
                loginButton.setVisibility(View.GONE);
                btn_login.setVisibility(View.GONE);
                dot_progress_bar_login.setVisibility(View.VISIBLE);
                final String mail =et_mail.getText().toString();
                mAuth.signInWithEmailAndPassword(mail, et_password.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    loginButton.setVisibility(View.VISIBLE);
                                    btn_login.setVisibility(View.VISIBLE);
                                    dot_progress_bar_login.setVisibility(View.GONE);
                                   Util.showToast(getApplicationContext(),"Usuario no registrado o contraseña incorrecta");
                                }else{
                                    Util.showToast(getApplicationContext(),"Usuario reigstrado");
                                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                    Util.showLog("UID "+firebaseUser.getUid());
                                    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference(Constantes.FIREBASE_DB_USERS).child(firebaseUser.getUid());
                                    mDatabase.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            User userFoundInFireBase=dataSnapshot.getValue(User.class);
                                            Util.saveSharedPreferences(getApplicationContext(),userFoundInFireBase);
                                             Intent intent = new Intent(FaceboolLoginActivity.this,
                                                                           PerfilActivity.class);
                                                           startActivity(intent);
                                                           finish();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Util.showToast(getApplicationContext(),getString(R.string.problemas));
                                            loginButton.setVisibility(View.VISIBLE);
                                            btn_login.setVisibility(View.VISIBLE);
                                            dot_progress_bar_login.setVisibility(View.GONE);
                                        }
                                    });
                                }

                                // ...
                            }
                        });
            }
        }


    }


    private void createUser(User user) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }
    @OnClick(R.id.tv_registrarse)
    public void registrarse(){
        Intent intent = new Intent(FaceboolLoginActivity.this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void getPermition() {
        List<String> permissions= new ArrayList<>();
        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasPermissions(getApplicationContext(),permissions.toArray(new String[permissions.size()]))){
            Util.showLog("Tiene permisos");
        }else{
            Util.showLog("No tiene permisos");
            String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            if(!hasPermissions(this, PERMISSIONS)){
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }
        }
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){

        }
    }
}
