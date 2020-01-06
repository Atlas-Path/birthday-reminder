package com.example.atlaspath.models;

import java.util.Map;

public class Contact {

    public String id = "123";
    public String content = "TEST";
    public Map<String, String> data;

    public Contact() {
    }

    public Contact(Map<String, String> userData) {
        data = userData;
    }

    public String getDisplayName() {
        String firstname = data.get("firstname");
        String lastname = data.get("lastname");

        return firstname + " " + lastname;
    }

}
