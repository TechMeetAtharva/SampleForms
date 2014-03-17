package com.abc.sampleforms.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.abc.sampleforms.R;
import com.abc.sampleforms.adapter.FeedbackPagerAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class FeedbackActivity extends ActionBarActivity implements
		FragmentRateSpeaker.onSpeakerRatedListener,
		FragmentRateTopic.onTopicRatedListener {

	private Button mPrev, mNext;

	public static int mProgress = 1;

	private FragmentExpect mExpectFragment;
	private String mExpectation;

	private FragmentQuestionCheck mQuestionCheckFragment;
	private SparseBooleanArray checkedAnswers;

	private FragmentQuestionRadio mQuestionRadioFragment;
	private int checkedRadio = -1;

	private FeedbackPagerAdapter collection;
	private ViewPager pager;

	private float mSpeakerRating, mTopicRating;
	private boolean mIsSpeakerFilled, mIsTopicFilled, mIsExpectFilled;

	private DisplayImageOptions imageOptions;

	private String mTagCheckFragment, mTagExpectFragment, mTagRadioFragment;

	public void putTagExpectFragment(String tagExpectFragment) {
		mTagExpectFragment = tagExpectFragment;
	}

	public String getTagExpectFragment() {
		return mTagExpectFragment;
	}

	public void putTagRadioFragment(String tagRadioFragment) {
		mTagRadioFragment = tagRadioFragment;
	}

	public String getTagRadioFragment() {
		return mTagRadioFragment;
	}

	public void putTagCheckFragment(String tagCheckFragment) {
		mTagCheckFragment = tagCheckFragment;
	}

	public String getTagCheckFragment() {
		return mTagCheckFragment;
	}

	public DisplayImageOptions getImageOptions() {
		return imageOptions;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		pager = (ViewPager) findViewById(R.id.pager);
		mPrev = (Button) findViewById(R.id.buttonPrevious);
		mNext = (Button) findViewById(R.id.buttonNext);
		pager.setOffscreenPageLimit(6);
		collection = new FeedbackPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(collection);

		imageOptions = new DisplayImageOptions.Builder()
				.delayBeforeLoading(2000).cacheInMemory(true).cacheOnDisc(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		mNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pager.setCurrentItem(pager.getCurrentItem() + 1, true);
			}
		});

		mPrev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pager.setCurrentItem(pager.getCurrentItem() - 1, true);
			}
		});

		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				checkLeftButtonAbility();
				checkRightButtonAbility();
				switch (arg0) {

				case 0:
					// mPrev.setEnabled(false);
					break;
				case 1:
					if (mIsTopicFilled)
						setWizardProgress(FragmentRateTopic.mTopicAllow);
					// mPrev.setEnabled(true);
					break;
				case 2:
					mQuestionRadioFragment = (FragmentQuestionRadio) getSupportFragmentManager()
							.findFragmentByTag(getTagRadioFragment());
					if (checkedRadio != -1)
						setWizardProgress(FragmentQuestionRadio.mRadioAllow);
					break;
				case 3:
					// checkedRadio =
					// mQuestionRadioFragment.getSelectedPosition();
					mQuestionCheckFragment = (FragmentQuestionCheck) getSupportFragmentManager()
							.findFragmentByTag(mTagCheckFragment);
					break;
				case 4:
					checkedAnswers = mQuestionCheckFragment
							.getCheckedPositions();
					mExpectFragment = (FragmentExpect) getSupportFragmentManager()
							.findFragmentByTag(getTagExpectFragment());
					mNext.setText("Next");
					break;
				case 5:
					mIsExpectFilled = mExpectFragment.isExpectationFilled();
					if (mIsExpectFilled) {
						Log.e("MainActivity", "Getting Expectation");
						mExpectation = mExpectFragment.getExpectation();
						((TextView) pager.findViewById(R.id.textMessage))
								.setText(mExpectation);
					}
					mNext.setText("Submit");
					break;

				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		// final Timer timer = new Timer();
		// final Handler handler = new Handler();
		//
		// timer.scheduleAtFixedRate(new TimerTask() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// handler.post(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// pager.arrowScroll(View.FOCUS_RIGHT);
		// if (pager.getCurrentItem() == 99)
		// timer.cancel();
		// }
		// });
		//
		// }
		// }, 1000, 1000);

	}

	private void checkLeftButtonAbility() {
		if (pager.getCurrentItem() == 0) {
			mPrev.setEnabled(false);
		} else {
			mPrev.setEnabled(true);
		}
		return;
	}

	private void checkRightButtonAbility() {
		if (pager.getCurrentItem() == collection.getCount() - 1) {
			mNext.setEnabled(false);
		} else {
			mNext.setEnabled(true);
		}
	}

	public void setWizardProgress(int progress) {
		mProgress = progress;
		collection.notifyDataSetChanged();
		checkLeftButtonAbility();
		checkRightButtonAbility();
	}

	public float getSpeakerRating() {
		return mSpeakerRating;
	}

	public float getTopicRating() {
		return mTopicRating;
	}

	@Override
	public void onSpeakerRated(float rating) {
		// TODO Auto-generated method stub
		if (rating != 0.0) {
			mSpeakerRating = rating;
			mIsSpeakerFilled = true;
			setWizardProgress(FragmentRateSpeaker.mSpeakerAllow);
			pager.setCurrentItem(pager.getCurrentItem() + 1, true);
		} else {
			mIsSpeakerFilled = false;
			setWizardProgress(FragmentRateSpeaker.mSpeakerHold);
		}
	}

	@Override
	public void onTopicRated(float rating) {
		// TODO Auto-generated method stub
		if (rating != 0.0) {
			mTopicRating = rating;
			mIsTopicFilled = true;
			setWizardProgress(FragmentRateTopic.mTopicAllow);
			pager.setCurrentItem(pager.getCurrentItem() + 1, true);
		} else {
			mIsTopicFilled = false;
			setWizardProgress(FragmentRateTopic.mTopicHold);
		}
	}

	public void setCheckedRadio(int position) {
		checkedRadio = position;
	}

	// @Override
	// public boolean isAllFilled() {
	// // TODO Auto-generated method stub
	// return (mIsSpeakerFilled && mIsTopicFilled);
	// }

}
