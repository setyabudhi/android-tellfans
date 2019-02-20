/*
 * To change this template, choose Tools | Templates
 * && open the template in the editor.
 */
package com.tellfan.adapter;

import java.io.ObjectOutputStream.PutField;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.tellfan.R;
import com.tellfan.TellFan;
import com.tellfan.R.layout;
import com.tellfan.media.MediaData;
import com.tellfan.util.ImageLoader;

/**
 * <p>
 * </p><p>
 * Created on 21 Aug 2010
 * </p>
 *
 * @author Jason Morris
 */
public class WallgalleryAdapter extends BaseAdapter {
	
	 public ImageLoader imageLoader; 
	 private Activity activity;
	 private List<MediaData> medias;
	 
	 public WallgalleryAdapter() {
	    
	 }
	 
	public WallgalleryAdapter(Activity a,List<MediaData> d) {
	     activity = a;
	     medias = d;
	     imageLoader=new ImageLoader(activity.getApplicationContext());
	     //TellFan.getInstance().setImageUrl(new HashMap<String, String>());
	     TellFan.getInstance().setImageUrl(new HashMap<String, MediaData>());
	 }


//
    public View getView(
            final int index,
            final View reuse,
            final ViewGroup parent) {

        final ImageView view = (reuse instanceof ImageView)
                ? (ImageView)reuse
                : (ImageView)LayoutInflater.from(parent.getContext()).inflate(
                R.layout.gallery_thn,
                null);
        
//    	String url0 = medias.get(index).getURL();
//        String url = url0.replace("..", TellFan.getInstance().getUrl());
//        TellFan.getInstance().addImageUrl(""+index, url);
       
       	String url0 = medias.get(index).getURL();
        String url = url0.replace("..", TellFan.getInstance().getUrl());
        TellFan.getInstance().addImageUrl(""+index, medias.get(index));
        
        imageLoader.DisplayImage(url, activity, view);
        return view;
    }


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return medias.size();
	}


	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}


	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

}
