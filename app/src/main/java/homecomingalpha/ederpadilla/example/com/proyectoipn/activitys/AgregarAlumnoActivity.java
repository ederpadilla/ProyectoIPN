package homecomingalpha.ederpadilla.example.com.proyectoipn.activitys;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.silvestrpredko.dotprogressbar.DotProgressBar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import homecomingalpha.ederpadilla.example.com.proyectoipn.R;
import homecomingalpha.ederpadilla.example.com.proyectoipn.models.Alumnos;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Constantes;
import homecomingalpha.ederpadilla.example.com.proyectoipn.util.Util;
import io.realm.Realm;

public class AgregarAlumnoActivity extends AppCompatActivity {
    @BindView(R.id.cimv_estudiante_perfil)
    CircularImageView cimv_estudiante_perfil;
    @BindView(R.id.et_agregar_alumno_nombre)
    TextInputEditText et_agregar_alumno_nombre;
    @BindView(R.id.et_agregar_alumno_edad)
    TextInputEditText et_agregar_alumno_edad;
    @BindView(R.id.et_agregar_alumno_fecha)
    TextInputEditText et_agregar_alumno_fecha;
    @BindView(R.id.agregar_alumno_spinner_sangre)
    MaterialSpinner spinner;
    @BindView(R.id.et_agregar_alumno_telefono)
    TextInputEditText et_agregar_alumno_telefono;
    @BindView(R.id.et_agregar_alumno_grupo)
    TextInputEditText et_agregar_alumno_grupo;
    @BindView(R.id.dot_progress_bar_student)
    DotProgressBar dotProgressBar;
    @BindView(R.id.btn_agregar_estudiante)
    Button btn_agregar_estudiante;
    private String tipoDeSangre="";
    private Bitmap studentProfileImage;
    private List<String> permissions= new ArrayList<>();

