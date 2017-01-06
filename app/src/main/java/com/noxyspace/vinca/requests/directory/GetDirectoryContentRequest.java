package com.noxyspace.vinca.requests.directory;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.noxyspace.vinca.requests.*;

import android.util.Log;
import org.json.JSONObject;

public class GetDirectoryContentRequest extends HttpRequest {
    public GetDirectoryContentRequest(int folder_id, Response.Listener<JSONObject> responseListener) {
        super(Method.GET, DIRECTORY_URL + "/content/" + folder_id, responseListener,
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("GetDirectoryError", error.getMessage() != null ? error.getMessage() : "There was an error.");
                }
            }
        );
    }
}
