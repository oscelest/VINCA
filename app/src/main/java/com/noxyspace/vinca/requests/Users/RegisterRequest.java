package com.noxyspace.vinca.requests.Users;

import com.android.volley.*;
import com.noxyspace.vinca.Objects.UserObject;
import com.noxyspace.vinca.requests.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.util.Map;
import java.util.HashMap;

public class RegisterRequest extends CustomRequest {
    public RegisterRequest(String email, String password, String passwordConfirm, String firstName, String lastName) {
        super(Method.POST, USER_URL, generateParamsList(email, password, passwordConfirm, firstName, lastName),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("success")) {
                            try {
                                if (response.getBoolean("success") == true) {
                                    Log.d("RegisterRequestSuccess", response.toString());

                                /* Do success-stuff */
                                } else {
                                    Log.d("RegisterRequestFailure", response.toString());
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
                        Log.d("RegisterRequestErr", error.getMessage());
                    }
                });
    }

    private static Map<String, String> generateParamsList(String email, String password, String passwordConfirm, String firstName, String lastName) {
        Map<String, String> params = new HashMap<String, String>();

        params.put("email", email);
        params.put("password", password);
        params.put("confirm", passwordConfirm);
        params.put("first_name", firstName);
        params.put("last_name", lastName);

        return params;
    }
}