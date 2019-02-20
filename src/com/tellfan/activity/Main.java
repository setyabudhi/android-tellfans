/*
 * To change this template, choose Tools | Templates
 * && open the template in the editor.
 */
package com.tellfan.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tellfan.About;
import com.tellfan.Call;
import com.tellfan.Login;
import com.tellfan.Media;
import com.tellfan.R;
import com.tellfan.TellFan;


public abstract class Main extends Activity {
	
	private static final int MENU_ABOUT = 0;
	private static final int MENU_LOGOUT = 1;
	private static final int MENU_CALL = 2;
	private static final int MENU_MEDIA = 3;
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_ABOUT, 0, R.string.about);
		menu.add(0, MENU_LOGOUT, 0, R.string.logout);
		menu.add(0, MENU_CALL, 0, R.string.call);
		menu.add(0, MENU_MEDIA, 0, R.string.media);
		menu.getItem(0).setIcon(R.drawable.logo2);
		menu.getItem(1).setIcon(R.drawable.logout_0);
		menu.getItem(2).setIcon(R.drawable.call_1);
		menu.getItem(3).setIcon(R.drawable.media_1);
	
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {

		case MENU_ABOUT:
			Intent i = new Intent(this, About.class);
			startActivity(i);
			break;

		case MENU_LOGOUT:
			TellFan.getInstance().getLoginActivity().getPreferences(MODE_PRIVATE).edit().putString("password", "").commit();
			TellFan.getInstance().getLoginActivity().getPreferences(MODE_PRIVATE).edit().putString("email", "").commit();
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			SharedPreferences.Editor editor = settings.edit();
			editor.clear();
			editor.commit();
			
			Intent act = new Intent(this, Login.class);
			startActivity(act);
			finish();
			break;

		case MENU_CALL: {
			Intent i2 = new Intent(this, Call.class);
			startActivity(i2);
		}
			break;
		case MENU_MEDIA: {
			Intent i3 = new Intent(this, Media.class);
			startActivity(i3);

		}
			break;

		}
		return true;
	}
	
	public void onLogout(View view){
		TellFan.getInstance().getLoginActivity().getPreferences(MODE_PRIVATE).edit().putString("password", "").commit();
		TellFan.getInstance().getLoginActivity().getPreferences(MODE_PRIVATE).edit().putString("email", "").commit();
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		SharedPreferences.Editor editor = settings.edit();
		editor.clear();
		editor.commit();
		
		Intent act = new Intent(this, Login.class);
		startActivity(act);
		finish();
		
	}
	
	public void onBack(View view){
		finish();
	}
	
	public void onHome(View view){
	
		if (TellFan.getInstance().getUser().isClient){
			Intent act = new Intent(this, MainFirstActivity.class);
			startActivity(act);
			finish();
		}
		else {
			Intent act = new Intent(this, MainFirstUserActivity.class);
			startActivity(act);
			finish();
		}
		
	}
	
	public void onCall(View view){
		Intent i2 = new Intent(this, Call.class);
		startActivity(i2);
	}
	
	public void onUpload (View vie){
		Intent i3 = new Intent(this, Media.class);
		startActivity(i3);
	}
	

}
