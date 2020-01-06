package com.example.atlaspath.models;

import java.util.Map;

public class Contact {

    public String id = "";
    public String content = "";
    public Map<String, Map> data;

    public Contact() {
    }

    public Contact(Map<String, Map> userData) {
        data = userData;
    }

}
