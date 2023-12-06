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

public class MyAsyncTask extends AsyncTask<String, Void, Response> {
    public static final MediaType JSON = MediaType.get("application/json");

    OkHttpClient client = new OkHttpClient();

    AsyncTaskListener taskListener;

    public MyAsyncTask(AsyncTaskListener taskListener){
        this.taskListener = taskListener;
    }

    @Override
    protected Response doInBackground(String... params) {
        String url = params[0];
        String json = params[1];
        try {
        Response response;
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

            response = client.newCall(request).execute();
            Log.d("STATUS_MESSAGE",response.toString());
            return  response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    protected void onPostExecute(Response result) {
        taskListener.onEventPost(result);
        super.onPostExecute(result);
    }



}
