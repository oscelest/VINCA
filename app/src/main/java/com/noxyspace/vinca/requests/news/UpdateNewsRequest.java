package com.noxyspace.vinca.requests.news;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.noxyspace.vinca.requests.HttpParameter;
import com.noxyspace.vinca.requests.HttpRequest;

import org.json.JSONObject;

public class UpdateNewsRequest extends HttpRequest {
    public UpdateNewsRequest(String news_id, String title, String description, String image, String preview, Response.Listener<JSONObject> responseListener) {
        super(Method.PUT, NEWS_URL + "/" + news_id, responseListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("UpdateNewsError", error.getMessage() != null ? error.getMessage() : "There was an error.");
                    }
                },
                new HttpParameter("title", (title == null ? "" : title)),
                new HttpParameter("description", (description == null ? "" : description)),
                new HttpParameter("image", (image == null ? "" : image)),
                new HttpParameter("preview", (preview == null ? "" : preview))
        );
    }
}
