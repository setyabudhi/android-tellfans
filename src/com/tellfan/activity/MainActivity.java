package com.tellfan.activity;

import com.tellfan.R;
import com.tellfan.R.id;
import com.tellfan.R.layout;
import com.tellfan.adapter.ListAdapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainActivity extends Main implements OnClickListener{
    
    ListView list;
    ListAdapter adapter;
    Button logout;
 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        list=(ListView)findViewById(R.id.list);
        
		//add the footer before adding the adapter, else the footer will nod load!
		View footerView = ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listfooter, null, false);
        list.addFooterView(footerView);
 
        
        adapter=new ListAdapter(this, mStrings);
        list.setAdapter(adapter);
        
        logout=(Button)findViewById(R.id.logoutBtn);
        logout.setOnClickListener(this);
        
    
    }
    
    @Override
    public void onDestroy()
    {
        adapter.imageLoader.stopThread();
        list.setAdapter(null);
        super.onDestroy();
    }
    
    
    private String[] mStrings={
            "aam-logo-v3-twitter.png",
            "AndroidCast-350_normal.png",
            "Droid_normal.jpg",
            "twitterhalf_normal.jpg",
            "icon_normal.png",
            "AG.png",
            "androinica-avatar_normal.png",
            "Android_Biz_Man_normal.png",
            "AndroidHomme-LOGO_normal.jpg",
            "aam-logo-v3-twitter.png",
            "AndroidCast-350_normal.png",
            "Droid_normal.jpg",
            "twitterhalf_normal.jpg",
            "icon_normal.png",
            "AG.png",
            "androinica-avatar_normal.png",
            "Android_Biz_Man_normal.png",
            "AndroidHomme-LOGO_normal.jpg",
    };

	@Override
	public void onClick(View arg0) {

	}
}