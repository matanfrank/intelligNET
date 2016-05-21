package com.example.matan.intellignet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity {
    private android.os.Handler h;
    private Runnable r;
    private static String username;
    private static String password;
    private static String firstName;
    private static String lastName;
    private static String birthday;
    private static String gender;
    private TypeUser user;

    @InjectView(R.id.input_username) EditText _usernameText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.input_first_name) EditText _firstNameText;
    @InjectView(R.id.input_last_name) EditText _lastNameText;
    @InjectView(R.id.input_birthday) EditText _birthdayText;
    @InjectView(R.id.input_gender) EditText _genderText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);
        _usernameText.requestFocus();
        _loginLink.setText(Html.fromHtml(getString(R.string.loginPageBtn)));//for color change in the text


        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signup() {
        Log.d("123456789","signUp");
        if (!validate()) {
            onSignupFailed();
            return;
        }

       // _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        //cast to UTF-8 for hebrew input
        try {
            username = URLEncoder.encode(_usernameText.getText().toString(), "UTF-8");
            password = URLEncoder.encode(_passwordText.getText().toString(), "UTF-8");
            firstName = URLEncoder.encode(_firstNameText.getText().toString(), "UTF-8");
            lastName = URLEncoder.encode(_lastNameText.getText().toString(), "UTF-8");
            birthday = URLEncoder.encode(_birthdayText.getText().toString(), "UTF-8");
            gender = URLEncoder.encode(_genderText.getText().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // TODO: Implement your own signup logic here.

        h = new android.os.Handler();
        r = new Runnable() {
            @Override
            public void run() {
               // _signupButton.setEnabled(false);
                try {
                    if(TashchezDAL.jsonArray.getString(0) != null && TashchezDAL.jsonArray.getString(0).contains("GOOD2"))
                    {
                        Log.d("123456789", "yes details");
                        progressDialog.dismiss();

                        onSignupSuccess();

                    }
                    else if(TashchezDAL.jsonArray.getString(0).contains("ERR1"))
                    {
                           Log.d("123456789", "no details");
                           progressDialog.dismiss();
                           onSignupFailed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        String s ="userAdd?username=" + username + "&password=" + password + "&firstName=" + firstName +
                "&lastName=" + lastName + "&birthday=" + birthday + "&gender=" + gender;
//        try {
//            String finalString = URLEncoder.encode(s, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        TashchezDAL tashchezDAL = new TashchezDAL(this);
        tashchezDAL.getDataFrom(s, h, r);
    }


    public void onSignupSuccess() {

        _signupButton.setEnabled(true);

        try {
            user = new TypeUser(URLDecoder.decode(username, "UTF-8"), URLDecoder.decode(password, "UTF-8"), URLDecoder.decode(firstName, "UTF-8"), URLDecoder.decode(lastName, "UTF-8"), URLDecoder.decode(birthday, "UTF-8"), URLDecoder.decode(gender, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        intent.putExtra("user", user);

        startActivity(intent);
        setResult(RESULT_OK, null);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;


        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();
        String firstName = _firstNameText.getText().toString();
        String lastName = _lastNameText.getText().toString();
        String birthday = _birthdayText.getText().toString();
        String gender = _genderText.getText().toString();



        if (username.isEmpty() || username.length() < 5 || username.length() > 15){// || !android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            _usernameText.setError(getResources().getString(R.string.errUsernamelimits));
            valid = false;
            Log.d("123456789", "1");
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 5 || password.length() > 15) {
            Log.d("123456789", "2");
            _passwordText.setError(getResources().getString(R.string.errPasswordlimits));
            valid = false;
        } else {
            _passwordText.setError(null);
        }


        if (firstName.length() < 2 || firstName.length() > 15) {
            Log.d("123456789", "3");
            _firstNameText.setError(getResources().getString(R.string.errFirstNamelimits));
            valid = false;
        } else {
            _firstNameText.setError(null);
        }




        if (lastName.length() < 2 || lastName.length() > 15) {
            Log.d("123456789", "4");
            _lastNameText.setError(getResources().getString(R.string.errLastNamelimits));
            valid = false;
        } else {
            _lastNameText.setError(null);
        }



        if(birthday.length() == 10) {
            Log.d("123456789", "5");
            if (!((birthday.charAt(0) == '1' || birthday.charAt(0) == '2') &&
                    (birthday.charAt(1) == '9' || birthday.charAt(1) == '0') &&
                    (birthday.charAt(2) == '0' || birthday.charAt(2) == '1' || birthday.charAt(2) == '2' || birthday.charAt(2) == '3' || birthday.charAt(2) == '4' || birthday.charAt(2) == '5' || birthday.charAt(2) == '6' || birthday.charAt(2) == '7' || birthday.charAt(2) == '8' || birthday.charAt(2) == '9') &&
                    (birthday.charAt(3) == '0' || birthday.charAt(3) == '1' || birthday.charAt(3) == '2' || birthday.charAt(3) == '3' || birthday.charAt(3) == '4' || birthday.charAt(3) == '5' || birthday.charAt(3) == '6' || birthday.charAt(3) == '7' || birthday.charAt(3) == '8' || birthday.charAt(3) == '9'))) {
                Log.d("123456789", "6");
                _birthdayText.setError(getResources().getString(R.string.errBirthdayYear));
                valid = false;
            } else {
                _birthdayText.setError(null);
            }

            if (birthday.charAt(4) != '-' || birthday.charAt(7) != '-') {
                Log.d("123456789", "7");
                _birthdayText.setError(getResources().getString(R.string.errBirthdayValid));
                valid = false;
            } else {
                _birthdayText.setError(null);
            }


            if (!((birthday.charAt(5) == '0' || birthday.charAt(5) == '1') &&
                    (birthday.charAt(6) == '0' || birthday.charAt(6) == '1' || birthday.charAt(6) == '2' || birthday.charAt(6) == '3' || birthday.charAt(6) == '4' || birthday.charAt(6) == '5' || birthday.charAt(6) == '6' || birthday.charAt(6) == '7' || birthday.charAt(6) == '8' || birthday.charAt(6) == '9'))) {
                Log.d("123456789", "8");

                _birthdayText.setError(getResources().getString(R.string.errBirthdayMonth));
                valid = false;
            } else {
                _birthdayText.setError(null);
            }


            if (!((birthday.charAt(8) == '0' || birthday.charAt(1) == '1' || birthday.charAt(2) == '1' || birthday.charAt(8) == '3') &&
                    (birthday.charAt(9) == '0' || birthday.charAt(9) == '1' || birthday.charAt(9) == '2' || birthday.charAt(9) == '3' || birthday.charAt(9) == '4' || birthday.charAt(9) == '5' || birthday.charAt(9) == '6' || birthday.charAt(9) == '7' || birthday.charAt(9) == '8' || birthday.charAt(9) == '9')) &&
                    (birthday.charAt(8) == '3' && (birthday.charAt(9) == '2' || birthday.charAt(9) == '3' || birthday.charAt(9) == '4' || birthday.charAt(9) == '5' || birthday.charAt(9) == '6' || birthday.charAt(9) == '7' || birthday.charAt(9) == '8' || birthday.charAt(9) == '9'))) {
                Log.d("123456789", "9");
                _birthdayText.setError(getResources().getString(R.string.errBirthdayDay));
                valid = false;
            } else {
                _birthdayText.setError(null);
            }
        }
        else
        {
            Log.d("123456789", "10");
            _birthdayText.setError(getResources().getString(R.string.errBirthdayValid));
            valid = false;
        }


        if (!gender.equals("זכר") && !gender.equals("נקבה")) {
            Log.d("123456789", "11");
            _genderText.setError(getResources().getString(R.string.errGenderValid));
            valid = false;
        } else {
            _genderText.setError(null);
        }

        Log.d("1234567890", ""+ valid);

        return valid;
    }
}