/*
 * To change this template, choose Tools | Templates
 * && open the template in the editor.
 */
package com.tellfan.adapter;

import com.tellfan.R;
import com.tellfan.R.id;
import com.tellfan.R.layout;
import com.tellfan.util.ImageItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ImageAdapter extends BaseAdapter {

    private final ImageItem[] items;


    public ImageAdapter(final ImageItem... items) {
        this.items = items;
       
    }

    private ViewGroup getViewGroup(
            final View reuse,
            final ViewGroup parent) {

        if(reuse instanceof ViewGroup) {
            return (ViewGroup)reuse;
        } else {
            final Context context = parent.getContext();
            final LayoutInflater inflater = LayoutInflater.from(context);    
            
            final ViewGroup item = (ViewGroup)inflater.inflate(
                    R.layout.media_item,
                    null);

            return item;
        }
    }

    public int getCount() {
        return items.length;
    }

    public Object getItem(final int index) {
        return items[index];
    }

    public long getItemId(final int index) {
        return index;
    }

    public View getView(
            final int index,
            final View reuse,
            final ViewGroup parent) {

        final ViewGroup view = getViewGroup(reuse, parent);
        final ImageItem item = items[index];

        final TextView text = ((TextView)view.findViewById(R.id.text));
        final ImageView image = ((ImageView)view.findViewById(R.id.icon));

        text.setText(item.name);
        image.setImageResource(item.image);

        return view;
    }



}
