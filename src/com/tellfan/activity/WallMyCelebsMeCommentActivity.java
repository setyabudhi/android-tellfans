package com.tellfan.activity;

//import com.pxr.tutorials.neverendinglist.R;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import HttpRequest.HttpData;
import HttpRequest.HttpRequest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.tellfan.R;
import com.tellfan.TellFan;
import com.tellfan.R.id;
import com.tellfan.R.layout;
import com.tellfan.R.string;
import com.tellfan.adapter.WallmecommentAdapter;
import com.tellfan.shoutout.Celeb;
import com.tellfan.shoutout.CelebsHandler;
import com.tellfan.shoutout.Fan;

public class WallMyCelebsMeCommentActivity extends Main implements OnClickListener {
    
  
	private String id(){
        Bundle b = getIntent().getExtras();        
        return b.getString("rootmessageid");
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallmecomment);
        findViewById(R.id.editText1).clearFocus();
        
        ListView list=(ListView)findViewById(R.id.wallmecommentlist);
               
        try {
	        
	        //http://fan2.tellfan.com/services/M2UserInfoHandler.ashx?whattype=shoutoutbyid&rootmessageid=264023
	        String url = getString(R.string.url_login);
	        
	        HttpResponse datax = HttpRequest.get3(url + "?whattype=shoutoutbyid&rootmessageid="+id());
			/** Handling XML */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			CelebsHandler handler = new CelebsHandler();
			xr.setContentHandler(handler);
			xr.parse(new InputSource(datax.getEntity().getContent()));
			
			
			List<Celeb> celebsList = handler.getCelebs();
			List<Celeb> celebs = new ArrayList<Celeb>();
			
			Celeb celeb = celebsList.get(0);
			celebs.add(celeb);
			List<Fan> fans = celeb.getFan();
			for (int i=0;i<fans.size();i++){
				Celeb cel = new Celeb();
				cel.setComment(true);
				cel.setCname(fans.get(i).getCname());
				cel.setThumburl(fans.get(i).getThumburl());
				cel.setCshortname(fans.get(i).getCshortname());
				cel.setCfirsname(fans.get(i).getCfirsname());
				cel.setClastname(fans.get(i).getClastname());
				cel.setDateadded(fans.get(i).getDateadded());
				cel.setMessage(fans.get(i).getMessage());
				celebs.add(cel);
			}
			
			list.setAdapter(new WallmecommentAdapter(this, celebs));
			
		}catch (Exception ex){
			Log.e("ERROR", "ERROR IN CODE:"+ex.getMessage());
		}
		
		ImageView img = (ImageView) findViewById(R.id.addTextComment);
	    img.setOnClickListener(this);
	    

    }
    
    @Override
    public void onDestroy()
    {
      
        super.onDestroy();
    }

	@Override
	public void onClick(View arg0) {
		if(arg0.getId() == R.id.addTextComment){
			String url = this.getString(R.string.url_clientoperation);
			Hashtable<String, String> userData = new Hashtable<String, String>();

			userData.put("type", "comment");
			
			userData.put("messageid", id());
			EditText shout = (EditText) findViewById(R.id.editText1);	

			userData.put("text", shout.getText().toString());
		
			HttpData dataLogin = HttpRequest.post2(url,userData);
			
			startActivity(getIntent()); 
			finish();

		}
		
	}
    
  



}