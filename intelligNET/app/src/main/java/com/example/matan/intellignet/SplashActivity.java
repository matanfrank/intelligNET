package com.example.matan.intellignet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    private android.os.Handler h;
    private Runnable r;
    private String username = "";
    private String password = "";
    private Intent mainIntent;
    private Intent loginIntent;
    private Activity activity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Map<String,String> params = new HashMap<String, String>();
        TashchezDAL tashchezDAL = new TashchezDAL(this);
//        params.put("query1", "ALTER TABLE Users ALTER COLUMN last_use_date TYPE TIMESTAMP;");
//        tashchezDAL.getDataFrom("general", params, null, null, null, "post");

        final SharedPreferences sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);

        //get the user that connected to the system if exist
        username = sharedpreferences.getString("username", "");
        password = sharedpreferences.getString("password", "");


        h = new android.os.Handler();
        r = new Runnable() {
            @Override
            public void run() {
                try {
                    //check if the details that stored in shredpreferns match
                    if (TashchezDAL.jsonArray.getJSONObject(0).getString("username").contains(username) &&
                            TashchezDAL.jsonArray.getJSONObject(0).getString("password").contains(password)) {
                        Log.d("21.03.17", "errrrrrrrrrrrrrrrr1");


                            MainActivity.user = new TypeUser(
                                    TashchezDAL.jsonArray.getJSONObject(0).getString("username"),
                                    TashchezDAL.jsonArray.getJSONObject(0).getString("password"),
                                    TashchezDAL.jsonArray.getJSONObject(0).getString("firstname"),
                                    TashchezDAL.jsonArray.getJSONObject(0).getString("lastname"),
                                    TashchezDAL.jsonArray.getJSONObject(0).getString("birthday"),
                                    TashchezDAL.jsonArray.getJSONObject(0).getString("gender"),
                                    TashchezDAL.jsonArray.getJSONObject(0).getInt("cwp_finished"),
                                    TashchezDAL.jsonArray.getJSONObject(0).getInt("helpforday"));



                        Log.d("21.03.17", "errrrrrrrrrrrrrrrr2");
                            mainIntent = new Intent(getBaseContext(), MainActivity.class);
                            LoginActivity.isGuest = false;
                            startActivity(mainIntent);

                            //Remove activity
                       }
                } catch (Exception e) {
                    //if there is no such a user.
                    Log.d("21.03.17", "errrrrrrrrrrrrrrrr3\n" + e.getMessage());
                    loginIntent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(loginIntent);
                }
            }
        };


        //if nothing stored in shared prfrces dont need to go to DB
        if (username.length() != 0 && password.length() != 0)//go to db
        {
            params = new HashMap<String, String>();
            params.put("username", username);
            params.put("password", password);
            tashchezDAL.getDataFrom("userGet", params, h, r, null, "post");
        }
        else//go to login page bcuz nothing stored
        {
            loginIntent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(loginIntent);
        }











//
//
//
//
//
//
//
//
//
//        // METHOD 1
//
//        /****** Create Thread that will sleep for 5 seconds *************/
//
//        Thread background = new Thread() {
//            public void run() {
//
//                try {
//                    // Thread will sleep for 5 seconds
//                    sleep(5 * 1000);
//
//                    // After 5 seconds redirect to another intent
//
//
//                } catch (Exception e) {
//
//                }
//            }
//        };
//
//
//
//
//
//        // start thread
//        background.start();
//
////METHOD 2
//
//        /*
//        new Handler().postDelayed(new Runnable() {
//
//            // Using handler with postDelayed called runnable run method
//
//            @Override
//            public void run() {
//                Intent i = new Intent(MainSplashScreen.this, FirstScreen.class);
//                startActivity(i);
//
//                // close this activity
//                finish();
//            }
//        }, 5*1000); // wait for 5 seconds
//        */
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}