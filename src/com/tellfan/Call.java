package com.tellfan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

public class Call extends ListActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setListAdapter(new
		// ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
		// mStrings));

		ArrayList<HashMap<String, String>> a = new ArrayList<HashMap<String, String>>();

		for (User u : TellFan.getInstance().getUser().clientList) {
			HashMap<String, String> m = new HashMap<String, String>();
			m.put("Name", u.name);
			m.put("Phone", u.phone);
			a.add(m);
		}

		// getListView().setTextFilterEnabled(true);
		SimpleAdapter adapter = new SimpleAdapter(this, a,
				android.R.layout.two_line_list_item, new String[] { "Name",
						"Phone" }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		setListAdapter(adapter);
		getListView().setTextFilterEnabled(true);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// Log.d("-", s);
		User u = TellFan.getInstance().getUser().clientList.get(position);
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		String url = String.format("tel:%s", u.phone);
		Log.d("--", url);
		callIntent.setData(Uri.parse(url));
		startActivity(callIntent);
	}

}
