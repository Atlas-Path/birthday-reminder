package com.example.atlaspath.models;

import java.util.Map;

public class Contact {

    public String id = "123";
    public String content = "TEST";
    public Map<String, Map> data;

    public Contact() {
    }

    public Contact(Map<String, Map> userData) {
        data = userData;
    }

}
