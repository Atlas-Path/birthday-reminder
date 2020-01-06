package com.example.atlaspath;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.atlaspath.models.Contact;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
        setupFilterButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        verifyUser();
    }

    protected void setupFilterButtons() {

        ArrayList<Integer> buttonIds = new ArrayList<>();
        buttonIds.add(R.id.top_button_all);
        buttonIds.add(R.id.top_button_today);
        buttonIds.add(R.id.top_button_tomorrow);
        buttonIds.add(R.id.top_button_week);
        buttonIds.add(R.id.top_button_month);
        buttonIds.add(R.id.top_button_next_month);

        for (Integer id : buttonIds) {
            Button b = findViewById(id);
            b.setOnClickListener((v) -> {
                b.setPressed(true);
                for (Integer nId : buttonIds) {
                    if (nId != id) {
                        Button c = findViewById(nId);
                        c.setPressed(false);
                    }
                }
            });
        }
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
