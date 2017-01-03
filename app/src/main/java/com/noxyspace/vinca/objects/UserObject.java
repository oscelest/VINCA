package com.noxyspace.vinca.objects;

public class UserObject {

    public UserObject(int id, String first_name, String last_name, String email, boolean admin, String user_token) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.admin = admin;
        this.user_token = user_token;
    }

    private int id;
    private String first_name, last_name, user_token, email;
    private boolean admin;

    public int getId() {
        return id;
    }

    public String getUserToken() {
        return user_token;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getAdmin() {
        return admin;
    }

}
