package com.example.camsave;

import java.io.ByteArrayInputStream;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class PopUp extends Activity {
Button btnSave;
ImageView imageDetail;
int imageId;
int Id;
EditText edDesc;
Bitmap theImage;
String Desc;

@Override
public void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.screen_popup);
 edDesc = (EditText) findViewById(R.id.editdesc2);
 btnSave = (Button) findViewById(R.id.btn_Save);
 imageDetail = (ImageView) findViewById(R.id.image1);
 /**
 * getting intent data from search and previous screen
 */
 Intent intnt = getIntent();
 theImage = (Bitmap) intnt.getParcelableExtra("imagename");
 imageId = intnt.getIntExtra("imageid", 20);
 Log.d("Image ID:****", String.valueOf(imageId));
 imageDetail.setImageBitmap(theImage);
 btnSave.setOnClickListener(new OnClickListener() {

  @Override
  public void onClick(View v) {
	  Desc=edDesc.getText().toString();
  DataBaseHandler db = new DataBaseHandler(
     PopUp.this);
	
   Log.d("Short Desc: ",Desc);

   db.updateNote(imageId, Desc);

   Log.d("update: ", "Updating.....");
   // /after deleting data go to main page
   List<Contact> contacts = db.getAllContacts();
   for (Contact cn : contacts) {
 	  
 	  ByteArrayInputStream imageStream = new ByteArrayInputStream(cn.getImage());
 	  Bitmap theImage = BitmapFactory.decodeStream(imageStream);
 	  
 	  String log = "ID:" + cn.getID() + " Name: " + cn.getName()
 	  + " ,Image: " + theImage  + " Latitude: " + cn.getLat()
 	     + " Longitude: " + cn.getLon()+ " Activecode: " + cn.getActiveCode()+ " ReferenceNo: " + cn.getRefno()+ "Description: " + cn.getSdesc()
 	    ;


    // Writing Contacts to log
    Log.d("Result: ", log);
    // add contacts data in arrayList
 

  }
   Intent intent = new Intent(PopUp.this,MainActivity.class);
   startActivity(intent);
    }
 });
}
public void onBackPressed(){
	   super.onBackPressed();
	   finish();
	 }
 }


