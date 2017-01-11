package com.noxyspace.vinca.requests.news;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.noxyspace.vinca.requests.HttpParameter;
import com.noxyspace.vinca.requests.HttpRequest;

import org.json.JSONObject;

public class CreateNewsRequest extends HttpRequest {
    public CreateNewsRequest(String title, String description, String image, String preview, Response.Listener<JSONObject> responseListener) {
        super(Method.POST, NEWS_URL, responseListener,
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("CreateNewsError", "There was an error.");
                }
            },
                new HttpParameter("title", (title == null ? "" : title)),
                new HttpParameter("description", (description == null ? "" : description)),
                new HttpParameter("image", (image == null ? "" : image)),
                new HttpParameter("preview", (preview == null ? "" : preview))
        );
    }
}
