package com.medici.app.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

public abstract class NetworkHandler
{
	private static ConnectivityManager	connMgr;

	public static boolean isMobileNetworkAvailable(Context con)
	{
		if (null == connMgr)
		{
			connMgr = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		NetworkInfo wifiInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mobileInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if ((null != wifiInfo && wifiInfo.isAvailable()) || (null != mobileInfo && mobileInfo.isAvailable()))
		{
			return true;
		}

		return false;
	}

	/**
	 * 引導使用者去網路設定頁
	 * @param con
	 */
	public static void showConnectionNADialog(final Context con)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(con);
		builder.setTitle("Network not available").setIcon(android.R.drawable.ic_dialog_alert)
				.setMessage("Go and check Wireless & networks settings?").setPositiveButton("OK", new OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
						con.startActivity(intent);
					}

				}).setNegativeButton("Cancel", new OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}

				}).setCancelable(true);

		builder.create().show();
	}
}
