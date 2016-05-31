package com.example.matan.intellignet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.logging.Handler;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private android.os.Handler h;
    private Runnable r;
    private static String username;
    private static String password;
    public static boolean guest;

    private TypeUser user;

    @InjectView(R.id.input_username) EditText _usernameText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;
    @InjectView(R.id.link_guest) TextView _guestLink;

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
                startActivity(intent);
            }
        });



    _guestLink.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // Start the Signup activity
            guest=true;
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    });
}


    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }



        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        username = _usernameText.getText().toString();
        password = _passwordText.getText().toString();




        h = new android.os.Handler();
        r = new Runnable() {
            @Override
            public void run() {
                _loginButton.setEnabled(false);
                try {
                    if(TashchezDAL.jsonArray.getJSONObject(0).getString("username").contains(username) &&
                            TashchezDAL.jsonArray.getJSONObject(0).getString("password").contains(password))
                    {

                        user = new TypeUser(username, password,
                                TashchezDAL.jsonArray.getJSONObject(0).getString("firstname"),
                                TashchezDAL.jsonArray.getJSONObject(0).getString("lastname"),
                                TashchezDAL.jsonArray.getJSONObject(0).getString("birthday"),
                                TashchezDAL.jsonArray.getJSONObject(0).getString("gender"),
                                TashchezDAL.jsonArray.getJSONObject(0).getInt("cwp_finished"),
                                TashchezDAL.jsonArray.getJSONObject(0).getInt("helpforday"));

                        progressDialog.dismiss();

                        onLoginSuccess();
                    }
                } catch (JSONException e) {
                    try {
                        if(TashchezDAL.jsonArray.getString(0).contains("ERR1"))
                        {
                            Log.d("123456789", "no details");
                            progressDialog.dismiss();
                            onLoginFailed();
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        };

        TashchezDAL tashchezDAL = new TashchezDAL(this);
        tashchezDAL.getDataFrom("userGet?username=" + username + "&password=" + password, h, r);

    }




    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);

        SharedPreferences sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();

        guest = false;

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);



    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        username = _usernameText.getText().toString();
        password = _passwordText.getText().toString();

        if (username.isEmpty() || username.length() < 5 || username.length() > 15){// || !android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            _usernameText.setError(getResources().getString(R.string.errUsernamelimits));
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 5 || password.length() > 15) {
            _passwordText.setError(getResources().getString(R.string.errPasswordlimits));
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}