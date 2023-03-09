package com.example.aplicacion1iip;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplicacion1iip.RestApiMethods.Methods;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;

public class ActivityCreate extends AppCompatActivity {

    ImageView imageView;
    Button btngaleria;
    static final int Result_galeria = 101;
    String POSTMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        ControlSet();

        btngaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GaleriaImagenes();

            }
        });
        
        ConsumeCreateApi();
    }

    private  void GaleriaImagenes()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Result_galeria);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode , @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri imageUri;
        if(resultCode == RESULT_OK && requestCode == Result_galeria)
        {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }

    }

    private void ControlSet()
    {
        imageView = (ImageView) findViewById(R.id.imageView);
        btngaleria = (Button) findViewById(R.id.btngaleria);
    }
    
    private void ConsumeCreateApi()
    {
        HashMap<String, String> parametros = new HashMap<>();
        parametros.put("nombres", "Ernesto");
        parametros.put("apellidos", "Valverde");
        parametros.put("fechanac", "2023-03-02");
        parametros.put("foto", "SDSDSDFDFDFDFDFGFGFD");

         POSTMethod = Methods.ApiCreate;
        JSONObject JsonAlumn = new JSONObject(parametros);

        RequestQueue peticion = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                POSTMethod,
                JsonAlumn, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i= 0; i<= jsonArray.length(); i++)
                    {
                        JSONObject msg = jsonArray.getJSONObject(i);
                    }
                }
                catch (Exception ex)
                {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        peticion.add(jsonObjectRequest);
    }
}