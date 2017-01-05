package com.noxyspace.vinca.requests.users;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.noxyspace.vinca.requests.HttpParameter;
import com.noxyspace.vinca.requests.HttpRequest;

import org.json.JSONObject;

public class RefreshRequest extends HttpRequest {
    public RefreshRequest(Response.Listener<JSONObject> responseListener) {
        super(Method.POST, USER_URL + "/login", responseListener,
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("LoginRequestError", error.getMessage() != null ? error.getMessage() : "There was an error.");
                }
            }
        );
    }
}
