package com.tellfan.adapter;

import com.tellfan.R;
import com.tellfan.R.id;
import com.tellfan.R.layout;
import com.tellfan.util.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FansAdapter extends BaseAdapter {
    
    private Activity activity;
    private String[] data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public FansAdapter(Activity a, String[] d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.fansitem, null);

        TextView text=(TextView)vi.findViewById(R.id.fanstext);
        ImageView image=(ImageView)vi.findViewById(R.id.fansimage);
        text.setText("Name "+position);
        //imageLoader.DisplayImage(data[position], activity, image);
        return vi;
    }
}