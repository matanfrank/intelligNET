package com.example.matan.intellignet;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;


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
    public static StringRequest stringRequest;


    public void getDataFrom(final String specPage, final Map<String,String> params, final android.os.Handler h, final Runnable r, final ProgressBar progressBar, String type) {
        if(progressBar != null)
            progressBar.setVisibility(View.VISIBLE);

        String page = url + specPage;
        requestQueue = Volley.newRequestQueue(mContext);

        //when all will be post ill erase that
        int t=0;
        if(type == "post")
            t = Request.Method.POST;
        else if(type == "get")
            t = Request.Method.GET;

        stringRequest = new StringRequest(t, page, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d("datadata", "response: "+ response);

                    try {
                        jsonArray = new JSONArray(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(mContext, specPage  +" \ngooooooooooooooood" , Toast.LENGTH_SHORT).show();
                    if (h != null && r != null)
                        h.post(r);

                    if(progressBar != null)
                        progressBar.setVisibility(View.GONE);

                }
            }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext,   specPage +"\nאופס...קיימת בעיה בשרת", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
             @Override
            protected Map<String,String> getParams(){
                     return params;
          }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15*1000, 3, 0));
        requestQueue.add(stringRequest);
    }
}

