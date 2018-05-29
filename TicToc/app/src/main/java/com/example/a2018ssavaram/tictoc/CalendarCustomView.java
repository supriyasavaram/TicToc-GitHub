package com.example.a2018ssavaram.tictoc;

/**
 * Created by 2018ssavaram on 2/14/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.a2018ssavaram.tictoc.DatabaseQuery;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class CalendarCustomView extends LinearLayout { //implements AddEventDialog.AddEventDialogListener{

    private static final String TAG = CalendarCustomView.class.getSimpleName();
    private ImageView previousButton, nextButton;
    private TextView currentDate;
    private GridView calendarGridView;
    private Button addEventButton;
    private static final int MAX_CALENDAR_COLUMN = 42;
    private int month, year;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private static Context context;
    private GridAdapter mAdapter;
    private DatabaseQuery mQuery;
    private String message;
    private String reminder;
    private String end;
    private SharedPreferences addEventPreferences;
    private SharedPreferences.Editor addEventEditor;

    public CalendarCustomView(Context context) {
        super(context);
    }
    public CalendarCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeUILayout();
        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
        setAddEventButtonClickEvent();
        setGridCellClickEvents();
        Log.d(TAG, "I need to call this method");
    }
    public CalendarCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void initializeUILayout(){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);
        previousButton = (ImageView)view.findViewById(R.id.previous_month);
        nextButton = (ImageView)view.findViewById(R.id.next_month);
        currentDate = (TextView)view.findViewById(R.id.display_current_date);
        addEventButton = (Button)view.findViewById(R.id.add_calendar_event);
        calendarGridView = (GridView)view.findViewById(R.id.calendar_grid);
    }
    private void setPreviousButtonClickEvent(){
        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, -1);
                setUpCalendarAdapter();
            }
        });
    }
    private void setNextButtonClickEvent(){
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, 1);
                setUpCalendarAdapter();
            }
        });
    }
    private void setAddEventButtonClickEvent(){
        addEventPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationContextProvider.getContext());
        addEventEditor = addEventPreferences.edit();
        message = "";
        reminder = "";
        end = ""; //date in d-MM-yyyy format
        addEventButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Trying to open dialog");
                Intent i = new Intent(context, AddEventDialog.class);
                context.startActivity(i);
                message = addEventPreferences.getString("username", "");
                end = addEventPreferences.getString("date", "");

                if(!message.equals("") && !end.equals("")) {
                    RequestQueue requestQueue = Volley.newRequestQueue(ApplicationContextProvider.getContext());//Creating the RequestQueue
                    SharedPreferences loginPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationContextProvider.getContext());
                    String user = loginPreferences.getString("username", "");
                    CalendarRequest calendarReq = new CalendarRequest(user, "0", message, "none", end, "new", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("Calendar Response", response);
                            // Response from the server is in the form if a JSON, so we need a JSON Object
                            try {
                                JSONObject jsonObject = new JSONObject(response);
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
                }
                setUpCalendarAdapter();
            }
        });
    }
    private void setGridCellClickEvents(){
        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "Clicked " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setUpCalendarAdapter(){
        List<Date> dayValueInCells = new ArrayList<Date>();
        mQuery = new DatabaseQuery();
        List<EventObjects> mEvents = mQuery.getAllFutureEvents();
        Calendar mCal = (Calendar)cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        while(dayValueInCells.size() < MAX_CALENDAR_COLUMN){
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.d(TAG, "Number of date " + dayValueInCells.size());
        String sDate = formatter.format(cal.getTime());
        currentDate.setText(sDate);
        mAdapter = new GridAdapter(context, dayValueInCells, cal, mEvents);
        calendarGridView.setAdapter(mAdapter);
    }
    /*public void openDialog(){
        AddEventDialog pop = new AddEventDialog();
        Log.i(TAG, "Trying to open dialog");
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        pop.show(fragmentManager, "Add Event");
    }

    @Override
    public void applyTexts(String m, String d){
        message = m;
        end = d;
    }*/
}
