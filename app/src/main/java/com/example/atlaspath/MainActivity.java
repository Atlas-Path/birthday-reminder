package com.example.atlaspath;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.atlaspath.models.UserRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    private Observer userRepoChanged = new Observer() {
        @Override
        public void update(Observable observable, Object o) {
            Log.d(MainActivity.class.getSimpleName(), "User Repository Updated with data");

            TextView textView = findViewById(R.id.fbUsername);
            if (observable == null) {
                Log.d("HERE", "Observable object is null =(");
            } else {
                String displayName = "Display Name: " + ((UserRepository) observable).getDisplayName();
                textView.setText(displayName);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBottomNavActions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        verifyUser();
    }

    protected void setupBottomNavActions() {
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.navigation_birthdays:
                    Toast.makeText(MainActivity.this, "Birthdays", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigation_contacts:
                    Toast.makeText(MainActivity.this, "Contacts", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigation_settings:
                    Intent intent = new Intent(MainActivity.this, EmailPasswordActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });
    }

    protected void verifyUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && !user.isAnonymous()) {
            UserRepository userRepo = new UserRepository(user.getUid());
            userRepo.addObserver(userRepoChanged);
        } else {
            Intent intent = new Intent(MainActivity.this, EmailPasswordActivity.class);
            startActivity(intent);
        }
    }


}
