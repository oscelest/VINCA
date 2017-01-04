package com.noxyspace.vinca.requests.Users;

import com.android.volley.*;
import com.android.volley.Response;

import com.noxyspace.vinca.requests.CustomRequest;
import com.noxyspace.vinca.requests.HttpParameter;

import android.util.Log;
import org.json.JSONObject;

public class RegisterRequest extends CustomRequest {
    public RegisterRequest(String firstName, String lastName, String email, String password, Response.Listener<JSONObject> responseListener) {
        super(Method.POST, USER_URL, responseListener,
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("RegisterRequestError", error.getMessage());
                }
            },
            new HttpParameter("first_name", firstName),
            new HttpParameter("last_name", lastName),
            new HttpParameter("email", email),
            new HttpParameter("password", password)
        );
    }
}