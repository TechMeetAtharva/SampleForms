package com.abc.sampleforms.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abc.sampleforms.R;

public class FragmentMessage extends Fragment {

	// allFilledListener mFilledListener;
	//
	// @Override
	// public void onAttach(Activity activity) {
	// // TODO Auto-generated method stub
	// mFilledListener = (allFilledListener) activity;
	// super.onAttach(activity);
	// }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_message, container,
				false);
		((TextView) view.findViewById(R.id.textMessage))
				.setText("Have a good day!");
		((TextView) view.findViewById(R.id.textMessageLabel)).setSelected(true);
		// Button mSubmit = (Button) view.findViewById(R.id.buttonSubmit);
		// mSubmit.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// if (mFilledListener.isAllFilled()) {
		// Toast.makeText(getActivity(),
		// "Your details have been saved", Toast.LENGTH_SHORT)
		// .show();
		// Toast.makeText(
		// getActivity(),
		// "Speaker Rating : "
		// + ((MainActivity) getActivity())
		// .getSpeakerRating(),
		// Toast.LENGTH_SHORT).show();
		// Toast.makeText(
		// getActivity(),
		// "Topic Rating : "
		// + ((MainActivity) getActivity())
		// .getTopicRating(),
		// Toast.LENGTH_SHORT).show();
		// getActivity().finish();
		// } else {
		// Toast.makeText(getActivity(),
		// "The form is still incomplete", Toast.LENGTH_SHORT)
		// .show();
		// }
		// }
		// });
		return view;
	}

	// public interface allFilledListener {
	// public boolean isAllFilled();
	// }
}
