package com.noxyspace.vinca;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.noxyspace.vinca.objects.ApplicationObject;
import com.noxyspace.vinca.objects.UserObject;
import com.noxyspace.vinca.requests.Users.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Setup for fields
        final TextInputEditText email = (TextInputEditText) findViewById(R.id.input_email);
        final TextInputLayout email_layout = (TextInputLayout) findViewById(R.id.input_email_layout);

        final TextInputEditText password = (TextInputEditText) findViewById(R.id.input_password);
        final TextInputLayout password_layout = (TextInputLayout) findViewById(R.id.input_password_layout);

        findViewById(R.id.text_register_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        findViewById(R.id.btn_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        // ClickListener for the Register Button
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Field check for email
                boolean email_check = email.getText().length() > 0;
                email_layout.setErrorEnabled(!email_check);

                if (!email_check) {
                    email_layout.setError("You need to enter an email.");
                    return;
                }

                // Field check for password
                boolean password_check = password.getText().toString().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");
                password_layout.setErrorEnabled(!password_check);

                if (!password_check) {
                    password_layout.setError("Password doesn't match password rules.");
                    return;
                }

                // Send request to server
                ApplicationObject.getInstance().addRequest(new LoginRequest(email.getText().toString(), password.getText().toString(),
                        new Response.Listener<JSONObject>() {
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getBoolean("success")) {
                                        Log.d("LoginSuccess", response.toString());

                                        JSONObject content = response.getJSONObject("content");

                                        ApplicationObject.setUser(new UserObject(
                                                content.getInt("id"),
                                                content.getString("first_name"),
                                                content.getString("last_name"),
                                                content.getString("email"),
                                                content.getInt("admin") != 0,
                                                content.getString("user_token")
                                        ));

                                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putString("com.noxyspace.vinca.USERTOKEN", content.getString("user_token"));
                                        editor.apply();

                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    } else {
                                        Log.d("LoginFailure", response.toString());
                                        email_layout.setErrorEnabled(true);
                                        email_layout.setError("Email/Password combination doesn't match.");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ));
            }
        });
    }
}
