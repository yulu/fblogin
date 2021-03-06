package com.littlecheesecake.fblogin;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MyApplication extends Application{

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	public void onCreate(){
		super.onCreate();
		initImageLoader(getApplicationContext());
	}
	
	public static void initImageLoader(Context context){
		// This configuration tuning is custom. You can tune every option,
		// you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this) method
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
						.threadPriority(Thread.NORM_PRIORITY - 2)
						.discCacheFileNameGenerator(new Md5FileNameGenerator())
						.tasksProcessingOrder(QueueProcessingType.LIFO)
						.writeDebugLogs() //Remove for release app
						.build();
		// Initialize ImageLoader with configuration
		ImageLoader.getInstance().init(config);
	}

}
