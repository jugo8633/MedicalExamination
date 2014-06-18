package com.medici.app.model;

import java.util.Timer;
import java.util.TimerTask;

import com.medici.app.R;
import com.medici.app.view.ShapButton;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public abstract class Global
{
	public static Activity	theActivity	= null;
	public static boolean	mbLogined	= false;
	private static Dialog	dialog		= null;
	public static int		mnUserId	= 2048;
	private static Timer	timer		= null;

	public static int getUserId()
	{
		return (++mnUserId);
	}

	public static String str(int nResId)
	{
		if (null != theActivity)
		{
			return theActivity.getString(nResId);
		}
		return null;
	}

	public static void showDidlog(final Activity activity, final Handler handler, String strTitle, String strMessage,
			final int nId)
	{
		dialog = new Dialog(activity, R.style.Translucent_NoTitle);
		dialog.setContentView(R.layout.dialog);

		TextView message = (TextView) dialog.findViewById(R.id.textViewDialogMessage);
		message.setText(strMessage);
		// 由程式設定 Dialog 視窗外的明暗程度, 亮度從 0f 到 1f
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.dimAmount = 0.2f;
		dialog.getWindow().setAttributes(lp);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
		{
			@Override
			public void onCancel(DialogInterface dialog)
			{
				EventHandler.notify(handler, EventMessage.MSG_CLOSE_MESSAGE_DIALOG, nId, 0, null);
			}
		});
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
					EventHandler.notify(handler, EventMessage.MSG_CLOSE_MESSAGE_DIALOG, nId, 0, null);
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

	public static boolean isShowDialog()
	{
		if (null != dialog)
		{
			return dialog.isShowing();
		}
		return false;
	}

	public static void hideIME(Activity activity)
	{
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
	}

	public static void timerStart(long delay, long period, final Handler notifyHandler, final int nId)
	{
		if (0 > delay)
		{
			return;
		}

		timerStop();

		if (0 < period)
		{
			timer = new Timer();
			timer.schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					EventHandler.notify(notifyHandler, EventMessage.MSG_ON_TIMER, nId, 0, null);
				}
			}, delay, period);
		}
		else
		{
			timer.schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					EventHandler.notify(notifyHandler, EventMessage.MSG_ON_TIMER, nId, 0, null);
				}
			}, delay);
		}
	}

	public static void timerStop()
	{
		if (null != timer)
		{
			timer.cancel();
		}
	}
}
