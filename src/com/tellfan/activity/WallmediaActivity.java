package com.tellfan.activity;

//import com.pxr.tutorials.neverendinglist.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;

import HttpRequest.HttpRequest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.tellfan.R;
import com.tellfan.TellFan;
import com.tellfan.R.id;
import com.tellfan.R.layout;
import com.tellfan.R.string;
import com.tellfan.adapter.WallmediaAdapter;
import com.tellfan.media.MediaItem;
import com.tellfan.media.Medias;

public class WallmediaActivity extends Main implements OnClickListener, OnScrollListener, OnItemClickListener{
    
    ListView list;
    WallmediaAdapter adapter;
	int itemsPerPage = 5;
	boolean loadingMore = false;
	Medias myListItems;
	int initialpage = 0;
	int total = 0;
	int nextButton = 0;
	View footerView ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallmedia);
                
        list=(ListView)findViewById(R.id.wallmedialist);

		//add the footer before adding the adapter, else the footer will nod load!
		footerView = ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listfooter, null, false);
        list.addFooterView(footerView);
               
		//list scroll listener
		this.list.setOnScrollListener(new OnScrollListener(){
			
			//useless here, skip!
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			
			//dumdumdum			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
				
				//what is the bottom iten that is visible
				int lastInScreen = firstVisibleItem + visibleItemCount;				
				
				//is the bottom item visible & not loading more already ? Load more !
				if((lastInScreen == totalItemCount) && !(loadingMore)){					
					Thread thread =  new Thread(null, loadMoreListItems);
			        thread.start();
				}
			}
		});
        
		myListItems = new Medias();
		adapter=new WallmediaAdapter(this, myListItems);
        list.setAdapter(adapter);
		
        this.list.setOnItemClickListener(this);
		
		//Load the first 10 items
		Thread thread =  new Thread(null, loadMoreListItems);
        thread.start();
 
    }
    
    @Override
    public void onDestroy()
    {
        list.setAdapter(null);
        super.onDestroy();
    }
    
    

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

	
    //Runnable to load the items 
    private Runnable loadMoreListItems = new Runnable() {			
		@Override
		public void run()  {
			//Set flag so we cant load new items 2 at the same time
			try {
				if (initialpage <= total) {
				loadingMore = true;
				initialpage = initialpage+1;
				
				//Reset the array that holds the new items
		    	myListItems =new Medias();
		    			    	
				//Simulate a delay, delete this on a production environment!
		    	String url = getString(R.string.url);    	
				HttpResponse datax = HttpRequest.get3(url + "/services/mediaBrowseAll.ashx?fl=0&p="+initialpage+"&s="+itemsPerPage+"&c="+TellFan.getInstance().getUser().id+"&t=&g=&id=&m=&x=b");
				
				Medias  medias = new Medias();	                
	            List<MediaItem> itemmedia = new ArrayList();
				
                String line;
                StringBuffer ret = new StringBuffer();
                MediaItem mediaItem = null;
                BufferedReader rd = new BufferedReader(new InputStreamReader(datax.getEntity().getContent()));
                
                try {
	                while ((line = rd.readLine()) != null) {	                	
	                	ret.append(line);	   
	                	
						if (line.contains("nextButton")) {
							String next = line.replace(
									"nextButton", "").replace("\"",
									"").replace(":", "").replace(",", "")
									.trim();
							
							nextButton = Integer.parseInt(next);
						}
						
	                	if (line.contains("totalResultsAvailable")){
	                		String totalResultsAvailable = line.replace("totalResultsAvailable","").
	                		replace("\"", "").replace(":", "").replace(",", "").trim();
	                		System.out.println(totalResultsAvailable);
	                		medias.setTotalResultsAvailable(Integer.parseInt(totalResultsAvailable));
	                	}
	                	
	                  	if (line.contains("totalPages")){
	                		String totalPages = line.replace("totalPages","").
	                		replace("\"", "").replace(":", "").replace(",", "").trim();
	                		System.out.println(totalPages);
	                		total = Integer.parseInt(totalPages.replace(".0", "").trim());
	                		medias.setTotalPages(Integer.parseInt(totalPages.replace(".0", "").trim()));
	                	}
	  	                  	
	                	if (line.contains("mediaLabel")){
	                		mediaItem = new MediaItem();
	                		String mediaLabel = line.replace("mediaLabel","").
	                		replace("\"", "").replace(":", "").replace(",", "").trim();
	                		System.out.println(mediaLabel);
	                		mediaItem.setMediaLabel(mediaLabel);
	                	}	
	                  	
	                  	if (line.contains("mediaSource")){
	                		String mediaSource = line.replace("mediaSource","").
	                		replace("\"", "").replace(":", "").replace(",", "").trim();
	                		System.out.println(mediaSource);
	                		mediaItem.setMediaSource(mediaSource);
	                	}		                  	
	                  	
	                  	if (line.contains("mediaLink")){
	                		String mediaLink = line.replace("mediaLink","").
	                		replace("\"", "").replace(":", "").replace(",", "").trim();
	                		System.out.println(mediaLink);
	                		mediaItem.setMediaLink(mediaLink);
	                	}	
	                  	
	                  	if (line.contains("albumid")){
	                		String albumid = line.replace("albumid","").
	                		replace("\"", "").replace(":", "").replace(",", "").trim();
	                		System.out.println(albumid);
	                		mediaItem.setAlbumId(albumid);
	                	}	
	                  		                	                  	
	                  	if (line.contains("messagetypeid")){
	                		String messagetypeid = line.replace("messagetypeid","").
	                		replace("\"", "").replace(":", "").replace(",", "").trim();
	                		System.out.println(messagetypeid);
	                		mediaItem.setMessageTypeid(messagetypeid);
	                	}
	                  		                  	
	                  	if (line.contains("galleryname")){
	                		String galleryname = line.replace("galleryname","").
	                		replace("\"", "").replace(":", "").replace(",", "").trim();
	                		System.out.println(galleryname);
	                		mediaItem.setGalleryName(galleryname);
	                	}
	                  	
	                  	if (line.contains("ismultiple")){
	                		String ismultiple = line.replace("ismultiple","").
	                		replace("\"", "").replace(":", "").replace(",", "").trim();
	                		System.out.println(ismultiple);
	                		mediaItem.setMultiple(Boolean.parseBoolean(ismultiple));
	                		itemmedia.add(mediaItem);
	                	}
	                  	
	                }
	                
	                Log.e("ERROR", line);	               
	                rd.close();
                } catch (Exception e) {
                	e.printStackTrace();
                    Log.e("ERROR", "ERROR IN CODE:"+e.getMessage());
                }

	                
                medias.addResult(itemmedia);
                myListItems = medias;
                
				//Done! now continue on the UI thread
		        runOnUiThread(returnRes);
				}
			}catch (Exception ex){
				Log.e("ERROR", "ERROR IN CODE:"+ex.getMessage());
			}
	       
		}
	};	
	
    
	//Since we cant update our UI from a thread this Runnable takes care of that! 
    private Runnable returnRes = new Runnable() {
        @Override
        public void run() {			
			adapter.addData(myListItems.getResult());				
			//Tell to the adapter that changes have been made, this will cause the list to refresh
            adapter.notifyDataSetChanged();			
			//Done loading more.
            loadingMore = false;
            
           	if (nextButton == 0){
    			list.removeFooterView(footerView);
    		}
        }
    };

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		 TextView albumID =(TextView)arg1.findViewById(R.id.textAlbumId);
		 TextView messageTypeId=(TextView)arg1.findViewById(R.id.textMessageTypeID);
		 

		Intent act = new Intent(this, WallgalleryActivity.class);
		    act.putExtra("albumid",albumID.getText().toString() );
		    act.putExtra("messagetypeid", messageTypeId.getText().toString());
		startActivity(act);	
		
	}
}