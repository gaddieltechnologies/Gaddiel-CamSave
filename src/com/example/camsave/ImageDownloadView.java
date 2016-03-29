package com.example.camsave;

import java.io.InputStream;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;



public class ImageDownloadView extends Activity {

	String stuff;
	JSONAdapter jSONAdapter ;
    ImageView imageview;
	 JSONParser jsonParser = new JSONParser();
	 ListView dataList;
	 Bitmap image;
	
    private final String imageURL = "http://gaddieltech.fatcow.com/AndroidPhotoUpload/download.php?referenceNo=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.img_list);
   	


if (android.os.Build.VERSION.SDK_INT > 9) {
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
}


   	   imageview = (ImageView) findViewById(R.id.imgView);
   	
   	 /**
   	 * getting intent data from search and previous screen
   	 */
   	  Bundle bundle =getIntent().getExtras();
   	    stuff = bundle.getString("refid");
 
   	    getImage();
   	
    }


    private void getImage() {
     
        class GetImage extends AsyncTask<String,Void,Bitmap>{


            ImageView bmImage;
            ProgressDialog loading;

            public GetImage(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                Log.e("Error11",bitmap.toString() );
                loading.dismiss();
               bmImage.setImageBitmap(bitmap);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ImageDownloadView.this,"Downloading Image","Please wait...",true,true);
            }

            @Override
            protected Bitmap doInBackground(String... strings) {
                String url = imageURL+ strings[0];
                Bitmap mIcon = null;
                try {
                    InputStream in = new java.net.URL(url).openStream();
                    mIcon = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
                return mIcon;
            }
        }

        GetImage gi = new GetImage(imageview);
        gi.execute(stuff);
    }
}
