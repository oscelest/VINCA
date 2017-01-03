package com.noxyspace.vinca.requests.Users;

import com.android.volley.*;
import com.noxyspace.vinca.objects.ApplicationObject;
import com.noxyspace.vinca.objects.UserObject;
import com.noxyspace.vinca.requests.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.util.Map;
import java.util.HashMap;

public class LoginRequest extends CustomRequest {
    public LoginRequest(String email, String password) {
        /* Login is not yet added to the server's REST events */
        super(Method.POST, USER_URL + "/login", generateParamsList(email, password),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("success")) {
                            try {
                                if (response.getBoolean("success") == true) {
                                    Log.d("LoginRequestSuccess", response.toString());
                                    ApplicationObject.setUser(new UserObject(
                                            response.getInt("id"),
                                            response.getString("first_name"),
                                            response.getString("last_name"),
                                            response.getString("email"),
                                            response.getBoolean("admin"),
                                            response.getString("user_token")
                                    ));
                                } else {
                                    Log.d("LoginRequestFailure", response.toString());
                                /* Do failure-stuff */
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("LoginRequestErr", error.getMessage());
                    }
                });
    }

    private static Map<String, String> generateParamsList(String email, String password) {
        Map<String, String> params = new HashMap<String, String>();

        params.put("email", email);
        params.put("password", password);

        return params;
    }
}