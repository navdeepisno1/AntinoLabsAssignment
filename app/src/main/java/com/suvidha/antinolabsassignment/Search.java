package com.suvidha.antinolabsassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Search extends AppCompatActivity {

    EditText editText_searchlocation;
    ImageView button_search;
    RecyclerView recyclerView_restaurants;
    Geocoder geocoder;
    Context context = this;
    List<Address> addresses;
    double lat,lon;
    String API_KEY = "1b3c8b37ea96785391fa55c288ac385c";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        editText_searchlocation = findViewById(R.id.search_et_restaurant);
        button_search = findViewById(R.id.search_btn_find);

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String saddress = editText_searchlocation.getText().toString().trim();
                if(saddress.isEmpty())
                {
                    Toast.makeText(Search.this,"Enter Loaction First",Toast.LENGTH_LONG).show();
                    return;
                }
                geocoder = new Geocoder(context, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocationName(saddress,10);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(addresses.size()>0)
                {
                    Address address = addresses.get(0);
                    lat = address.getLatitude();
                    lon = address.getLongitude();
                    Log.e("Tag"," " + lat + "," + lon);
                    url = "https://developers.zomato.com/api/v2.1/search?lat=" + lat + "&lon=" + lon;
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONArray jsonArray = response.getJSONArray("restaurants");
                                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                                        recyclerView_restaurants.setLayoutManager(linearLayoutManager);
                                        recyclerView_restaurants.setAdapter(new RVAdatper_Shops(context,jsonArray));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            })
                    {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("user-key", API_KEY);
                            params.put("Accept-Language", "fr");
                            params.put("Accept","application/json");
                            return params;
                        }
                    };
                    requestQueue.add(jsonObjectRequest);
                }
                else
                {
                    Log.e("Tag","NA");
                }
            }
        });

        recyclerView_restaurants = findViewById(R.id.search_rv_restaurant);

    }


}