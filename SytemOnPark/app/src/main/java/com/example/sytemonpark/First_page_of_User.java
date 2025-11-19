package com.example.sytemonpark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

public class First_page_of_User extends AppCompatActivity {
    Button signUp,logIn;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("user_session", MODE_PRIVATE);

        if (prefs.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(this, User_Home_Page.class));
            finish();
        }
        setContentView(R.layout.activity_firs_page_of_user);

        signUp=findViewById(R.id.btnSignUp);
        logIn=findViewById(R.id.btnLogin);
        LottieAnimationView anim = findViewById(R.id.parkingAnimation);
        anim.setImageAssetsFolder("images/");   // must match folder name in assets
        anim.setAnimation("freeparking.json");
        anim.playAnimation();

        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(First_page_of_User.this, User_Registration_Form.class);
            startActivity(intent);
        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(First_page_of_User.this, User_Login_Page.class);
                startActivity(intent);
            }
        });
    }
}