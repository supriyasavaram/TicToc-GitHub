package com.example.a2018ssavaram.tictoc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 2018ssavaram on 11/8/2017.
 */

public class Pop extends DialogFragment {

    private TextView wecan;
    private TextView copyright;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        TextView title = new TextView(getActivity());
        title.setText("Terms of Service");
        title.setBackgroundColor(Color.parseColor("#D3D3D3"));
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.parseColor("#4a4a4a"));
        title.setTextSize(20);
        title.setTypeface(null, Typeface.BOLD);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.termspopup, null))
            .setCustomTitle(title)
            .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                
            }
            })
        .setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // Prevent dialog close on back press button
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });
        Dialog dialog = builder.create();
        return dialog;
    }

    /*
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.termspopup);

        wecan = (TextView) view.findViewById(R.id.wecan);
        copyright = (TextView) view.findViewById(R.id.copyright);

        String wecancontents[] = this.getString(R.string.wecan).split("\n");
        int bulletGap = (int) dp(10);
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        for (int i = 0; i < wecancontents.length; i++) {
            String line = wecancontents[i];
            SpannableString ss = new SpannableString(line);
            ss.setSpan(new BulletSpan(bulletGap), 0, line.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.append(ss);

            //avoid last "\n"
            if(i+1<wecancontents.length)
                ssb.append("\n");
        }
        wecan.setText(ssb);

        String contentcopyright[] = this.getString(R.string.contentscopyright).split("\n");
        SpannableStringBuilder s = new SpannableStringBuilder();
        for (int i = 0; i < contentcopyright.length; i++) {
            String line = contentcopyright[i];
            SpannableString ss = new SpannableString(line);
            ss.setSpan(new BulletSpan(bulletGap), 0, line.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            s.append(ss);

            //avoid last "\n"
            if(i+1<contentcopyright.length)
                s.append("\n");
        }
        copyright.setText(s);

        /*DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.8));*/

        /*String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);

    }

    private float dp(int dp) {
        return getResources().getDisplayMetrics().density * dp;
    }*/
}
