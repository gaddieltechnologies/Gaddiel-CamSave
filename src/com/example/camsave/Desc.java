package com.example.camsave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Desc extends Activity {
	 String result;
	 DataBaseHandler db;
	Button button;
	 Button save;
	 String Desc;
	 EditText edDesc;
	 Intent i;
	 private Uri fileUri;
	 private Bitmap bitmap;
	 ArrayList<Integer> sel = new ArrayList<Integer>();
	 public static final String UPLOAD_URL = "http://gaddieltech.fatcow.com/AndroidPhotoUpload/upload.php";
	 private static final int CAMERA_REQUEST = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.description);
		  save = (Button) findViewById(R.id.Save);
		  Log.i("clicks","You Clicked B2");
		  db = new DataBaseHandler(this);

		   i = new Intent(this, MainActivity.class);
		  save.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					
					edDesc = (EditText) findViewById(R.id.editText1);
					  Desc=edDesc.getText().toString();
					
					  Log.d("Insert desc: ", Desc); 
					  Bundle bundle =getIntent().getExtras();
					  if (!bundle.isEmpty()) {
						
						  ArrayList<Integer> stuff = bundle.getIntegerArrayList("getlist"); 
						  ArrayList<Integer> count = bundle.getIntegerArrayList("count"); 
						  	for (final Integer number : stuff) {
								 for (final Integer num : count) {
									 if(number!=num){
										 sel.add(number);
									 }
									  
								  }
					    		 
					    		 }
							if(sel.size() < 1){
								for (final Integer num : stuff) {
									class UploadImage extends AsyncTask<Bitmap,Void,String>{

								        ProgressDialog loading;
								        RequestHandler rh = new RequestHandler();
									
										
								        @Override
								        protected void onPreExecute() {
								            super.onPreExecute();
								            loading = ProgressDialog.show(Desc.this, "Uploading Image", "Please wait...",true,true);
								            Log.d("Insert desc: ", "Upload"); 

								        }

								        @Override
								        protected void onPostExecute(String s) {
								            super.onPostExecute(s);
								            loading.dismiss();
								            //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
								            Intent intent = new Intent(
										              Desc.this,MainActivity.class);
								            			startActivity(intent);
								            if("Error"!=s){
								            int id=Integer.parseInt(s);
								            db.deleteContact(new Contact(id));
								            Log.d("Delete Image: ", "DeletingAll.....");
								             //callCamera();
								            
								            }
								            else{
								            	Toast.makeText(getApplicationContext(), " Database Error " , Toast.LENGTH_LONG).show();   	
								            }
								                    }
								       

								        @Override
								        protected String doInBackground(Bitmap... params) {
								        	
										List<Contact> contact = db.getOneContacts(new Contact(num));
								for (Contact cn : contact) {
									byte[] imageBytes = cn.getImage();
								    String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
								  	  String id=Integer.toString(cn.getID());
								  	  String lat= cn.getLat().toString();
								  	  String lon= cn.getLon().toString();
								  	  String refno= cn.getRefno().toString();
								  	String activecode= cn.getActiveCode().toString();
								  	 String shortDescription= cn.getSdesc().toString();
								   // Writing Contacts to log
								 
								            HashMap<String,String> data = new HashMap<String, String>();
								            
								            data.put("image", encodedImage);
								            data.put("Latitude", lat);
								            data.put("Longitude", lon);
								            data.put("Description", Desc);
								            data.put("active_code", activecode);
								            data.put("RefNo", refno);
								            data.put("ShortDescription",shortDescription);
								            
								            data.put("id",id);
								         	Log.d(" Before upload: ",""+ cn.getID());
								             result = rh.sendPostRequest(UPLOAD_URL,data);
								            
								            Log.d("shortDescription: ",shortDescription + "shortDescription.....");
								 
								       }
								            return result;
								        }
								    }
								  	//Log.d(" Before upload: ",imageId +"");
								    UploadImage ui = new UploadImage();
								    ui.execute(bitmap);
								    Log.d("Upload Image: ", "Upload.....");
							}
					    		    
					 } else{
						 for ( final Integer number : sel) {
							 class UploadImage extends AsyncTask<Bitmap,Void,String>{

							        ProgressDialog loading;
							        RequestHandler rh = new RequestHandler();
								
									
							        @Override
							        protected void onPreExecute() {
							            super.onPreExecute();
							            loading = ProgressDialog.show(Desc.this, "Uploading Image", "Please wait...",true,true);
							            Log.d("Insert desc: ", "Upload"); 

							        }

							        @Override
							        protected void onPostExecute(String s) {
							            super.onPostExecute(s);
							            loading.dismiss();
							            //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
							            Intent intent = new Intent(
									              Desc.this,MainActivity.class);
							            			startActivity(intent);
							            if("Error"!=s){
							            int id=Integer.parseInt(s);
							            db.deleteContact(new Contact(id));
							            Log.d("Delete Image: ", "DeletingAll.....");
							             //callCamera();
							            
							            }
							            else{
							            	Toast.makeText(getApplicationContext(), "Database Error " , Toast.LENGTH_LONG).show();   	
							            }
							                    }
							       

							        @Override
							        protected String doInBackground(Bitmap... params) {
							        	
									List<Contact> contact = db.getOneContacts(new Contact(number));
							for (Contact cn : contact) {
								byte[] imageBytes = cn.getImage();
							    String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
							  	  String id=Integer.toString(cn.getID());
							  	  String lat= cn.getLat().toString();
							  	  String lon= cn.getLon().toString();
							  	  String refno= cn.getRefno().toString();
							  	String activecode= cn.getActiveCode().toString();
							  	 String shortDescription= cn.getSdesc().toString();
							   // Writing Contacts to log
							 
							            HashMap<String,String> data = new HashMap<String, String>();
							            
							            data.put("image", encodedImage);
							            data.put("Latitude", lat);
							            data.put("Longitude", lon);
							            data.put("Description", Desc);
							            data.put("active_code", activecode);
							            data.put("RefNo", refno);
							            data.put("ShortDescription",shortDescription);
							            
							            data.put("id",id);
							         	Log.d(" Before upload: ",""+ cn.getID());
							             result = rh.sendPostRequest(UPLOAD_URL,data);
							            
							            Log.d("shortDescription: ",shortDescription + "shortDescription.....");
							 
							       }
							            return result;
							        }
							    }
							  	//Log.d(" Before upload: ",imageId +"");
							    UploadImage ui = new UploadImage();
							    ui.execute(bitmap);
							    Log.d("Upload Image: ", "Upload.....");
						}
					 }
					  }
					  else
					  {
						  Toast.makeText(getApplicationContext(), " No Image Selected " , Toast.LENGTH_LONG).show();  
					  }
				}
			
			});
	}
	

	 public void callCamera() {
			
		  PackageManager packageManager = getPackageManager();
		  boolean doesHaveCamera = packageManager
		          .hasSystemFeature(PackageManager.FEATURE_CAMERA);

		  if (doesHaveCamera) {
		      // start the image capture Intent
		      Intent intent = new Intent(
		              MediaStore.ACTION_IMAGE_CAPTURE);
		      // Get our fileURI
		      intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		      startActivityForResult(intent, CAMERA_REQUEST);

		  }
	 }
	 @Override
	 public void onBackPressed() {
		 Intent launchNextActivity;
		 launchNextActivity = new Intent(Desc.this, MainActivity.class);
		 launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);                  
		 launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		 startActivity(launchNextActivity);
	 }
}

