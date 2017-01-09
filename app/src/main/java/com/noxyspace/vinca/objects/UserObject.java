package com.noxyspace.vinca.objects;

public class UserObject {

    public UserObject(String id, String first_name, String last_name, String email, boolean admin, boolean verified, String user_token) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.admin = admin;
        this.verified = verified;
        this.user_token = user_token;
    }

    private String id, first_name, last_name, user_token, email;
    private boolean admin, verified;

    public String getId() {
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
