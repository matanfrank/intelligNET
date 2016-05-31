package com.example.matan.intellignet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;

public class SplashActivity extends AppCompatActivity {

    private TypeUser user;
    private android.os.Handler h;
    private Runnable r;
    private static String username;
    private static String password;
    private Intent mainIntent;
    private Intent loginIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        username = sharedpreferences.getString("username", "");
        password = sharedpreferences.getString("password", "");


        h = new android.os.Handler();
        r = new Runnable() {
            @Override
            public void run() {
                try {
                    if (TashchezDAL.jsonArray.getJSONObject(0).getString("username").contains(username) &&
                            TashchezDAL.jsonArray.getJSONObject(0).getString("password").contains(password)) {

                        user = new TypeUser(username, password,
                                TashchezDAL.jsonArray.getJSONObject(0).getString("firstname"),
                                TashchezDAL.jsonArray.getJSONObject(0).getString("lastname"),
                                TashchezDAL.jsonArray.getJSONObject(0).getString("birthday"),
                                TashchezDAL.jsonArray.getJSONObject(0).getString("gender"),
                                TashchezDAL.jsonArray.getJSONObject(0).getInt("cwp_finished"),
                                TashchezDAL.jsonArray.getJSONObject(0).getInt("helpforday"));


                            mainIntent = new Intent(getBaseContext(), MainActivity.class);
                            mainIntent.putExtra("user", user);
                            startActivity(mainIntent);


                        //Remove activity


                    }
                } catch (Exception e) {
                    //if there is no such a user
                    loginIntent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(loginIntent);
                }
            }
        };


            TashchezDAL tashchezDAL = new TashchezDAL(this);
            tashchezDAL.getDataFrom("userGet?username="+username+"&password="+password,h,r);



















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