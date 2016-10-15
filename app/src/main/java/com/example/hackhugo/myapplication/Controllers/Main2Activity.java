package com.example.hackhugo.myapplication.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hackhugo.myapplication.R;
import com.example.hackhugo.myapplication.RestClient.CallbackRest.RespuestaObject;
import com.example.hackhugo.myapplication.RestClient.RestClient;
import com.example.hackhugo.myapplication.models.Contactos;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;

public class Main2Activity extends AppCompatActivity implements Validator.ValidationListener {
    @NotEmpty(message = "Es requerido el campo Telefono")
    EditText txtTelefono;
    @NotEmpty(message = "Es requerido el campo Nombre")
    EditText txtNombre;
    @NotEmpty(message = "Es requerido el campo Correo")
    @Email(message = "Ingresa un correo correcto")
    EditText txtCorreo;
    @NotEmpty(message = "Es requerido el campo Mensaje")
    EditText txtMensaje;
    Button btnguardar;
    RestClient oPeticion = new RestClient();
    String idContacto = "";
    String id;
    String FlagUpdate;
    Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Realm realm = Realm.getDefaultInstance();
        Intent intent = getIntent();
        idContacto = intent.getStringExtra("idContacto");
        FlagUpdate = intent.getStringExtra("FlagUpdate");
        txtNombre = (EditText) findViewById(R.id.txtnombre);
        txtCorreo = (EditText) findViewById(R.id.txtemail);
        txtTelefono = (EditText) findViewById(R.id.txttelefono);
        txtMensaje = (EditText) findViewById(R.id.txtmensaje);
        btnguardar = (Button) findViewById(R.id.btnguardar);
        validator = new Validator(this);
        validator.setValidationListener(this);
        if (FlagUpdate.equals("Update")) {
            Contactos result = realm.where(Contactos.class).equalTo("id", Integer.parseInt(idContacto)).findFirst();
            id = String.valueOf(result.getIdContactos());
            txtNombre.setText(result.getNombre());
            txtCorreo.setText(result.getEmail());
            txtTelefono.setText(result.getTelefono());
            txtMensaje.setText(result.getMensaje());
            btnguardar.setText("Editar");
        }
        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        if (FlagUpdate.equals("Update")) {
            oPeticion.EditarContacto(getApplicationContext(), id, txtNombre.getText().toString(), txtCorreo.getText().toString(), txtTelefono.getText().toString(), txtMensaje.getText().toString(), new RespuestaObject() {
                @Override
                public void respuesta(JSONObject response) {
                    Log.d("Respuest------------", response.toString());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void error(VolleyError error) {
                    Log.d("error------------", error.toString());
                }
            });
        } else {

            oPeticion.guardarContacto(getApplicationContext(), txtNombre.getText().toString(), txtCorreo.getText().toString(), txtTelefono.getText().toString(), txtMensaje.getText().toString(), new RespuestaObject() {
                @Override
                public void respuesta(JSONObject response) {
                    Log.d("Respuest------------", response.toString());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

                @Override
                public void error(VolleyError error) {
                    Log.d("error------------", error.toString());
                }
            });
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
