package com.littlecheesecake.fblogin;

import java.util.ArrayList;

public class UserProfile {
	private String mUserName;
	private String mEmail;
	private String mPicUrl;
	private String mGender;
	
	private ArrayList<ProfileUpdateListener> mListener;
	
	public UserProfile() {

	}
	
	public void registerUpdateListener(ProfileUpdateListener l){
		if(mListener == null)
			mListener = new ArrayList<ProfileUpdateListener>();
		
		mListener.add(l);
	}
	
	public void setProfile(String username, String email, String pic, String gender){
		
		if(username != null)
			this.mUserName = username;
		if(email != null)
			this.mEmail = email;
		if(pic != null)
			this.mPicUrl = pic;
		if(gender != null)
			this.mGender = gender;
		
		if(mListener != null){
			for(ProfileUpdateListener l : mListener){
				l.OnProfileUpdated();
			}
		}
		
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