    private String studentImageUrl;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private StorageReference mStorageRef;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_alumno);
        ButterKnife.bind(this);
        spinnerAdapter();
        firebaseInit();
    }

    private void firebaseInit() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
        }
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void backActivity(){
        Intent intent = new Intent(AgregarAlumnoActivity.this,PerfilActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       backActivity();
    }
    @OnClick(R.id.img_arrow_back)
    public void goBack(){
      backActivity();
    }
    @OnClick(R.id.btn_agregar_estudiante)
    public void agregarAlumno(){
        validarCampor();
    }

    private void validarCampor() {

        String nombre,edad,fecha,telefono,grupo;
            if (
                Util.emptyField(et_agregar_alumno_nombre)==0||
                Util.emptyField(et_agregar_alumno_edad)==0||
                Util.emptyField(et_agregar_alumno_fecha)==0||
                Util.emptyField(et_agregar_alumno_grupo)==0||
                Util.emptyField(et_agregar_alumno_telefono)==0){
                Util.showToast(getApplicationContext(),getString(R.string.camposvacios));

            }else if (studentProfileImage==null){
                Util.showToast(getApplicationContext(),getString(R.string.falta_foto_alumno));
            }else{
               if (tipoDeSangre.equals("")){
                   Util.showToast(getApplicationContext(),"Falta especificar el tipo de sangre");
               }else {
                   if (Util.isValidPhoneNumber(
                           et_agregar_alumno_telefono.getText().toString())==Constantes.INPUT_OK){
                       btn_agregar_estudiante.setVisibility(View.GONE);
                       dotProgressBar.setVisibility(View.VISIBLE);
                       writeInFireBase(asignarValoresRealesAlumno(firebaseUser));
                 //crearAlumno(asignarValoresRealesAlumno());
                  // Util.showLog("Se crea "+asignarValoresRealesAlumno().toString());
                  // Intent intent = new Intent(AgregarAlumnoActivity.this,
                  //         PerfilActivity.class);
                  // startActivity(intent);
                  // finish();
                   }else{
                       Util.showToast(getApplicationContext(),getString(R.string.telefono_invalido));
                   }
               }
               }
    }
    private void writeInFireBase(final Alumnos alumnos){
        mStorageRef= FirebaseStorage.getInstance().getReferenceFromUrl(Constantes.FIREBASE_DB_STUDENTS_PORFILE_PICTURE_URL).child(alumnos.getCodigoAlumno());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        studentProfileImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mStorageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            Util.showToast(getApplicationContext(),getString(R.string.problemas));
                dotProgressBar.setVisibility(View.GONE);
                btn_agregar_estudiante.setVisibility(View.VISIBLE);
            }
        }
        ).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                studentImageUrl=downloadUrl.toString();
                DatabaseReference mFirebaseDatabase = database.getReference(Constantes.FIREBASE_DB_STUDENTS).child(alumnos.getCodigoAlumno());
                Map<String, Object> map = new HashMap<>();
                map.put(Constantes.FIREBASE_DB_STUDENTS_NAME,alumnos.getNombreCompletoAlumno());
                map.put(Constantes.FIREBASE_DB_STUDENTS_AGE,alumnos.getEdadAlumno());
                map.put(Constantes.FIREBASE_DB_STUDENTS_BIRTHDATE,alumnos.getFechaNacimientoAlumno());
                map.put(Constantes.FIREBASE_DB_STUDENTS_BLODTYPE,alumnos.getTipoDeSangreAlumno());
                map.put(Constantes.FIREBASE_DB_STUDENTS_PHONE,alumnos.getTelefonoAlumno());
                map.put(Constantes.FIREBASE_DB_STUDENTS_GROUP,alumnos.getGrupoAlumno());
                map.put(Constantes.FIREBASE_DB_STUDENTS_PHOTO_URL,studentImageUrl);
                map.put(Constantes.FIREBASE_DB_STUDENTS_USERID,alumnos.getIdDelProfesor());
                map.put(Constantes.FIREBASE_DB_STUDENTS_USERLIST,alumnos.getUsersList());
                map.put(Constantes.FIREBASE_DB_STUDENTS_CODE,alumnos.getCodigoAlumno());
                mFirebaseDatabase.updateChildren(map);
                 Intent intent = new Intent(AgregarAlumnoActivity.this,
                         PerfilActivity.class);
                 startActivity(intent);
                 finish();

            }
        });


    }

    private void spinnerAdapter() {
        final String A_POSITIVO=getString(R.string.A_POSITIVO_TIPODESANGRE);
        final String B_POSITIVO=getString(R.string.B_POSITIVO_TIPODESANGRE);
        final String AB_POSITIVO=getString(R.string.AB_POSITIVO_TIPODESANGRE);
        final String O_POSITIVO=getString(R.string.O_POSITIVO_TIPODESANGRE);
        final String A_NEGATIVO=getString(R.string.A_NEGATIVO_TIPODESANGRE);
        final String B_NEGATIVO=getString(R.string.B_NEGATIVO_TIPODESANGRE);
        final String AB_NEGATIVO=getString(R.string.AB_NEGATIVO_TIPODESANGRE);
        final String O_NEGATIVO=getString(R.string.O_NEGATIVO_TIPODESANGRE);


        spinner.setItems(getString(R.string.tipo_de_sangre),A_POSITIVO,A_NEGATIVO,B_POSITIVO,B_NEGATIVO,O_POSITIVO,O_NEGATIVO,AB_POSITIVO,AB_NEGATIVO);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

              if (item.equals(A_POSITIVO)) {
                  tipoDeSangre= getString(R.string.A_POSITIVO_TIPODESANGRE);
              }else if (item.equals(A_NEGATIVO)){
                  tipoDeSangre= getString(R.string.A_NEGATIVO_TIPODESANGRE);
              }else if (item.equals(B_POSITIVO)){
                  tipoDeSangre= getString(R.string.B_POSITIVO_TIPODESANGRE);
              }else if(item.equals(B_NEGATIVO)){
                  tipoDeSangre= getString(R.string.B_NEGATIVO_TIPODESANGRE);
              }else if(item.equals(O_NEGATIVO)){
                  tipoDeSangre= getString(R.string.O_NEGATIVO_TIPODESANGRE);
              }else if(item.equals(O_POSITIVO)){
                  tipoDeSangre= getString(R.string.O_POSITIVO_TIPODESANGRE);
              }else if(item.equals(AB_NEGATIVO)){
                  tipoDeSangre= getString(R.string.AB_NEGATIVO_TIPODESANGRE);
              }else if(item.equals(AB_POSITIVO)){
                  tipoDeSangre= getString(R.string.AB_POSITIVO_TIPODESANGRE);
              }else {
                  tipoDeSangre="";
              }
            }
        });
    }
    private String generarCodigo(){
        String codigo=null;
        Random r = new Random();
        char penultimaLetra = (char)(r.nextInt(26) + 'a');
        char ultimaLetra = (char)(r.nextInt(26) + 'a');
        char letrapenultima= Character.toUpperCase(penultimaLetra);
        char letraUltima= Character.toUpperCase(ultimaLetra);
        String nom= et_agregar_alumno_nombre.getText().toString().substring(0,2).toUpperCase();
        int numeroRandom= Util.randomInt(0,1000);
        codigo=nom+numeroRandom+letrapenultima+letraUltima;
        Util.showLog(codigo);
    return codigo;
    }
    private Alumnos asignarValoresRealesAlumno(FirebaseUser firebaseUser){
        String nombre,edad,fecha,telefono,grupo;
        nombre=et_agregar_alumno_nombre.getText().toString();
        edad=et_agregar_alumno_edad.getText().toString();
        fecha=et_agregar_alumno_fecha.getText().toString();
        telefono=et_agregar_alumno_telefono.getText().toString();
        grupo=et_agregar_alumno_grupo.getText().toString();
        Alumnos alumnoCreado= new Alumnos(nombre,edad,fecha,tipoDeSangre,telefono,grupo,firebaseUser.getUid(),generarCodigo());
        return alumnoCreado;
    }
    @OnClick(R.id.cimv_estudiante_perfil)
    public void setPicture(){
        if (Util.hasPermissions(getApplicationContext(),permissions.toArray(new String[permissions.size()]))){
            selectImage();
        }else{
            Util.showLog("No tiene permisos");
            String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            if(!Util.hasPermissions(this, PERMISSIONS)){
                Util.showToast(getApplicationContext(),"No ah brindado permisos de acceso a la aplicación");
            }
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Cámara", "Galería"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AgregarAlumnoActivity.this);
        builder.setTitle("Obtener fotografía");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Cámara")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment
                            .getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    Util.showLog("antes del start de camara");
                    startActivityForResult(intent, Constantes.REQUEST_CAMERA);
                } else if (items[item].equals("Galería")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            Constantes.SELECT_FILE);
                }
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Util.showLog("Entra al activityresult");
        super.onActivityResult(requestCode, resultCode, data);
        Util.showLog("Entra despues del super");
        if (requestCode == Constantes.REQUEST_CAMERA) {
            if (resultCode==RESULT_OK){
                File f = new File(Environment.getExternalStorageDirectory()
                        .toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    BitmapFactory.Options btmapOptions = new BitmapFactory.Options();

                    studentProfileImage = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            btmapOptions);

                    cimv_estudiante_perfil.setImageBitmap(studentProfileImage);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream fOut = null;
                    File file = new File(path, String.valueOf(System
                            .currentTimeMillis()) + ".jpg");
                    try {
                        fOut = new FileOutputStream(file);
                        studentProfileImage.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                        fOut.flush();
                        fOut.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == Constantes.SELECT_FILE&&resultCode==RESULT_OK) {
            Uri selectedImageUri = data.getData();

            String tempPath = Util.getPath(selectedImageUri, AgregarAlumnoActivity.this);
            BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
            studentProfileImage = BitmapFactory.decodeFile(tempPath, btmapOptions);
            cimv_estudiante_perfil.setImageBitmap(studentProfileImage);
            }
        }


    }


