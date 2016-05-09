package com.example.matan.intellignet;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
    private ProgressDialog mProgressDialog;
    private final String url = "http://intellignet.herokuapp.com/";
    public static JSONArray jsonArray = new JSONArray();


    public TashchezDAL(Context ctx) {
        mContext = ctx;
    }






    public void getDataFrom(String specPage, final android.os.Handler h, final Runnable r) {


        String page = url + specPage;

        createProgressDialog("Just a minute...", "Getting data from server.");

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, page, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                jsonArray = response;
                try {
                    Log.d("123456789", "DAL: " + jsonArray.getString(0));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(mContext, "gooooooooooooooood", Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
                if(h != null && r != null)
                    h.post(r);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //when response returns an ERROR
                Toast.makeText(mContext, "Oops! Something went wronggg...", Toast.LENGTH_SHORT).show();
                Log.e("12345678", "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n\n\n\n\n\n");
                error.printStackTrace();
                Log.e("12345678", "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n\n\n\n\n\n");
                mProgressDialog.dismiss();
            }
        });



        requestQueue.add(jsonArrayRequest);
    }







    public ProgressDialog getProgressDialog() {
        return mProgressDialog;
    }

    //create a progress dialog while data is being received
    private void createProgressDialog(String title, String msg) {
        mProgressDialog = ProgressDialog.show(mContext, title,
                msg, true);
    }

}

