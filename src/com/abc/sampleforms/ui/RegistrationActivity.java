package com.abc.sampleforms.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.abc.sampleforms.R;
import com.abc.sampleforms.adapter.SmartphoneAdapter;
import com.abc.sampleforms.net.HttpRequest;

public class RegistrationActivity extends ActionBarActivity {

	private static final String EMPTY = "";
	private static final String[] SMARTPHONES = { "Select OS", "Android",
			"iOS", "Windows Phone", "Blackberry (RIM)", "Symbian" };
	private String[] cities;
	private int[] imgResID;
	private Alerter alerter;

	private boolean doubleBackPressedAlready = false;

	public static final int DIALOG_CODE_RESET = 1;
	public static final int DIALOG_CODE_INCOMPLETE = 2;
	public static final int DIALOG_CODE_SUBMIT = 3;
	public static final int DIALOG_CODE_SUBMITTING = 4;
	public static final int DIALOG_CODE_SUBMITTED = 5;
	public static final int DIALOG_CODE_RETRY = 6;

	private SharedPreferences preferences;
	private Editor editor;

	public static final String LOCALHOST = "http://10.0.2.1";

	public static final String KEY_FNAME = "FNAME";
	public static final String KEY_LNAME = "LNAME";
	public static final String KEY_EMAIL = "EMAIL";
	public static final String KEY_THANDLE = "THANDLE";
	public static final String KEY_CITY = "CITY";
	public static final String KEY_STU = "STU";
	public static final String KEY_PRO = "PRO";
	public static final String KEY_COLNAME = "COLNAME";
	public static final String KEY_EDUCATION = "EDUCATION";
	public static final String KEY_COMPNAME = "COMPNAME";
	public static final String KEY_PROF = "PROF";
	public static final String KEY_GEN = "GEN";
	public static final String KEY_SPHONE = "SPHONE";

	public static final String SUCCESS = "success";

	private EditText mFirstName;
	private EditText mLastName;
	private EditText mEmail;
	private EditText mTwitterHandle;
	private AutoCompleteTextView mCity;
	private EditText mCollegeName;
	private EditText mEducation;
	private EditText mCompanyName;
	private EditText mProfession;

	private Button mSubmit;
	private Button mReset;

	private RadioButton mGenderMale;
	private RadioButton mGenderFemale;

	// private RadioButton mSmartphoneYes;
	private RadioButton mSmartphoneNo;

	// private RadioButton mStudent;
	// private RadioButton mProfessional;

	private RadioGroup mGender, mSmartphone, mPerson;

	private RelativeLayout mLayoutStudent, mLayoutProfessional;

	private Spinner mSmartphoneSpinner;

	private EditText mTextFields[];

	private SmartphoneAdapter mSmartphoneAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		ActionBar actionBar = getSupportActionBar();
		// actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		getAllReferences();

		preferences = getSharedPreferences("form_prefs", MODE_PRIVATE);
		editor = preferences.edit();

		imgResID = new int[] { R.drawable.silhouette, R.drawable.logoandroid,
				R.drawable.logoios, R.drawable.logowp, R.drawable.logobb,
				R.drawable.logosymbian };

