package com.example.hackhugo.myapplication.RestClient.CallbackRest;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by hackhugo on 13/10/2016.
 */

public interface RespuestaObject {
    public void respuesta(JSONObject response);
    public void error(VolleyError error);
}
