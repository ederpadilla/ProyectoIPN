package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;


import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.FacebookUserResponse;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.User;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Constantes;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Util;
import io.realm.Realm;

public class EntrarActivity extends AppCompatActivity {

}