		mSmartphone.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.radio_SmartphoneYes:
					mSmartphoneAdapter = new SmartphoneAdapter(
							getApplicationContext(), SMARTPHONES, imgResID);
					mSmartphoneSpinner.setAdapter(mSmartphoneAdapter);
					mSmartphoneSpinner.setVisibility(View.VISIBLE);
					break;
				case R.id.radio_SmartphoneNo:
					mSmartphoneSpinner.setVisibility(View.GONE);
					break;
				}
			}
		});

		mPerson.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.radio_Student:
					mLayoutStudent.setVisibility(View.VISIBLE);
					mLayoutProfessional.setVisibility(View.GONE);
					mCollegeName.requestFocus();
					break;
				case R.id.radio_Professional:
					mLayoutProfessional.setVisibility(View.VISIBLE);
					mLayoutStudent.setVisibility(View.GONE);
					mCompanyName.requestFocus();
					break;
				}
			}
		});

		mSubmit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!AllFilled()) {
					alerter = Alerter
							.newInstance(
									android.R.drawable.ic_dialog_alert,
									"Fields Empty",
									"Some fields are empty, please ensure that they are filled properly.",
									DIALOG_CODE_INCOMPLETE);
					alerter.show(getSupportFragmentManager(),
							"EmptyFieldDialog");
				} else {
					alerter = Alerter.newInstance(
							android.R.drawable.ic_dialog_info, "Confirm",
							"Are you sure you want to register your details?",
							DIALOG_CODE_SUBMIT);
					alerter.show(getSupportFragmentManager(),
							"CompleteFormDialog");
				}
			}
		});

		mReset.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alerter = Alerter.newInstance(
						android.R.drawable.ic_dialog_alert, "Reset",
						"Do you want to reset all fields?", DIALOG_CODE_RESET);
				alerter.show(getSupportFragmentManager(), "ResetFormDialog");
			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (formChanged()) {
			if (doubleBackPressedAlready) {
				super.onBackPressed();
				return;
			}
			doubleBackPressedAlready = true;
			Toast.makeText(getBaseContext(),
					"Press back again to discard the form", Toast.LENGTH_SHORT)
					.show();
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					doubleBackPressedAlready = false;
				}
			}, 2000);
		} else {
			super.onBackPressed();
		}
	}

	@SuppressWarnings("unchecked")
	public void doPositiveSubmitClick() {
		saveAllFormValues();
		SendFormTask task = new SendFormTask();
		List<NameValuePair> params = getAllFormValues();
		task.execute(params);
		// Intent intent = new Intent(getApplicationContext(), SendForm.class);
		// startService(intent);
	}

	public void doPositiveResetClick() {
		clearAllFields();
	}

	private void getAllReferences() {

		mFirstName = (EditText) findViewById(R.id.editText_FirstName);
		mLastName = (EditText) findViewById(R.id.editText_LastName);
		mEmail = (EditText) findViewById(R.id.editText_Email);
		mTwitterHandle = (EditText) findViewById(R.id.editText_TwitterHandle);
		mCollegeName = (EditText) findViewById(R.id.editText_CollegeName);
		mEducation = (EditText) findViewById(R.id.editText_Education);
		mCompanyName = (EditText) findViewById(R.id.editText_Company);
		mProfession = (EditText) findViewById(R.id.editText_Profession);

		mCity = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextView_City);
		cities = getResources().getStringArray(R.array.cities);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.simple_autocomplete_item,
				cities);
		mCity.setThreshold(2);
		mCity.setAdapter(arrayAdapter);

		mTextFields = new EditText[] { mFirstName, mLastName, mEmail,
				mTwitterHandle, mCity, mCollegeName, mEducation, mCompanyName,
				mProfession };

		mSubmit = (Button) findViewById(R.id.button_Submit);
		mReset = (Button) findViewById(R.id.button_Reset);

		mGenderMale = (RadioButton) findViewById(R.id.radio_GenderMale);
		mGenderFemale = (RadioButton) findViewById(R.id.radio_GenderFemale);
		// mSmartphoneYes = (RadioButton)
		// findViewById(R.id.radio_SmartphoneYes);
		mSmartphoneNo = (RadioButton) findViewById(R.id.radio_SmartphoneNo);
		// mStudent = (RadioButton) findViewById(R.id.radio_Student);
		// mProfessional = (RadioButton) findViewById(R.id.radio_Professional);

		mGender = (RadioGroup) findViewById(R.id.radioGroup_Gender);
		mSmartphone = (RadioGroup) findViewById(R.id.radioGroup_Smartphone);
		mPerson = (RadioGroup) findViewById(R.id.radioGroup_Person);

		mLayoutStudent = (RelativeLayout) findViewById(R.id.layout_Student);
		mLayoutProfessional = (RelativeLayout) findViewById(R.id.layout_Professional);

		mSmartphoneSpinner = (Spinner) findViewById(R.id.spinner_Smartphone);
	}

	private void saveAllFormValues() {
		editor.putString(KEY_FNAME, getTrimmedString(mFirstName));
		editor.putString(KEY_LNAME, getTrimmedString(mLastName));
		editor.putString(KEY_EMAIL, getTrimmedString(mEmail));
		editor.putString(KEY_THANDLE, getTrimmedString(mTwitterHandle));
		editor.putString(KEY_CITY, getTrimmedString(mCity));
		switch (mGender.getCheckedRadioButtonId()) {
		case R.id.radio_GenderMale:
			editor.putString(KEY_GEN, mGenderMale.getText().toString());
			break;
		case R.id.radio_GenderFemale:
			editor.putString(KEY_GEN, mGenderFemale.getText().toString());
			break;
		}
		switch (mSmartphone.getCheckedRadioButtonId()) {
		case R.id.radio_SmartphoneNo:
			editor.putString(KEY_SPHONE, mSmartphoneNo.getText().toString());
			break;
		case R.id.radio_SmartphoneYes:
			editor.putString(KEY_SPHONE,
					SMARTPHONES[mSmartphoneSpinner.getSelectedItemPosition()]);
			break;
		}
		switch (mPerson.getCheckedRadioButtonId()) {
		case R.id.radio_Student:
			editor.putString(KEY_STU, "1");
			editor.putString(KEY_COLNAME, getTrimmedString(mCollegeName));
			editor.putString(KEY_EDUCATION, getTrimmedString(mEducation));
			editor.putString(KEY_PRO, EMPTY);
			editor.putString(KEY_COMPNAME, EMPTY);
			editor.putString(KEY_PROF, EMPTY);
			break;
		case R.id.radio_Professional:
			editor.putString(KEY_PRO, "1");
			editor.putString(KEY_COMPNAME, getTrimmedString(mCompanyName));
			editor.putString(KEY_PROF, getTrimmedString(mProfession));
			editor.putString(KEY_STU, EMPTY);
			editor.putString(KEY_COLNAME, EMPTY);
			editor.putString(KEY_EDUCATION, EMPTY);
			break;
		}
		editor.commit();
	}

	private List<NameValuePair> getAllFormValues() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(KEY_FNAME,
				getTrimmedString(mFirstName)));
		params.add(new BasicNameValuePair(KEY_LNAME,
				getTrimmedString(mLastName)));
		params.add(new BasicNameValuePair(KEY_EMAIL, getTrimmedString(mEmail)
				.toLowerCase(Locale.US)));
		params.add(new BasicNameValuePair(KEY_THANDLE,
				getTrimmedString(mTwitterHandle)));
		params.add(new BasicNameValuePair(KEY_CITY, getTrimmedString(mCity)));
		switch (mGender.getCheckedRadioButtonId()) {
		case R.id.radio_GenderMale:
			params.add(new BasicNameValuePair(KEY_GEN, mGenderMale.getText()
					.toString()));
			break;
		case R.id.radio_GenderFemale:
			params.add(new BasicNameValuePair(KEY_GEN, mGenderFemale.getText()
					.toString()));
			break;
		}
		switch (mSmartphone.getCheckedRadioButtonId()) {
		case R.id.radio_SmartphoneNo:
			params.add(new BasicNameValuePair(KEY_SPHONE, mSmartphoneNo
					.getText().toString()));
			break;
		case R.id.radio_SmartphoneYes:
			params.add(new BasicNameValuePair(KEY_SPHONE,
					SMARTPHONES[mSmartphoneSpinner.getSelectedItemPosition()]));
		}
		switch (mPerson.getCheckedRadioButtonId()) {
		case R.id.radio_Student:
			params.add(new BasicNameValuePair(KEY_STU, "1"));
			params.add(new BasicNameValuePair(KEY_COLNAME,
					getTrimmedString(mCollegeName)));
			params.add(new BasicNameValuePair(KEY_EDUCATION,
					getTrimmedString(mEducation)));
			break;
		case R.id.radio_Professional:
			params.add(new BasicNameValuePair(KEY_PRO, "1"));
			params.add(new BasicNameValuePair(KEY_COMPNAME,
					getTrimmedString(mCompanyName)));
			params.add(new BasicNameValuePair(KEY_PROF,
					getTrimmedString(mProfession)));
			break;
		}
		return params;
	}

	private String getTrimmedString(EditText e) {
		// TODO Auto-generated method stub
		return e.getText().toString().trim();
	}

	private void clearAllFields() {

		for (EditText e : mTextFields) {
			e.setText(EMPTY);
		}
		mGender.clearCheck();
		mSmartphone.clearCheck();
		mPerson.clearCheck();

		mLayoutStudent.setVisibility(View.GONE);
		mLayoutProfessional.setVisibility(View.GONE);

		mSmartphoneSpinner.setVisibility(View.GONE);

	}

	private boolean AllFilled() {
		int len = 0;
		int id = 0;
		for (EditText e : mTextFields) {
			id = e.getId();
			switch (id) {
			case R.id.editText_TwitterHandle:
			case R.id.editText_CollegeName:
			case R.id.editText_Education:
			case R.id.editText_Company:
			case R.id.editText_Profession:
				break;
			default:
				len = getTrimmedLength(e);
				if (len == 0)
					return false;
				break;
			}

		}

		if (mLayoutProfessional.isShown()) {
			len = getTrimmedLength(mProfession);
			if (len == 0)
				return false;
			len = getTrimmedLength(mCompanyName);
			if (len == 0)
				return false;
		} else if (mLayoutStudent.isShown()) {
			len = getTrimmedLength(mCollegeName);
			if (len == 0)
				return false;
			len = getTrimmedLength(mEducation);
			if (len == 0)
				return false;
		}

		if (mGender.getCheckedRadioButtonId() == -1)
			return false;
		if (mSmartphone.getCheckedRadioButtonId() == -1)
			return false;
		if (mPerson.getCheckedRadioButtonId() == -1)
			return false;

		if (mSmartphoneSpinner.isShown()
				&& mSmartphoneSpinner.getSelectedItemPosition() == 0)
			return false;

		return true;
	}

	private boolean formChanged() {

		int len = 0;
		int id = 0;
		for (EditText e : mTextFields) {
			id = e.getId();
			switch (id) {
			case R.id.editText_CollegeName:
			case R.id.editText_Education:
			case R.id.editText_Company:
			case R.id.editText_Profession:
				break;
			default:
				len = getTrimmedLength(e);
				if (len != 0)
					return true;
				break;
			}

		}

		if (mLayoutProfessional.isShown()) {
			return true;
		} else if (mLayoutStudent.isShown()) {
			return true;
		}

		if (mGender.getCheckedRadioButtonId() != -1)
			return true;
		if (mSmartphone.getCheckedRadioButtonId() != -1)
			return true;
		if (mPerson.getCheckedRadioButtonId() != -1)
			return true;

		return false;

	}

	private int getTrimmedLength(EditText e) {
		return TextUtils.getTrimmedLength(e.getText());
	}

	private class SendFormTask extends
			AsyncTask<List<NameValuePair>, Void, String> {
		// ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			alerter = Alerter.newInstance(android.R.drawable.ic_dialog_info,
					"Submitting", "Please wait...", DIALOG_CODE_SUBMITTING);
			alerter.show(getSupportFragmentManager(), "SubmittingFormDialog");
		}

		@Override
		protected String doInBackground(List<NameValuePair>... params) {
			// TODO Auto-generated method stub
			HttpRequest httpRequest = new HttpRequest();
			InputStream is = httpRequest
					.makeHttpRequest(RegistrationActivity.LOCALHOST
							+ "/abc/save.php", HttpRequest.POST_METHOD,
							params[0], getApplicationContext());
			StringBuilder reply = new StringBuilder();
			if (is != null) {
				try {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(is));
					String rline;
					while ((rline = br.readLine()) != null) {
						reply.append(rline);
						// reply+=rline;
					}
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return reply.toString();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (alerter.isResumed()) {
				alerter.dismiss();

				if (result.equals(HttpRequest.RESULT_SUBMIT_SUCCESS)) {
					alerter = Alerter.newInstance(
							android.R.drawable.ic_dialog_info, "Success",
							EMPTY, DIALOG_CODE_SUBMITTED);
					alerter.show(getSupportFragmentManager(),
							"SubmitSuccessDialog");

				} else if (result.equals(HttpRequest.RESULT_NETWORK_FAIL)) {
					Toast.makeText(getBaseContext(),
							"Network error! Please check you connection",
							Toast.LENGTH_LONG).show();
				} else {
					alerter = Alerter.newInstance(
							android.R.drawable.ic_dialog_alert, "Failed",
							"Submit was unsuccessful. Please try again.",
							DIALOG_CODE_RETRY);
					alerter.show(getSupportFragmentManager(),
							"RetrySubmitDialog");
				}
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		if (alerter != null && alerter.isResumed())
			alerter.getDialog().dismiss();
		super.onSaveInstanceState(outState);
	}
}
