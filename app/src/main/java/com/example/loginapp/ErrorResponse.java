package com.example.loginapp;

import com.google.gson.Gson;

public class ErrorResponse {
    private String message;

    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // fromJson method to convert JSON string to User object
    public static ErrorResponse fromJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, ErrorResponse.class);
    }
}
