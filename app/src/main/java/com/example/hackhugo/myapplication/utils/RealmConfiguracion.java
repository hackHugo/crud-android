package com.example.hackhugo.myapplication.utils;

import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by hackhugo on 09/10/2016.
 */

public class RealmConfiguracion  {

    public void Config(Context context) {

        // The Realm file will be located in Context.getFilesDir() with name "default.realm"
        Realm.init(context);
        RealmConfiguration Config = new RealmConfiguration.Builder()
                .name("prueba.realm")
                .schemaVersion(2)
                .build();
        Log.d("Ruta Realm: ",context.getFilesDir().toString());
        Realm.setDefaultConfiguration(Config);
    }
}
