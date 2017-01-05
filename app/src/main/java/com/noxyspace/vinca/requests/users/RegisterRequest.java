package com.noxyspace.vinca.requests.users;

import com.android.volley.*;
import com.android.volley.Response;

import com.noxyspace.vinca.requests.*;

import android.util.Log;
import org.json.JSONObject;

public class RegisterRequest extends HttpRequest {
    public RegisterRequest(String firstName, String lastName, String email, String password, Response.Listener<JSONObject> responseListener) {
        super(Method.POST, USER_URL, responseListener,
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("RegisterRequestError", error.getMessage() != null ? error.getMessage() : "There was an error.");
                }
            },
            new HttpParameter("first_name", firstName),
            new HttpParameter("last_name", lastName),
            new HttpParameter("email", email),
            new HttpParameter("password", password)
        );
    }
}
