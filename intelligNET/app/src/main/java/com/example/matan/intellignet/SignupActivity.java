package com.example.matan.intellignet;

    import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private static String year;
    private static String month;
    private static String day;
    private static String gender;
    private TypeUser user;

    @InjectView(R.id.input_username) EditText _usernameText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.input_password_confirm) EditText _passwordConfirmText;
    @InjectView(R.id.input_first_name) EditText _firstNameText;
    @InjectView(R.id.input_last_name) EditText _lastNameText;
    @InjectView(R.id.input_year) EditText _yearText;
    @InjectView(R.id.input_month) EditText _monthText;
    @InjectView(R.id.input_day) EditText _dayText;
    @InjectView(R.id.input_gender) EditText _genderText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;
    @InjectView(R.id.link_guest) TextView _guestLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);
        _usernameText.requestFocus();
        _loginLink.setText(Html.fromHtml(getString(R.string.loginPageBtn)));//for color change in the text
        _guestLink.setText(Html.fromHtml(getString(R.string.guestBtn)));

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


        _guestLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                LoginActivity.guest=true;
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
            year = URLEncoder.encode(_yearText.getText().toString(), "UTF-8");
            month = URLEncoder.encode(_monthText.getText().toString(), "UTF-8");
            day = URLEncoder.encode(_dayText.getText().toString(), "UTF-8");
            gender = URLEncoder.encode(_genderText.getText().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        birthday = year + "-" + month + "-" + day;

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

        SharedPreferences sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();

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
        String passwordConfirm = _passwordConfirmText.getText().toString();
        String firstName = _firstNameText.getText().toString();
        String lastName = _lastNameText.getText().toString();
        String year = _yearText.getText().toString();
        String month = _monthText.getText().toString();
        String day = _dayText.getText().toString();
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

        if (passwordConfirm.compareTo(password) != 0) {
            Log.d("123456789", "2");
            _passwordConfirmText.setError(getResources().getString(R.string.errPasswordConfirmLimits));
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


        //year
        if(year.length() == 4) {
            Log.d("123456789", "5");
            if (!((year.charAt(0) == '1' || year.charAt(0) == '2') &&
                    (year.charAt(1) == '9' || year.charAt(1) == '0') &&
                    (year.charAt(2) == '0' || year.charAt(2) == '1' || year.charAt(2) == '2' || year.charAt(2) == '3' || year.charAt(2) == '4' || year.charAt(2) == '5' || year.charAt(2) == '6' || year.charAt(2) == '7' || year.charAt(2) == '8' || year.charAt(2) == '9') &&
                    (year.charAt(3) == '0' || year.charAt(3) == '1' || year.charAt(3) == '2' || year.charAt(3) == '3' || year.charAt(3) == '4' || year.charAt(3) == '5' || year.charAt(3) == '6' || year.charAt(3) == '7' || year.charAt(3) == '8' || year.charAt(3) == '9'))) {
                Log.d("123456789", "6");
                _yearText.setError(getResources().getString(R.string.errBirthdayYear));
                valid = false;
            } else {
                _yearText.setError(null);
            }
        }
        else
        {
            Log.d("123456789", "10");
            _yearText.setError(getResources().getString(R.string.errBirthdayYear));
            valid = false;
        }


        //month
        if(month.length() == 2) {
            if (!((month.charAt(0) == '0' || month.charAt(0) == '1') &&
                    (month.charAt(1) == '0' || month.charAt(1) == '1' || month.charAt(1) == '2' || month.charAt(1) == '3' || month.charAt(1) == '4' || month.charAt(1) == '5' || month.charAt(1) == '6' || month.charAt(1) == '7' || month.charAt(1) == '8' || month.charAt(1) == '9'))) {
                Log.d("123456789", "8");
                _monthText.setError(getResources().getString(R.string.errBirthdayMonth));
                valid = false;
            } else {
                _monthText.setError(null);
            }
        }
            else
            {
                Log.d("123456789", "10");
                _monthText.setError(getResources().getString(R.string.errBirthdayMonth));
                valid = false;
            }


            //day
            if(day.length() == 2) {
                if (!((day.charAt(0) == '0' || day.charAt(0) == '1' || day.charAt(0) == '1' || day.charAt(0) == '3') &&
                        (day.charAt(1) == '0' || day.charAt(1) == '1' || day.charAt(1) == '2' || day.charAt(1) == '3' || day.charAt(1) == '4' || day.charAt(1) == '5' || day.charAt(1) == '6' || day.charAt(1) == '7' || day.charAt(1) == '8' || day.charAt(1) == '9')) &&
                        (day.charAt(0) == '3' && (day.charAt(1) == '2' || day.charAt(1) == '3' || day.charAt(1) == '4' || day.charAt(1) == '5' || day.charAt(1) == '6' || day.charAt(1) == '7' || day.charAt(1) == '8' || day.charAt(1) == '9'))) {
                    Log.d("123456789", "9");
                    _dayText.setError(getResources().getString(R.string.errBirthdayDay));
                    valid = false;
                } else {
                    _dayText.setError(null);
                }
            }
            else
            {
                Log.d("123456789", "10");
                _dayText.setError(getResources().getString(R.string.errBirthdayDay));
                valid = false;
            }




        if (!gender.equals("זכר") && !gender.equals("נקבה")) {
            Log.d("123456789", "11");
            _genderText.setError(getResources().getString(R.string.errGenderValid));
            valid = false;
        } else {
            _genderText.setError(null);
        }

             Log.d("1234567890", "" + valid);

        return valid;
    }
}