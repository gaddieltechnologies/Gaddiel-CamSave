package com.example.camsave;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class MainActivity extends Activity {

 Button addImage;
 Button RefImage;
 Button delete;
 EditText et;
 Button btnClosePopup;
 ArrayList<Contact> imageArry = new ArrayList<Contact>();
 ContactImageAdapter imageAdapter;
 private static final int CAMERA_REQUEST = 1;
 ListView dataList;
 byte[] imageName;
 ArrayList<Integer> countlist = new ArrayList<Integer>();
 ArrayList<Integer> count = new ArrayList<Integer>();
 String urs;
 int imageId;
 String Desc;
 Bitmap theImage;
 DataBaseHandler db;
 LoginActivity login;
 ImageView imageDetail;
 private Uri fileUri;
 String result;
 public static final String UPLOAD_URL = "http://gaddieltech.fatcow.com/AndroidPhotoUpload/upload.php";
 public static final String UPLOAD_IMG = "image";
 public static final String UPLOAD_LAT = "Latitude";
 public static final String UPLOAD_LON = "Longitude";
 public static final String UPLOAD_DESC = "Description";
 public static final String UPLOAD_REFNO = "Referenceno";
 public static final String UPLOAD_SDESC = "ShortDescription";
 public static final String TAG = "MY MESSAGE";

private Button NxtImage;
static ActionBar actionbar;

 /** Called when the activity is first created. */
@Override
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  dataList = (ListView) findViewById(R.id.list);
  /**
   * create DatabaseHandler object
   */
 
//Permission StrictMode
  if (android.os.Build.VERSION.SDK_INT > 9) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

  db = new DataBaseHandler(this);

  /**
   * Reading and getting all records from database
   */
  List<Contact> contacts = db.getAllContacts();
  if(contacts.isEmpty()){
	  
	  callCamera();
  }else{
  for (Contact cn : contacts) {
	  
	  ByteArrayInputStream imageStream = new ByteArrayInputStream(cn.getImage());
	  Bitmap theImage = BitmapFactory.decodeStream(imageStream);
	  
	  String log = "ID:" + cn.getID() + " Name: " + cn.getName()
	  + " ,Image: " + theImage  + " Latitude: " + cn.getLat()
	     + " Longitude: " + cn.getLon()+ " Activecode: " + cn.getActiveCode()+ " ReferenceNo: " + cn.getRefno()+ "ShortDescription: " + cn.getSdesc()
	    ;


   // Writing Contacts to log
   Log.d("Result: ", log);
   // add contacts data in arrayList
   imageArry.add(cn);
   
  }
  addImage = (Button) findViewById(R.id.btnAdd);
  
	  addImage.setOnClickListener(new View.OnClickListener() {
		     public void onClick(View v) {
		  	   callCamera();
		     }
		    });
	  et=(EditText)findViewById(R.id.editText1);
	  NxtImage = (Button) findViewById(R.id.btnDesc);
	  
	  NxtImage.setOnClickListener(new View.OnClickListener() {
		     public void onClick(View v) {
		    	 
		    	 
		    	 for (Integer number : countlist) {
		    		   System.out.println("Number = " + number);
		    		   } 
		    	 for (Integer num : count) {
		    		   System.out.println("Num = " + num);
		    		   } 
		    	 Log.i("clicks","You Clicked B1");
		    	 Intent intent = new Intent( MainActivity.this,Desc.class);
		    	 Bundle bundle = new Bundle();
		    	 bundle.putIntegerArrayList("getlist", countlist);
		    	 bundle.putIntegerArrayList("count", count);
				intent.putExtras(bundle);
				startActivity(intent);
		    	
		  		    	
		     }
		    });

	  
  }
  /**
   * go to next activity for detail image
   */

  
  imageAdapter = new ContactImageAdapter(this, R.layout.screen_list,imageArry);
  dataList.setAdapter(imageAdapter);
  dataList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
    	   CheckBox chk = (CheckBox) view.findViewById(R.id.checkBox1);
    	   chk.performClick();
    	   Contact contact = (Contact) parent.getItemAtPosition(position);
    	   if(chk.isChecked())
    	   {
    	  countlist.add(contact.getID());
    	 	return true;
    	   }else{
    		   count.add(contact.getID());
    		   Log.i("clicks","Returns False");
        	   return true;
        	   
    	   }
    	 
      }
    });
  
  dataList.setOnItemClickListener(new OnItemClickListener() {

	   @Override
	   public void onItemClick(AdapterView<?> parent, View v,
	     final int position, long id) {
	    imageName = imageArry.get(position).getImage();
	    imageId = imageArry.get(position).getID();

	    Log.d("Before Send:****", imageName + "-" + imageId);
	    // convert byte to bitmap
	    ByteArrayInputStream imageStream = new ByteArrayInputStream(
	      imageName);
	    theImage = BitmapFactory.decodeStream(imageStream);
	    Intent intent = new Intent(MainActivity.this,
	      PopUp.class);
	    intent.putExtra("imagename", theImage);
	    intent.putExtra("imageid", imageId);
	    startActivity(intent);

	   }
	  });
  
 }
 GPSTracker gps;
 /**
  * On activity result
  */

 @Override
 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  if ((resultCode == RESULT_OK)&& (requestCode==CAMERA_REQUEST))
  {
	  gps = new GPSTracker(MainActivity.this);
	  Bundle extras = data.getExtras();
	  if(gps.canGetLocation())
      {
      double latitude = gps.getLatitude();
      double longitude = gps.getLongitude();
      if (extras != null) {
  	    Bitmap yourImage = extras.getParcelable("data");
  	    // convert bitmap to byte
  	    ByteArrayOutputStream stream = new ByteArrayOutputStream();
  	    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
  	    byte imageInByte[] = stream.toByteArray();
  	    Log.e("output before conversion", imageInByte.toString());
  	  SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddhhmmss");
  	  dateFormatter.setLenient(false);
  	  Date today = new Date();
  	  String refnum = dateFormatter.format(today);
  	  List<User> user = db.getAllUser();
  	for (User cn : user) { 
  	
  	  urs= cn.getActiveCode();
  	}
  	    // Inserting Contacts
  	    Log.d("Insert: ", "Inserting ..");
  	    db.addContact(new Contact(01, urs+refnum, imageInByte,latitude,longitude,urs,refnum,"Not Given"));
  	    Intent i = new Intent(MainActivity.this,MainActivity.class);
  	    startActivity(i);
  	    finish();
  	   
  	   }
      // \n is for new line
      Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
  } else {
	  
      //Can't get location.
      //GPS or network is not enabled.
       //Please enable GPS/network in settings.

  }
  }
 }
 
 /**
  * open camera method
  */
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
	 launchNextActivity = new Intent(MainActivity.this, HomePage.class);
	 launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	 launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);                  
	 launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	 startActivity(launchNextActivity);
 }

 }


 
