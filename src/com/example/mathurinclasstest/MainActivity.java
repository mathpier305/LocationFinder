package com.example.mathurinclasstest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	String SERVER_URL ="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20local.search%20where%20zip%3D%2794085%27%20and%20query%3D%27pizza%27&format=json&diagnostics=true&callback=";
	protected ListView mListView;
	protected String mTitle;
	protected String mAddress;
	protected Double mDistance;
	protected int mTotalReviews;
	protected String mLastReviews;
	MyAdapter adapter;
	ArrayList<HashMap<String, String>> arrayList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);  
		Log.d("eventpam", " love me today");
		Button buttonLook = (Button)findViewById(R.id.buttonlookid);
		buttonLook.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText thing = (EditText) findViewById(R.id.editText1);
				String thing_string = thing.getText().toString();
				EditText locate = (EditText) findViewById(R.id.editText2);
				String locate_string = locate.getText().toString();
				SERVER_URL = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20local.search%20where%20zip%3D%27"+locate_string+"%27%20and%20query%3D%27"+thing_string+"%27&format=json&diagnostics=true&callback=";
				DownloadStuff download = new DownloadStuff();
				download.execute();
				 
			}
		});
		
	}
	public void launchSecond(View v)
	{
		Intent second = new Intent(this, Second_activity.class);
		startActivity(second);
		
	}
	 public class  ViewHolder {
	       TextView tv_title;
	       TextView tv_address;
	       TextView tv_distance;
	       TextView tv_totalReviews;
	       TextView tv_LastReviews;

	       ViewHolder(View v) {
	           tv_title = (TextView) v.findViewById(R.id.textView1);
	           tv_address = (TextView) v.findViewById(R.id.textView2);
	           tv_distance = (TextView) v.findViewById(R.id.textView3);
	           tv_totalReviews = (TextView)v.findViewById(R.id.textView4);
	           tv_LastReviews = (TextView)v.findViewById(R.id.textView5);
	        		   
	      }
	 }
     
	public class MyAdapter extends BaseAdapter {
		 
        
        LayoutInflater inflater;
        Context context;
        ArrayList<HashMap<String, String>> data;
        HashMap<String, String> temp = new HashMap<String, String>();
        
        MyAdapter(Context c, ArrayList<HashMap<String, String>> arraylist ){
            this.context = c;
           data = arraylist;
           // list = new ArrayList<SingleRow>();
             //   list.add(new SingleRow(title, addr, distan, totalReview, lastReview));
            }

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}


		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
		/*
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row =  inflater.inflate(R.layout.single_item, parent, false);

	          TextView tv_title = (TextView) findViewById(R.id.textView1);
	          TextView tv_address = (TextView) findViewById(R.id.textView2);
	          TextView tv_distance = (TextView) findViewById(R.id.textView3);
	          TextView tv_totalReviews = (TextView)findViewById(R.id.textView4);
	          TextView tv_LastReviews = (TextView)findViewById(R.id.textView5);

            temp = data.get(position);
            tv_title.setText(temp.get(tv_title));
            tv_address.setText(temp.get(tv_address));
            tv_distance.setText(""+temp.get(tv_distance));
            tv_totalReviews.setText(""+temp.get(tv_totalReviews));
            tv_LastReviews.setText(temp.get(tv_LastReviews));
            

            return row;
        }*/

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View row = convertView;
	          ViewHolder myViewHolder =null;
	          if(row == null)
	          {
	            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            row =  inflater.inflate(R.layout.single_item, parent, false);
	            myViewHolder = new ViewHolder(row);
	            row.setTag(myViewHolder);
	 
		       temp = data.get(position);
		       
	         
	          }
	          else
	          {
	        	  myViewHolder = (ViewHolder)row.getTag();
	          }
	          myViewHolder.tv_title.setText(temp.get("Title"));
	            myViewHolder.tv_address.setText(temp.get("Address"));
	            myViewHolder.tv_distance.setText(""+temp.get("Distance"));
	            myViewHolder.tv_totalReviews.setText(""+temp.get("TotalReviews"));
	            myViewHolder.tv_LastReviews.setText(temp.get("LastReviewIntro"));
	       

				return row;
		}
	}

	public class DownloadStuff extends AsyncTask< Void, Void, Void> {
		 
        @Override
        protected Void doInBackground(Void... params) {
 
            // Return ArrayList for Deals
            arrayList = new ArrayList<HashMap<String, String>>();
                         
            try {
                 
                //Request for JSON
                  DefaultHttpClient defaultClient = new DefaultHttpClient();
                  HttpGet httpGetRequest = new HttpGet(SERVER_URL);
     
                  HttpResponse httpResponse = defaultClient.execute(httpGetRequest);
                  BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
                  String json = reader.readLine();
                  
     
                 JSONObject json_data = new JSONObject(json);
                 Log.d("eventpam", "another one");
                 JSONObject json_query = json_data.getJSONObject("query");
                 JSONObject json_result = json_query.getJSONObject("results");
                JSONArray jArray = json_result.getJSONArray("Result");
                 //JSONArray jArray = jsonObject.getJSONArray("results");
                  Log.d("eventpam", "the number of object is : "+jArray.length());
                 for (int i = 0; i < jArray.length(); i++) {
                	 HashMap<String, String> map =  new HashMap<String, String>();
                	 
     
                      JSONObject oneObject = jArray.getJSONObject(i);
                    //  mTitle = oneObject.getString("Title");
                    //  mAddress = oneObject.getString("Address");
                    //  mDistance = oneObject.getDouble("Distance");
                      JSONObject ratingObject = oneObject.getJSONObject("Rating");
                    //  mTotalReviews = ratingObject.getInt("TotalReviews");
                    //  mLastReviews = ratingObject.getString("LastReviewIntro");
                      map.put("Title", oneObject.getString("Title"));
                      map.put("Address", oneObject.getString("Address")+ ",  "+oneObject.getString("City")+ ",  "+oneObject.getString("State"));
                      map.put("Distance", ""+oneObject.getDouble("Distance"));
                      map.put("TotalReviews", ""+ratingObject.getInt("TotalReviews"));
                      map.put("LastReviewIntro", ratingObject.getString("LastReviewIntro"));
                      arrayList.add(map);
                      Log.d("eventpam", "Title: "+oneObject.getString("Title")+" . Address: "+ oneObject.getString("Address")+" . Distance: "+oneObject.getDouble("Distance")+ " Total Rating "+ratingObject.getInt("TotalReviews")+ " last Rating "+ratingObject.getString("LastReviewIntro"));
                
                  }
              } catch (Exception e) {
                  e.printStackTrace();
              }
            return null; // ----->
        }
         
        // <------
        @Override
        protected void onPostExecute(Void result) {

            adapter = new MyAdapter(MainActivity.this, arrayList);
            mListView = (ListView)findViewById(R.id.listView1);
            mListView.setAdapter(adapter);
         
        }
    }
}
	
