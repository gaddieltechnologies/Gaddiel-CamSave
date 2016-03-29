package com.example.camsave;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class DownloadRefnoActivity extends Activity {
	
	 DataBaseHandler db;
	 JSONParser jsonParser = new JSONParser();
	 String ActiveCode;
	 ListView dataList;
	 ArrayList<Contact> ActiveCodeArry = new ArrayList<Contact>();
	 JSONAdapter jSONAdapter ;
	 ArrayList<Contact> refList;
	 String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download);
   	   dataList = (ListView) findViewById(R.id.listdown);
        postData();
        
        
        dataList.setOnItemClickListener(new OnItemClickListener() {

     	   @Override
     	   public void onItemClick(AdapterView<?> parent, View v,
     	     final int position, long id) {
     		    
     		    TextView txt=(TextView)v.findViewById(R.id.txtref);
            	String selected =txt.getText().toString();
     		  	Intent intent = new Intent(DownloadRefnoActivity.this,ImageDownloadView.class);
   	  	   	    intent.putExtra("refid", selected);
		   	  	Log.d("After Send:****", selected);
		   	    startActivity(intent);
             
     	   }
     	   
     	  });
      
    }
    public void postData() {
        // Create a new HttpClient and Post Header
    	  db = new DataBaseHandler(this);
          List<User> user = db.getOneUser();
  	  	for (User cn : user) { 
  	  		
  	  		ActiveCode= cn.getActiveCode();
  	  	}
    	try {
    		   		
        // Add your data
    		Log.d("Active Code: ", ActiveCode);
    		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
    		nameValuePairs.add(new BasicNameValuePair("active_Code", ActiveCode));                   
    		String insertImgValueUrl="http://gaddieltech.fatcow.com/AndroidPhotoUpload/download.php";
    		Log.d("insert PHP: ", ">>> step 5 after url" );
		   json =jsonParser.makeHttpRequest(insertImgValueUrl, "POST",nameValuePairs);
		   Log.d("insert PHP: ", json);
			 JSONArray jArray = new JSONArray(json);
                   if(jArray.length()==0){
                	   Toast.makeText(getApplicationContext(), "No File In The Database",Toast.LENGTH_SHORT).show();
                   }else{
                	   jSONAdapter = new JSONAdapter(this, jArray);
                	   dataList.setAdapter(jSONAdapter);
                	   
       			}
                    
                   
			
		} catch (JSONException e) {
			Log.e("DownloadRefnoActivity: Exception", "Exception is: " + e.getMessage());
			e.printStackTrace();
		}

		
    } 
   
}
