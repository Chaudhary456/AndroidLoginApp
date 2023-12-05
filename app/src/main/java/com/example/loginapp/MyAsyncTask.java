package com.example.loginapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyAsyncTask extends AsyncTask<String, Void, String> {
    public static final MediaType JSON = MediaType.get("application/json");

    OkHttpClient client = new OkHttpClient();

    AsyncTaskListener taskListener;

    public MyAsyncTask(AsyncTaskListener taskListener){
        this.taskListener = taskListener;
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d("Inside","11111");
        String url = params[0];
        String json = params[1];
        String result="";
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("RESULT",result);
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        taskListener.onEventPost(result);
        super.onPostExecute(result);
    }



}
