package com.example.android.courtcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView pointsView;
    private int totalPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pointsView = (TextView) findViewById(R.id.text_score_view);
        totalPoints = Integer.parseInt(pointsView.getText().toString());
    }

    public void pointer3(View view) {
        totalPoints = totalPoints + 3;
        display();
    }

    public void pointer2(View view) {
        totalPoints = totalPoints + 2;
        display();
    }

    public void pointer1(View view) {
        totalPoints = totalPoints + 1;
        display();
    }

    private void display(){
        pointsView.setText("" +totalPoints);
    }
}