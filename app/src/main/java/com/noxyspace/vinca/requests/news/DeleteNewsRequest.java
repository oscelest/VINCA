package com.noxyspace.vinca.requests.news;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.noxyspace.vinca.requests.HttpRequest;

import org.json.JSONObject;

public class DeleteNewsRequest extends HttpRequest {
    public DeleteNewsRequest(String news_id, Response.Listener<JSONObject> responseListener) {
        super(Method.DELETE, NEWS_URL + "/" + news_id, responseListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("DeleteNewsError", error.getMessage() != null ? error.getMessage() : "There was an error.");
                    }
                }
        );
    }
}
