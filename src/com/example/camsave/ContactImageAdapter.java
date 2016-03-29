package com.example.camsave;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactImageAdapter extends ArrayAdapter<Contact>{
 Context context;
 private SparseBooleanArray mSelectedItemsIds;
   int layoutResourceId;
   boolean[] itemChecked;
   Bitmap image;
   ImageDownloadView imageDownloadView;
   
    ArrayList<Contact> data=new ArrayList<Contact>();
    public ContactImageAdapter(Context context, int layoutResourceId, ArrayList<Contact> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        mSelectedItemsIds = new SparseBooleanArray();
		itemChecked = new boolean[data.size()];
		Log.d(" Step1: ",""+ itemChecked);
    }



@Override
 public long getItemId(int position) {
  return data.indexOf(getItem(position));
 }
  class ViewHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
		 CheckBox chk;
		 
    }
    @SuppressLint("InflateParams")
	@Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    	  View row = convertView;
          ImageHolder holder = null;
      
          if(row == null)
          {
              LayoutInflater inflater = ((Activity)context).getLayoutInflater();
              row = inflater.inflate(layoutResourceId, parent, false);
          
              holder = new ImageHolder();
              holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle1);
              holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
              holder.chk = (CheckBox) row.findViewById(R.id.checkBox1); 
              row.setTag(holder);
              
              Contact row_pos = data.get(position);
			  Log.d(" Step3: ",""+ row_pos);
			  
			  holder.txtTitle.setText(row_pos.getName());
			  holder.chk.setChecked(false);
				
			  
			  if (itemChecked[position])
			  {
				  Log.d(" Step3a: ","Before True"+ itemChecked[position]);
				  holder.chk.setChecked(true);
				  Log.d(" Step3b: ","True"+ itemChecked[position]);
			  }
			  else
			  {
				  Log.d(" Step3c: ","Before False"+ itemChecked[position]);
				  holder.chk.setChecked(false);
				  Log.d(" Step3d: ","False"+ itemChecked[position]);
			  }
              
          }
          else
          {
              holder = (ImageHolder)row.getTag();
          }
      
          Contact picture = data.get(position);
          holder.txtTitle.setText(picture._name);
          //convert byte to bitmap take from contact class
        
          byte[] outImage=picture._image;
          ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
          Bitmap theImage = BitmapFactory.decodeStream(imageStream);
          holder.imgIcon.setImageBitmap(theImage);
         return row;
       }
	public void toggleSelection(int position) {
		selectView(position, !mSelectedItemsIds.get(position));
	}
    public void selectView(int position, boolean value) {
		if (value)
			mSelectedItemsIds.put(position, value);
		else
			mSelectedItemsIds.delete(position);
		notifyDataSetChanged();
	}

    static class ImageHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
        CheckBox chk;
        
    }
      
}