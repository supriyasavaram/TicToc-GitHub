package com.example.a2018ssavaram.tictoc;

/**
 * Created by 2018ssavaram on 4/18/2018.
 */

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_URL = "https://director.tjhsst.edu/site/1088/None/register.php";
    private Map<String, String> parameters;
    public RegisterRequest(String name, String username, String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("name", name);
        parameters.put("username", username);
        parameters.put("password", password);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
