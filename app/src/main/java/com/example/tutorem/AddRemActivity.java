package com.example.tutorem;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class AddRemActivity extends AppCompatActivity {
    Button backButton;
    TextView title;
    ViewFlipper viewFlipper;
    EditText editTextRemName;
    EditText editTextTimeInterval;
    TimeEditText timeEditTextTime;
    boolean askForTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rem);

        viewFlipper = findViewById(R.id.view_flipper);
        editTextRemName = findViewById(R.id.editTextRemName);
        editTextTimeInterval = findViewById(R.id.editTextTimeInterval);
        timeEditTextTime = findViewById(R.id.timeEditTextTime);

        View toolbar = findViewById(R.id.toolbar);
        title = toolbar.findViewById(R.id.textViewTitle);
        title.setText("Add REM");
        backButton = toolbar.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewFlipper.getDisplayedChild() == 0) {
                    finish();
                }else{
                    viewFlipper.showPrevious();
                }
            }
        });
    }
    public void nextView(View v){
        viewFlipper.showNext();
    }
    public void createRem(View v){
        String remName = editTextRemName.getText().toString();
        String dayInterval = editTextTimeInterval.getText().toString();
        if(remName == null || remName.isEmpty()){
            editTextRemName.setHintTextColor(Color.RED);
            editTextRemName.setHint("Missing Rem Name");
            viewFlipper.setDisplayedChild(0);
        }else if(dayInterval == null || dayInterval.isEmpty()){
            editTextTimeInterval.setHintTextColor(Color.RED);
            editTextTimeInterval.setHint("?");
            viewFlipper.setDisplayedChild(2);
        }else if(timeEditTextTime.getHour() == 0 && timeEditTextTime.getMinutes() == 0 && !askForTime){
            LinearLayout timeLayout = findViewById(R.id.timeLayout);
            TextView textView = new TextView(this);
            textView.setText("Are you sure\nabout this time?");
            textView.setTextSize(30);
            textView.setTextColor(Color.RED);
            timeLayout.addView(textView);
            this.askForTime = true;
            viewFlipper.setDisplayedChild(2);
        }else{
            //save Rem
            finish();
        }

    }
}