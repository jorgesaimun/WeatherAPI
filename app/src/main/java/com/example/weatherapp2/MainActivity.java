//package com.example.weatherapp2;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.w3c.dom.Text;
//
//public class MainActivity extends AppCompatActivity {
//
//    EditText et;
//    TextView tv;
//    Button btn;
//    String url;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        et = findViewById(R.id.cityNameID);
//        tv = findViewById(R.id.textView);
//        btn = findViewById(R.id.searchbtn);
//
//        final String[] temp = {""};
//
//        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
//
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String city = et.getText().toString();
//                Toast.makeText(MainActivity.this, "city name :" + city, Toast.LENGTH_SHORT).show();
//                try {
//
//                    if (city != null) {
//                        url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=b374620219dd4504b1e50cce80d7bb68";
//
//                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONArray response) {
//
//                                try {
//                                    JSONObject main = response.getJSONObject("main");
//                                    double temperature = main.getDouble("temp");
//                                    int humidity = main.getInt("humidity");
//
//                                    tv.setText("Temperature :" + temperature + "\n" + "humidity: " + humidity);
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                    tv.setText("error parsing weather data");
//                                }
//
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        });
//
//                        requestQueue.add(jsonObjectRequest);
//
//
//                    } else {
//                        Toast.makeText(MainActivity.this, "Please Enter a City Name", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                } catch (Exception e) {
//
//                }
//
//
//            }
//        });
//
//    }
//}


package com.example.weatherapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText et;
    TextView tv;
    Button btn;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.cityNameID);
        tv = findViewById(R.id.textView);
        btn = findViewById(R.id.searchbtn);

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = et.getText().toString();
                if (city.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a city name", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(MainActivity.this, "City name: " + city, Toast.LENGTH_SHORT).show();

                url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=b374620219dd4504b1e50cce80d7bb68";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject main = response.getJSONObject("main");
                                    double temperature = main.getDouble("temp") - 273;
                                    int humidity = main.getInt("humidity");

                                    double feels_like = main.getDouble("feels_like") - 273;

                                    JSONArray weatherArray = response.getJSONArray("weather");
                                    JSONObject weatherObject = weatherArray.getJSONObject(0);
                                    String description = weatherObject.getString("description");

                                    tv.setText("Temperature: " + temperature + "\n Feels like :" + feels_like + "\nHumidity: " + humidity + "\n Description :" + description);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    tv.setText("Error parsing weather data");
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tv.setText("Error fetching weather data");
                    }
                });

                requestQueue.add(jsonObjectRequest);
            }
        });
    }
}
