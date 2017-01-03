package com.noxyspace.vinca.requests.Users;

import com.android.volley.Response;
import com.noxyspace.vinca.requests.CustomRequest;

import org.json.JSONObject;

import java.util.Map;

public class LoginRequest extends CustomRequest {
    public LoginRequest(Map<String, String> params, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener ) {
        super(Method.POST, USER_URL + "/login", params, responseListener, errorListener);
    }
}