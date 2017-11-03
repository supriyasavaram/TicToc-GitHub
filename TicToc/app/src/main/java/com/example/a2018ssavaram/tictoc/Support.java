package com.example.a2018ssavaram.tictoc;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by 2018ssavaram on 9/18/2017.
 */

public class Support extends Fragment {

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.support, container, false);

        //Button service = (Button) findViewById(R.id.service);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Support");
    }

    public void termsOfService(View view) {
        //Intent intent = new Intent(this, TermsOfService.class);
        //startActivity(intent);
    }
}
