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
import java.util.Collection;
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

            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine())
            {
                jsonFile+=myReader.nextLine();
            }
            Log.d("JSONHANDLER",jsonFile);
            myReader.close();

            JSONObject jsonObj = new JSONObject(jsonFile);
            JSONArray activities = jsonObj.getJSONArray("activities");
            for (int i = 0; i < activities.length(); i++)
            {
                JSONObject c = activities.getJSONObject(i);
                activity ac = new activity();
                ac.id = c.getString("id");
                ac.name = c.getString("name");
                ac.notes = c.getString("notes");
                ac.endDate = c.getString("endDate");
                ac.startDate = c.getString("startDate");
                JSONObject interval = c.getJSONObject("interval");
                ac.intervalHour = Integer.parseInt(c.getString("hour"));
                ac.intervalValue = Integer.parseInt(c.getString("value"));
                JSONArray sessions = jsonObj.getJSONArray("activities");
                ac.sessionList = new List<activity.session>() {
                    @Override
                    public int size() {
                        return 0;
                    }

                    @Override
                    public boolean isEmpty() {
                        return false;
                    }

                    @Override
                    public boolean contains(@Nullable Object o) {
                        return false;
                    }

                    @NonNull
                    @Override
                    public Iterator<activity.session> iterator() {
                        return null;
                    }

                    @NonNull
                    @Override
                    public Object[] toArray() {
                        return new Object[0];
                    }

                    @NonNull
                    @Override
                    public <T> T[] toArray(@NonNull T[] a) {
                        return null;
                    }

                    @Override
                    public boolean add(activity.session session) {
                        return false;
                    }

                    @Override
                    public boolean remove(@Nullable Object o) {
                        return false;
                    }

                    @Override
                    public boolean containsAll(@NonNull Collection<?> c) {
                        return false;
                    }

                    @Override
                    public boolean addAll(@NonNull Collection<? extends activity.session> c) {
                        return false;
                    }

                    @Override
                    public boolean addAll(int index, @NonNull Collection<? extends activity.session> c) {
                        return false;
                    }

                    @Override
                    public boolean removeAll(@NonNull Collection<?> c) {
                        return false;
                    }

                    @Override
                    public boolean retainAll(@NonNull Collection<?> c) {
                        return false;
                    }

                    @Override
                    public void clear() {

                    }

                    @Override
                    public activity.session get(int index) {
                        return null;
                    }

                    @Override
                    public activity.session set(int index, activity.session element) {
                        return null;
                    }

                    @Override
                    public void add(int index, activity.session element) {

                    }

                    @Override
                    public activity.session remove(int index) {
                        return null;
                    }

                    @Override
                    public int indexOf(@Nullable Object o) {
                        return 0;
                    }

                    @Override
                    public int lastIndexOf(@Nullable Object o) {
                        return 0;
                    }

                    @NonNull
                    @Override
                    public ListIterator<activity.session> listIterator() {
                        return null;
                    }

                    @NonNull
                    @Override
                    public ListIterator<activity.session> listIterator(int index) {
                        return null;
                    }

                    @NonNull
                    @Override
                    public List<activity.session> subList(int fromIndex, int toIndex) {
                        return null;
                    }
                };
                for (int j = 0; j < sessions.length(); j++)
                {
                    JSONObject s = activities.getJSONObject(i);
                    activity.session ses = new activity.session();
                    ses.sessionDate = c.getString("sessionDate");
                    ses.sessionRating = Float.parseFloat(c.getString("rating"));
                    ses.sessionNotes = c.getString("sessionNotes");
                    ac.sessionList.add(ses);
                }
                activityList.add(ac);
                Log.d("hello","Could load JSON");
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


    public static class activity
    {
        public activity()
        {
            id="";
            name="";
            notes="";
            endDate="";
            startDate="";
            intervalHour=0;
            intervalValue=0;
            id="";
            id="";
            id="";
        }
        public String id;
        public String name;
        public String notes;
        public String endDate;
        public String startDate;
        public int intervalHour;
        public int intervalValue;
        public List<session> sessionList = new ArrayList<session>();
        public static class session
        {
            public session()
            {
                sessionDate="";
                sessionRating=0;
                sessionNotes="";
            }
            public String sessionDate;
            public float sessionRating;
            public String sessionNotes;
            public void set(String fieldName, String Value)
            {
                switch(fieldName)
                {
                    case "sessionDate":
                        sessionDate=Value;
                        break;
                    case "sessionRating":
                        sessionRating=Float.parseFloat(Value);
                        break;
                    case "sessionNotes":
                        sessionNotes=Value;
                        break;
                }
            }
        }
        public void set(String fieldName, String Value)
        {
            switch(fieldName)
            {
                case "id":
                    id=Value;
                    break;
                case "name":
                    name=Value;
                    break;
                case "notes":
                    notes=Value;
                    break;
                case "endDate":
                    endDate=Value;
                    break;
                case "startDate":
                    startDate=Value;
                    break;
                case "intervalHour":
                    intervalHour=Integer.parseInt(Value);
                    break;
                case "intervalValue":
                    intervalValue=Integer.parseInt(Value);
                    break;
            }
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
                j_activity.put("endDate", ac.endDate);
                j_activity.put("startDate", ac.startDate);
                j_activity.put("notes", ac.notes);
                JSONObject j_interval = new JSONObject();
                j_interval.put("hour", ac.intervalHour);
                j_interval.put("value", ac.intervalValue);
                j_activity.put("interval", j_interval);
                JSONArray j_sessions = new JSONArray();
                for(int j=0; j<ac.sessionList.size(); j++)
                {
                    activity.session ses = ac.sessionList.get(j);
                    JSONObject j_session = new JSONObject();
                    j_session.put("sessionDate", ses.sessionDate);
                    j_session.put("rating", ses.sessionRating);
                    j_session.put("sessionNotes", ses.sessionNotes);
                    j_sessions.put(j_session);
                }
                j_activity.put("sessions", j_sessions);
                j_activities.put(j_activity);
            }
            FileWriter writer = new FileWriter(this.context.getFileStreamPath(path));
            writer.write("{"+j_activities.toString()+"}");
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

    public void addActivity(activity activityToAdd)
    {
        activityList.add(activityToAdd);
    }

    public void addActivity(List<activity> listOfActivitiesToAdd)
    {
        activityList = listOfActivitiesToAdd;
    }
}