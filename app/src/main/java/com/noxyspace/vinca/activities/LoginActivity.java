package com.noxyspace.vinca.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Response;
import com.noxyspace.vinca.R;
import com.noxyspace.vinca.objects.ApplicationObject;
import com.noxyspace.vinca.objects.UserObject;
import com.noxyspace.vinca.requests.users.LoginRequest;
import com.noxyspace.vinca.requests.users.RefreshRequest;

import org.json.JSONException;
import org.json.JSONObject;

import static com.noxyspace.vinca.R.mipmap.vinca_v_logo;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    TextInputEditText email;
    TextInputLayout email_layout;
    TextInputEditText password;
    TextInputLayout password_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();

        // Makes sure the keyboard doesn't show on start
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // Setup for fields
        email = (TextInputEditText) findViewById(R.id.input_email);
        email_layout = (TextInputLayout) findViewById(R.id.input_email_layout);

        password = (TextInputEditText) findViewById(R.id.input_password);
        password_layout = (TextInputLayout) findViewById(R.id.input_password_layout);

        findViewById(R.id.text_register_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        // ClickListener for the Login Button
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        findViewById(R.id.btn_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setText("rune@thode.dk");
                password.setText("Qwer1234");
                login();
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

    public void login() {
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

        ApplicationObject.getInstance().setUser(null);
        ApplicationObject.getInstance().setUserToken(null);

        editor.remove("com.noxyspace.vinca.USERTOKEN");
        editor.apply();

        // Send request to server
        ApplicationObject.getInstance().addRequest(new LoginRequest(email.getText().toString(), password.getText().toString(),
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

                                editor.putString("com.noxyspace.vinca.USERTOKEN", content.getString("user_token"));
                                editor.apply();

                                startActivity(new Intent(getApplicationContext(), HubActivity.class));
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
}
