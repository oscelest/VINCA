package com.noxyspace.vinca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.noxyspace.vinca.Objects.ApplicationObject;
import com.noxyspace.vinca.requests.Users.LoginRequest;

public class LoginScreen extends AppCompatActivity {

    Button register, skip;
    EditText email, password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen_activity);

        ApplicationObject.getInstance().initializeRequestQueue(this);

        register = (Button)findViewById(R.id.btn_Register);
        skip = (Button)findViewById(R.id.btn_Skip);

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.pass);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Implement security check on strings
                if (email.length() > 0 && password.length() > 0) {
                    ApplicationObject.getInstance().addRequest(new LoginRequest(email.getText().toString(), password.getText().toString()));
                    Toast.makeText(getApplicationContext(), "Redirecting", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
