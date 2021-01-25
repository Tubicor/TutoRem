package com.example.tutorem.Instrumentation;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;


public class JSONHandler
{
    public String path = "activities.json";
    public Context context;
    public List<activity> activityList = new ArrayList<>();

    public JSONHandler(Context context)
    {
        this.context = context;
        try
        {
            String jsonFile="";
            File file = this.context.getFileStreamPath(path);
            if(!file.exists()){
                file.createNewFile();
                Log.d("JSONHANDLER","Does not exist");
            }
            if(false){
                FileWriter writer = new FileWriter(this.context.getFileStreamPath(path));
                writer.write("");
                writer.close();
            }

            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine())
            {
                jsonFile+=myReader.nextLine();
            }
            myReader.close();

            JSONObject jsonObj = new JSONObject(jsonFile);
            JSONArray activities = jsonObj.getJSONArray("activities");
            //iterate over Activities
            for (int i = 0; i < activities.length(); i++)
            {
                JSONObject c = activities.getJSONObject(i);
                activity ac = new activity();
                ac.id = c.getString("id");
                ac.name = c.getString("name");
                ac.notes = c.getString("notes");
                ac.endDate = Converter.stringToDate(c.getString("endDate"));
                ac.startDate = Converter.stringToDate(c.getString("startDate"));
                ac.nextNotiDate = Converter.stringToDate(c.getString("nextNotiDate"));
                JSONObject interval = c.getJSONObject("interval");
                ac.intervalHour = interval.getInt("hour");
                ac.intervalValue = interval.getInt("value");
                JSONArray sessions = c.getJSONArray("sessions");
                ac.sessionList = new ArrayList<activity.session>();
                //iterate over sessions
                if(sessions != null) {
                    for (int j = 0; j < sessions.length(); j++) {
                        JSONObject s = sessions.getJSONObject(j);
                        activity.session ses = new activity.session();
                        ses.sessionDate = Converter.stringToDate(s.getString("sessionDate"));
                        ses.sessionRating = Float.parseFloat(s.getString("rating"));
                        ses.sessionNotes = s.getString("sessionNotes");
                        ac.sessionList.add(ses);
                    }
                }
                activityList.add(ac);
                Log.d("JSONHANDLER","Could load JSON");
            }
        }
        catch(org.json.JSONException e){
            Log.d("JSONHANDLER","Problem with JSON File");
        }
        catch (Exception e)
        {
            Log.d("hello","Could not load JSON");
            e.printStackTrace();
        }
    }

    public List<activity> getNextActivity(boolean withHour){
        List<activity> activities = new ArrayList<>();
        Date now = Calendar.getInstance().getTime();
        for(activity ac: this.activityList){
            Date acDate;
            if(true) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(ac.nextNotiDate);
                calendar.add(Calendar.HOUR, (int) ac.intervalHour / 100);
                calendar.add(Calendar.MINUTE, ac.intervalHour % 100);
                acDate = calendar.getTime();
            }else{
                acDate = ac.nextNotiDate;
            }
            if(now.after(acDate)){
                activities.add(ac);
            }
        }
        return activities;
    }


    public static class activity
    {
        public String id;
        public String name;
        public String notes;
        public Date endDate;
        public Date startDate;
        public int intervalHour;
        public int intervalValue;
        public List<session> sessionList = new ArrayList<session>();

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public void setIntervalHour(int intervalHour) {
            this.intervalHour = intervalHour;
        }

        public void setIntervalValue(int intervalValue) {
            this.intervalValue = intervalValue;
        }

        public void setSessionList(List<session> sessionList) {
            this.sessionList = sessionList;
        }

        public void setNextNotiDate(Date nextNotiDate) {
            this.nextNotiDate = nextNotiDate;
        }

        public Date nextNotiDate;
        public static class session
        {
            public session()
            {
                sessionRating=0;
                sessionNotes="";
            }

            public void setSessionDate(Date sessionDate) {
                this.sessionDate = sessionDate;
            }

            public void setSessionRating(float sessionRating) {
                this.sessionRating = sessionRating;
            }

            public void setSessionNotes(String sessionNotes) {
                this.sessionNotes = sessionNotes;
            }

            public Date sessionDate;
            public float sessionRating;
            public String sessionNotes;
        }
        public void addSession(session ses){
            this.sessionList.add(ses);
        }
        public void refreshNextNotification(){
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE,this.intervalValue);
            this.setNextNotiDate(c.getTime());
        }

    }
    public void saveData()
    {
        try
        {
            JSONArray j_activities = new JSONArray();
            for(int i=0; i<activityList.size(); i++)
            {
                activity ac = activityList.get(i);
                JSONObject j_activity = new JSONObject();
                j_activity.put("id", ac.id);
                j_activity.put("name", ac.name);
                j_activity.put("endDate", Converter.dateToString(ac.endDate));
                j_activity.put("startDate", Converter.dateToString(ac.startDate));
                j_activity.put("notes", ac.notes);
                j_activity.put("nextNotiDate",Converter.dateToString(ac.nextNotiDate));
                JSONObject j_interval = new JSONObject();
                j_interval.put("hour", ac.intervalHour);
                j_interval.put("value", ac.intervalValue);
                j_activity.put("interval", j_interval);
                JSONArray j_sessions = new JSONArray();
                for(int j=0; j<ac.sessionList.size(); j++)
                {
                    activity.session ses = ac.sessionList.get(j);
                    JSONObject j_session = new JSONObject();
                    j_session.put("sessionDate", Converter.dateToString(ses.sessionDate));
                    j_session.put("rating", ses.sessionRating);
                    j_session.put("sessionNotes", ses.sessionNotes);
                    j_sessions.put(j_session);
                }
                j_activity.put("sessions", j_sessions);
                j_activities.put(j_activity);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("activities",j_activities);
            FileWriter writer = new FileWriter(this.context.getFileStreamPath(path));
            writer.write(jsonObject.toString());
            writer.close();
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    public int getNextID()
    {
        int temp=0;
        for(activity ac : this.activityList){
            if(Integer.parseInt(ac.id)>temp)
                temp = Integer.parseInt(ac.id);
        }
        return temp+1;
    }
    public activity getActivity(String id){
        for(activity ac : activityList){
            if(ac.id.equals(id)){
                return ac;
            }
        }
        return null;
    }

    public void addActivity(activity activityToAdd)
    {
        activityList.add(activityToAdd);
    }

    public void addActivity(List<activity> listOfActivitiesToAdd)
    {
        activityList = listOfActivitiesToAdd;
    }
}