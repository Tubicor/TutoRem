package com.example.tutorem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.tutorem.Instrumentation.JSONHandler;

import java.util.Calendar;

public class AddRemActivity extends AppCompatActivity {
    ViewFlipper viewFlipper;
    EditText editTextRemName;
    EditText editTextTimeInterval;
    TimeEditText timeEditTextTime;
    boolean askForTime = false;
    ToolbarSetter toolbarSetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rem);
        toolbarSetter = new ToolbarSetter(false,"linearLayout",this,true);


        viewFlipper = findViewById(R.id.view_flipper);
        editTextRemName = findViewById(R.id.editTextRemName);
        editTextTimeInterval = findViewById(R.id.editTextTimeInterval);
        timeEditTextTime = findViewById(R.id.timeEditTextTime);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if(viewFlipper.getDisplayedChild() == 0) {
                    finish();
                }else{
                    viewFlipper.showPrevious();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

            JSONHandler jsonHandler = new JSONHandler(this);
            JSONHandler.activity activity = new JSONHandler.activity();
            activity.setName(remName);
            activity.setIntervalValue(Integer.parseInt(dayInterval));
            activity.setIntervalHour(timeEditTextTime.getHour()+timeEditTextTime.getMinutes());
            activity.setNotes("These are notes");//TODO
            activity.setStartDate(Calendar.getInstance().getTime());
            activity.setEndDate(Calendar.getInstance().getTime());//TODO
            activity.refreshNextNotification();
            activity.setId(""+jsonHandler.getNextID());

            Log.d("ADDREM","Rem with id"+jsonHandler.getNextID());
            jsonHandler.addActivity(activity);
            jsonHandler.saveData();
            finish();
        }

    }
}