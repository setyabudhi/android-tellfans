package com.tellfan;

import java.io.IOException;

import android.app.Activity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.hardware.Camera;


public class CameraView extends Activity implements SurfaceHolder.Callback,OnClickListener
{
	SurfaceView mSurfaceView;
	SurfaceHolder mSurfaceHolder;
	Camera mCamera;
	Boolean mPreviewRunning;
	
	 public void onCreate(Bundle savedInstanceState)
	 {
		 super.onCreate(savedInstanceState);
	
		 getWindow().setFormat(PixelFormat.TRANSLUCENT);

		 requestWindowFeature(Window.FEATURE_NO_TITLE);

		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,

		 WindowManager.LayoutParams.FLAG_FULLSCREEN);

		 setContentView(R.layout.camera);

		 mSurfaceView = (SurfaceView) findViewById(R.id.surface_camera);


		 mSurfaceHolder = mSurfaceView.getHolder();

		 mSurfaceHolder.addCallback(this);

		 mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		 mPreviewRunning = false;

		 //Read more: http://www.brighthub.com/mobile/google-android/articles/43414.aspx?p=2#ixzz10A0f4EBh
		 View takeButton = findViewById(R.id.take_button);
	     takeButton.setOnClickListener(this);

	 }
	 
	 public void onClick(View v)
	 {
	     switch(v.getId())
	     {
	    	case R.id.login_button:
	    	{
	    		//mCamera.takePicture(, raw, jpeg);
	    	}break;
	    	
	    	}
	   }
	 

	   
	 

	 public void surfaceCreated(SurfaceHolder holder) 
	 {
		 mCamera = Camera.open();
	 }

	 public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {


		 if (mPreviewRunning) {

		 mCamera.stopPreview();

		 }

		 Camera.Parameters p = mCamera.getParameters();

		 p.setPreviewSize(w, h);

		 //mCamera.setParameters(p);

		 try {

		 mCamera.setPreviewDisplay(holder);

		 } catch (IOException e) {


		 e.printStackTrace();

		 }

		 mCamera.startPreview();

		 mPreviewRunning = true;

		 }

	 public void surfaceDestroyed(SurfaceHolder holder) {


		 mCamera.stopPreview();

		 mPreviewRunning = false;

		 mCamera.release();

		 }



		 //Read more: http://www.brighthub.com/mobile/google-android/articles/43414.aspx?p=2#ixzz10A6mWD6b
	 	Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {

		 public void onPictureTaken(byte[] imageData, Camera c) {


		 }

		 };
	 
}
