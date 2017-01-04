package com.noxyspace.vinca.requests;

import com.android.volley.*;
import com.android.volley.Request.*;
import com.android.volley.Response.*;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CustomRequest extends Request<JSONObject> {
    private static final String SERVER_URL = "http://178.62.117.85:8080";

    /* User URL
     * GET = Get user object/info ('/' = admin-only/all users, '/self' = own user, '/:id' = user with given id)
     * POST = Register user object
     * PUT = Update user object/info ('/' and '/self' = own user, '/:id' = admin-only/user with given id)
     * DELETE = Delete user object ('/self' = own user, '/:id' = admin-only/user with given id)
     * */
    protected static final String USER_URL = SERVER_URL + "/api/users";

    /* News URL
     * GET = Get news object(s) ('/' = all news, '/:Id' = news with given id)
     * POST = Create news object (admin-only)
     * PUT = Update news object ('/:id' = admin-only/news with given id)
     * DELETE = Delete news object ('/:id' = admin-only/news with given id)
     * */
    protected static final String NEWS_URL = SERVER_URL + "/api/news";

    /* File/Folder URL
     * GET = Get object(s) ('/' = all objects, '/self' = current users objects, '/:Id' = object with given id)
     * POST = Create object
     * PUT = Update object ('/:id' = object with given id)
     * DELETE = Delete object ('/:id' = object with given id, '/:id/permanently' = object with given id, but permanently)
     * */
    protected static final String DIRECTORY_URL = SERVER_URL + "/api/directory";

    private Listener<JSONObject> listener;
    private Map<String, String> params;

    public CustomRequest(int method, String url, Listener<JSONObject> reponseListener, ErrorListener errorListener, HttpParameter... params) {
        super(method, url, errorListener);

        this.listener = reponseListener;
        this.generateParamsList(params);
    }

    protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
        return params;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        }
        catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
        catch (JSONException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }

    private void generateParamsList(HttpParameter... params) {
        if (this.params == null) {
            this.params = new HashMap<>();
        }

        this.params.clear();

        for (HttpParameter param : params) {
            this.params.put(param.getKey(), param.getValue());
        }
    }
}