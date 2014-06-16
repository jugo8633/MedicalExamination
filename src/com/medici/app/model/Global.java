package com.medici.app.model;

import com.medici.app.R;
import com.medici.app.view.ShapButton;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class Global
{
	public static Activity	theActivity	= null;
	public static boolean	mbLogined	= false;
	private static Dialog	dialog		= null;

	public static String str(int nResId)
	{
		if (null != theActivity)
		{
			return theActivity.getString(nResId);
		}
		return null;
	}

	public static void showDidlog(final Activity activity, final Handler handler, String strTitle, String strMessage)
	{
		dialog = new Dialog(activity, R.style.Translucent_NoTitle);
		dialog.setContentView(R.layout.dialog);

		TextView message = (TextView) dialog.findViewById(R.id.textViewDialogMessage);
		message.setText(strMessage);
		// 由程式設定 Dialog 視窗外的明暗程度, 亮度從 0f 到 1f
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.dimAmount = 0.2f;
		dialog.getWindow().setAttributes(lp);

		dialog.show();
		ShapButton okbtn = (ShapButton) dialog.findViewById(R.id.shapButtonDialogOK);
		okbtn.setOnButtonClickedListener(new ShapButton.OnButtonClickedListener()
		{
			@Override
			public void OnButtonClicked(boolean bSelected)
			{
				if (bSelected)
				{
					dialog.dismiss();
					EventHandler.notify(handler, EventMessage.MSG_CLOSE_MESSAGE_DIALOG, 0, 0, null);
				}
			}
		});
	}

	public static void closeDialog()
	{
		if (null != dialog)
		{
			dialog.dismiss();
			dialog = null;
		}
	}
}
