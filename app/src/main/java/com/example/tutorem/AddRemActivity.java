package com.example.tutorem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddRemActivity extends AppCompatActivity {
    Button addRemButton;
    Button backButton;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rem);

        addRemButton = findViewById(R.id.addRemButton);
        addRemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //first process data
                finish();
            }
        });
        View toolbar = findViewById(R.id.toolbar);
        title = toolbar.findViewById(R.id.textViewTitle);
        title.setText("Add REM");
        backButton = toolbar.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}