package com.medical.medicalexamination.controller;

import com.medical.medicalexamination.R;
import com.medical.medicalexamination.model.EventMessage;
import com.medical.medicalexamination.model.Logs;
import com.medical.medicalexamination.model.Type;
import com.medical.medicalexamination.view.FlipperView;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;


public class FlipperMenuController
{

	private FlipperView			flipperView			= null;
	private MenuID				menuId				= null;
	private RelativeLayout		historyMainLayout	= null;
	private RelativeLayout		calendarLayout		= null;
	private RelativeLayout		eyeTestLayout		= null;
	private Handler				notifyHandler		= null;
	private LoginController		loginController		= null;
	private EyeTestController	eyeTestController	= null;
	private HearTestController	hearTestController	= null;

	private class MenuID
	{
		public int	mnLoginId		= Type.INVALID;
		public int	mnHistoryId		= Type.INVALID;
		public int	mnCalendarId	= Type.INVALID;
		public int	mnEyeTestId		= Type.INVALID;
		public int	mnHearTestId	= Type.INVALID;
	}

	public FlipperMenuController(Activity activity)
	{
		super();
		init(activity);
	}

	@Override
	protected void finalize() throws Throwable
	{
		menuId = null;
		loginController = null;
		eyeTestController = null;
		hearTestController = null;
		super.finalize();
	}

	private void init(Activity activity)
	{
		menuId = new MenuID();

		flipperView = (FlipperView) activity.findViewById(R.id.fliper_menu_option);
		if (null == flipperView)
		{
			Logs.showTrace("Flipper view is invalid");
			return;
		}

		menuId.mnLoginId = flipperView.addChild(R.layout.login);
		menuId.mnHistoryId = flipperView.addChild(R.layout.history);
		menuId.mnCalendarId = flipperView.addChild(R.layout.calendar);
		menuId.mnEyeTestId = flipperView.addChild(R.layout.eye_test);
		menuId.mnHearTestId = flipperView.addChild(R.layout.hearing_test);

		loginController = new LoginController(activity);
		eyeTestController = new EyeTestController(activity);
		hearTestController = new HearTestController(activity);

		historyMainLayout = (RelativeLayout) flipperView.findViewById(R.id.history_main_layout);
		historyMainLayout.setOnTouchListener(mainLayoutTouch);
		historyHandler(activity);

		calendarLayout = (RelativeLayout) flipperView.findViewById(R.id.calendar_main_layout);
		calendarLayout.setOnTouchListener(mainLayoutTouch);

		eyeTestLayout = (RelativeLayout) flipperView.findViewById(R.id.eyetest_main_layout);
		eyeTestLayout.setOnTouchListener(mainLayoutTouch);

	}

	public void setNotifyHandler(Handler handler)
	{
		flipperView.setNotifyHandler(handler);
		notifyHandler = handler;
		loginController.setNotifyHandler(handler);
		eyeTestController.setNotifyHandler(selfHandler);
		hearTestController.setNotifyHandler(selfHandler);
	}

	public void setHideEnable(boolean bEnable)
	{
		flipperView.setHideEnable(bEnable);
	}

	public void showLogin()
	{
		flipperView.showView(menuId.mnLoginId);
	}

	public void showHistory()
	{
		flipperView.showView(menuId.mnHistoryId);
	}

	public void showCalendar()
	{
		flipperView.showView(menuId.mnCalendarId);
	}

	public void showEyeTest()
	{
		flipperView.showView(menuId.mnEyeTestId);
	}

	public void showHearTest()
	{
		flipperView.showView(menuId.mnHearTestId);
	}

	public void close()
	{
		flipperView.close();
	}

	private void historyHandler(final Activity activity)
	{

	}

	private OnTouchListener	mainLayoutTouch	= new OnTouchListener()
											{

												@Override
												public boolean onTouch(View v, MotionEvent event)
												{
													switch (event.getAction())
													{
													case MotionEvent.ACTION_DOWN:
														return true;
													}
													return false;
												}
											};

	private Handler			selfHandler		= new Handler()
											{

												@Override
												public void handleMessage(Message msg)
												{
													switch (msg.what)
													{
													case EventMessage.MSG_FLIPPER_CLOSE:
														close();
														break;
													}
												}

											};
}
