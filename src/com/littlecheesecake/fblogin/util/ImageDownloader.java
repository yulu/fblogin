package com.littlecheesecake.fblogin.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.littlecheesecake.fblogin.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ImageDownloader {
	private DisplayImageOptions options;
	protected ImageLoader mImageLoader = ImageLoader.getInstance();
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
		
		/**
		 * singleton class constructor
		 * @return
		 */
		private static ImageDownloader mImageDownloader;
		
		public static ImageDownloader getInstance(){
			if (mImageDownloader == null){
				mImageDownloader = new ImageDownloader();
			}
			
			return mImageDownloader;
		}
		
		private ImageDownloader() {
			options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.geek)
					.showImageForEmptyUri(R.drawable.geek)
					.showImageOnFail(R.drawable.geek)
					.cacheInMemory(true)
					.cacheOnDisc(false)
					.build();
		}
		
		public void displayImage(ImageView v, String url){
			
			
			mImageLoader.displayImage(url, v, options, animateFirstListener);
	
		}
	
		private class AnimateFirstDisplayListener extends SimpleImageLoadingListener{
			final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
			
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage){
				if(loadedImage != null){
					ImageView imageView = (ImageView) view;
					boolean firstDisplay = !displayedImages.contains(imageUri);
					if(firstDisplay){
						FadeInBitmapDisplayer.animate(imageView, 500);
						displayedImages.add(imageUri);
	
					}
				}
			}
		}


}
