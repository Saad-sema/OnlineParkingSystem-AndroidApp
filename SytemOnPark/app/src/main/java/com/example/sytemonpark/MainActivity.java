package com.example.sytemonpark;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         // or 0 for no repeat

        // Delay to open the main activity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, First_page_of_User.class); // Change to your actual main activity
            startActivity(intent);
            finish();
        }, SPLASH_DURATION);
    }
}
