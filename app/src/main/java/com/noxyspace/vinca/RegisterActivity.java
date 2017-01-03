package com.noxyspace.vinca;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.noxyspace.vinca.objects.ApplicationObject;
import com.noxyspace.vinca.objects.DirectoryObject;
import com.noxyspace.vinca.objects.UserObject;
import com.noxyspace.vinca.requests.Users.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Setup for fields
        final TextInputEditText first_name = (TextInputEditText) findViewById(R.id.input_first_name);
        final TextInputLayout first_name_layout = (TextInputLayout) findViewById(R.id.input_first_name_layout);
        final TextInputEditText last_name = (TextInputEditText) findViewById(R.id.input_last_name);
        final TextInputLayout last_name_layout = (TextInputLayout) findViewById(R.id.input_last_name_layout);
        final TextInputEditText email = (TextInputEditText) findViewById(R.id.input_email);
        final TextInputLayout email_layout = (TextInputLayout) findViewById(R.id.input_email_layout);
        final TextInputEditText password = (TextInputEditText) findViewById(R.id.input_password);
        final TextInputLayout password_layout = (TextInputLayout) findViewById(R.id.input_password_layout);
        final TextInputEditText confirm = (TextInputEditText) findViewById(R.id.input_confirm);
        final TextInputLayout confirm_layout = (TextInputLayout) findViewById(R.id.input_confirm_layout);

        findViewById(R.id.text_login_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Field check for first name
                boolean first_name_check = first_name.length() > 0;
                first_name_layout.setErrorEnabled(!first_name_check);
                if (!first_name_check) {
                    first_name_layout.setError("You need to enter a first name.");
                    return;
                }
                // Field check for last name
                boolean last_name_check = last_name.length() > 0;
                last_name_layout.setErrorEnabled(!last_name_check);
                if (!last_name_check) {
                    last_name_layout.setError("You need to enter a last name.");
                    return;
                }
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
                    password_layout.setError("A password must be 8 characters long and contain a number, a small and a capital letter.");
                    return;
                }
                // Field check for confirm
                boolean confirm_check = password.getText().toString().equals(confirm.getText().toString());
                confirm_layout.setErrorEnabled(!confirm_check);
                if (!confirm_check) {
                    confirm_layout.setError("Password and Confirmation do not match.");
                    return;
                }
                // Build parameter set for request
                Map<String, String> params = new HashMap<>();
                params.put("first_name", first_name.toString());
                params.put("last_name", last_name.toString());
                params.put("email", email.toString());
                params.put("password", password.toString());
                // Fire request to server
                new RegisterRequest(params, new Response.Listener<JSONObject>() {
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
                                Log.d("RegisterFailure", response.toString());
                                Toast.makeText(getApplicationContext(), "Server error, try again later.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.d("RegisterErr", error.getMessage());
                    }
                });
            }
        });

    }
}
