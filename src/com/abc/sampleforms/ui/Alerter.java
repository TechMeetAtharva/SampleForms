package com.abc.sampleforms.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import de.psdev.licensesdialog.LicensesDialogFragment;
import de.psdev.licensesdialog.R;

public class Alerter extends DialogFragment {
	public static final String DIALOG_KEY_ICON = "icon";
	public static final String DIALOG_KEY_TITLE = "title";
	public static final String DIALOG_KEY_MESSAGE = "message";
	public static final String DIALOG_KEY_CODE = "code";
	private static final String KEY_PREFERENCE_RESETFLAG = "resetFlag";

	public static final CharSequence BUTTON_OK = "OK";
	public static final CharSequence BUTTON_CANCEL = "Cancel";
	public static final CharSequence BUTTON_RETRY = "Retry";

	private SharedPreferences preferences;
	private Editor editor;

	private boolean[] prechecked;
	private boolean resetFlag;
	private boolean prevResetFlag;

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		Log.e("RegAct", "AutoPausedDialogFromFragment");
		super.onPause();
	}

	public static Alerter newInstance(int icon, String title, String message,
			int dialogCode) {
		Alerter alerter = new Alerter();
		Bundle args = new Bundle();
		args.putInt(DIALOG_KEY_ICON, icon);
		args.putString(DIALOG_KEY_TITLE, title);
		args.putString(DIALOG_KEY_MESSAGE, message);
		args.putInt(DIALOG_KEY_CODE, dialogCode);
		alerter.setArguments(args);
		return alerter;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		int icon = getArguments().getInt(DIALOG_KEY_ICON);
		String title = getArguments().getString(DIALOG_KEY_TITLE);
		String message = getArguments().getString(DIALOG_KEY_MESSAGE);
		int code = getArguments().getInt(DIALOG_KEY_CODE);

		switch (code) {
		case HomeActivity.DIALOG_CODE_ABOUT:
			return new AlertDialog.Builder(getActivity()).setIcon(icon)
					.setTitle(title).setMessage(message)
					.setPositiveButton("Licenses", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							LicensesDialogFragment dialogFragment = LicensesDialogFragment
									.newInstance(R.raw.notices, true);
							dialogFragment.show(getActivity()
									.getSupportFragmentManager(), null);
						}
					}).setNegativeButton(BUTTON_CANCEL, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					}).create();

		case RegistrationActivity.DIALOG_CODE_RESET:
			return new AlertDialog.Builder(getActivity()).setIcon(icon)
					.setTitle(title).setMessage(message)
					.setPositiveButton(BUTTON_OK, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							((RegistrationActivity) getActivity())
									.doPositiveResetClick();
						}
					}).setNegativeButton(BUTTON_CANCEL, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					}).create();

		case RegistrationActivity.DIALOG_CODE_INCOMPLETE:
			return new AlertDialog.Builder(getActivity())
					.setIcon(icon)
					.setTitle(title)
					.setMessage(message)
					.setNeutralButton(BUTTON_OK,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							}).create();

		case RegistrationActivity.DIALOG_CODE_SUBMIT:
			return new AlertDialog.Builder(getActivity())
					.setIcon(icon)
					.setTitle(title)
					.setMessage(message)
					.setPositiveButton(BUTTON_OK,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									((RegistrationActivity) getActivity())
											.doPositiveSubmitClick();
								}
							})
					.setNegativeButton(BUTTON_CANCEL, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					}).create();

		case RegistrationActivity.DIALOG_CODE_SUBMITTING:
			final ProgressDialog progressDialog = new ProgressDialog(
					getActivity());
			progressDialog.setMessage(message);
			progressDialog.setTitle(title);
			progressDialog.setIcon(icon);
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(false);
			progressDialog.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					// TODO Auto-generated method stub
					if (keyCode == KeyEvent.KEYCODE_BACK)
						return true;
					return false;
				}
			});
			return progressDialog;

		case RegistrationActivity.DIALOG_CODE_SUBMITTED:
			preferences = getActivity().getSharedPreferences("form_prefs",
					Context.MODE_PRIVATE);
			resetFlag = preferences.getBoolean(KEY_PREFERENCE_RESETFLAG, true);
			if (resetFlag)
				if (prechecked != null)
					prechecked[0] = true;
				else
					prechecked = new boolean[] { true };
			prevResetFlag = resetFlag;
			return new AlertDialog.Builder(getActivity())
					.setCancelable(false)
					.setIcon(icon)
					.setTitle(title)
					.setMultiChoiceItems(
							new CharSequence[] { "Reset all fields as well" },
							prechecked, new OnMultiChoiceClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which, boolean isChecked) {
									// TODO Auto-generated method stub
									resetFlag = isChecked;
								}
							})
					.setNeutralButton(BUTTON_OK,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									if (resetFlag)
										((RegistrationActivity) getActivity())
												.doPositiveResetClick();
									if (prevResetFlag != resetFlag) {
										editor = preferences.edit();
										editor.putBoolean(
												KEY_PREFERENCE_RESETFLAG,
												resetFlag);
										editor.commit();
									}
									dialog.dismiss();
								}
							}).create();

		case RegistrationActivity.DIALOG_CODE_RETRY:
			return new AlertDialog.Builder(getActivity())
					.setIcon(icon)
					.setTitle(title)
					.setMessage(message)
					.setPositiveButton(BUTTON_RETRY,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									((RegistrationActivity) getActivity())
											.doPositiveSubmitClick();
								}
							})
					.setNegativeButton(BUTTON_CANCEL, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					}).create();

		}
		return null;

	}
}
