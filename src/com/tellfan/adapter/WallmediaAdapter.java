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
import com.tellfan.media.MediaItem;
import com.tellfan.media.Medias;
import com.tellfan.shoutout.Celeb;
import com.tellfan.util.ImageLoader;

public class WallmediaAdapter extends BaseAdapter {
    
    private Activity activity;
    private Medias data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public WallmediaAdapter(Activity a, Medias d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public void setData(Medias data){
    	this.data = data;
        	
    }
    
    public void addData(List<MediaItem> data){
    	this.data.addResult(data);
    }
    
    public int getCount() {
        return this.data.getResult().size();
    }

    public Object getItem(int position) {
        return position;
    }

    
    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent)  {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.wallmediaitem, null);

        if (data.getResult().get(position) != null) {
	        TextView text=(TextView)vi.findViewById(R.id.mediaText);
	        String cname = data.getResult().get(position).getMediaLabel();
	        text.setText(cname);
	
	        TextView messagetypeid=(TextView)vi.findViewById(R.id.textMessageTypeID);
	        String msgtypeid = data.getResult().get(position).getMessageTypeid();
	        messagetypeid.setText(msgtypeid);
	        
	        TextView textalbumid=(TextView)vi.findViewById(R.id.textAlbumId);
	        String albmid = data.getResult().get(position).getAlbumId();
	        textalbumid.setText(albmid);
	        
	        ImageView image=(ImageView)vi.findViewById(R.id.mediaimage);
	        String url0 = data.getResult().get(position).getMediaSource();
	        String url = url0.replace("..", TellFan.getInstance().getUrl());
	        imageLoader.DisplayImage(url, activity, image);
        }
        return vi;
    }
}