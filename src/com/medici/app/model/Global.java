package com.medici.app.model;

import android.app.Activity;

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
}
