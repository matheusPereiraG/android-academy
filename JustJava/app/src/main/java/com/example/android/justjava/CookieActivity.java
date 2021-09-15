package com.example.android.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CookieActivity extends AppCompatActivity {
    private Button eatCookie;
    private ImageView cookieImage;
    private TextView textView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookie);
        eatCookie = findViewById(R.id.btn_eat_cookie);
        cookieImage = findViewById(R.id.android_cookie_image_view);
        textView = findViewById(R.id.status_text_view);

        eatCookie.setOnClickListener(view -> {
            cookieImage.setImageResource(R.drawable.after_cookie);
            textView.setText("I'm so full");
        });

    }

    /**
     * Called when the cookie should be eaten.
     */
    public void eatCookie(View view) {
        // TODO: Find a reference to the ImageView in the layout. Change the image.

        // TODO: Find a reference to the TextView in the layout. Change the text.

    }
}