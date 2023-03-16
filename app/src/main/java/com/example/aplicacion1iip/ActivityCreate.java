package com.example.aplicacion1iip;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;

public class ActivityCreate extends AppCompatActivity {

    ImageView imageView;
    Button btngaleria;
    Button btnenviar;
    static final int Result_galeria = 101;
    String POSTMethod;
    String currentPath;

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

        btnenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConsumeCreateApi();
            }
        });
        

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
        btnenviar = (Button) findViewById(R.id.btnenviar);
    }
    
    private void ConsumeCreateApi()
    {
        HashMap<String, String> parametros = new HashMap<>();
        parametros.put("nombres", "Ernesto");
        parametros.put("apellidos", "Valverde");
        parametros.put("fechanac", "2023-03-02");
        parametros.put("foto", ImageToBase64(currentPath));

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

    public static String ImageToBase64(String path)
    {
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String Image64String = null;

        try
        {
           bmp = BitmapFactory.decodeFile(path);
           bos = new ByteArrayOutputStream();
           bmp.compress(Bitmap.CompressFormat.JPEG,50 , bos);
           bt = bos.toByteArray();
           Image64String = android.util.Base64.encodeToString(bt, android.util.Base64.DEFAULT);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();

        }
        return Image64String;

    }
}