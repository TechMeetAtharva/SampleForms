package com.abc.sampleforms.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.abc.sampleforms.R;

public class FragmentQuestionRadio extends Fragment {

	public static final int mRadioHold = 3;
	public static final int mRadioAllow = 5;

	public String[] breads = { "White", "Wheat", "Rye", "Pretzel", "Ciabatta",
			"No Dressing", "Balsamic", "Oil & Vinegar", "Thousand Island",
			"Italian" };
	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		((FeedbackActivity) getActivity()).putTagRadioFragment(getTag());
		View view = inflater.inflate(R.layout.fragment_questions_radiotype,
				container, false);
		((TextView) view.findViewById(R.id.textQuestionRadio))
				.setText("What type of bread do you want?");
		listView = (ListView) view.findViewById(R.id.listViewRadios);
		listView.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_single_choice, breads));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				((FeedbackActivity) getActivity()).setCheckedRadio(arg2);
				((FeedbackActivity) getActivity())
						.setWizardProgress(FragmentQuestionRadio.mRadioAllow);
			}

		});
		// RadioGroup group = (RadioGroup) view
		// .findViewById(R.id.radioGroupQuestions);
		// RadioButton button;
		// int i = 0;
		// for (String string : breads) {
		// button = new RadioButton(getActivity());
		// RadioGroup.LayoutParams params = new LayoutParams(
		// LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		// button.setLayoutParams(params);
		// button.setText(string);
		// group.addView(button, i);
		// ++i;
		// }
		return view;
	}

	// public void setSelectedPosition(int position) {
	// listView.setItemChecked(position, true);
	// listView.setSelection(position);
	// }

	public int getSelectedPosition() {
		return listView.getCheckedItemPosition();
	}
}
