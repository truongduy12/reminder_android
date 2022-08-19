package com.example.reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.reminder.adapters.CardReminderAdapter;
import com.example.reminder.models.Reminder;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private NavigationBarView navigationBarView;
    private FirebaseAuth auth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth = FirebaseAuth.getInstance();

        getElements();
        setElementBehaviors();

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("some_int", 0);

//            getSupportFragmentManager().beginTransaction()
//                    .setReorderingAllowed(true)
//                    .add(R.id.fragment_container_view, UpcomingRemindFragment.class, bundle)
//                    .commit();
        }

        RecyclerView recyclerView = findViewById(R.id.fragment_upcoming_cardList);
        CardReminderAdapter adapter;
        ArrayList<Reminder> reminders;

        reminders = new ArrayList<Reminder>();

        for (int i = 0; i < 10; i++) {
            reminders.add(new Reminder("Title", "Content.....", "red"));
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new CardReminderAdapter(reminders, this));

    }

    private void getElements() {
        navigationBarView = findViewById(R.id.home_bottomNavigation);
    }

    private void setElementBehaviors() {
        navigationBarView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.page_upcoming:
                    break;
                case R.id.page_completed:
                    break;
                case R.id.page_setting:
                    break;
            }
            return false;
        });
    }
}