package com.medici.app.model;

import com.medici.app.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class Global
{
	public static Activity	theActivity	= null;
	public static boolean	mbLogined	= false;

	public static String str(int nResId)
	{
		if (null != theActivity)
		{
			return theActivity.getString(nResId);
		}
		return null;
	}

	public static void showDidlog(final Activity activity, String strTitle, String strMessage)
	{
		AlertDialog.Builder builder = new Builder(activity);
		builder.setTitle(strTitle);
		builder.setMessage(strMessage);
		builder.setPositiveButton(activity.getString(R.string.ok), new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		builder.create().show();

	}
}
