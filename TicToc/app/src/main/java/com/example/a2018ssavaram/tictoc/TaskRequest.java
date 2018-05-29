package com.example.a2018ssavaram.tictoc;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by supriya on 5/27/2018.
 */

public class TaskRequest extends StringRequest {
    private static final String TASK_URL = "https://user.tjhsst.edu/tictoc/tasks.php";
    private Map<String, String> parameters;
    public TaskRequest(String username, String task, String add, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, TASK_URL, listener, errorListener);
        parameters = new HashMap<String, String>();
        parameters.put("username", username);
        parameters.put("task", task);
        parameters.put("add", add);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
