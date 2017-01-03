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

public class CreateDirectoryObjectRequest extends CustomRequest {
    public CreateDirectoryObjectRequest(Map<String, String> params, Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener ) {
        super(Method.POST, DIRECTORY_URL, params, reponseListener, errorListener);
    }

    private static Map<String, String> generateParamsList(String email, String password) {
        Map<String, String> params = new HashMap<String, String>();

        params.put("email", email);
        params.put("password", password);

        return params;
    }
}