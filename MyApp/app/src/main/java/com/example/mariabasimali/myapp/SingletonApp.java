package com.example.mariabasimali.myapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by fahad on 10/9/2018.
 */

public class SingletonApp extends Application {

    public static final String TAG = SingletonApp.class
            .getSimpleName();

    public static final String BASE_URL = "https://192.168.0.106/";

    private RequestQueue mRequestQueue;

    private static SingletonApp mInstance;
    public static SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        preferences = getSharedPreferences("MyPref",MODE_PRIVATE);
    }

    public static synchronized SingletonApp getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
