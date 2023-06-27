package com.example.myapplication;




import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    ImageView img;
    Button next,share;
    String imageUrl=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img=findViewById(R.id.image);
        next=findViewById(R.id.next);
        share=findViewById(R.id.share);
        loadMeme();
    }
    public void loadMeme(){
      RequestQueue queue = Volley.newRequestQueue(this);
      String url="https://meme-api.com/gimme";
      JsonObjectRequest jsonObjectRequest=new  JsonObjectRequest(Request.Method.GET, url, null,
              new Response.Listener<JSONObject>()
      {
          @Override
          public void onResponse(JSONObject response) {
              try {
                  imageUrl = response.getString("url");
                  Glide.with(MainActivity.this).load(imageUrl).into(img);
              } catch (JSONException e) {
                  e.printStackTrace();
              }
          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
              Toast.makeText(MainActivity.this,"Error occurred",Toast.LENGTH_SHORT).show();
          }
      });
      queue.add(jsonObjectRequest);
    }

    public void nextMeme(View view) {
        loadMeme();
    }

    public void shareMeme(View view) {
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, Checkout this Cool meme i got from Reddit \n"+ imageUrl);
        startActivity(Intent.createChooser(intent,"Share this meme using..."));
    }
}