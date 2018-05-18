package com.example.a2018ssavaram.tictoc;

/**
 * Created by 2018ssavaram on 5/8/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.util.EventLog;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.a2018ssavaram.tictoc.EventObjects;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseQuery{
    private RequestQueue requestQueue;
    private SharedPreferences loginPreferences;
    private Date dateToday;
    List<EventObjects> events;

    public DatabaseQuery() {
        dateToday = new Date();
        events = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(ApplicationContextProvider.getContext());//Creating the RequestQueue
        loginPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationContextProvider.getContext());
    }
    public List<EventObjects> getAllFutureEvents(){
        String user = loginPreferences.getString("username", "");

        CalendarRequest calendarReq = new CalendarRequest(user, "0", "none", "none", "none", "none", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Calendar Response", response);
                // Response from the server is in the form if a JSON, so we need a JSON Object
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("success")) {
                        int id = Integer.parseInt(jsonObject.getString("_id"));
                        String message = jsonObject.getString("message");
                        String startDate = jsonObject.getString("end");
                        //convert start date to date object
                        Date reminderDate = convertStringToDate(startDate);
                        if(reminderDate.after(dateToday) || reminderDate.equals(dateToday)){
                            events.add(new EventObjects(id, message, reminderDate));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ApplicationContextProvider.getContext(), "Bad Response From Server", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof ServerError)
                    Toast.makeText(ApplicationContextProvider.getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText(ApplicationContextProvider.getContext(), "Connection Timed Out", Toast.LENGTH_SHORT).show();
                else if (error instanceof NetworkError)
                    Toast.makeText(ApplicationContextProvider.getContext(), "Bad Network Connection", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(calendarReq);

        /*String query = "select * from reminder";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String message = cursor.getString(cursor.getColumnIndexOrThrow("message"));
                String startDate = cursor.getString(cursor.getColumnIndexOrThrow("start_date"));
                //convert start date to date object
                Date reminderDate = convertStringToDate(startDate);
                if(reminderDate.after(dateToday) || reminderDate.equals(dateToday)){
                    events.add(new EventObjects(id, message, reminderDate));
                }
            }while (cursor.moveToNext());
        }
        cursor.close();*/
        return events;
    }
    private Date convertStringToDate(String dateInString){
        DateFormat format = new SimpleDateFormat("d-MM-yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
