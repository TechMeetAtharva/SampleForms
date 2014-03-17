package com.abc.sampleforms.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.abc.sampleforms.R;

public class FragmentExpect extends Fragment {
	// implements MainActivity.checkExpectationFilled {

	public static final int mExpectHold = 5;
	public static final int mExpectAllow = 6;

	private EditText suggestion;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		((FeedbackActivity) getActivity()).putTagExpectFragment(getTag());
		View view = inflater.inflate(R.layout.fragment_suggest, container,
				false);
		((TextView) view.findViewById(R.id.textSuggest))
				.setText("What more do you expect?");
		suggestion = (EditText) view.findViewById(R.id.editTextSuggest);
		suggestion.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				Log.e("", "s : " + s);
				if (!s.toString().equals("")) {
					((FeedbackActivity) getActivity())
							.setWizardProgress(FragmentExpect.mExpectAllow);
				} else {
					((FeedbackActivity) getActivity())
							.setWizardProgress(FragmentExpect.mExpectHold);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		return view;
	}

	public boolean isExpectationFilled() {
		// TODO Auto-generated method stub
		return TextUtils.getTrimmedLength(suggestion.getText()) > 0;
	}

	public String getExpectation() {
		return suggestion.getText().toString();
	}
}
