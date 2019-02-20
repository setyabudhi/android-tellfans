package com.tellfan.activity;

//import com.pxr.tutorials.neverendinglist.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.tellfan.R;
import com.tellfan.TellFan;
import com.tellfan.adapter.WallgalleryAdapter;
import com.tellfan.adapter.WallmecommentAdapter;
import com.tellfan.media.MediaData;
import com.tellfan.media.MediaDatasHandler;
import com.tellfan.shoutout.Celeb;
import com.tellfan.shoutout.Fan;
import com.tellfan.util.ImageLoader;

public class WallFansmegalleryActivity extends Main implements OnClickListener, OnItemSelectedListener, OnItemClickListener, OnTouchListener {
    
	 private ImageView photo;
	 private VideoView mVideoView;
	 public ImageLoader imageLoader; 
	 private boolean isVidView = false;	 
	 ViewFlipper flipper;
	 boolean isVisible = false;
	 boolean rightSide = false;
	
	private Animation inFromRightAnimation() {
		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);

		inFromRight.setDuration(500);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;

	}

	private Animation outToLeftAnimation() {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);

		outtoLeft.setDuration(500);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		return outtoLeft;
	}

	private Animation inFromLeftAnimation() {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f

		);

		inFromLeft.setDuration(500);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;

	}

	private Animation outToRightAnimation() {
		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);

		outtoRight.setDuration(500);
		outtoRight.setInterpolator(new AccelerateInterpolator());
		return outtoRight;
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.wallgalery);
        setContentView(R.layout.wallgalerymain);
        
        
        LinearLayout footerLayout = (LinearLayout) findViewById(R.id.footer);
        footerLayout.setVisibility(View.GONE);
        
        Gallery photos = ((Gallery)findViewById(R.id.gallery));
//        photos.setVisibility(View.GONE);
        
        
    	ImageView img = (ImageView) findViewById(R.id.addTextComment);
	    img.setOnClickListener(this);
        
        imageLoader=new ImageLoader(this.getApplicationContext());
        
        photo = ((ImageView)findViewById(R.id.photo));
        photo.setOnTouchListener(this);
        mVideoView = ((VideoView)findViewById(R.id.video));
        mVideoView.setMediaController(new MediaController(this));
        
        
        Bundle b = getIntent().getExtras();
        
        String albumid = b.getString("albumid");
        String messagetypeid = b.getString("messagetypeid");
        
        
        //http://fan2.tellfan.com/services/M2UserInfoHandler.ashx?whattype=shoutoutbyid&rootmessageid=264023
        String url = getString(R.string.url_login);
        try {
           
	        String id =  b.getString("rootmessageid");
	        String u = url + "?whattype=gallerybyid&id="+albumid+"&messagetypeid="+messagetypeid+"&clientid="+TellFan.getInstance().getUser().id+"&userid="+id+"&isViewedby=celeb";
	        HttpResponse datax = HttpRequest.get3(url + "?whattype=gallerybyid&id="+albumid+"&messagetypeid="+messagetypeid+"&clientid="+TellFan.getInstance().getUser().id+"&userid="+id+"&isViewedby=celeb");
	        /** Handling XML */
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			MediaDatasHandler handler = new MediaDatasHandler();
			xr.setContentHandler(handler);
			xr.parse(new InputSource(datax.getEntity().getContent()));			
			
			List<MediaData> mediaDatas = handler.getMediaData();
			
	   
	        photos.setAdapter(new WallgalleryAdapter(this,mediaDatas));
	        photos.setOnItemSelectedListener(this);
	        photos.setOnItemClickListener(this);
	        	        
	        
	        flipper = (ViewFlipper) findViewById(R.id.flipper);     
	        Button button1 = (Button) findViewById(R.id.button1);      
	        //Button button2 = (Button) findViewById(R.id.button2);          
			button1.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					rightSide = true;
					flipper.setInAnimation(inFromRightAnimation());
					flipper.setOutAnimation(outToLeftAnimation());
					flipper.showNext();
				}
			});

