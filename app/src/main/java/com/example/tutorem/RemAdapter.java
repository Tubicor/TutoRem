package com.example.tutorem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorem.Instrumentation.Converter;
import com.example.tutorem.Instrumentation.JSONHandler;

public class RemAdapter extends RecyclerView.Adapter<RemAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    private MainActivity context;
    private JSONHandler.activity[] mDataSet;

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView remDescription;
        private final TextView remName;
        private JSONHandler.activity activity;
        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,ShowRemActivity.class);
                    intent.putExtra("id",activity.id);
                    context.startActivity(intent);
                }
            });
            remDescription = (TextView) v.findViewById(R.id.remDescription);
            remName = (TextView) v.findViewById(R.id.remName);
        }

        public TextView getRemDescription() {
            return remDescription;
        }

        public TextView getRemName() {
            return remName;
        }

        public void setActivity(JSONHandler.activity activity) {
            this.activity = activity;
        }
    }
    // END_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public RemAdapter(JSONHandler.activity[] dataSet,MainActivity context) {
        mDataSet = dataSet;
        this.context = context;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item, viewGroup, false);

        return new ViewHolder(v);
    }

    // END_INCLUDE(recyclerViewOnCreateViewHolder)


    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RemAdapter.ViewHolder viewHolder, int position) {
        //Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.getRemName().setText(mDataSet[position].name);
        viewHolder.getRemDescription().setText("Next Planned Session: "+ Converter.dateToReadableString(mDataSet[position].nextNotiDate));
        viewHolder.setActivity(mDataSet[position]);
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}
