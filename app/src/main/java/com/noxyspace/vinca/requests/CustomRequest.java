package com.noxyspace.vinca.requests;

import com.android.volley.*;
import com.android.volley.Request.*;
import com.android.volley.Response.*;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class CustomRequest extends Request<JSONObject>
{
    private static final String SERVER_URL = "http://178.62.117.85:8080";
    protected static final String USER_URL = SERVER_URL + "/api/users";

    private Listener<JSONObject> listener;
    private Map<String, String> params;

    public CustomRequest(int method, String url, Map<String, String> params, Listener<JSONObject> reponseListener, ErrorListener errorListener)
    {
        super(method, url, errorListener);

        this.listener = reponseListener;
        this.params = params;
    }

    protected Map<String, String> getParams() throws com.android.volley.AuthFailureError
    {
        return params;
    };

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response)
    {
        try
        {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        }
        catch (UnsupportedEncodingException e)
        {
            return Response.error(new ParseError(e));
        }
        catch (JSONException e)
        {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response)
    {
        listener.onResponse(response);
    }
}