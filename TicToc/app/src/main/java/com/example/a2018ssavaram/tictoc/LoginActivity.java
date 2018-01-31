package com.example.a2018ssavaram.tictoc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    //private SharedPreferences loginPreferences;
    //private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText mUser = (EditText) findViewById(R.id.username);
        final EditText mPass = (EditText) findViewById(R.id.password);
        final Button mLogin = (Button) findViewById(R.id.login);
        final Button mRegister = (Button) findViewById(R.id.register);
        //final TextView mForgot = (TextView) findViewById(R.id.forgot);
        final CheckBox mRemember = (CheckBox) findViewById(R.id.remember);
        //loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        //loginPrefsEditor = loginPreferences.edit();

        mRemember.setChecked(false);

        /*saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            mUser.setText(loginPreferences.getString("username", ""));
            mPass.setText(loginPreferences.getString("password", ""));
            mRemember.setChecked(true);
        }*/

        /*mForgot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });*/

        mRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                /*loginPrefsEditor.clear();
                loginPrefsEditor.commit();
                loginPrefsEditor.putBoolean("saveLogin", false);
                mUser.setText(loginPreferences.getString("username", ""));
                mPass.setText(loginPreferences.getString("password", ""));
                mRemember.setChecked(false);
                saveLogin = false;*/
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        mLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkIfEmpty(mUser) && checkIfEmpty(mPass)){
                    if (mRemember.isChecked()) {
                        /*loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", mUser.getText().toString());
                        loginPrefsEditor.putString("password", mPass.getText().toString());
                        loginPrefsEditor.commit();*/
                    } else {
                        /*loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                        loginPrefsEditor.putBoolean("saveLogin", false);
                        saveLogin = false;*/
                    }
                    //check if password matches the one for that username in database
                    
                    /*else{
                        Intent mainIntent =  new Intent(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.startActivity(mainIntent);
                        LoginActivity.this.finish();
                    }*/
                }

            }
        });
    }

    public boolean checkIfEmpty(EditText x){
        String input = x.getText().toString();
        if (input.matches("")) {
            Toast.makeText(this, "Enter all fields.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}


