package com.example.atlaspath.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.Map;
import java.util.Observable;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class User extends Observable implements Serializable {

    private FirebaseFirestore db;
    private DocumentSnapshot userDoc;
    private Map<String, Object> userData;
    private Boolean loaded = false;

    public ContactManager contactManager;

    /**
     * Constructor
     * @param userID
     */
    public User(String userID) {
        // Access a Cloud Firestore instance from your Activity
        this.db = FirebaseFirestore.getInstance();
        if(userID != null) {
            this.getUser(userID);
        }
    }

    /**
     * Get User
     * @param userID
     */
    private void getUser(String userID) {
        String collectionName = "users";
        DocumentReference docRef = db.collection(collectionName).document(userID);
        docRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                userDoc = task.getResult();
                if(userDoc != null && userDoc.exists()) {
                    // Get the core data
                    userData = userDoc.getData();

                    // Mark User as loaded
                    loaded = true;

                    // Load contact manager with user data
                    contactManager = new ContactManager(userData);

                    // This will notify observers that data has been loaded
                    setChanged();
                    notifyObservers();
                }
                else {
                    Log.d(TAG, "No such document");
                }
            }
            else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
    }

    /**
     * Get Display Name
     * @return String
     */
    public String getDisplayName() {
        String displayName = "";
        if(this.userDoc != null
                && this.userDoc.exists()
                && this.userData.containsKey("firstname")
                && this.userData.containsKey("lastname")
        ) {
            displayName = this.userData.get("firstname")
                    + " "
                    + this.userData.get("lastname");
        }
        return displayName;
    }

    public Boolean isLoaded() {
        return loaded;
    }

    /**
     * Set Value
     * @param key
     * @param value
     */
    public void setValue(String key, Object value) {
        this.userData.put(key, value);
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Get Value
     * @param key
     * @return
     */
    public Object getValue(String key) {
        return this.userData.containsKey(key)
                ? this.userData.get(key)
                : null;
    }

    /**
     * Has Key
     * @param key
     * @return
     */
    public Boolean hasKey(String key) {
        return this.userData.containsKey(key);
    }
}
