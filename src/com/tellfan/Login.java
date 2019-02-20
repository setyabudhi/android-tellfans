package com.tellfan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import HttpRequest.HttpData;
import HttpRequest.HttpRequest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.tellfan.activity.MainFirstActivity;
import com.tellfan.activity.MainFirstUserActivity;

public class Login extends Activity implements OnClickListener {
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		View button = findViewById(R.id.login_button);
		button.setOnClickListener(this);

		User u = new User();
		TellFan.getInstance().setUser(u);

		TellFan.getInstance().setUrl(this.getString(R.string.url));
		
		TellFan.getInstance().setLoginActivity(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		setContentView(R.layout.login);
		EditText etx = (EditText) findViewById(R.id.username);

		View button = findViewById(R.id.login_button);
		button.setOnClickListener(this);

		String us = getPreferences(MODE_PRIVATE).getString("email", "");
		etx.setText(us);

		us = getPreferences(MODE_PRIVATE).getString("password", "");
		etx = (EditText) findViewById(R.id.password);
		etx.setText(us);

	}

	ProgressDialog dialog;
	class EfekLogin extends AsyncTask<Object, Void, String> {

        @Override
        protected void onPreExecute()
        {
        	dialog = new ProgressDialog(Login.this);
        	dialog.setMessage("Authenticating...");
        	dialog.show(); 
        }


       
        @Override
        protected void onPostExecute(String result)
        {
           dialog.dismiss();
           dialog.cancel();
        }



		@Override
		protected String doInBackground(Object... params) {
			// TODO Auto-generated method stub
			return "";
		}

    } 
	
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.login_button: {
			
			try {
				
				dialog = ProgressDialog.show(this, "", "Authenticating...", false);				
				EfekLogin efekLogin = new EfekLogin();
				efekLogin.execute();
				
				EditText etx = (EditText) findViewById(R.id.username);
				String username = etx.getText().toString();
				etx = (EditText) findViewById(R.id.password);
				String pass = etx.getText().toString();
	
				String url = this.getString(R.string.url_login);
				Hashtable<String, String> userData = new Hashtable<String, String>();
				//userData.put("type", "loggedin");
				userData.put("type", "loggedinm2");
				userData.put("mode", "loggedinm2");
				userData.put("username", username);
				userData.put("password", pass);
	
				HttpData dataLogin = HttpRequest.post2(url,userData);
					
				if (dataLogin.content != null & !dataLogin.content.trim().equalsIgnoreCase("")) {
						User u = new User();
						u.email = username;
						u.password = pass;
						getPreferences(MODE_PRIVATE).edit().putString("email", username).commit();
						getPreferences(MODE_PRIVATE).edit().putString("password", pass).commit();
		
						String st = dataLogin.content;
		
						String[] a = st.split(",");
						String s = a[0];
		
						if (s.equalsIgnoreCase("IsClient")) {
							u.id = Integer.parseInt(a[1]);
							u.phone = a[2];
							u.name = "Voice Mail";
							u.isClient = true;
							if (a.length == 4){
								String[] greetingData = a[3].split("^");	
								for (int i = 0; i < greetingData.length; i++) {
									if (greetingData[i].trim().indexOf("]") > -1) {
										Greeting greeting = new Greeting();
										String[] greetingParts = greetingData[i].split("]");
										greeting.greeting = (greetingParts[0].replace('[',' ').trim());
										greeting.pvgId = (greetingParts[1].trim());
										u.greetingList.add(greeting);
									}
								}
							}
		
						} else if (s.equalsIgnoreCase("IsUser")) {
							u.isClient = false;
							s = a[1];
							u.id = Integer.parseInt(s);
							s = a[2];
		
							String b[];
							String[] a2 = s.split("\\^");
							for (String str : a2) {
								b = str.split("]");
		
								User c = new User();
								if (b.length == 2) {
									c.phone = b[1];
									s = b[0];
									c.name = s.substring(1);
								} else {
									c.name = s.substring(1);
									c.phone = "";
								}
								u.clientList.add(c);
		
							}
						}
					
					
					HttpData data = HttpRequest.get2(url + "?whattype=userinfo");

					if (data.content != null) {
						//User u = new User();
						//u.email = username;
						//u.password = pass;
						
						//getPreferences(MODE_PRIVATE).edit().putString("email",username).commit();
						//getPreferences(MODE_PRIVATE).edit().putString("password", pass).commit();

						st = data.content;

						a = st.split(",");
						
						//String isclient = (a[0].split(":"))[1].replace("\"","").trim();
						//u.isClient = Boolean.parseBoolean(isclient);
						String firstname = (a[1].split(":"))[1].replace("\"","").trim();
						u.firstName = firstname;
						String lastname = (a[2].split(":"))[1].replace("\"","").trim();
						u.lastName = lastname;
						String usertype = (a[3].split(":"))[1].replace("\"","").trim();
						u.userType = usertype;
						String thumbnail = (a[4].split(":"))[1].replace("\"","").replace("..","").trim();
						u.thumbnail = this.getString(R.string.url)+thumbnail;
						String id = ((a[5].split(":"))[1]).replace("\"","").trim();
						u.id = Integer.parseInt(id);
					
						
						 DateFormat formatter ; 
						 Date date ; 
						 formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
						 String dt = (a[6]).replace("\"Expires\":","").replace("\"","").replace("}","").trim();
						 date = (Date) formatter.parse(dt); 
						 
						 u.expires = date;

						TellFan.getInstance().setUser(u);
						TellFan.getInstance().setLogin(true);
						// dialog.dismiss();
						if (u.isClient){
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
				}
				
				dialog.dismiss();
				dialog.cancel();
				
			}catch (Exception ex){
				ex.printStackTrace();
				
				dialog.dismiss();
				dialog.cancel();
				
				new AlertDialog.Builder(this)
			    .setTitle("TellFan")
			    .setMessage("Login failed")		 
			    .show();
				
				Intent act = new Intent(this, Login.class);
				startActivity(act);
				finish();
				
			}

		}

			break;
		}

	}

}