//			button2.setOnClickListener(new View.OnClickListener() {
//				public void onClick(View view) {
//					flipper.setInAnimation(inFromLeftAnimation());
//					flipper.setOutAnimation(outToRightAnimation());
//					flipper.showPrevious();
//					}
//			});
					
			
        }catch (Exception ex){
        	ex.printStackTrace();
        }
 
        
    }
    
    @Override
    public void onBack(View view){
    	if (rightSide){
    		flipper.setInAnimation(inFromLeftAnimation());
    		flipper.setOutAnimation(outToRightAnimation());
    		flipper.showPrevious();
    		rightSide = false;
    	}
    	else {
    		finish();
    	}
    }
    
    @Override
    public void onDestroy()
    {
      
        super.onDestroy();
    }

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub	
		LinearLayout photos = ((LinearLayout)findViewById(R.id.gallayout));
	    photos.setVisibility(View.GONE);
	    
		LinearLayout footerLayout = (LinearLayout) findViewById(R.id.footer);
		footerLayout.setVisibility(View.GONE);
		
		isVisible = false;
		
	 	String url0 = TellFan.getInstance().getImageUrl().get(""+arg2).getURL();
	 	if (url0.contains("images")){
	 		if (isVidView){
	 			LinearLayout ln=	((LinearLayout)findViewById(R.id.linearLayout1));
	 			ln.addView(photo);
	 			isVidView = false;
	 		}
	 		String url = url0.replace("..", TellFan.getInstance().getUrl());
	 		imageLoader.DisplayImage(url.replace("_small", "_big"), this, photo);
	 	}
	 	else {
	 		try {
	 			if (!isVidView){
	 				LinearLayout ln=	((LinearLayout)findViewById(R.id.linearLayout1));
	 				ln.removeView(photo);
	 				isVidView = true;
	 			}
		 		//String path = TellFan.getInstance().getUrl()+"/services/VideoStreamer.ashx?fn="+(TellFan.getInstance().getImageUrl().get(""+arg2).getOutputFileName().replace("\n", "")).trim();
		 		String path = TellFan.getInstance().getUrl()+"/video/output/"+(TellFan.getInstance().getImageUrl().get(""+arg2).getOutputFileName().replace("\n", "")).trim();
		 		//mVideoView.setVideoPath(getDataSource(path));
		 		mVideoView.setVideoPath(path);
	            mVideoView.start();
	            mVideoView.requestFocus();
	 		}catch(Exception e){
	 			e.printStackTrace();
	 			Log.e("WallgalleryActivity", "error: " + e.getMessage(), e);
	 		}
	 	}
	 	
	 	  
	 	  List<Celeb> celebs = new ArrayList<Celeb>();
	      ListView list=(ListView)findViewById(R.id.wallmecommentlist);
	      MediaData media = TellFan.getInstance().getImageUrl().get(""+arg2);
	      
			List<Fan> fans = media.getComments();
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
			
			LinearLayout lay = (LinearLayout)findViewById(R.id.footer2);
			if (fans.size() == 0){
				
				lay.removeAllViews();
			}
			
			TextView hidden = (TextView) findViewById(R.id.textHidden);
			hidden.setText(media.getMessageID());
			
			Button btnMsg = (Button) findViewById(R.id.button1);
			btnMsg.setText(fans.size()+" Comments");
			
			
			
	}

	
	private String getDataSource(String path) throws IOException {
        
            URL url = new URL(path);
            URLConnection cn = url.openConnection();
            cn.connect();
            InputStream stream = cn.getInputStream();
            if (stream == null)
                throw new RuntimeException("stream is null");
            File temp = File.createTempFile("mediaplayertmp", "mp4");
            temp.deleteOnExit();
            String tempPath = temp.getAbsolutePath();

            FileOutputStream out = new FileOutputStream(temp);
            byte buf[] = new byte[128];
            do {
                int numread = stream.read(buf);
                if (numread <= 0)
                    break;
                out.write(buf, 0, numread);
            } while (true);
            try {
                stream.close();
            } catch (IOException ex) {
            	ex.printStackTrace();
                Log.e("WallgalleryActivity", "error: " + ex.getMessage(), ex);
            }
            return tempPath;
       
    }

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub	
	 	String url0 = TellFan.getInstance().getImageUrl().get(""+arg2).getURL();
	 	if (url0.contains("images")){
	 		if (isVidView){
	 			LinearLayout ln=	((LinearLayout)findViewById(R.id.linearLayout1));
	 			ln.addView(photo);
	 			isVidView = false;
	 		}
	 		String url = url0.replace("..", TellFan.getInstance().getUrl());
	 		imageLoader.DisplayImage(url.replace("_small", "_big"), this, photo);
	 	}
	 	else {
	 		try {
	 			if (!isVidView){
	 				LinearLayout ln=	((LinearLayout)findViewById(R.id.linearLayout1));
	 				ln.removeView(photo);
	 				isVidView = true;
	 			}
		 		//String path = TellFan.getInstance().getUrl()+"/services/VideoStreamer.ashx?fn="+(TellFan.getInstance().getImageUrl().get(""+arg2).getOutputFileName().replace("\n", "")).trim();
		 		String path = TellFan.getInstance().getUrl()+"/video/output/"+(TellFan.getInstance().getImageUrl().get(""+arg2).getOutputFileName().replace("\n", "")).trim();
		 		//mVideoView.setVideoPath(getDataSource(path));
		 		mVideoView.setVideoPath(path);
	            mVideoView.start();
	            mVideoView.requestFocus();
	 		}catch(Exception e){
	 			e.printStackTrace();
	 			Log.e("WallgalleryActivity", "error: " + e.getMessage(), e);
	 		}
	 	
	 	}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId() == R.id.addTextComment){
			String url = this.getString(R.string.url_clientoperation);
			Hashtable<String, String> userData = new Hashtable<String, String>();
			userData.put("type", "comment");			
			//Bundle b = getIntent().getExtras();		        
		    //String rootmessageid = b.getString("rootmessageid");			
			TextView hidden = (TextView) findViewById(R.id.textHidden);
			String rootmessageid =  hidden.getText().toString();			
			userData.put("messageid", rootmessageid);
			EditText shout = (EditText) findViewById(R.id.editText1);	
			userData.put("text", shout.getText().toString());		
			HttpData dataLogin = HttpRequest.post2(url,userData);			
			startActivity(getIntent()); 
			finish();

		}
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		if (!isVisible){
			LinearLayout footerLayout = (LinearLayout) findViewById(R.id.footer);
			
			footerLayout.setVisibility(View.VISIBLE);
			TranslateAnimation  anim = new TranslateAnimation(0.0f, 0.0f, footerLayout.getHeight(), 0.0f);
			anim.setDuration(600);
		    anim.setInterpolator(new AccelerateInterpolator(1.0f));
		    footerLayout.startAnimation(anim);
			
			
		    LinearLayout header = (LinearLayout) findViewById(R.id.header);	
		    LinearLayout photos = ((LinearLayout)findViewById(R.id.gallayout));
			TranslateAnimation  anim2 = new TranslateAnimation(0.0f, 0.0f, -header.getHeight(),  0.0f);
			anim2.setDuration(600);
		    anim2.setInterpolator(new AccelerateInterpolator(1.0f));
			photos.setVisibility(View.VISIBLE);
			photos.startAnimation(anim2);
			
			isVisible = true;
		}
		else {
			LinearLayout footerLayout = (LinearLayout) findViewById(R.id.footer);
			footerLayout.setVisibility(View.GONE);
			
			//Gallery photos = ((Gallery)findViewById(R.id.gallery));
			LinearLayout photos = ((LinearLayout)findViewById(R.id.gallayout));
			photos.setVisibility(View.GONE);
			
			isVisible = false;
		}
		
	  return false;
	}
    
  



}