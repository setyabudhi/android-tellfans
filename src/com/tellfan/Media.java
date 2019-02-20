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

public class Media extends ListActivity {

	String SD_CARD_TEMP_DIR = Environment.getExternalStorageDirectory()+ File.separator + "tmpPhoto.jpg";
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 5;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 6;
	private static final int PICK_IMAGE_ACTIVITY_REQUEST_CODE = 8;
	private static final int MEDIA_INFO_REQUEST_CODE = 9;
	private String[] mStrings = { "Gallery", "Take Photo", "Capture Video" };

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
			Intent intent = new Intent(Intent.ACTION_PICK);
			if (TellFan.getInstance().getUser().isClient){
				intent.setType("image/*;video/*");
			}
			else {
				intent.setType("image/*");
			}
		    //intentBrowseFiles.setType("video/*");
		    //startActivity(intentBrowseFiles);
		    //finish();		    
			//Intent gal = new Intent(this, Gallery.class);			
			//startActivity(gal);
		    //finish();
			//Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);		
			//Intent intent = new Intent(Intent.ACTION_PICK);		
			//String cat = "android.intent.category.LAUNCHER";
            //intent.addCategory(cat);
			//String cn = "com.android.camera.GalleryPicker";
			//String pkg = "com.android.gallery";
			//intent.setClassName(pkg, cn);
			startActivityForResult(intent, PICK_IMAGE_ACTIVITY_REQUEST_CODE);	
			
			break;
		case 1:
			File f = new File(SD_CARD_TEMP_DIR);
			Intent intent2 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			intent2.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
			intent2.putExtra(android.provider.MediaStore.EXTRA_VIDEO_QUALITY,1);
			startActivityForResult(intent2, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			break;
		case 2:
			File f2 = new File(SD_CARD_TEMP_DIR);
			Intent intent3 = new Intent("android.media.action.VIDEO_CAPTURE");
			startActivityForResult(intent3, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// use imageUri here to access the image
				File f = new File(SD_CARD_TEMP_DIR);
				try {
					Uri uri = Uri
							.parse(android.provider.MediaStore.Images.Media
									.insertImage(getContentResolver(), f
											.getAbsolutePath(), null, null));

					TellFan.getInstance().setSelectedPath(this.getRealPathFromURI(uri));
					Intent i3 = new Intent(this, MediaInfo.class);
					i3.setData(uri);
					i3.putExtra("Video", false);
					startActivityForResult(i3, MEDIA_INFO_REQUEST_CODE);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				// Log.d("--", "SUCCESS");

			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Picture was not taken",
						Toast.LENGTH_SHORT);
			} else {
				Toast.makeText(this, "Picture was not taken",
						Toast.LENGTH_SHORT);
			}
		} else if (requestCode == PICK_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// use imageUri here to access the image
				Uri uri = data.getData();
				// finish();
				// Log.d("--", "SUCCESS");
				Intent i3 = new Intent(this, MediaInfo.class);
				i3.setData(uri);
				
				String selectedPath = this.getRealPathFromURI(uri);
			
				TellFan.getInstance().setSelectedPath(selectedPath);
				if (selectedPath.substring(selectedPath.lastIndexOf(".")).equalsIgnoreCase(".3gp") 
						|| selectedPath.substring(selectedPath.lastIndexOf(".")).equalsIgnoreCase(".mp4")){
					i3.putExtra("Video", true);
				}else {			
					i3.putExtra("Video", false);
				}
				startActivityForResult(i3, MEDIA_INFO_REQUEST_CODE);

			} else if (resultCode == RESULT_CANCELED) {
				// Toast.makeText(this, "Picture was not taken",
				// Toast.LENGTH_SHORT);
			} else {
				// Toast.makeText(this, "Picture was not taken",
				// Toast.LENGTH_SHORT);
			}
		} else if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) { // a good results
				try {
					File f = new File(SD_CARD_TEMP_DIR);
					Uri uri = Uri
							.parse(android.provider.MediaStore.Images.Media
									.insertImage(getContentResolver(), f
											.getAbsolutePath(), null, null));
					uri = data.getData();

					TellFan.getInstance().setSelectedPath(this.getRealPathFromURI(uri));
					// Log.i("camera", "Selected image: " +
					// capturedImage.toString());
					Intent i3 = new Intent(this, MediaInfo.class);
					i3.setData(uri);
					i3.putExtra("Video", true);
					startActivityForResult(i3, MEDIA_INFO_REQUEST_CODE);

				} catch (FileNotFoundException e) {

				}
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
