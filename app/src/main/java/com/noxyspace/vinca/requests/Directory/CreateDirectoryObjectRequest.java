package com.noxyspace.vinca.requests.Directory;

import com.android.volley.Response;
import com.noxyspace.vinca.requests.CustomRequest;

import org.json.JSONObject;

import java.util.Map;

public class CreateDirectoryObjectRequest extends CustomRequest {
    public CreateDirectoryObjectRequest(Map<String, String> params, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener ) {
        super(Method.POST, DIRECTORY_URL, responseListener, errorListener);
    }
}