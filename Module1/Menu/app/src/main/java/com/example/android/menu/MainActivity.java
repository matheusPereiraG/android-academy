package com.example.android.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView menu1;
    TextView menu2;
    TextView menu3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menu1 = findViewById(R.id.menu_item_1);
        menu2 = findViewById(R.id.menu_item_2);
        menu3 = findViewById(R.id.menu_item_3);
    }

    public void printToLogs(View view) {
        Log.i(this.getClass().getSimpleName(), menu1.getText().toString());
        Log.w(this.getClass().getSimpleName(), menu2.getText().toString());
        Log.e(this.getClass().getSimpleName(), menu3.getText().toString());
    }
}