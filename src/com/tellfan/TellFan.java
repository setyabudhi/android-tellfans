package com.tellfan;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.tellfan.media.MediaData;

public class TellFan extends Application {
	    private static String TAG = "TellFan";
	    private static TellFan singleton;
	    public static TellFan getInstance(){return singleton;}
	    private  boolean isLogin = false;
	    private  User user;
	    private  Activity loginActivity;
	    private  String selectedPath;
	    private  String url;
	    private  HashMap<String,MediaData> imageUrl;

	    @Override
	    public void onCreate() {
	        super.onCreate();
	        singleton = this;
	       
	    }

		public void uploadFile2(String caption, String purpose,
				String postURL, Boolean isVideo) {
			String s = getSelectedPath();
			File f = new File(s);
			try {
				HttpClient client = new DefaultHttpClient();

				HttpPost post = new HttpPost(postURL);
				FileBody bin = new FileBody(f);
				MultipartEntity reqEntity = new MultipartEntity(
						HttpMultipartMode.BROWSER_COMPATIBLE);
				reqEntity.addPart("uploadedfile", bin);

				reqEntity.addPart("email", new StringBody(user.email, "text/plain",Charset.forName("UTF-8")));
				reqEntity.addPart("pwd", new StringBody(user.password,"text/plain", Charset.forName("UTF-8")));
				if (isVideo) {
					reqEntity.addPart("fileType", new StringBody("video","text/plain", Charset.forName("UTF-8")));
				} else {
					reqEntity.addPart("fileType", new StringBody("picture","text/plain", Charset.forName("UTF-8")));
				}
				reqEntity.addPart("caption", new StringBody(caption, "text/plain",Charset.forName("UTF-8")));
				reqEntity.addPart("mediaPurpose", new StringBody(purpose,"text/plain", Charset.forName("UTF-8")));
				reqEntity.addPart("device", new StringBody("android", "text/plain",Charset.forName("UTF-8")));
				post.setEntity(reqEntity);
				HttpResponse response = client.execute(post);
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					Log.i("RESPONSE", EntityUtils.toString(resEntity));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		public void uploadFile(String caption, String purpose,
				String postURL, Boolean isVideo, String pvgid) {
			String s = getSelectedPath();
			File f = new File(s);
			try {
				HttpClient client = new DefaultHttpClient();

				HttpPost post = new HttpPost(postURL);
				FileBody bin = new FileBody(f);
				MultipartEntity reqEntity = new MultipartEntity(
						HttpMultipartMode.BROWSER_COMPATIBLE);
				reqEntity.addPart("uploadedfile", bin);

				reqEntity.addPart("email", new StringBody(user.email, "text/plain",Charset.forName("UTF-8")));
				reqEntity.addPart("pwd", new StringBody(user.password,"text/plain", Charset.forName("UTF-8")));
				if (isVideo) {
					reqEntity.addPart("fileType", new StringBody("video","text/plain", Charset.forName("UTF-8")));
				} else {
					reqEntity.addPart("fileType", new StringBody("picture","text/plain", Charset.forName("UTF-8")));
				}
				reqEntity.addPart("caption", new StringBody(caption, "text/plain",Charset.forName("UTF-8")));

				reqEntity.addPart("pvgid", new StringBody(pvgid, "text/plain",Charset.forName("UTF-8")));
				reqEntity.addPart("mediaPurpose", new StringBody(purpose,"text/plain", Charset.forName("UTF-8")));
				reqEntity.addPart("device", new StringBody("android", "text/plain",Charset.forName("UTF-8")));

				post.setEntity(reqEntity);
				HttpResponse response = client.execute(post);
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					Log.i("RESPONSE", EntityUtils.toString(resEntity));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public boolean isLogin() {
			return isLogin;
		}

		public void setLogin(boolean isLogin) {
			this.isLogin = isLogin;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Activity getLoginActivity() {
			return loginActivity;
		}

		public void setLoginActivity(Activity loginActivity) {
			this.loginActivity = loginActivity;
		}

		public String getSelectedPath() {
			return selectedPath;
		}

		public void setSelectedPath(String selectedPath) {
			this.selectedPath = selectedPath;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public HashMap<String, MediaData> getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(HashMap<String,MediaData> imageUrl) {
			this.imageUrl = imageUrl;
		}
		
		public void addImageUrl(String key, MediaData imageUrl) {
			this.imageUrl.put(key, imageUrl);
		}
}
