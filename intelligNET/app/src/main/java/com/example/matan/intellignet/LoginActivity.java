package com.example.matan.intellignet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private android.os.Handler h;
    private Runnable r;
    private static String username;
    private static String password;
    public static boolean isGuest;
    public TashchezDAL tashchezDAL = new TashchezDAL(this);
    private Activity activity =  this;

    @InjectView(R.id.input_username) EditText _usernameText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;
    @InjectView(R.id.link_guest) TextView _guestLink;
    @InjectView(R.id.progress_bar_login)ProgressBar _progressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        _usernameText.requestFocus();
        _signupLink.setText(Html.fromHtml(getString(R.string.signUpPageBtn)));//for color change in the text
        _guestLink.setText(Html.fromHtml(getString(R.string.guestBtn)));


        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



    _guestLink.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // Start the Signup activity
            isGuest =true;
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    });
}


    public void login() {
        Log.d(TAG, "Login");

        if (validate()) {


//            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
//                    R.style.AppTheme);
//            progressDialog.setIndeterminate(true);
//            progressDialog.setMessage("Authenticating...");
//            progressDialog.show();
            _progressBar.setVisibility(View.VISIBLE);
            username = _usernameText.getText().toString();
            password = _passwordText.getText().toString();



            h = new android.os.Handler();
            r = new Runnable() {
                @Override
                public void run() {
                    //_loginButton.setEnabled(false);
                    try {
                        if (TashchezDAL.jsonArray.getJSONObject(0).getString("username").contains(username) &&
                                TashchezDAL.jsonArray.getJSONObject(0).getString("password").contains(password)) {


//                            if(TashchezDAL.jsonArray.getJSONObject(0).getString("is_connected").compareTo("false") == 0) {



                            MainActivity.user = new TypeUser(
                                    TashchezDAL.jsonArray.getJSONObject(0).getString("username"),
                                    TashchezDAL.jsonArray.getJSONObject(0).getString("password"),
                                    TashchezDAL.jsonArray.getJSONObject(0).getString("firstname"),
                                    TashchezDAL.jsonArray.getJSONObject(0).getString("lastname"),
                                    TashchezDAL.jsonArray.getJSONObject(0).getString("birthday"),
                                    TashchezDAL.jsonArray.getJSONObject(0).getString("gender"),
                                    TashchezDAL.jsonArray.getJSONObject(0).getInt("cwp_finished"),
                                    TashchezDAL.jsonArray.getJSONObject(0).getInt("helpforday"));

//                            progressDialog.dismiss();
                                //_progressBar.setVisibility(View.GONE);
                                onLoginSuccess();
//                            }



//                            else//the user is alreay connected in other device.
//                            {
//                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity).
//                                        setTitle("התראה!").
//                                        setIcon(android.R.drawable.ic_dialog_alert).
//                                        setMessage(R.string.twoDevicesLoginDialog).
//                                        setPositiveButton("התנתק מהחשבון במכשיר השני", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                //set is_connected
//                                            }
//                                        }).
//                                        setPositiveButton("הכנס כאורח", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//
//                                            }
//                                        })
//                                alertDialog.show();
//
//                            }


                        }
                    } catch (JSONException e) {
                        try {
                            if (TashchezDAL.jsonArray.getString(0).contains("ERR1")) {
                                Log.d("123456789", "no details");
//                                progressDialog.dismiss();
                                //_progressBar.setVisibility(View.GONE);
                                onLoginFailed();
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            };

            Map<String,String> params = new HashMap<String, String>();
            params.put("username", username);
            params.put("password", password);
            tashchezDAL.getDataFrom("userGet", params, h, r, _progressBar, "post");
        }
    }




    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
//        _loginButton.setEnabled(true);

        SharedPreferences sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();

        isGuest = false;

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


//        //do not able two device connected to same account. sign that the user is connected
//        Map<String,String> params = new HashMap<String, String>();
//        params.put("username", MainActivity.user.getUsername());
//        params.put("is_connected", "true");
//        tashchezDAL.getDataFrom("changeConnectedStatus", params, null, null, null, "post");
//        MainActivity.user.setIsConnected(true);//need to cheack if secceded first!!!!!!!!!!!!!!!!
    }

    public void onLoginFailed() {
        Toast toast= Toast.makeText(getApplicationContext(), getString(R.string.errWrongPassOrUser), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER| Gravity.CENTER, 0, 0);
        toast.show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        username = _usernameText.getText().toString();
        password = _passwordText.getText().toString();

        if (username.isEmpty()){// || !android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            _usernameText.setError(getResources().getString(R.string.errUsernameEmpty));
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty()) {
            _passwordText.setError(getResources().getString(R.string.errPasswordEmpty));
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }


    DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.CANADA);
    TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
    Calendar rightNow = Calendar.getInstance();
}