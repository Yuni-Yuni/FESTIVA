package com.example.festiva;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class GetGreetingActivity extends AppCompatActivity {

    TextInputEditText ShowGreeting;
    String event_title, greeting_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_greeting);

        Window window = getWindow();
        window.setNavigationBarColor(Color.TRANSPARENT);
        window.setStatusBarColor(Color.TRANSPARENT);

        ShowGreeting = findViewById(R.id.ShowGreeting);

        getAndSetIntentData();
    }

    public void backToHomeFragment(View view) {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent); // Указываем успешный результат
        finish(); // Завершаем

    }

    void getAndSetIntentData(){
        if (getIntent().hasExtra("event_title")){

            event_title = getIntent().getStringExtra("event_title");
            greeting_text = getIntent().getStringExtra("greeting_text");
            //Log.e("event_title", greeting_text);

            //====
            ShowGreeting.setText(greeting_text);

        } else {
            //Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }

    }
}