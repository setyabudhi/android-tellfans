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


public class MainFirstActivity extends Main implements OnClickListener,OnFocusChangeListener{
	

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_first_celeb);
        
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
        
        View v3 = findViewById(R.id.fans);
        v3.setClickable(true);
        v3.setOnClickListener(this);
        
        View v4 = findViewById(R.id.celebnetworks);
        v4.setClickable(true);
        v4.setOnClickListener(this);
        
        
        View v5 = findViewById(R.id.celebfriends);
        v5.setClickable(true);
        v5.setOnClickListener(this);
        
       

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
		else if (v.getId() == R.id.fans){
			//Intent act = new Intent(this, FansActivity.class);
			Intent act = new Intent(this, WallfansActivity.class);
			startActivity(act);			
		}
		else if (v.getId() == R.id.celebnetworks){
			//Intent act = new Intent(this, FansActivity.class);
			Intent act = new Intent(this, WallCelebsNetworksActivity.class);
			startActivity(act);		
		}
		else if (v.getId() == R.id.celebfriends){
			//Intent act = new Intent(this, FansActivity.class);
			Intent act = new Intent(this, WallCelebsFriendsActivity.class);
			startActivity(act);	
				
		}
		
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// TODO Auto-generated method stub
//		super.onCreateOptionsMenu(menu);
//		menu.add(0, MENU_ABOUT, 0, R.string.about);
//		menu.add(0, MENU_LOGOUT, 0, R.string.logout);
//		menu.add(0, MENU_CALL, 0, R.string.call);
//		menu.add(0, MENU_MEDIA, 0, R.string.media);
//		menu.getItem(0).setIcon(R.drawable.logo2);
//		menu.getItem(1).setIcon(R.drawable.logout_0);
//		menu.getItem(2).setIcon(R.drawable.call_1);
//		menu.getItem(3).setIcon(R.drawable.media_1);
//	
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// TODO Auto-generated method stub
//		super.onOptionsItemSelected(item);
//
//		switch (item.getItemId()) {
//
//		case MENU_ABOUT:
//			Intent i = new Intent(this, About.class);
//			startActivity(i);
//			break;
//
//		case MENU_LOGOUT:
//			TellFan.getInstance().getLoginActivity().getPreferences(MODE_PRIVATE).edit()
//					.putString("password", "").commit();
//			TellFan.getInstance().getLoginActivity().getPreferences(MODE_PRIVATE).edit()
//					.putString("email", "").commit();			
//			SharedPreferences settings =  PreferenceManager.getDefaultSharedPreferences(getBaseContext());                          
//	        SharedPreferences.Editor editor = settings.edit();
//	        
//	        editor.clear();
//	        editor.commit();                
//			
//			finish();
//			break;
//
//		case MENU_CALL: {
//			Intent i2 = new Intent(this, Call.class);
//			startActivity(i2);
//		}
//			break;
//		case MENU_MEDIA: {
//			Intent i3 = new Intent(this, Media.class);
//			startActivity(i3);
//
//		}
//			break;
//
//		}
//		return true;
//	}
	
}
