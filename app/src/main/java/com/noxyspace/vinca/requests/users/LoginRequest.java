package com.noxyspace.vinca.requests.users;

import com.android.volley.*;
import com.android.volley.Response;

import com.noxyspace.vinca.requests.*;

import android.util.Log;
import org.json.JSONObject;

public class LoginRequest extends HttpRequest {
    public LoginRequest(String email, String password, Response.Listener<JSONObject> responseListener) {
        super(Method.POST, USER_URL + "/login", responseListener,
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("LoginRequestError", error.getMessage() != null ? error.getMessage() : "There was an error.");
                }
            },
            new HttpParameter("email", email),
            new HttpParameter("password", password)
        );
    }
}
