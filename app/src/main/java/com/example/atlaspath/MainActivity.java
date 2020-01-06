package com.example.atlaspath;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.atlaspath.models.Contact;
import com.example.atlaspath.models.User;
import com.example.atlaspath.utils.UserSingleton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Observer;

public class MainActivity extends AppCompatActivity implements BirthdaysFragment.OnListFragmentInteractionListener {

    BottomNavigationView bottomNav;
    BirthdaysFragment birthdaysFragment;

    public void setFragment(Fragment fragment) {
        if(fragment instanceof BirthdaysFragment) {
            birthdaysFragment = (BirthdaysFragment) fragment;
        }
    }

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
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser == null || fbUser.isAnonymous()) {
            Intent intent = new Intent(MainActivity.this, EmailPasswordActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onListFragmentInteraction(Contact contact) {
        Log.d("MAIN", "Contact clicked in Main Activity");
    }
}
