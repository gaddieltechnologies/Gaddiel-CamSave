package com.example.camsave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

class JSONAdapter extends BaseAdapter implements ListAdapter {

    private final Activity activity;
    private final JSONArray jsonArray;
    JSONAdapter (Activity activity, JSONArray jsonArray) {
        assert activity != null;
        assert jsonArray != null;
        this.jsonArray = jsonArray;
        this.activity = activity;
    }


    @Override public int getCount() {
        if(null==jsonArray) 
         return 0;
        else
        return jsonArray.length();
    }

    @Override public JSONObject getItem(int position) {
         if(null==jsonArray) return null;
         else
           return jsonArray.optJSONObject(position);
    }

    @Override public long getItemId(int position) {
        JSONObject jsonObject = getItem(position);

        return jsonObject.optLong("id");
    }

    @SuppressLint("InflateParams")
	@Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = activity.getLayoutInflater().inflate(R.layout.ref_list, null);



        TextView text =(TextView)convertView.findViewById(R.id.txtref);

                    JSONObject json_data = getItem(position);  
                    if(null!=json_data ){
                    String jj = null;
					try {
						jj = json_data.getString("refno");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    text.setText(jj); 
                   }

         return convertView;
    }
}