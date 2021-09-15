package com.example.android.courtcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView pointsViewA;
    private TextView pointsViewB;
    private int totalPointsA;
    private int totalPointsB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pointsViewA = (TextView) findViewById(R.id.text_scoreA_view);
        pointsViewB = (TextView) findViewById(R.id.text_scoreB_view);
        totalPointsA = Integer.parseInt(pointsViewA.getText().toString());
        totalPointsB = Integer.parseInt(pointsViewB.getText().toString());
    }

    public void pointer3A(View view) {
        totalPointsA = totalPointsA + 3;
        displayForTeamA();
    }

    public void pointer2A(View view) {
        totalPointsA = totalPointsA + 2;
        displayForTeamA();
    }

    public void pointer1A(View view) {
        totalPointsA = totalPointsA + 1;
        displayForTeamA();
    }

    public void pointer3B(View view) {
        totalPointsB = totalPointsB + 3;
        displayForTeamB();
    }

    public void pointer2B(View view) {
        totalPointsB = totalPointsB + 2;
        displayForTeamB();
    }

    public void pointer1B(View view) {
        totalPointsB = totalPointsB + 1;
        displayForTeamB();
    }

    private void displayForTeamA(){
        pointsViewA.setText(String.valueOf(totalPointsA));
    }
    private void displayForTeamB() { pointsViewB.setText(String.valueOf(totalPointsB));}
}