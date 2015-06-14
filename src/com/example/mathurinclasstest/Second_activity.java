package com.example.mathurinclasstest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Second_activity  extends Activity{
	Button save;
	SharedPreferences myPref;
	EditText mMessage;
	TextView tv_message;
	String mMessageString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondactivity);
		tv_message = (TextView)findViewById(R.id.textView1);
		mMessage = (EditText) findViewById(R.id.editText1);
		myPref = getSharedPreferences("mypref", MODE_PRIVATE);
		save = (Button)findViewById(R.id.button1);
		
		Toast.makeText(getBaseContext(), myPref.getString("NAME_KEY", ""), Toast.LENGTH_LONG).show();
		if(myPref.contains("NAME_KEY"))
			if(myPref.getString("NAME_KEY", "") != ""){
				mMessageString = myPref.getString("NAME_KEY", "");
			tv_message.setText(mMessageString);
			mMessage.setText(mMessageString);
		}
			
		tv_message.setVisibility(View.GONE);
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(save.getText().toString() == "Reset")
				{
					tv_message.setVisibility(View.INVISIBLE);
					SharedPreferences.Editor edit = myPref.edit();
					mMessageString ="";
					edit.putString("NAME_KEY", mMessageString);
					
					edit.commit();
					mMessage.setVisibility(View.VISIBLE);
					save.setText("Save");
				}
				else
				{
					save_Content(v);
				}
			/*	if(save.getText().toString() == "")
				{
					String message =myPref.getString("NAME_KEY", ""); 
					
					if(message == "" )
					{
						
					}
					else
					{
						tv_message.setText(message);
					}
					
				}*/
				
				
				
			}
		});

		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		String message =myPref.getString("NAME_KEY", ""); 
		
		if(message == "" )
		{
			
		}
		else
		{
			mMessage.setVisibility(View.GONE);
			tv_message.setText(mMessageString);
			tv_message.setVisibility(View.VISIBLE);
			Button button = (Button)findViewById(R.id.button1);
			button.setText("Reset");
		}
	}
	private void save_Content(View v)
	{
		mMessage = (EditText)findViewById(R.id.editText1);
		mMessageString= mMessage.getText().toString();
		
		SharedPreferences.Editor edit = myPref.edit();
		edit.putString("NAME_KEY", mMessageString);
		edit.commit();
		
		mMessage.setVisibility(View.GONE);
		tv_message.setText(mMessageString);
		tv_message.setVisibility(View.VISIBLE);
		Button button = (Button)v.findViewById(R.id.button1);
		button.setText("Reset");
	}
	private void goToDefault()
	{
		
	}

}
