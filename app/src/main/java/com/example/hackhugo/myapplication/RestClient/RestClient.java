package com.example.hackhugo.myapplication.RestClient;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hackhugo.myapplication.RestClient.CallbackRest.RespuestaObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hackhugo on 08/10/2016.
 */

public class RestClient {
    public String RUTA_API = "http://yourIP/proyectos/apiRest/public/api/v1";

    public void getContactos(Context context, final RespuestaObject respuesta) {
        RequestQueue queue = Volley.newRequestQueue(context);

// Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET,RUTA_API+"/contacto",null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                respuesta.respuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                respuesta.error(error);
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                String mApiKey = "application/json";
                headers.put("Content-Type", mApiKey);
                return headers;
            }
        };


// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public void guardarContacto(Context context, final String nombre, final String correo, final String telefono, final String mensaje, final RespuestaObject respuesta){
        RequestQueue queue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("nombre",nombre);
        params.put("email",correo);
        params.put("telefono",telefono);
        params.put("mensaje",mensaje);
        JSONObject jsonObj = new JSONObject(params);
// Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,RUTA_API+"/contacto",jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                respuesta.respuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                respuesta.error(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };


// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public void EditarContacto(Context context,String id, final String nombre, final String correo, final String telefono, final String mensaje, final RespuestaObject respuesta){
        RequestQueue queue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("nombre",nombre);
        params.put("email",correo);
        params.put("telefono",telefono);
        params.put("mensaje",mensaje);
        JSONObject jsonObj = new JSONObject(params);
// Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.PUT,RUTA_API+"/contacto/"+id,jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                respuesta.respuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                respuesta.error(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };


// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public void EliminarContacto(Context context,String id,final RespuestaObject respuesta){
        RequestQueue queue = Volley.newRequestQueue(context);
// Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.DELETE,RUTA_API+"/contacto/"+id,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                respuesta.respuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                respuesta.error(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };


// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
