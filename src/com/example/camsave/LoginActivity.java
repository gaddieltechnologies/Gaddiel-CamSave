package com.example.camsave;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("CommitPrefEdits")
public class LoginActivity extends Activity {
	public static final String UPLOAD_URL1 = "http://gaddieltech.fatcow.com/AndroidPhotoUpload/Active_Code.php";
EditText activecode;
String Active;
Bitmap theImage;
String result;
DataBaseHandler db;
ProgressDialog pDialog;
HttpPost httppost;
StringBuffer buffer;
HttpResponse response;
HttpClient httpclient;

List<NameValuePair> nameValuePairs;
JSONParser jsonParser = new JSONParser();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        isInternetOn();
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //Toast.makeText(this, "GPS is Enabled", Toast.LENGTH_SHORT).show();
        }else{
            showGPSDisabledAlertToUser();
        }
        
        if (android.os.Build.VERSION.SDK_INT > 9) {
    		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
    				.permitAll().build();
    		StrictMode.setThreadPolicy(policy);
    	}
    
        db = new DataBaseHandler(this);
        Log.d("Step 2: ","Before Login");
        activecode=(EditText)findViewById(R.id.editText2);
       
        Button register = (Button) findViewById(R.id.login);
       
        register.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Active=activecode.getText().toString();
				isInternetOn();
				//showGPSDisabledAlertToUser();
				postData();
			}
		});
        
     
    }
    public void postData() {
        // Create a new HttpClient and Post Header
    	try {
        // Add your data
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("active_Code", Active));                   
		String insertImgValueUrl="http://gaddieltech.fatcow.com/AndroidPhotoUpload/Active_Code.php";
		Log.d("insert PHP: ", ">>> step 4 after url" );
		String json =jsonParser.makeHttpRequest(insertImgValueUrl, "POST",nameValuePairs);
		  
			 JSONArray jArray = new JSONArray(json);
                   if(jArray.length()==0){
                	   showError();
                   }else{
                	   JSONObject json_obj = jArray.getJSONObject(0);
                	 String activecodej = json_obj.getString("activate_code");
                	   SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                	   SharedPreferences.Editor editor = settings.edit();
                	   editor.putString("activecode", activecodej);
                	   db.addUser(new User(01,activecodej));
       				Intent i = new Intent(getApplicationContext(), HomePage.class);
       				startActivity(i);
                	  
       				Log.d("Active coddde ", activecodej );
       			}
                    
                   
			
		} catch (JSONException e) {
			Log.e("MainActivity: Exception", "Exception is: " + e.getMessage());
			e.printStackTrace();
		}

	
    } 
public final boolean isInternetOn() {
        
        getBaseContext();
		// get Connectivity Manager object to check connection
        ConnectivityManager connec =  
                       (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
         
           // Check for network connections
            if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                 connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                 connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                 connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
                
                // if connected with internet
            	Log.d("","Internet Connected!"); 
                //Toast.makeText(this, " Internet Connected ", Toast.LENGTH_LONG).show();
                return true;
                 
            } else if ( 
              connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
              connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
            	Log.d("","Internet Not Connected!"); 
                Toast.makeText(this, " Please Connect Internet", Toast.LENGTH_LONG).show();
               
                return false;
            }
          return false;
        }
private void showGPSDisabledAlertToUser(){
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
    .setCancelable(false)
    .setPositiveButton("Go to Settings",
            new DialogInterface.OnClickListener(){
        public void onClick(DialogInterface dialog, int id){
            Intent callGPSSettingIntent = new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(callGPSSettingIntent);
        }
    });
    alertDialogBuilder.setNegativeButton("Cancel",
            new DialogInterface.OnClickListener(){
        public void onClick(DialogInterface dialog, int id){
            dialog.cancel();
        }
    });
    AlertDialog alert = alertDialogBuilder.create();
    alert.show();
}
private void showError(){
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    alertDialogBuilder.setMessage("Invalid active code")
    .setCancelable(false);
 
    alertDialogBuilder.setNegativeButton("Cancel",
            new DialogInterface.OnClickListener(){
        public void onClick(DialogInterface dialog, int id){
            dialog.cancel();
        }
    });
    AlertDialog alert = alertDialogBuilder.create();
    alert.show();
}
    
}
       