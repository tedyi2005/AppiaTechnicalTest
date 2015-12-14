package com.example.tedyi2005.appiatechnicaltest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by tedyi2005 on 12/9/15.
 */
public class AdRecyclerAdapter  extends RecyclerView.Adapter<AdListRowHolder> {

    private List<AdItem> adItemList;

    private Context mContext;

    public AdRecyclerAdapter(Context context, List<AdItem> adItemList) {
        this.adItemList = adItemList;
        this.mContext = context;
    }

    @Override
    public AdListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        AdListRowHolder mh = new AdListRowHolder(v);

        return mh;
    }

    @Override
    public void onBindViewHolder(AdListRowHolder adListRowHolder, int i) {
        AdItem adItem = adItemList.get(i);

        Picasso.with(mContext).load(adItem.getProductThumbnail())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(adListRowHolder.thumbnail);

        adListRowHolder.title.setText(Html.fromHtml(adItem.getProductName()));
    }

    @Override
    public int getItemCount() {
        return (null != adItemList ? adItemList.size() : 0);
    }

}
