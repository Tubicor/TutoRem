package com.example.tutorem;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import com.example.tutorem.Instrumentation.JSONHandler;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentTransaction;

import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "TutoRem";
    TextView title;
    ToolbarSetter toolbarSetter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(!StartServiceReceiver.started){
            Util.scheduleJob(this);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String title = "You have ";
        JSONHandler jsonHandler = new JSONHandler(this);
        title+= ""+jsonHandler.getNextActivity(false).size();
        title += " Rems today";
        toolbarSetter = new ToolbarSetter(true,title,this,false);

        if(savedInstanceState == null){
           this.updateRecyclerView();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddRemActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        this.updateRecyclerView();
        super.onResume();
    }

    public void updateRecyclerView(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        RecyclerViewFragment fragment = new RecyclerViewFragment(this);
        transaction.replace(R.id.sample_content_fragment, fragment);
        transaction.commit();
    }
}