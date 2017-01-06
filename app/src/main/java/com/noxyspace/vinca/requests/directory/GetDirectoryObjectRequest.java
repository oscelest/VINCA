package com.noxyspace.vinca.requests.directory;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.noxyspace.vinca.requests.*;

import android.util.Log;
import org.json.JSONObject;

public class GetDirectoryObjectRequest extends HttpRequest {
    public GetDirectoryObjectRequest(int file_id, Response.Listener<JSONObject> responseListener) {
        super(Method.GET, DIRECTORY_URL + "/" + file_id, responseListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("GetDirectoryError", error.getMessage() != null ? error.getMessage() : "There was an error.");
                    }
                }
        );
    }
}
