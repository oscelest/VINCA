package com.noxyspace.vinca.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.noxyspace.vinca.R;
import com.noxyspace.vinca.objects.ApplicationObject;
import com.noxyspace.vinca.objects.UserObject;

import com.noxyspace.vinca.requests.users.RefreshRequest;
import com.noxyspace.vinca.requests.users.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();

        // Makes sure the keyboard doesn't show on start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });



        // ClickListener for the Register Button
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Field check for first name
                boolean first_name_check = first_name.getText().length() > 0;
                first_name_layout.setErrorEnabled(!first_name_check);

                if (!first_name_check) {
                    first_name_layout.setError("You need to enter a first name.");
                    return;
                }

                // Field check for last name
                boolean last_name_check = last_name.getText().length() > 0;
                last_name_layout.setErrorEnabled(!last_name_check);

                if (!last_name_check) {
                    last_name_layout.setError("You need to enter a last name.");
                    return;
                }

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

                ApplicationObject.getInstance().setUser(null);
                ApplicationObject.getInstance().setUserToken(null);
                editor.remove("com.noxyspace.vinca.USERTOKEN");
                editor.apply();

                // Send request to server
                ApplicationObject.getInstance().addRequest(new RegisterRequest(first_name.getText().toString(), last_name.getText().toString(), email.getText().toString(), password.getText().toString(),
                        new Response.Listener<JSONObject>() {
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getBoolean("success")) {
                                        Log.d("RegisterSuccess", response.toString());

                                        JSONObject content = response.getJSONObject("content");

                                        ApplicationObject.getInstance().setUserToken(content.getString("user_token"));
                                        ApplicationObject.getInstance().setUser(new UserObject(
                                                content.getString("_id"),
                                                content.getString("first_name"),
                                                content.getString("last_name"),
                                                content.getString("email"),
                                                content.getBoolean("admin"),
                                                content.getBoolean("verified"),
                                                content.getString("user_token")
                                        ));

                                        editor.putString("com.noxyspace.vinca.USERTOKEN", content.getString("user_token"));
                                        editor.apply();

                                        startActivity(new Intent(getApplicationContext(), HubActivity.class));
                                    } else {
                                        Log.d("RegisterFailure", response.toString());
                                        Toast.makeText(getApplicationContext(), "Server error, try again later.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ));
            }
        });
        String user_token = sharedPreferences.getString("com.noxyspace.vinca.USERTOKEN", null);
        if (user_token != null) {
            ApplicationObject.getInstance().setUserToken(user_token);
            ApplicationObject.getInstance().addRequest(new RefreshRequest(
                    new Response.Listener<JSONObject>() {
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getBoolean("success")) {
                                    Log.d("LoginSuccess", response.toString());
                                    JSONObject content = response.getJSONObject("content");
                                    ApplicationObject.getInstance().setUserToken(content.getString("user_token"));
                                    ApplicationObject.getInstance().setUser(new UserObject(
                                            content.getString("_id"),
                                            content.getString("first_name"),
                                            content.getString("last_name"),
                                            content.getString("email"),
                                            content.getBoolean("admin"),
                                            content.getBoolean("verified"),
                                            content.getString("user_token")
                                    ));
                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("com.noxyspace.vinca.USERTOKEN", content.getString("user_token"));
                                    editor.apply();
                                    startActivity(new Intent(getApplicationContext(), HubActivity.class));
                                } else {
                                    Log.d("RefreshFailure", response.toString());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

            ));
        }
    }

    // Disables going back to previous screen on back button (closes application).
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


    // Removes focus when clicked outside EditText
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
