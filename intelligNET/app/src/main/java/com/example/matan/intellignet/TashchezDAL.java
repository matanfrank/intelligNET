package com.example.matan.intellignet;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class TashchezDAL {
    private Context mContext;
    private int failCounter = 0;
    private final String url = "http://intellignet.herokuapp.com/";
    public static JSONArray jsonArray = new JSONArray();
    final int MAX_TRY_AGAIN = 10;

    public TashchezDAL(Context ctx) {
        mContext = ctx;
    }
    public static RequestQueue requestQueue;
    public static JsonArrayRequest jsonArrayRequest;


    public void getDataFrom(String specPage, final android.os.Handler h, final Runnable r) {
        String page = url + specPage;
        requestQueue = Volley.newRequestQueue(mContext);

        jsonArrayRequest= new JsonArrayRequest(Request.Method.GET, page, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    jsonArray = response;
                    Toast.makeText(mContext, "gooooooooooooooood", Toast.LENGTH_SHORT).show();
                    if (h != null && r != null)
                        h.post(r);
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mContext, "אופס...קיימת בעיה בשרת", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(15*1000, 3, 0));
        requestQueue.add(jsonArrayRequest);
    }
}

