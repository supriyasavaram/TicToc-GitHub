package com.example.a2018ssavaram.tictoc;

/**
 * Created by 2018ssavaram on 5/16/2018.
 */

import android.app.Application;
import android.content.Context;

public class ApplicationContextProvider extends Application {
    /**
     * Keeps a reference of the application context
     */
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();

    }

    /**
     * Returns the application context
     *
     * @return application context
     */
    public static Context getContext() {
        return sContext;
    }
}
