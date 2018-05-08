package com.example.a2018ssavaram.tictoc;

/**
 * Created by 2018ssavaram on 5/8/2018.
 */

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
public class Database extends SQLiteAssetHelper {
    private static final String DATABASE_NAMES = "events";
    private static final int DATABASE_VERSION = 3;
    public Database(Context context) {
        super(context, DATABASE_NAMES, null, DATABASE_VERSION);
    }
}
