package com.tellfan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import HttpRequest.HttpData;
import HttpRequest.HttpRequest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class MediaShare extends Activity implements OnClickListener, OnItemSelectedListener
{
	private static final int DIALOG1_KEY = 0;
	private static final int DIALOG2_KEY = 1;
	protected static final int PROGRESS_DIALOG = 0;
	protected static final int GREETINGS_DIALOG = 1;
	HashMap<String, String> greetId = new HashMap<String, String>();

	ProgressThread progressThread;
	ProgressThread2 progressThread2;
    ProgressDialog progressDialog;
     
    private Boolean isVideo;
	@Override
	 public void onCreate(Bundle savedInstanceState)
	 {
		if (!TellFan.getInstance().isLogin()){
			super.onCreate(savedInstanceState);
			setContentView(R.layout.login);
			View button = findViewById(R.id.login_button);
			button.setOnClickListener(this);

			User u = new User();
			TellFan.getInstance().setUser(u);

			TellFan.getInstance().setLoginActivity(this);
		}
		else {		
			super.onCreate(savedInstanceState);
			setContentView(R.layout.mediainfo);		     
			showDisplay();
		}
	        
	 }
	
	private void showDisplay(){
		boolean isClient = TellFan.getInstance().getUser().isClient;
		System.out.println("isClient :" + isClient);

		Spinner s = (Spinner) findViewById(R.id.spinner);

		Uri currImageURI = (Uri) getIntent().getExtras().get(Intent.EXTRA_STREAM);		
		String selectedPath = this.getRealPathFromURI(currImageURI);
	
		TellFan.getInstance().setSelectedPath(selectedPath);
		if (selectedPath.substring(selectedPath.lastIndexOf(".")).equalsIgnoreCase(".3gp") 
				|| selectedPath.substring(selectedPath.lastIndexOf(".")).equalsIgnoreCase(".mp4")){
			isVideo = true;
		}else {			
			isVideo = false;
		}
		
		if (!isClient && isVideo){
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("TellFan");
			alertDialog.setMessage("Sorry, \npicture/photo only. \nPlease select picture \nto upload ");
			alertDialog.setButton("Close", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int which) {
			      // here you can add functions
				   finish();
			   }
			});
			alertDialog.setIcon(R.drawable.icon);
			alertDialog.show();			
		}
	
		int purpose = R.array.purposeUser;
		if (isClient) {
			purpose = R.array.purpose;
			if (isVideo)
				purpose = R.array.purposeClient;
		}

		// for testing only
		// purpose = R.array.purposeClient;
		ArrayAdapter adapter = ArrayAdapter.createFromResource(this,purpose, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);
		s.setVisibility(0);
		s.setOnItemSelectedListener(this);

		View button = findViewById(R.id.upload_button);
		button.setOnClickListener(this);
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
	 
	public void onClick(View v)
    {
		switch (v.getId()) {
		case R.id.login_button: 
				loginProcess();
				// dialog.dismiss();
				//Intent i = new Intent(this, MediaShare.class);
				if (TellFan.getInstance().isLogin()) {
					setContentView(R.layout.mediainfo);				
					showDisplay();
				}			
				break;
			
		case R.id.upload_button:    		
			   showDialog(PROGRESS_DIALOG);    			   			    		
			   break;
		}
    	
    }
	
	private void loginProcess(){
		EditText etx = (EditText) findViewById(R.id.username);
		String username = etx.getText().toString();
		etx = (EditText) findViewById(R.id.password);
		String pass = etx.getText().toString();

		String url = String.format(this.getString(R.string.url_login),
				username, pass);

		HttpData data = HttpRequest.get(url);

		if (data.content != null) {
			User u = new User();
			u.email = username;
			u.password = pass;
			getPreferences(MODE_PRIVATE).edit().putString("email", username).commit();
			getPreferences(MODE_PRIVATE).edit().putString("password", pass).commit();

			String st = data.content;

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
			
			
			TellFan.getInstance().setUser(u);
			TellFan.getInstance().setLogin(true);
		}
	}
	
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case PROGRESS_DIALOG:
			EditText etx = (EditText) findViewById(R.id.caption);
			String caption = etx.getText().toString();
			String p;
			Spinner s = (Spinner) findViewById(R.id.spinner);
			p = (String) s.getSelectedItem();
			boolean defaultUpload = true;
			if (p.equalsIgnoreCase("Public")) {
				p = "public";
			} else if (p.equalsIgnoreCase("Profile")) {
				p = "profile";
			} else if (p.equalsIgnoreCase("Fans")) {
				p = "fans";
			} else if (p.equalsIgnoreCase("Pic/Video")) {
				p = "public";
			} else if (p.equalsIgnoreCase("Greetings")) {
				p = "pvg";
			} else {				
				defaultUpload = false;
			}
			
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("Uploading...");
			progressDialog.setCancelable(false);

			if (defaultUpload){
				progressThread = new ProgressThread();
				progressThread.caption = caption;
				progressThread.purpose = p;
				progressThread.url = this.getString(R.string.url_upload);
				progressThread.start();
			}
			else {
				progressThread2 = new ProgressThread2();
				progressThread2.caption = caption;
				progressThread2.purpose = "pvg";
				progressThread2.pvgid = greetId.get(p);
				progressThread2.url = this.getString(R.string.url_upload);
				progressThread2.start();
			}
			return progressDialog;
			
		default:
			return null;
		}
	}

	   
	/** Nested class that performs progress calculations (counting) */
	private class ProgressThread extends Thread {
		public String caption;
		public String purpose;
		public String url;

		public void run() {
			TellFan.getInstance().uploadFile2(caption, purpose, url, isVideo);
			finish();

		}
	}


	private class ProgressThread2 extends Thread {
		public String caption;
		public String purpose;
		public String url;
		public String greeting;
		public String pvgid;

		public void run() {
			TellFan.getInstance().uploadFile(caption, purpose, url, isVideo, pvgid);
			finish();

		}
	}
	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		String p = (String) arg0.getSelectedItem();
        if (p.equalsIgnoreCase("Greetings"))  
        {        	
        	List<Greeting> lst =TellFan.getInstance().getUser().greetingList;
        	List<String> greeting = new ArrayList();
        	
        	for (int i=0;i<lst.size();i++){
        		greeting.add(lst.get(i).greeting);
        		greetId.put(lst.get(i).greeting, lst.get(i).pvgId);
        	}
        	       	
        	Spinner s = (Spinner) findViewById(R.id.spinner);
        	ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,greeting);     
   	     	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
   	     	s.setAdapter(adapter);
   	     	s.setVisibility(0);
   	     	s.setOnItemSelectedListener(this);
        	
        }
		
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public String getRealPathFromURI(Uri contentUri) {
		// can post image
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(contentUri, proj, // Which columns to
														// return
				null, // WHERE clause; which rows to return (all rows)
				null, // WHERE clause selection arguments (none)
				null); // Order-by clause (ascending by name)
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
}
