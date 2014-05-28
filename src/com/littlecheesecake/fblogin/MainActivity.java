package com.littlecheesecake.fblogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements UserProfile.ProfileUpdateListener{
	private FBlogin mFBlogin;
	private UserProfile mUser;
	
	/**
	 * views
	 */
	private Button mFBButton;
	private TextView mUserNameView;
	private TextView mEmailView;
	private TextView mGenderView;
	private ImageView mPicView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		
		//mFBlogin = FBlogin.getInstance(this);
		mFBlogin = new FBlogin(this);
		mFBlogin.onCreate(savedInstanceState);
		mUser = new UserProfile();
		mUser.registerUpdateListener(this);
		
		//view
		mFBButton = (Button)findViewById(R.id.fb_button);
		mUserNameView = (TextView) findViewById(R.id.user_name);
		mEmailView = (TextView) findViewById(R.id.user_email);
		mGenderView = (TextView) findViewById(R.id.user_gender);
		
		mFBButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				mFBlogin.FBLoginAndLink(mUser);			
			}
			
		});

	}
	
	@Override
	public void onStart(){
		super.onStart();
		mFBlogin.onStart();
	}
	
	@Override
	public void onStop(){
		super.onStop();
		mFBlogin.onStop();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		mFBlogin.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void OnProfileUpdated() {
		
		runOnUiThread(new Runnable(){

			@Override
			public void run() {
				mUserNameView.setText(mUser.getUserName());
				mEmailView.setText(mUser.getEmail());
				mGenderView.setText(mUser.getGender());		
				mFBButton.setClickable(false);
			}
			
		});		
	}

}
