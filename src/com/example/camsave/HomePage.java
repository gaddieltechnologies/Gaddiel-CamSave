package com.example.camsave;

import java.util.List;

import org.apache.http.NameValuePair;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class HomePage extends Activity {
	DataBaseHandler db;
	List<NameValuePair> nameValuePairs;
	JSONParser jsonParser = new JSONParser();
	String ActiveCode;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup);
		Button btnUpload = (Button)findViewById(R.id.btn_upload);
		
		btnUpload.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent( HomePage.this,MainActivity.class);
				startActivity(intent);
				// Switching to Register screen
				
			}
		});
		Button btnDownload = (Button)findViewById(R.id.btn_download);

		btnDownload.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				 
				Intent intent = new Intent( HomePage.this,DownloadRefnoActivity.class);
				startActivity(intent);
						}
				  
			
		});
		
		
	}
 
    
    public void onBackPressed(){
    	new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
        .setMessage("Are you sure you want to exit?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);//***Change Here***
                startActivity(intent);
                finish();
                System.exit(0);
                 
            }
        }).setNegativeButton("No", null).show();
    }
}
