package com.littlecheesecake.fblogin;

public class UserProfile {
	private String mUserName;
	private String mEmail;
	private String mPicUrl;
	private String mGender;
	
	public UserProfile() {
		// TODO Auto-generated constructor stub
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
	

}
