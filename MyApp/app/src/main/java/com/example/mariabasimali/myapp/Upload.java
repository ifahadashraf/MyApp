package com.example.mariabasimali.myapp;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static android.provider.MediaStore.Images.Media.getBitmap;
import static com.example.mariabasimali.myapp.SingletonApp.BASE_URL;

public class Upload extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        try{
            Bitmap bitmap = null;
            try {
                bitmap = getBitmap(getContentResolver(),Uri.parse(getIntent().getStringExtra("iv")));
                ((ImageView)findViewById(R.id.img)).setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (Exception e){

        }

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String b = getIntent().getStringExtra("img");
                JSONObject obj = new JSONObject();
                try {
                    obj.put("img",b);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final String json = obj.toString();

                StringRequest checkLogin = new StringRequest(Request.Method.POST,BASE_URL + "lol/test.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Upload.this, "Uploaded to" + response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Upload.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return json == null ? null : json.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", json, "utf-8");
                            return null;
                        }
                    }
                };
                SingletonApp.getInstance().addToRequestQueue(checkLogin);
            }
        });
    }
}
