package com.example.a2018ssavaram.tictoc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.preference.PreferenceManager;
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

import com.android.volley.NetworkError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(LoginActivity.this);//Creating the RequestQueue

        final EditText mUser = (EditText) findViewById(R.id.username);
        final EditText mPass = (EditText) findViewById(R.id.password);
        final Button mLogin = (Button) findViewById(R.id.login);
        final Button mRegister = (Button) findViewById(R.id.register);
        //final TextView mForgot = (TextView) findViewById(R.id.forgot);
        final CheckBox mRemember = (CheckBox) findViewById(R.id.remember);
        loginPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationContextProvider.getContext());
        loginPrefsEditor = loginPreferences.edit();

        mRemember.setChecked(false);

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            mUser.setText(loginPreferences.getString("username", ""));
            mPass.setText(loginPreferences.getString("password", ""));
            mRemember.setChecked(true);
        }

        /*mForgot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });*/

        mRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPrefsEditor.clear();
                loginPrefsEditor.commit();
                loginPrefsEditor.putBoolean("saveLogin", false);
                mUser.setText(loginPreferences.getString("username", ""));
                mPass.setText(loginPreferences.getString("password", ""));
                mRemember.setChecked(false);
                saveLogin = false;
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        mLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkUser(mUser) && checkPass(mPass)){
                    String user = getText(mUser);
                    String pass = getText(mPass);

                    if (mRemember.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", user);
                        loginPrefsEditor.putString("password", pass);
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                        loginPrefsEditor.putBoolean("saveLogin", false);
                        saveLogin = false;
                    }
                    loginPrefsEditor.putString("username", user);
                    loginPrefsEditor.commit();
                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("Please Wait");
                    progressDialog.setMessage("Logging You In");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    LoginRequest loginRequest = new LoginRequest(user, pass, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("Login Response", response);
                            progressDialog.dismiss();
                            // Response from the server is in the form if a JSON, so we need a JSON Object
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getBoolean("success")) {
                                    Intent loginSuccess = new Intent(LoginActivity.this, MainActivity.class);
                                    //Passing all received data from server to next activity
                                    loginSuccess.putExtra("name", jsonObject.getString("name"));
                                    startActivity(loginSuccess);
                                    finish();
                                } else {
                                    if(jsonObject.getString("status").equals("INVALID"))
                                        Toast.makeText(LoginActivity.this, "User Not Found!", Toast.LENGTH_SHORT).show();
                                    else{
                                        Toast.makeText(LoginActivity.this, "Passwords Don't Match!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(LoginActivity.this, "Bad Response From Server", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            if (error instanceof ServerError)
                                Toast.makeText(LoginActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                            else if (error instanceof TimeoutError)
                                Toast.makeText(LoginActivity.this, "Connection Timed Out", Toast.LENGTH_SHORT).show();
                            else if (error instanceof NetworkError)
                                Toast.makeText(LoginActivity.this, "Bad Network Connection", Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(loginRequest);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed(); commented this line in order to disable back press
        //Write your code here
    }

    public String getText(EditText x){
        return x.getText().toString();
    }

    public boolean checkUser(EditText x){
        String input = x.getText().toString();
        if(input.equals("")) {
            x.setError("Enter a Username");
            return false;
        } else if(input.length() > 20) {
            x.setError("Maximum 20 Characters");
            return false;
        } else if(input.length() < 6) {
            x.setError("Minimum 6 Characters");
            return false;
        }
        x.setError(null);
        return true;
    }

    public boolean checkPass(EditText x){
        String input = x.getText().toString();
        if(input.equals("")) {
            x.setError("Enter Your Password");
            return false;
        } else if(input.length() > 20) {
            x.setError("Maximum 20 Characters");
            return false;
        } else if(input.length() < 8) {
            x.setError("Minimum 8 Characters");
            return false;
        }
        x.setError(null);
        return true;
    }
}


