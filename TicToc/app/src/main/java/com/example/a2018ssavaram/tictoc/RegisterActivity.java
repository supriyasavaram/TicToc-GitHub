package com.example.a2018ssavaram.tictoc;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 2018ssavaram on 12/15/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                if(checkIfEmpty(mName) && checkIfEmpty(mUser) && checkIfEmpty(mPass) && checkIfEmpty(mConfirmPass)){
                    //if() CHECK IF USERNAME IS TAKEN AND IS ALREADY IN DATABASE
                     if(mPass.getText().toString().matches(mConfirmPass.getText().toString()) == false)
                         Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
                     else {
                         //ENTER INFO IN DATABASE
                         Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                         finish();
                     }
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

    public boolean checkIfEmpty(EditText x){
        String input = x.getText().toString();
        if (input.matches("")) {
            Toast.makeText(this, "Enter all fields.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
