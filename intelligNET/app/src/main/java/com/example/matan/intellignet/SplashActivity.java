package com.example.matan.intellignet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    private android.os.Handler h;
    private Runnable r;
    private String username = "";
    private String password = "";
    private Intent mainIntent;
    private Intent loginIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);

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

                    MainActivity.user = new TypeUser(
                            TashchezDAL.jsonArray.getJSONObject(0).getString("username"),
                            TashchezDAL.jsonArray.getJSONObject(0).getString("password"),
                            TashchezDAL.jsonArray.getJSONObject(0).getString("firstname"),
                            TashchezDAL.jsonArray.getJSONObject(0).getString("lastname"),
                            TashchezDAL.jsonArray.getJSONObject(0).getString("birthday"),
                            TashchezDAL.jsonArray.getJSONObject(0).getString("gender"),
                            TashchezDAL.jsonArray.getJSONObject(0).getInt("cwp_finished"),
                            TashchezDAL.jsonArray.getJSONObject(0).getInt("helpforday"));


                    mainIntent = new Intent(getBaseContext(), MainActivity.class);
                    LoginActivity.isGuest = false;
                    startActivity(mainIntent);

                    //Remove activity
                    }
                } catch (Exception e) {
                    //if there is no such a user.
                    loginIntent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(loginIntent);
                }
            }
        };


        TashchezDAL tashchezDAL = new TashchezDAL(this);

        if (username.length() != 0 && password.length() != 0)//if nothing stored in shared prfrces dont need to go to DB
            tashchezDAL.getDataFrom("userGet?username=" + username + "&password=" + password, h, r);
        else
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