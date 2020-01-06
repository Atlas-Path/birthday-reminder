package com.example.atlaspath;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atlaspath.models.Contact;
import com.example.atlaspath.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import io.opencensus.tags.Tag;

public class MainActivity extends AppCompatActivity implements BirthdaysFragment.OnListFragmentInteractionListener {

    BottomNavigationView bottomNav;

    private Observer userRepoChanged = (observable, o) -> {
        Log.d(MainActivity.class.getSimpleName(), "User Repository Updated with data");
        if (observable == null) {
            Log.d("MAIN", "Observable object is null =(");
        } else {
            User user = ((User) observable);
            if(user.isLoaded()) {

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

    /**
     * Verify User
     */
    protected void verifyUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && !user.isAnonymous()) {
            User userRepo = new User(user.getUid());
            userRepo.addObserver(userRepoChanged);
        } else {
            Intent intent = new Intent(MainActivity.this, EmailPasswordActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onListFragmentInteraction(Contact contact) {
        Log.d("MAIN", "Contact clicked in Main Activity");
    }
}
