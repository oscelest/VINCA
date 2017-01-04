package com.noxyspace.vinca.requests;

public class HttpParameter {
    private String key;
    private String value;

    public HttpParameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    String getKey() {
        return this.key;
    }

    String getValue() {
        return this.value;
    }
}
