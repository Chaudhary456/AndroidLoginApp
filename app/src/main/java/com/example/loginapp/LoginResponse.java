package com.example.loginapp;

import android.util.Log;

import com.google.gson.Gson;

public class LoginResponse {
        private int id;
        private String username;
        private String email;
        private String firstName;
        private String lastName;
        private String gender;
        private String image;
        private String token;

        // Constructors...

        // Getter and setter methods for id
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        // Getter and setter methods for username
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        // Getter and setter methods for email
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        // Getter and setter methods for firstName
        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            Log.d("InLoginResponseClass","firstname_set");
            this.firstName = firstName;
        }

        // Getter and setter methods for lastName
        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        // Getter and setter methods for gender
        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        // Getter and setter methods for image
        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        // Getter and setter methods for token
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

    // toJson method to convert User object to JSON string
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // fromJson method to convert JSON string to User object
    public static LoginResponse fromJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, LoginResponse.class);
    }

}
