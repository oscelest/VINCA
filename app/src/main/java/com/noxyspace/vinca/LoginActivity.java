package com.noxyspace.vinca;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.noxyspace.vinca.objects.ApplicationObject;
import com.noxyspace.vinca.objects.DirectoryObject;
import com.noxyspace.vinca.objects.UserObject;
import com.noxyspace.vinca.requests.Users.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // ClickListener for the Register Button
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Field check for email
                boolean email_check = email.length() > 0;
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
                // Build parameter set for request
                Map<String, String> params = new HashMap<>();
                params.put("email", password.toString());
                params.put("password", email.toString());
                // Fire request to server
                new LoginRequest(params, new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                Log.d("RegisterSuccess", response.toString());
                                List<DirectoryObject> dir = ApplicationObject.getDirectory();
                                JSONObject content = response.getJSONObject("content");
                                ApplicationObject.setUser(new UserObject(
                                        content.getInt("id"),
                                        content.getString("first_name"),
                                        content.getString("last_name"),
                                        content.getString("email"),
                                        content.getBoolean("admin"),
                                        content.getString("user_token")
                                ));
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                email_layout.setErrorEnabled(true);
                                email_layout.setError("Email/Password combination doesn't match.");
                                Log.d("RegisterFailure", response.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.d("RegisterErr", error.getMessage());
                        Toast.makeText(getApplicationContext(), "Server error, try again later.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
