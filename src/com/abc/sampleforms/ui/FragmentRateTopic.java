package com.abc.sampleforms.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.abc.sampleforms.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class FragmentRateTopic extends Fragment {

	onTopicRatedListener mTopicRatedListener;

	public static final int mTopicHold = 2;
	public static final int mTopicAllow = 3;

	private ImageView imageView;
	private ProgressBar bar;

	private static final String URI_IMAGE_TOPIC = "drawable://"
			+ R.drawable.androidtopic;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mTopicRatedListener = (onTopicRatedListener) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_rate, container, false);
		((TextView) view.findViewById(R.id.textRate)).setText("Rate the Topic");

		((TextView) view.findViewById(R.id.textImageTitle))
				.setText("Developing Mobile Applications For Android Handheld Systems");
		RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				mTopicRatedListener.onTopicRated(rating);
			}
		});

		imageView = (ImageView) view.findViewById(R.id.imageView1);
		bar = (ProgressBar) view.findViewById(R.id.progressBar1);

		ImageLoader loader = ImageLoader.getInstance();
		loader.loadImage(URI_IMAGE_TOPIC,
				((FeedbackActivity) getActivity()).getImageOptions(),
				new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String imageUri, View view) {
						// TODO Auto-generated method stub
						Log.e("Rate Topic", "loading started");
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						// TODO Auto-generated method stub
						Log.e("Rate Topic", "loading complete");
						bar.setVisibility(View.GONE);
						imageView.setImageBitmap(loadedImage);
					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {
						// TODO Auto-generated method stub

					}
				});
		return view;
	}

	public interface onTopicRatedListener {
		public void onTopicRated(float rating);
	}

}
