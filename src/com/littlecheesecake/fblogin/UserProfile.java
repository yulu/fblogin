package com.littlecheesecake.fblogin;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserProfile {
	private static final String PREF_NAME 			= "Profile";
	private static final int PRIVATE_MODE 			= 0;
	private static final String OPEN 				= "session_open";
	private static final String USER_NAME			= "user_name";
	private static final String EMAIL				= "email";
	private static final String PIC					= "pic";
	private static final String GENDER				= "gender";
	
	private static UserProfile mUserProfile;
	private SharedPreferences mPref;
	private Editor mEditor;
	private Context mContext;
	
	private boolean mOpen; //is session opened?
	private String mUserName;
	private String mEmail;
	private String mPicUrl;
	private String mGender;
	
	private ArrayList<ProfileUpdateListener> mListener;
	
	public static UserProfile getInstance(Context c){
		if(mUserProfile == null){
			mUserProfile = new UserProfile(c);
		}
		
		return mUserProfile;
	}
	
	public UserProfile(Context c) {
		this.mContext = c;
		/**
		 * open preference
		 */
		mPref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		mEditor = mPref.edit();
		
		GetProfile();
	}
	
	public void registerUpdateListener(ProfileUpdateListener l){
		if(mListener == null)
			mListener = new ArrayList<ProfileUpdateListener>();
		
		mListener.add(l);
	}
	
	public void closeSession(){
		this.mOpen = false;
		this.mUserName = "";
		this.mEmail = "";
		this.mPicUrl = "";
		this.mGender = "";
		
		CommitChange();	
		
		if(mListener != null){
			for(ProfileUpdateListener l : mListener){
				l.OnProfileUpdated();
			}
		}
	}
	
	public void setProfile(){
		if(mOpen){
			setProfile(mUserName, mEmail, mPicUrl, mGender);
		}

	}
	
	public void setProfile(String username, String email, String pic, String gender){
		
		this.mOpen = true;
		this.mUserName = username;
		this.mEmail = email;
		this.mPicUrl = pic;
		this.mGender = gender;
		
		CommitChange();
		
		if(mListener != null){
			for(ProfileUpdateListener l : mListener){
				l.OnProfileUpdated();
			}
		}
		
	}
	
	private void GetProfile(){
		mOpen = mPref.getBoolean(OPEN, false);
		mUserName = mPref.getString(USER_NAME, null);
		mEmail = mPref.getString(EMAIL, null);
		mPicUrl = mPref.getString(PIC, null);
		mGender = mPref.getString(GENDER, null);
	}
	
	private void CommitChange(){
		mEditor.putBoolean(OPEN, mOpen);
		mEditor.putString(USER_NAME, mUserName);
		mEditor.putString(EMAIL, mEmail);
		mEditor.putString(PIC, mPicUrl);
		mEditor.putString(GENDER, mGender);
		mEditor.commit();
	}
	
	public String getUserName(){
		return mUserName;
	}
	
	public String getEmail(){
		return mEmail;
	}
	
	public String getPicUrl(){
		return mPicUrl;
	}
	
	public String getGender(){
		return mGender;
	}
	
	public interface ProfileUpdateListener{
		public void OnProfileUpdated();
	}

}
