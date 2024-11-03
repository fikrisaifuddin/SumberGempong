package com.example.s_gempong;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import Navigation.Akun;
import Navigation.History;
import Navigation.Home;
import Navigation.Tiket;
import Navigation.denah;
import User.Login;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isUserLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
            return;
        }

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.green1));
        setContentView(R.layout.activity_main);

        // Mengatur BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                fragment = new Home();
            } else if (itemId == R.id.tiket) {
                fragment = new Tiket();
            } else if (itemId == R.id.history) {
                fragment = new History();
            } else if (itemId == R.id.akun) {
                fragment = new Akun();
            }
            return loadFragment(fragment);
        });


        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

        if (savedInstanceState == null) {
            loadFragment(new Home());
        }

        FloatingActionButton fab = findViewById(R.id.fab_map);
        fab.setOnClickListener(view -> {
            Fragment fragment = new denah();
            loadFragment(fragment);
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null) // Menambahkan transaksi ke back stack
                    .commit();
            return true;
        }
        return false;
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack(); // Kembali ke fragment sebelumnya
        } else {
            super.onBackPressed(); // Jika tidak ada fragment, gunakan default back action
        }
    }
}
