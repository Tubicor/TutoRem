package com.example.tutorem;

import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class ToolbarSetter {
    private boolean expandable;
    private String title;
    AppCompatActivity context;
    private boolean backbutton;
    public ToolbarSetter(boolean expandable,String title,AppCompatActivity context,boolean backbutton) {
        this.title = title;
        this.expandable = expandable;
        this.context = context;
        this.backbutton = backbutton;
        this.setToolbar();
    }

    public void setToolbar(){
        Toolbar toolbar = context.findViewById(R.id.toolbar);
        context.setSupportActionBar(toolbar);
        final CollapsingToolbarLayout collapsingToolbarLayout = context.findViewById(R.id.toolbar_layout);
        context.getSupportActionBar().setDisplayHomeAsUpEnabled(backbutton);
        collapsingToolbarLayout.setTitle(title);
        AppBarLayout appBarLayout = context.findViewById(R.id.app_bar);
        if(!expandable) {
            ViewGroup.LayoutParams params = appBarLayout.getLayoutParams();
            params.height = toolbar.getLayoutParams().height;
            appBarLayout.setLayoutParams(params);
            appBarLayout.setExpanded(true, false);
            appBarLayout.setBackgroundColor(context.getResources().getColor(R.color.yellowREM));
        }
        else {
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                int scrollRange = -1;

                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout.getTotalScrollRange();
                    }
                    if (scrollRange + verticalOffset == 0) {
                        collapsingToolbarLayout.setTitle("");
                    } else {
                        collapsingToolbarLayout.setTitle(title);
                    }

                }
            });
        }
    }
}
