package com.example.atlaspath.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.Map;

public class ContactManager {

    public ArrayList<Contact> contacts;

    /**
     * Constructor
     * @param userData
     */
    public ContactManager(Map<String, Object> userData) {
        contacts = new ArrayList<>();
        try {
            Map<String, Map> o = (Map<String, Map>) userData.get("contacts");
            for(Map.Entry<String, Map> entry : o.entrySet()) {
                contacts.add(new Contact((Map<String,String>)entry.getValue()));
            }

        }
        catch (NullPointerException ex) {
            Log.d("ERROR", "Mapping exception in Contact Manager", ex);
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage(), e);
        }
    }
}
