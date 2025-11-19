package com.example.sytemonpark; // Change this to your actual package name

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_about_us extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us); // Make sure this matches your layout file name

        Button backButton = findViewById(R.id.back_to_home);

        // Go back to the previous activity (e.g., Home)
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finishes the current activity
            }
        });
    }
}
