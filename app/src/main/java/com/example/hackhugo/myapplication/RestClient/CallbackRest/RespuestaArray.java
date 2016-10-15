package com.example.hackhugo.myapplication.RestClient.CallbackRest;

import com.android.volley.VolleyError;

import org.json.JSONArray;

/**
 * Created by hackhugo on 08/10/2016.
 */

public interface RespuestaArray {
    public void respuesta(JSONArray response);
    public void error(VolleyError error);
}
