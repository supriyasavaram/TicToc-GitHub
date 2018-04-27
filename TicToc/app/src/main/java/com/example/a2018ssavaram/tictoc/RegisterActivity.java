package com.example.a2018ssavaram.tictoc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 2018ssavaram on 12/15/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        requestQueue = Volley.newRequestQueue(RegisterActivity.this);

        final EditText mName = (EditText) findViewById(R.id.name);
        final EditText mUser = (EditText) findViewById(R.id.newusername);
        final EditText mPass = (EditText) findViewById(R.id.newpassword);
        final EditText mConfirmPass = (EditText) findViewById(R.id.confirmpass);
        final Button mRegister = (Button) findViewById(R.id.newbutton);
        final Button mBack = (Button) findViewById(R.id.back);

        mPass.setTypeface(Typeface.DEFAULT);
        mPass.setTransformationMethod(new PasswordTransformationMethod());
        mConfirmPass.setTypeface(Typeface.DEFAULT);
        mConfirmPass.setTransformationMethod(new PasswordTransformationMethod());

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkName(mName) && checkUser(mUser) && checkPass(mPass) && checkConfirm(mConfirmPass, mPass)){
                    //if() CHECK IF USERNAME IS TAKEN AND IS ALREADY IN DATABASE
                    String name = getText(mName);
                    String user = getText(mUser);
                    String pass = getText(mPass);
                    String confirmPass = getText(mConfirmPass);

                    final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
                    progress.setTitle("Please Wait");
                    progress.setMessage("Creating Your Account");
                    progress.setCancelable(false);
                    progress.show();
                    //Validation Success
                    RegisterRequest registerRequest = new RegisterRequest(name, user, pass, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("Response", response);
                            progress.dismiss();
                            try {
                                if (new JSONObject(response).getBoolean("success")) {
                                    Toast.makeText(RegisterActivity.this, "Account Successfully Created!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else if (new JSONObject(response).getString("status").equals("USERNAME")){
                                    Toast.makeText(RegisterActivity.this, "Username Is Already Taken. Please Try Again!", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(RegisterActivity.this, "Something Has Happened. Please Try Again!", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    requestQueue.add(registerRequest);
                }
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public String getText(EditText x){
        return x.getText().toString();
    }

    public boolean checkName(EditText x){
        String input = x.getText().toString();
        if(input.equals("")) {
            x.setError("Enter Your Name");
            return false;
        } else if(input.length() > 50) {
            x.setError("Maximum 50 Characters");
            return false;
        }
        x.setError(null);
        return true;
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

    public boolean checkConfirm(EditText x, EditText y){
        String confirm = x.getText().toString();
        String real = y.getText().toString();
        if(confirm.equals("")) {
            x.setError("Re-Enter Your Password");
            return false;
        } else if(!confirm.equals(real)){
            x.setError("Passwords Do Not Match");
            y.setError("Passwords Do Not Match");
            return false;
        }
        x.setError(null);
        return true;
    }
}
