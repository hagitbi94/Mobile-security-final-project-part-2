package com.example.myapplication.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


import com.example.myapplication.Fragment.Call_Fragment;

import com.example.myapplication.Fragment.SMS_Fragment;
import com.example.myapplication.Object.Status;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class Main_Screen extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;
    private ImageView main_IMG_Log_Out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__screen);

        findView();
        initButton();

        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.Main_FRG_Content, new SMS_Fragment()).commit();
    }

    // Set bottom navigation bar
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {

                case R.id.Nav_SMSHistory:
                    selectedFragment = new SMS_Fragment();
                    break;
                case R.id.Nav_CallHistory:
                    selectedFragment = new Call_Fragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.Main_FRG_Content, selectedFragment).commit();

            return true;
        }
    };

    private void initButton() {
        // Log out button from the program
        main_IMG_Log_Out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;

                Status status = new Status();
                status.setOn(false);

                FirebaseDatabase.getInstance().getReference("STATUS/").child(FirebaseAuth.getInstance().getCurrentUser().getUid() + "/").setValue(status);

                FirebaseAuth.getInstance().signOut();

                intent = new Intent(getApplicationContext(), Open_Screen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void findView() {
        bottomNavigation = findViewById(R.id.Main_BTN_Navigation);
        main_IMG_Log_Out = findViewById(R.id.Main_IMG_Log_Out);
    }
}