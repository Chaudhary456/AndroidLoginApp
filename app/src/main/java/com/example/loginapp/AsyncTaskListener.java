package com.example.loginapp;

import okhttp3.Response;

public interface AsyncTaskListener {
    public void onEventPost(Response response);
}
