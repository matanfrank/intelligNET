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
    private boolean succeedFlag = false;
    private int failCounter = 0;
//    private ProgressDialog mProgressDialog;
    private final String url = "http://intellignet.herokuapp.com/";
    public static JSONArray jsonArray = new JSONArray();
    final int MAX_TRY_AGAIN = 10;

    public TashchezDAL(Context ctx) {
        mContext = ctx;
    }
    public static RequestQueue requestQueue;
    public static JsonArrayRequest jsonArrayRequest;
    public static  Runnable rTryAgain;
    public static boolean firstTime = true;




    public void getDataFrom1(String specPage, final android.os.Handler h, final Runnable r) {
        while(!getDataFrom(specPage, h, r));

    }


    public boolean getDataFrom(String specPage, final android.os.Handler h, final Runnable r) {


        String page = url + specPage;
        Log.d("123456789", page);

//        createProgressDialog("Just a minute...", "Getting data from server.");

        requestQueue = Volley.newRequestQueue(mContext);





        jsonArrayRequest = null;
            jsonArrayRequest= new JsonArrayRequest(Request.Method.GET, page, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    Log.d("09.08", "22222");
                    succeedFlag = true;
                    jsonArray = response;
                    try {
                        Log.d("123456789", "DAL: " + jsonArray.getString(0));
                    } catch (JSONException e) {
                        Log.e("12345678", "####################################\n\n\n\n\n\n");
                        e.printStackTrace();
                        Log.e("12345678", "####################################\n\n\n\n\n\n");
                    }
                    Toast.makeText(mContext, "gooooooooooooooood", Toast.LENGTH_SHORT).show();
//                mProgressDialog.dismiss();
                    if (h != null && r != null) {
                        Log.d("123456789", "yes h");
                        h.post(r);
                        Log.d("09.08", "333333");

                    } else
                        Log.d("123456789", "no h");

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    when response returns an ERROR
//                    if(firstTime)
//                        firstTime = false;
//                    else
                    succeedFlag = false;
                      Toast.makeText(mContext, "אופס...קיימת בעיה בשרת", Toast.LENGTH_SHORT).show();
                    Log.e("12345678", "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n\n\n\n\n\n");
                    error.printStackTrace();
                    Log.e("12345678", "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n\n\n\n\n\n");
                    Log.d("09.08", "444444");
                    h.postDelayed(rTryAgain, 10000);
                    Log.d("09.08", "555555");
//                mProgressDialog.dismiss();
                }
            });



//            while(true) {
//                if(responseFlag) {
//                    if (succeedFlag)
//                        break;
//                    else
//
//                }
//            }
//
////        for(int i=0 ; i < MAX_TRY_AGAIN && !succeedFlag ; i++) {
////            requestQueue.add(jsonArrayRequest);
////        }
//
//
//
////            if(firstTime)
////            {
////                Log.d("09.08", "666666");
////                requestQueue.add(jsonArrayRequest);
//////                firstTime = false;
////
////            }
////            else
////            {



//                rTryAgain = new Runnable() {
//                @Override
//                 public void run() {
//                    Log.d("09.08", "777777");
                    requestQueue.add(jsonArrayRequest);
//        }};
//}
        Log.d("123456", "777777 " + succeedFlag);
return succeedFlag;
    }

}

