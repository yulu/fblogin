package com.littlecheesecake.fblogin;

import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class FBlogin {
	private Context mContext;
	private UserProfile mUserProfile;

	/**
	 * TODO: should not use singleton class!!!!! otherwise could not make onActivityResult work!!!
	 */
	/*private static FBlogin mFBlogin;
	
	public static FBlogin getInstance(Context c){
		if( mFBlogin == null){
			mFBlogin = new FBlogin(c);
		}
		
		return mFBlogin;
	}*/

	public FBlogin(Context c) {
		mContext = c;
	}
	
	public void onCreate(Bundle b){
  
		Session fbsession = Session.getActiveSession();
		if(fbsession == null){
			if(b != null){
				fbsession = Session.restoreSession(mContext, null, statusCallback, b);
			}
			if(fbsession == null){
				fbsession = new Session(mContext);
			}
			Session.setActiveSession(fbsession);
		}
	}
	
	public void onStart(){
		Session.getActiveSession().addCallback(statusCallback);
	}
	
	public void onLogout(UserProfile user){
		Session fbsession = Session.getActiveSession();
		
		fbsession.removeCallback(statusCallback);
		if( !fbsession.isClosed()){
			fbsession.closeAndClearTokenInformation();
		}
		
		if(user != null){
			user.closeSession();
		}
	}
	
	public void onActivityResult(int reqeustCode, int resultCode, Intent data){
		Session.getActiveSession().onActivityResult((Activity) mContext, reqeustCode, resultCode, data);
	}
	
	
	public void FBLoginAndLink(UserProfile userProfile){
		mUserProfile = userProfile;
		
		Session.OpenRequest request = new Session.OpenRequest((Activity) mContext);
		if(!isValidFacebookVersion()){
			request.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
		}
		request.setPermissions(Arrays.asList("email"));
		request.setCallback(statusCallback);
		
		Session fbsession = Session.getActiveSession();
		if(!fbsession.isOpened()&& !fbsession.isClosed()){
			fbsession.openForRead(request);
		}else{
			if(fbsession.isClosed()){
				fbsession = new Session(mContext);
				Session.setActiveSession(fbsession);
			}
			fbsession.openForRead(request);
		}
	}
	
	//check facebook version code
	private boolean isValidFacebookVersion(){
		boolean validity = false;
		try {
			int packageVersion = mContext.getPackageManager().getPackageInfo("com.facebook.katana",
				    PackageManager.GET_SIGNATURES).versionCode;			
			/**
			 * The minimum compatable version of facebook apk with facebook sdk is 3.5 (288749)
			 */
			if(packageVersion < 288749){
				validity = false;
			}else{				
				validity = true;
			}			
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return validity;		
	}

	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	
	private class SessionStatusCallback implements Session.StatusCallback{

		@SuppressWarnings("deprecation")
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			final Session fbsession = Session.getActiveSession();
			
			if(fbsession.isOpened() && fbsession.getPermissions().contains("email")){
				
				//final FBProfile fbProfile = new FBProfile();
				Request.executeMeRequestAsync(fbsession, new Request.GraphUserCallback() {
					
					@Override
					public void onCompleted(GraphUser user, Response response) {
						try{
							//update profile using the user data 
							if(mUserProfile != null){
								String picUrl = "https://graph.facebook.com/"+user.getId()+"/picture?type=large";
								mUserProfile.setProfile(user.getName(), (String)user.getProperty("email"), picUrl, (String)user.getProperty("gender"));
							}
						}catch(Exception e){
							e.printStackTrace();
						}
						
					}
				});
			}		
		}	
	}
}
