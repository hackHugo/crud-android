package com.example.hackhugo.myapplication.Controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hackhugo.myapplication.R;
import com.example.hackhugo.myapplication.RestClient.CallbackRest.RespuestaObject;
import com.example.hackhugo.myapplication.RestClient.RestClient;
import com.example.hackhugo.myapplication.models.Contactos;

import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by hackhugo on 11/10/2016.
 */

public class TablaAdapter extends ArrayAdapter<Contactos>{
    RestClient oPeticion = new RestClient();
    public TablaAdapter(Context context, ArrayList<Contactos> contatos) {
        super(context, 0, contatos);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Realm realm = Realm.getDefaultInstance();
        // Get the data item for this position
        Contactos contactos = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tablacomponent, parent, false);
        }
        // Lookup view for data population
        TextView lbl1 = (TextView) convertView.findViewById(R.id.lbl1);
        TextView lbl2 = (TextView) convertView.findViewById(R.id.lbl2);
        Button btnEditar = (Button) convertView.findViewById(R.id.btnEditar);
        Button btnEliminar = (Button) convertView.findViewById(R.id.btnEliminar);
        btnEditar.setTag(position);
        btnEliminar.setTag(position);
        // Populate the data into the template view using the data object
        lbl1.setText(contactos.getId().toString());
        lbl2.setText(contactos.email);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                // Access the row position here to get the correct data item
                Contactos cont = getItem(position);
                Intent intent = new Intent(getContext(), Main2Activity.class);
                Contactos result = realm.where(Contactos.class).equalTo("id",position+1).findFirst();
                intent.putExtra("idContacto",result.getId().toString());
                intent.putExtra("FlagUpdate","Update");
               getContext().startActivity(intent);
                Toast.makeText(getContext(),"posision editar: "+position,Toast.LENGTH_SHORT).show();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                // Access the row position here to get the correct data item
                Contactos result = realm.where(Contactos.class).equalTo("id",position+1).findFirst();
                Contactos cont = getItem(position);
              oPeticion.EliminarContacto(getContext(), result.getIdContactos().toString(), new RespuestaObject() {
                  @Override
                  public void respuesta(JSONObject response) {
                      Log.d("Respuest------------",response.toString());
                      Intent intent = new Intent(getContext(), MainActivity.class);
                      getContext().startActivity(intent);
                  }

                  @Override
                  public void error(VolleyError error) {
                      Log.d("Error------------",error.toString());
                  }
              });
                Toast.makeText(getContext(),"posision eliminar: "+position,Toast.LENGTH_SHORT).show();
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}
