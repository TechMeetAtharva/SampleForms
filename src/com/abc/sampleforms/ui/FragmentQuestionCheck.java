package com.abc.sampleforms.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.abc.sampleforms.R;

public class FragmentQuestionCheck extends Fragment {
	public String[] meats = { "Pepperoni", "Turkey", "Ham", "Pastrami",
			"Roast Beef", "Bologna", "Balsamic", "Oil & Vinegar",
			"Thousand Island", "Italian" };

	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		((FeedbackActivity) getActivity()).putTagCheckFragment(getTag());
		View view = inflater.inflate(R.layout.fragment_questions_checktype,
				container, false);
		// LinearLayout layout = (LinearLayout) view
		// .findViewById(R.id.linearLayoutCheckHolder);
		((TextView) view.findViewById(R.id.textQuestionCheck))
				.setText("What types of meats do you want?");
		listView = (ListView) view.findViewById(R.id.listViewCheckBox);
		listView.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_multiple_choice, meats));

		// CheckBox box;
		// int i = 0;
		// for (String string : meats) {
		// box = new CheckBox(getActivity());
		// box.setText(string);
		// layout.addView(box, i);
		// ++i;
		// }
		return view;
	}

	public SparseBooleanArray getCheckedPositions() {
		return listView.getCheckedItemPositions();
	}
}
