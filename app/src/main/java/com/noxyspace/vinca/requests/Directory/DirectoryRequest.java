package com.noxyspace.vinca.requests.Directory;

import com.android.volley.*;
import com.noxyspace.vinca.Folder;
import com.noxyspace.vinca.Objects.ApplicationObject;
import com.noxyspace.vinca.Objects.DirectoryObject;
import com.noxyspace.vinca.Objects.UserObject;
import com.noxyspace.vinca.requests.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import static com.noxyspace.vinca.Objects.DirectoryObject.ObjectType.File;

public class DirectoryRequest extends CustomRequest {

    public DirectoryRequest(String user_token, int folder_id) {
        /* Login is not yet added to the server's REST events */
        super(Method.POST, DIRECTORY_URL + "/login", generateParamsList(folder_id),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("success")) {
                            try {
                                if (response.getBoolean("success")) {
                                    Log.d("LoginRequestSuccess", response.toString());
                                    JSONObject content = response.getJSONObject("content");
                                    JSONArray directory = content.getJSONArray("directory");
                                    List<DirectoryObject> dirlist = new ArrayList<DirectoryObject>();
                                    for (int i = 0; i < directory.length(); i++) {
                                        Object o = directory.get(i);
                                        dirlist.add(new DirectoryObject(1, "title", File, "test", 0, true));
                                    }
                                    ApplicationObject.setDirectory(dirlist);
                                } else {
                                    Log.d("LoginRequestFailure", response.toString());
                                /* Do failure-stuff */
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("LoginRequestErr", error.getMessage());
                    }
                });
    }

    private static Map<String, String> generateParamsList(int id) {
        Map<String, String> params = new HashMap<String, String>();

        params.put("id", Integer.toString(id));

        return params;
    }
}