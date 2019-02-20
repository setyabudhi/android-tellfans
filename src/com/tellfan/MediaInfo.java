package com.tellfan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class MediaInfo extends Activity implements OnClickListener, OnItemSelectedListener
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
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.mediainfo);
	     
	     boolean isClient = TellFan.getInstance().getUser().isClient;
	     System.out.println("isClient :"+isClient);
	     
	     Spinner s = (Spinner) findViewById(R.id.spinner);
	     
	     Intent  it = getIntent();	     
	     isVideo = it.getExtras().getBoolean("Video");
	     
	     int purpose = R.array.purposeUser;
	     if (isClient) {
	    	 purpose = R.array.purpose;
	    	 if (isVideo)
	    		 purpose = R.array.purposeClient;
	     }
	      
	     // for testing only
	     //purpose = R.array.purposeClient;
	     ArrayAdapter adapter = ArrayAdapter.createFromResource(
	             this, purpose, android.R.layout.simple_spinner_item);
	     
	     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	     s.setAdapter(adapter);
	     s.setVisibility(0);
	     s.setOnItemSelectedListener(this);
	     
	     View button = findViewById(R.id.upload_button);
	     button.setOnClickListener(this);
	        
	 }
	
	 
	public void onClick(View v)
    {
    	switch(v.getId())
    	{
    		case R.id.upload_button:    		
			   showDialog(PROGRESS_DIALOG);    			   			    		
    		   break;
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
			} else if (p.equalsIgnoreCase("Photo Album")) {
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
	
}
