package com.tellfan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;

public class Gallery extends ListActivity {

	String SD_CARD_TEMP_DIR = Environment.getExternalStorageDirectory()+ File.separator + "tmpPhoto.jpg";
	private static final int PICK_IMAGE_ACTIVITY_REQUEST_CODE = 8;
	private static final int PICK_VIDEO_ACTIVITY_REQUEST_CODE = 10;
	private static final int MEDIA_INFO_REQUEST_CODE = 9;
	private String[] mStrings = { "Videos", "Images" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Use an existing ListAdapter that will map an array
		// of strings to TextViews

		setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, mStrings));
		getListView().setTextFilterEnabled(true);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String s = (String) l.getItemAtPosition(position);
		// Log.d("-", s);

		switch (position) {
		case 0:
			Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
			startActivityForResult(intent, PICK_IMAGE_ACTIVITY_REQUEST_CODE);
			break;
		case 1:
			Intent intentVideo = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Video.Media.INTERNAL_CONTENT_URI);
			startActivityForResult(intentVideo, PICK_IMAGE_ACTIVITY_REQUEST_CODE);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PICK_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// use imageUri here to access the image
				Uri uri = data.getData();

				TellFan.getInstance().setSelectedPath(this.getRealPathFromURI(uri));
				// finish();
				// Log.d("--", "SUCCESS");
				Intent i3 = new Intent(this, MediaInfo.class);
				i3.setData(uri);
				i3.putExtra("Image", false);
				startActivityForResult(i3, MEDIA_INFO_REQUEST_CODE);

			} else if (resultCode == RESULT_CANCELED) {
				// Toast.makeText(this, "Picture was not taken",
				// Toast.LENGTH_SHORT);
			} else {
				// Toast.makeText(this, "Picture was not taken",
				// Toast.LENGTH_SHORT);
			}
		}else if (requestCode == PICK_VIDEO_ACTIVITY_REQUEST_CODE){
			if (resultCode == RESULT_OK) {
				// use imageUri here to access the image
				Uri uri = data.getData();

				TellFan.getInstance().setSelectedPath(this.getRealPathFromURI(uri));
				// finish();
				// Log.d("--", "SUCCESS");
				Intent i3 = new Intent(this, MediaInfo.class);
				i3.setData(uri);
				i3.putExtra("Video", false);
				startActivityForResult(i3, MEDIA_INFO_REQUEST_CODE);

			} else if (resultCode == RESULT_CANCELED) {
				// Toast.makeText(this, "Picture was not taken",
				// Toast.LENGTH_SHORT);
			} else {
				// Toast.makeText(this, "Picture was not taken",
				// Toast.LENGTH_SHORT);
			}
		}

	

		else {
			finish();
		}

	}

	public static File convertImageUriToFile(Uri imageUri, Activity activity) {

		Cursor cursor = null;

		try {

			String[] proj = { android.provider.MediaStore.Images.Media.DATA,
					android.provider.MediaStore.Images.Media._ID,
					android.provider.MediaStore.Images.ImageColumns.ORIENTATION };

			cursor = activity.managedQuery(imageUri,

			proj, // Which columns to return

					null, // WHERE clause; which rows to return (all rows)

					null, // WHERE clause selection arguments (none)

					null); // Order-by clause (ascending by name)

			int file_ColumnIndex = cursor.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DATA);
			int orientation_ColumnIndex = cursor.getColumnIndexOrThrow(android.provider.MediaStore.Images.ImageColumns.ORIENTATION);

			if (cursor.moveToFirst()) {
				String orientation = cursor.getString(orientation_ColumnIndex);
				return new File(cursor.getString(file_ColumnIndex));

			}

			return null;

		} finally {

			if (cursor != null) {

				cursor.close();

			}

		}

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
