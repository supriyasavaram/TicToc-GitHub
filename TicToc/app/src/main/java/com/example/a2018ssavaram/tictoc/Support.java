package com.example.a2018ssavaram.tictoc;


import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by 2018ssavaram on 9/18/2017.
 */

public class Support extends Fragment implements View.OnClickListener {

    private Button terms;
    private View rootView;

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.support, container, false);
        terms = (Button) rootView.findViewById(R.id.service);
        terms.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Support");
    }

    private void showDialog() {
        Pop popup = new Pop();
        popup.show(getActivity().getFragmentManager(), "Terms");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.service:
                showDialog();
                break;
        }
    }
}
