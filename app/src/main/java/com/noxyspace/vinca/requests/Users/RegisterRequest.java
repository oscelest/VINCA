package com.noxyspace.vinca.requests.Users;

import com.android.volley.Response;
import com.noxyspace.vinca.requests.CustomRequest;

import org.json.JSONObject;

import java.util.Map;

public class RegisterRequest extends CustomRequest {
    public RegisterRequest(Map<String, String> params, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener ) {
        super(Method.POST, USER_URL + "/register", params, responseListener, errorListener);
    }
}