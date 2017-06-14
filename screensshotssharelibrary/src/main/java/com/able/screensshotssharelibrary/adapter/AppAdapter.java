package com.able.screensshotssharelibrary.adapter;

/**
 * Created by dongping-yuan on 2017/6/14.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.able.screensshotssharelibrary.R;
import com.able.screensshotssharelibrary.bean.ShareApp;

import java.util.ArrayList;

/** An array adapter that knows how to render views when given CustomData classes */
public class AppAdapter extends ArrayAdapter<ShareApp> {

    private LayoutInflater mInflater;

    public AppAdapter(Context context, ArrayList<ShareApp> shareAppList) {
        super(context, R.layout.item_app_view, shareAppList);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null) {
            // Inflate the view since it does not exist
            convertView = mInflater.inflate(R.layout.item_app_view, parent, false);

            // Create and save off the holder in the tag so we get quick access to inner fields
            // This must be done for performance reasons
            holder = new Holder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.item_app_icon);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        // Populate the text
        holder.imageView.setImageResource(getItem(position).appIcon);
        holder.imageView.setBackgroundResource(getItem(position).appIconBg);

        // Set the color
//        convertView.setBackgroundColor(getItem(position).appIconBg);

        return convertView;
    }

    /** View holder for the views we need access to */
    private static class Holder {
        public ImageView imageView;
    }
}
