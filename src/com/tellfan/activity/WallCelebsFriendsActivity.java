package com.tellfan.activity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;

import HttpRequest.HttpRequest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.tellfan.R;
import com.tellfan.TellFan;
import com.tellfan.celebs.Celebs;
import com.tellfan.celebs.WallCelebsAdapter;
import com.tellfan.media.MediaItem;

public class WallCelebsFriendsActivity extends Main  implements OnItemClickListener{
    
    ListView list;
    WallCelebsAdapter adapter;
 
    int itemsPerPage = 10;
	boolean loadingMore = false;
	List<Celebs> myListItems;
	int initialpage = 1;
	int uk = 0;
	int nextButton = 0;
	View footerView ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallfan);
        
        list=(ListView)findViewById(R.id.wallfanlist);
        
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
					if (visibleItemCount < uk){
					 Thread thread =  new Thread(null, loadMoreListItems);
			         thread.start();
					}
					
				}
			}
		});
        
		myListItems = new ArrayList<Celebs>();
		adapter=new WallCelebsAdapter(this, myListItems);
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
    
    
 
	
	
    //Runnable to load the items 
    private Runnable loadMoreListItems = new Runnable() {			
		@Override
		public void run()  {
			//Set flag so we cant load new items 2 at the same time
			try {
				loadingMore = true;
				
				
				//Reset the array that holds the new items
		    	myListItems = new ArrayList<Celebs>();
		    			    	
				//Simulate a delay, delete this on a production environment!
		    	String url = getString(R.string.url);
		    	
				HttpResponse datax = HttpRequest.get3(url + "/services/clientNetworkAndSubscribers.ashx?p="+initialpage+"&s="+itemsPerPage+"&c="+TellFan.getInstance().getUser().id+"&x=c");
				//http://fan2.tellfan.com/services/clientNetworkAndSubscribers.ashx?p=1&s=100&c=13&x=c
				/** Handling XML */				
	            String line;
                StringBuffer ret = new StringBuffer();
                MediaItem mediaItem = null;
                BufferedReader rd = new BufferedReader(new InputStreamReader(datax.getEntity().getContent()));
				List<Celebs> listFans = new ArrayList<Celebs>();
				Celebs celebs = null;
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
						
						
						if (line.contains("clientid")) {
							celebs = new Celebs();
							String fullname = line.replace(
									"clientid", "").replace("\"",
									"").replace(":", "").replace(",", "")
									.trim();
							System.out.println(fullname);
							celebs.setClientID(fullname);
							
						}

						if (line.contains("clientName")) {
							String link = line.replace("clientName", "")
									.replace("\"", "").replace(":", "")
									.replace(",", "").trim();
							System.out.println(link);							
							celebs.setClientName(link);
						}

						if (line.contains("mediaSource")) {
							String thumbnail = line.replace("mediaSource", "")
									.replace("\"", "").replace(":", "")
									.replace(",", "").trim();
							System.out.println(thumbnail);
							celebs.setMediaSource(thumbnail);
						}

						if (line.contains("mediaLink")) {
							String ispaidsubscription = line
									.replace("mediaLink", "").replace("\"",
											"").replace(":", "").replace(",",
											"").trim();
							System.out.println(ispaidsubscription);
							celebs.setMediaLink(ispaidsubscription);
							
							listFans.add(celebs);
						}


					}

					Log.e("ERROR", line);
					rd.close();
				} catch (Exception e) {
					e.printStackTrace();
					Log.e("ERROR", "ERROR IN CODE:" + e.getMessage());
				}

	            myListItems = listFans;
	            
	            uk = listFans.size();
	                
				
				//Done! now continue on the UI thread
		        runOnUiThread(returnRes);
			}catch (Exception ex){
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
            
        	if (nextButton == 0){
    			list.removeFooterView(footerView);
    		}
        }
    };
    
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		 TextView text=(TextView)arg1.findViewById(R.id.textHiddenMe);
		 TextView url=(TextView)arg1.findViewById(R.id.textUrlImage);
		 TextView viewing=(TextView)arg1.findViewById(R.id.fanText);
		 
		 
		 //TextView textSum=(TextView)arg1.findViewById(R.id.sumcomment);
		 
		 //if (!(textSum.getText() == null || textSum.getText().toString().trim().length()==0)){
				Intent act = new Intent(this, WallCelebsMeActivity.class);
			    act.putExtra("rootmessageid",text.getText().toString() );
			    act.putExtra("urlImage",url.getText().toString() );
			    act.putExtra("viewing",viewing.getText().toString() );
				startActivity(act);	
		// }
		
	}
}