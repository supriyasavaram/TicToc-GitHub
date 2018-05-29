package com.example.a2018ssavaram.tictoc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by 2018ssavaram on 9/18/2017.
 */

public class Homework extends Fragment {
    private final RequestQueue requestQueue = Volley.newRequestQueue(ApplicationContextProvider.getContext());;
    private final SharedPreferences loginPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationContextProvider.getContext());;
    private View rootView;

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.homework, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.list_todo);
        updateTasks();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Homework");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.task_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_add_task:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Add a task");
                builder.setMessage("What do you want to do?");
                final EditText inputField = new EditText(getActivity());
                builder.setView(inputField);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String inputTask = inputField.getText().toString();
                        String user = loginPreferences.getString("username", "");
                        Log.i("User", user);
                        Log.i("Task", inputTask);
                        TaskRequest taskReq = new TaskRequest(user, inputTask, "new", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Tasks Response, Add", response);
                                // Response from the server is in the form if a JSON, so we need a JSON Object
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("added")) {
                                        Toast.makeText(ApplicationContextProvider.getContext(), "Task Added!", Toast.LENGTH_SHORT).show();
                                        //updateTasks();
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
                        requestQueue.add(taskReq);
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.create().show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateTasks(){
        String user = loginPreferences.getString("username", "");
        TaskRequest taskReq = new TaskRequest(user, "", "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Tasks Response, Update", response);
                // Response from the server is in the form if a JSON, so we need a JSON Object
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("updated")) {
                        int count = jsonObject.getInt("counter");
                        for(int x = 0; x <= count; x++) {
                            String task = jsonObject.getJSONArray("task").getString(x);
                            TextView textView = (TextView) rootView.findViewById(R.id.task_title);
                            Button done_button = (Button) rootView.findViewById(R.id.task_delete);
                            textView.setText(task);
                            done_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    deleteTask();
                                }
                            });
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
        requestQueue.add(taskReq);
    }

    public void deleteTask(){
        String user = loginPreferences.getString("username", "");
        TaskRequest taskReq = new TaskRequest(user, "", "delete", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Tasks Response, Delete", response);
                // Response from the server is in the form if a JSON, so we need a JSON Object
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("deleted")) {
                        Toast.makeText(ApplicationContextProvider.getContext(), "Task Completed!", Toast.LENGTH_SHORT).show();
                        updateTasks();
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
        requestQueue.add(taskReq);
    }
}
