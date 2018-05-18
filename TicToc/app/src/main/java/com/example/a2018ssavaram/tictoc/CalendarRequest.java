package com.example.a2018ssavaram.tictoc;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
/**
 *
 * Custom Request to calendar
 */
public class CalendarRequest extends StringRequest {
    private static final String CALENDAR_URL = "https://user.tjhsst.edu/tictoc/calendar.php";
    private Map<String, String> parameters;
    public CalendarRequest(String username, String id, String message, String reminder, String end, String add, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, CALENDAR_URL, listener, errorListener);
        parameters = new HashMap<String, String>();
        parameters.put("username", username);
        parameters.put("_id", id);
        parameters.put("message", message);
        parameters.put("reminder", reminder);
        parameters.put("end", end);
        parameters.put("add", add);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}