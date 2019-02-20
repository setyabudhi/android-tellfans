package com.tellfan.activity;

import com.tellfan.R;
import com.tellfan.R.id;
import com.tellfan.R.layout;
import com.tellfan.adapter.FansAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FansActivity extends Main implements OnItemClickListener, OnClickListener{
    
    ListView list;
    FansAdapter adapter;
    Button logout;
    ImageButton upload;
    ImageButton call;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fans);
        
        list=(ListView)findViewById(R.id.fanslist);
        
		//add the footer before adding the adapter, else the footer will nod load!
		View footerView = ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listfooter, null, false);
        list.addFooterView(footerView);
 
        adapter=new FansAdapter(this, mStrings);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        
        logout=(Button)findViewById(R.id.logoutBtn);
        logout.setOnClickListener(this);
        
        upload=(ImageButton)findViewById(R.id.uploadBtn);
        upload.setOnClickListener(this);
        
        call=(ImageButton)findViewById(R.id.callBtn);
        call.setOnClickListener(this);
    }
    
    @Override
    public void onDestroy()
    {
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
    };

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent act = new Intent(this, WallfansActivity.class);
		startActivity(act);		
	}
}