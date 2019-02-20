package com.tellfan.adapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tellfan.R;
import com.tellfan.TellFan;
import com.tellfan.R.id;
import com.tellfan.R.layout;
import com.tellfan.shoutout.Celeb;
import com.tellfan.util.ImageLoader;

public class WallmeAdapter extends BaseAdapter  {
    
    private Activity activity;
    private List<Celeb> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public WallmeAdapter(Activity a, List<Celeb> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public void setData(List<Celeb> data){
    	this.data = data;
        	
    }
    
    public void addData(List<Celeb> data){
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
    
    public View getView(int position, View convertView, ViewGroup parent)  {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.wallmeitem, null);

       
        
        if (data.get(position) != null) {
	        TextView text=(TextView)vi.findViewById(R.id.textMe);
	        String cname = data.get(position).getCname();
	        text.setText(cname);
	        
	        
	        TextView hidden=(TextView)vi.findViewById(R.id.textHiddenMe);
	        String rootMString = data.get(position).getRootmessageid();
	        hidden.setText(rootMString);
	        
	        TextView textDate=(TextView)vi.findViewById(R.id.datewrote);
	        String date = data.get(position).getDateadded();
	        
	        DateFormat formatter ; 
			Date date0 ; 
	        try {
		   	 	formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		   	 	date0 = (Date) formatter.parse(date.replace("T", " ")); 	 
		   	 	formatter = new SimpleDateFormat("hh:mm MMM dd");
		   	 	String dateS = formatter.format(date0);	        		        
		        textDate.setText(dateS);		        
	        }catch (ParseException ex){
	        	ex.printStackTrace();
	        }
	        
	        TextView textData=(TextView)vi.findViewById(R.id.datame);
	        
	        String message = data.get(position).getMessage();
	        textData.setText(Html.fromHtml(message.replaceFirst("true", "")));
	        
	        TextView textComment=(TextView)vi.findViewById(R.id.sumcomment);
	        int siz = data.get(position).getFan().size();
	        if (siz == 0){
	            textComment.setText("");
	        }
	        else {
	        	textComment.setText(siz+" comments");
	        }
	        
	        ImageView image=(ImageView)vi.findViewById(R.id.meimage);
	        String url0 = data.get(position).getClientlb();
	        if (url0 != null) {
	        	String url = url0.replace("../..", TellFan.getInstance().getUrl());
	        	imageLoader.DisplayImage(url, activity, image);
	        }
	        else {
	        	String url00 = data.get(position).getThumburl();
	        	String url = url00.replace("../..", TellFan.getInstance().getUrl());
	        	imageLoader.DisplayImage(url, activity, image);
	        }
        }
        return vi;
    }
    

}