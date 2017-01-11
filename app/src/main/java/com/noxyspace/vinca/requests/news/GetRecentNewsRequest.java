package com.noxyspace.vinca.requests.news;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.noxyspace.vinca.requests.HttpRequest;

import org.json.JSONObject;

public class GetRecentNewsRequest extends HttpRequest {
    public GetRecentNewsRequest(Response.Listener<JSONObject> responseListener) {
        super(Method.GET, NEWS_URL + "/recent", responseListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("GetRecentNewsError", error.getMessage() != null ? error.getMessage() : "There was an error.");
                    }
                }
        );
    }
}
