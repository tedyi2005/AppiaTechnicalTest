package com.example.tedyi2005.appiatechnicaltest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by tedyi2005 on 12/9/15.
 */
public class AdListRowHolder  extends RecyclerView.ViewHolder  {

    protected ImageView thumbnail;
    protected TextView title;
    public AdListRowHolder(View view) {
        super(view);
        this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        this.title = (TextView) view.findViewById(R.id.title);
    }
}
