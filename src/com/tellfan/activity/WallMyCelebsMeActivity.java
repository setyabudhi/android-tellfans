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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.tellfan.R;
import com.tellfan.TellFan;
import com.tellfan.adapter.WallmeAdapter;
import com.tellfan.shoutout.Celeb;
import com.tellfan.shoutout.CelebsHandler;
import com.tellfan.util.ImageLoader;

public class WallMyCelebsMeActivity extends Main implements OnClickListener, OnTouchListener ,OnItemClickListener {
    
    ListView list;
    WallmeAdapter adapter;
    Button logout;
    ImageButton upload;
    ImageButton call;
	int itemsPerPage = 10;
	int counter = 0;
	boolean loadingMore = false;
	List<Celeb> myListItems;
	int initialpage = 1;
	View footerView ;
	EditText editText;
	boolean more = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallcelebme);
       
        
        list=(ListView)findViewById(R.id.wallmelist);
        
        editText=(EditText)findViewById(R.id.textShotout);
        editText.setOnTouchListener(this);
        editText.setInputType(0);
       
        
		//add the footer before adding the adapter, else the footer will nod load!
		footerView = ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listfooter, null, false);
        list.addFooterView(footerView);
        list.requestFocus();
               
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
        
		
		
		myListItems = new ArrayList<Celeb>();
		adapter=new WallmeAdapter(this, myListItems);
        list.setAdapter(adapter);
		
        this.list.setOnItemClickListener(this);
        
       
		//Load the first 10 items
		Thread thread =  new Thread(null, loadMoreListItems);
        thread.start();
        
        
        ImageView img = (ImageView) findViewById(R.id.imageView2);
        img.setOnClickListener(this);
        
        
        Bundle b = getIntent().getExtras();    
        TextView viewing = (TextView)findViewById(R.id.viewingname);
        viewing.setText( "Viewing "+b.getString("viewing")+"'s Page");
 
        ImageView image = (ImageView)findViewById(R.id.viewingimage);
        
        ImageLoader imageLoader=new ImageLoader(this.getApplicationContext());
        
        imageLoader.DisplayImage(b.getString("urlImage"), this, image);
 
    }
    
    @Override
    public void onDestroy()
    {
        list.setAdapter(null);
        super.onDestroy();
    }
    
    

	@Override
	public void onClick(View arg0) {
		if(arg0.getId() == R.id.imageView2){
			String url = this.getString(R.string.url_clientoperation);
			Hashtable<String, String> userData = new Hashtable<String, String>();

			userData.put("type", "shoutout");
			userData.put("currentclientid", ""+id());
			EditText shout = (EditText) findViewById(R.id.textShotout);		    
			userData.put("value", shout.getText().toString());
			userData.put("counter", counter+"");
		
			HttpData dataLogin = HttpRequest.post2(url,userData);
			counter++;
			initialpage = 0;
	
			startActivity(getIntent()); 
			finish();

		}
	}


	private String id(){
        Bundle b = getIntent().getExtras();        
        return b.getString("rootmessageid");
	}
	
    //Runnable to load the items 
    private Runnable loadMoreListItems = new Runnable() {			
		@Override
		public void run()  {
			//Set flag so we cant load new items 2 at the same time
			try {
				loadingMore = true;
								
				//Reset the array that holds the new items
		    	myListItems = new ArrayList<Celeb>();
		    	
		    	
				//Simulate a delay, delete this on a production environment!
		    	String url = getString(R.string.url_login);
				HttpResponse datax = HttpRequest.get3(url + "?whattype=shoutout&page="+initialpage+"&limit="+itemsPerPage+"&isViewedby=user&ViewID="+TellFan.getInstance().getUser().id+"&targetID="+id());
				//http://fan2.tellfan.com/services/M2UserInfoHandler.ashx?whattype=shoutout&page=1&limit=10&isViewedby=celebtoceleb&ViewID=13&targetID=15
              
				/** Handling XML */
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();
				CelebsHandler handler = new CelebsHandler();
				xr.setContentHandler(handler);
				if (datax.getEntity() != null){
				   xr.parse(new InputSource(datax.getEntity().getContent()));
				}
				
				more = handler.isMoreDisplayed();
				
				List<Celeb> celebsList = handler.getCelebs();
				myListItems = celebsList;
				
			    if (myListItems.size() == 0) {
	                list.removeFooterView(footerView);
	            }
				//Done! now continue on the UI thread
		        runOnUiThread(returnRes);
			}catch (Exception ex){
				ex.printStackTrace();
				Log.e("ERROR", "ERROR IN CODE:"+ex.getMessage());
			}
			initialpage++;
		}
	};	
	
    
	//Since we cant update our UI from a thread this Runnable takes care of that! 
    private Runnable returnRes = new Runnable() {
        @Override
        public void run() {			
			adapter.addData(myListItems);				
			//Tell to the adapter that changes have been made, this will cause the list to refresh
            adapter.notifyDataSetChanged();			
			//Done loading more.
            loadingMore = false;
            
          	if (!more){
    			list.removeFooterView(footerView);
    		}
        }
    };

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		 TextView text=(TextView)arg1.findViewById(R.id.textHiddenMe);
		 
		 //TextView textSum=(TextView)arg1.findViewById(R.id.sumcomment);
		 
		 //if (!(textSum.getText() == null || textSum.getText().toString().trim().length()==0)){
				Intent act = new Intent(this, WallMyCelebsMeCommentActivity.class);
			    act.putExtra("rootmessageid",text.getText().toString() );
				startActivity(act);	
		// }
		
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		if(arg0.getId() == R.id.textShotout){
			this.editText.setInputType(1);
		}
		return false;
	}


}