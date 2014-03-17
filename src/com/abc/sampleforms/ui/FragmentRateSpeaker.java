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

public class FragmentRateSpeaker extends Fragment {
	onSpeakerRatedListener mSpeakerRatedListener;

	public static final int mSpeakerHold = 1;
	public static final int mSpeakerAllow = 2;

	private static final String URI_IMAGE_SPEAKER = "drawable://"
			+ R.drawable.androidspeaker;
	private ProgressBar bar;
	private ImageView imageView;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mSpeakerRatedListener = (onSpeakerRatedListener) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_rate, container, false);
		((TextView) view.findViewById(R.id.textRate))
				.setText("Rate the Speaker");

		((TextView) view.findViewById(R.id.textImageTitle))
				.setText("Mr. Android Guy");
		RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				mSpeakerRatedListener.onSpeakerRated(rating);
			}
		});
		imageView = (ImageView) view.findViewById(R.id.imageView1);
		bar = (ProgressBar) view.findViewById(R.id.progressBar1);
		ImageLoader loader = ImageLoader.getInstance();
		loader.loadImage(URI_IMAGE_SPEAKER,
				((FeedbackActivity) getActivity()).getImageOptions(),
				new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String imageUri, View view) {
						// TODO Auto-generated method stub
						Log.e("Rate Speaker", "loading started");
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
						Log.e("Rate Speaker", "loading complete");
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

	public interface onSpeakerRatedListener {
		public void onSpeakerRated(float rating);
	}
}
