package com.example.hackhugo.myapplication.Controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hackhugo.myapplication.R;
import com.example.hackhugo.myapplication.RestClient.CallbackRest.RespuestaObject;
import com.example.hackhugo.myapplication.RestClient.RestClient;
import com.example.hackhugo.myapplication.models.Contactos;
import com.example.hackhugo.myapplication.utils.RealmConfiguracion;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;

public class MainActivity extends AppCompatActivity {
    RestClient oCliente = new RestClient();
    RealmConfiguracion oConfig = new RealmConfiguracion();
    ListView mLeadsList;
    ArrayAdapter<String> mLeadsAdapter;
    AVLoadingIndicatorView avi;
    View alertLayout;
    Button btnNuevo;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutInflater inflater = getLayoutInflater();
        listView = (ListView) findViewById(R.id.listaContacto);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        alertLayout = inflater.inflate(R.layout.mensaje, null);
        oConfig.Config(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Contactos> query = realm.where(Contactos.class);
        realm.beginTransaction();
        query.findAll().deleteAllFromRealm();
        realm.commitTransaction();
        peticion();
        btnNuevo = (Button) findViewById(R.id.btnCrear);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                intent.putExtra("FlagUpdate","Create");
                startActivity(intent);
            }
        });
    }

    public void peticion() {

        // or avi.smoothToShow();
        avi.show();
        final Realm realm = Realm.getDefaultInstance();
        oCliente.getContactos(this, new RespuestaObject() {
            @Override
            public void respuesta(JSONObject response) {

                Log.d("Respuesta: ", response.toString());

                try {
                    JSONArray data = (JSONArray) response.get("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject contacto = (JSONObject) data.get(i);
                        Contactos item = new Contactos();
                        item.setId(i+1);
                        item.setIdContactos(Integer.parseInt(contacto.getString("id")));
                        item.setNombre(contacto.getString("nombre"));
                        item.setEmail(contacto.getString("email"));
                        item.setTelefono(contacto.getString("telefono"));
                        item.setMensaje(contacto.getString("mensaje"));
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(item);
                        realm.commitTransaction();
                    }
                    cargaTabla();
                    avi.hide();
                } catch (JSONException jsonError) {

                }

            }

            @Override
            public void error(VolleyError error) {
                mensaje("prueba", error.getMessage());
                Log.d("error: ", error.toString());
            }
        });
    }

    public void cargaTabla() {
        final Realm realm = Realm.getDefaultInstance();
        RealmQuery<Contactos> query = realm.where(Contactos.class);
        ArrayList<Contactos> List = new ArrayList<Contactos>();

        List.addAll(query.findAll());
        TablaAdapter tabla = new TablaAdapter(this, List);
        listView.setAdapter(tabla);
        Log.d("query ", String.valueOf(query.findAll()));

    }

    public void mensaje(String titulo, String mensaje) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(titulo);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        alert.setMessage(mensaje);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getBaseContext(), "Ok clicked", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}
