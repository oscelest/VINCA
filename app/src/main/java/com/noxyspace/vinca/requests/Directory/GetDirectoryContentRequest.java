package com.noxyspace.vinca.requests.Directory;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.noxyspace.vinca.requests.CustomRequest;
import com.noxyspace.vinca.requests.HttpParameter;

import org.json.JSONObject;

public class GetDirectoryContentRequest extends CustomRequest {
    public GetDirectoryContentRequest(String folder_id, Response.Listener<JSONObject> responseListener) {
        super(Method.GET, DIRECTORY_URL + "/content/"+folder_id, responseListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("GetDirContentErr", error.getMessage());
                    }
                }
        );
    }
}