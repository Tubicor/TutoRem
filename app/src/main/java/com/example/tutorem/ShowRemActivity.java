package com.example.tutorem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tutorem.Instrumentation.Converter;
import com.example.tutorem.Instrumentation.JSONHandler;

import java.util.Calendar;

//TODO additional Field for overall notes
public class ShowRemActivity extends AppCompatActivity {
    private ToolbarSetter toolbarSetter;
    private EditText editTextViewName;
    private TextView textViewStartDate;
    private TextView nextNotification;
    private EditText editTextDays;
    private TimeEditText editTextTime;
    private EditText sessionNotes;
    private EditText sessionRating;
    private LinearLayout sessionLayout;
    private String activityId;
    private Button postponeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rem);
        toolbarSetter = new ToolbarSetter(false,"",this,true);

        JSONHandler jsonHandler = new JSONHandler(this);
        if(savedInstanceState == null){
            Bundle bundle = getIntent().getExtras();
            if(bundle == null){
                Log.d("SHWOREM","Could not load Rem");
                finish();
            }else {
                activityId = bundle.getString("id");
            }
        }else{
            activityId =(String)savedInstanceState.getSerializable("id");
        }if(activityId == null || activityId.equals("")){
            Log.d("SHWOREM","Could not load Rem");
            finish();
        }
        JSONHandler.activity activity = jsonHandler.getActivity(activityId);
        editTextViewName = findViewById(R.id.showName);
        textViewStartDate = findViewById(R.id.showStartDate);
        editTextDays = findViewById(R.id.showDays);
        editTextTime = findViewById(R.id.showTime);
        sessionNotes = findViewById(R.id.nSessionNotes);
        sessionRating = findViewById(R.id.nSessionRating);
        sessionLayout = findViewById(R.id.sessionLayout);
        nextNotification = findViewById(R.id.nextNotification);
        postponeButton = findViewById(R.id.buttonPostpone);

        //fill sessions
        for(JSONHandler.activity.session session:activity.sessionList) {
            TextView textView = new TextView(this);
            textView.setText(Converter.dateToReadableString(session.sessionDate)+" - Rated: "+session.sessionRating+"\n\t"+session.sessionNotes+"");
            textView.setTextColor(getResources().getColor(R.color.white));
            sessionLayout.addView(textView);
        }

        editTextViewName.setText(activity.name);
        textViewStartDate.setText("Started on : "+Converter.dateToReadableString(activity.startDate));
        editTextDays.setText(""+activity.intervalValue);
        editTextTime.setHour((int)activity.intervalHour/100);
        editTextTime.setMinutes(activity.intervalHour%100);
        nextNotification.setText(Converter.dateToReadableString(activity.nextNotiDate) + " at "+(int)(activity.intervalHour/100)+":"+activity.intervalHour%100);
        //show the PostPoneButton only when the Rem is due
        for(JSONHandler.activity ac :jsonHandler.getNextActivity(true)){
            if(this.activityId.equals(ac.id)){
                this.postponeButton.setVisibility(View.VISIBLE);
            }
        }

    }
    public void addSession(View v){
        int rating;
        if(sessionRating.getText().toString() == null ||sessionRating.getText().toString().equals("")){
            rating = -1;
        }else {
             rating = Integer.parseInt(sessionRating.getText().toString());
        }
        if(rating> 10 || rating<0){
            sessionRating.setText("");
            sessionRating.setHint("Rating between 0 and 10!");
            sessionRating.setHintTextColor(Color.RED);
        }else {
            JSONHandler.activity.session session = new JSONHandler.activity.session();
            session.sessionRating = rating;
            session.sessionDate = Calendar.getInstance().getTime();
            session.sessionNotes = sessionNotes.getText().toString();

            JSONHandler jsonHandler = new JSONHandler(this);
            JSONHandler.activity activity = jsonHandler.getActivity(activityId);
            activity.addSession(session);
            activity.refreshNextNotification();
            jsonHandler.saveData();

            Intent intent = getIntent();
            intent.putExtra("id",this.activityId);
            finish();
            startActivity(intent);
        }
    }
    public void postponeRem(View v){
        JSONHandler jsonHandler = new JSONHandler(this);
        JSONHandler.activity activity = jsonHandler.getActivity(activityId);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(activity.nextNotiDate);
        calendar.add(Calendar.DATE,1);
        activity.nextNotiDate = calendar.getTime();
        jsonHandler.saveData();

        Intent intent = getIntent();
        intent.putExtra("id",this.activityId);
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}