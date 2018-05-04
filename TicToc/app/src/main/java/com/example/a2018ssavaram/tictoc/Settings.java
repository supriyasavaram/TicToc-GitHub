package com.example.a2018ssavaram.tictoc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by 2018ssavaram on 9/18/2017.
 */

public class Settings extends Fragment implements View.OnClickListener {

    private Button signout;
    private View rootView;

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.settings, container, false);
        signout = (Button) rootView.findViewById(R.id.logout);
        signout.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Settings");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                Intent i = new Intent(this.getActivity(), LoginActivity.class);
                i.putExtra("logout", true);
                startActivity(i);
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}
