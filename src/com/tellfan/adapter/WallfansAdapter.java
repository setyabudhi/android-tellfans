package com.tellfan.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tellfan.R;
import com.tellfan.TellFan;
import com.tellfan.R.id;
import com.tellfan.R.layout;
import com.tellfan.fans.Fans;
import com.tellfan.util.ImageLoader;

public class WallfansAdapter extends BaseAdapter {
    
    private Activity activity;
    private List<Fans> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public WallfansAdapter(Activity a,List<Fans> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }
    
    public void setData(List<Fans> data){
    	this.data = data;
        	
    }
    
    public void addData(List<Fans> data){
    	this.data.addAll(data);
    }

    public int getCount() {
        return data.size();
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
            vi = inflater.inflate(R.layout.wallfanitem, null);

        TextView hidden=(TextView)vi.findViewById(R.id.textHiddenMe);
        String rootMString = data.get(position).getLink();
        hidden.setText(rootMString);
        
        TextView text=(TextView)vi.findViewById(R.id.fanText);
        ImageView image=(ImageView)vi.findViewById(R.id.fanimage);
        text.setText(data.get(position).getFullname());
        
        String url0 = data.get(position).getThumbnail();
        String url = url0.replace("..", TellFan.getInstance().getUrl());
        
        TextView urlImage=(TextView)vi.findViewById(R.id.textUrlImage);       
        urlImage.setText(url);
        
        imageLoader.DisplayImage(url, activity, image);
        return vi;
    }
}