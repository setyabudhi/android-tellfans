/*
 * To change this template, choose Tools | Templates
 * && open the template in the editor.
 */
package com.tellfan.activity;

import com.tellfan.R;
import com.tellfan.TellFan;
import com.tellfan.R.id;
import com.tellfan.R.layout;
import com.tellfan.util.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageView;
import android.widget.TextView;


public class MainFirstUserActivity extends Main implements OnClickListener, OnFocusChangeListener{
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_first_user);
        
        ImageLoader imageLoader=new ImageLoader(this.getApplicationContext());
        
        TextView text=(TextView)findViewById(R.id.profilename);
        text.setText(TellFan.getInstance().getUser().firstName+" "+TellFan.getInstance().getUser().lastName);
        
        ImageView image=(ImageView) findViewById(R.id.imageView2);
        imageLoader.DisplayImage(TellFan.getInstance().getUser().thumbnail, this, image);
        
        View v0 = findViewById(R.id.profile);
        v0.setClickable(true);
        v0.setFocusable(true);
        v0.setOnClickListener(this);
        
        View v1 = findViewById(R.id.shoutout);
        v1.setClickable(true);
        v1.setOnClickListener(this);
        
        View v2 = findViewById(R.id.media);
        v2.setClickable(true);
        v2.setOnClickListener(this);
        
        View v3 = findViewById(R.id.myceleb);
        v3.setClickable(true);
        v3.setOnClickListener(this);
        
        View v4 = findViewById(R.id.mycelebnetworks);
        v4.setClickable(true);
        v4.setOnClickListener(this);
       
   
        
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.profile) {
			//Intent act = new Intent(this, MainActivity.class);
			//startActivity(act);			
		}
		else if (v.getId() == R.id.media){
			//Intent act = new Intent(this, ImageGridActivity.class);
			Intent act = new Intent(this, WallmediaActivity.class);
			startActivity(act);			
		}
		else if (v.getId() == R.id.shoutout){
			Intent act = new Intent(this, WallmeActivity.class);
			startActivity(act);			
		}
		else if (v.getId() == R.id.myceleb){
			//Intent act = new Intent(this, FansActivity.class);
			Intent act = new Intent(this, WallMyCelebsActivity.class);
			startActivity(act);			
		}
		else if (v.getId() == R.id.mycelebnetworks){
			//Intent act = new Intent(this, FansActivity.class);
			Intent act = new Intent(this, WallMyCelebsNetworksActivity.class);
			startActivity(act);			
		}
		
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		
	}


	
}
