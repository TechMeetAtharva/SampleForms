package com.abc.sampleforms.ui;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.abc.sampleforms.R;

public class HomeActivity extends ActionBarActivity {
	private Button mRegistrationButton, mFeedbackButton;

	public static final int DIALOG_CODE_ABOUT = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		mRegistrationButton = (Button) findViewById(R.id.button_Registration);
		mFeedbackButton = (Button) findViewById(R.id.button_Feedback);

		mRegistrationButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						RegistrationActivity.class));
			}
		});
		mFeedbackButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						FeedbackActivity.class));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_about:
			String version = null;
			try {
				version = getPackageManager().getPackageInfo(getPackageName(),
						0).versionName;
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (version != null) {
				Alerter alerter = Alerter.newInstance(
						android.R.drawable.ic_dialog_info, "About",
						"Application version : " + version, DIALOG_CODE_ABOUT);
				alerter.show(getSupportFragmentManager(), null);
				return true;
			} else {
				return false;
			}
		default:
			return super.onOptionsItemSelected(item);
		}

	}
}
