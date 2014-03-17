package com.abc.sampleforms.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.abc.sampleforms.ui.FeedbackActivity;
import com.abc.sampleforms.ui.FragmentExpect;
import com.abc.sampleforms.ui.FragmentMessage;
import com.abc.sampleforms.ui.FragmentQuestionCheck;
import com.abc.sampleforms.ui.FragmentQuestionRadio;
import com.abc.sampleforms.ui.FragmentRateSpeaker;
import com.abc.sampleforms.ui.FragmentRateTopic;

public class FeedbackPagerAdapter extends FragmentPagerAdapter {

	public FeedbackPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int i) {
		// TODO Auto-generated method stub
		Fragment fragment = null;
		switch (i) {
		case 0:
			fragment = new FragmentRateSpeaker();
			break;
		case 1:
			fragment = new FragmentRateTopic();
			break;
		case 2:
			fragment = new FragmentQuestionRadio();
			break;
		case 3:
			fragment = new FragmentQuestionCheck();
			break;
		case 4:
			fragment = new FragmentExpect();
			break;
		case 5:
			fragment = new FragmentMessage();
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return FeedbackActivity.mProgress;
	}

}
