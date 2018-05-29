package com.example.a2018ssavaram.tictoc;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by supriya on 5/26/2018.
 */

public class AddEventDialog extends AppCompatActivity implements View.OnClickListener {
    private EditText messageText, dateText;
    private Button okay, cancel;
    private SharedPreferences addEventPreferences;
    private SharedPreferences.Editor addEventEditor;
    //private AddEventDialogListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addevent);

        addEventPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationContextProvider.getContext());
        addEventEditor = addEventPreferences.edit();

        messageText = (EditText) findViewById(R.id.message);
        dateText = (EditText) findViewById(R.id.date);
        okay = (Button) findViewById(R.id.ok_btn_id);
        cancel = (Button) findViewById(R.id.cancel_btn_id);
        this.setFinishOnTouchOutside(false);

        okay.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_btn_id:
                addEventEditor.putString("message", messageText.getText().toString());
                addEventEditor.putString("date", dateText.getText().toString());
                this.finish();
                break;
            case R.id.cancel_btn_id:
                this.finish();
                break;
        }
    }

    /*@Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(ApplicationContextProvider.getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.addevent, null);
        builder.setView(view)
                .setTitle("Add Event")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        String message = messageText.getText().toString();
                        String date = dateText.getText().toString();
                        listener.applyTexts(message, date);
                    }
                });
        messageText = (EditText) view.findViewById(R.id.message);
        dateText = (EditText) view.findViewById(R.id.date);
        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            listener = (AddEventDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement AddEventDialogListener");
        }
    }

    public interface AddEventDialogListener{
        void applyTexts(String m, String d);
    }*/
}
