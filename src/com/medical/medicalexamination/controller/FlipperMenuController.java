package com.medical.medicalexamination.controller;

import com.medical.medicalexamination.R;
import com.medical.medicalexamination.model.EventMessage;
import com.medical.medicalexamination.model.Logs;
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

	private FlipperView					flipperView					= null;
	private LoginController				loginController				= null;
	private EyeTestController			eyeTestController			= null;
	private HearTestController			hearTestController			= null;
	private Comprehension1Controller	comprehension1Controller	= null;
	private Comprehension2Controller	comprehension2Controller	= null;
	private Comprehension3Controller	comprehension3Controller	= null;

	/**
	 * list android layout of medical test
	 */
	private final int[]					listTestLayout				= { R.layout.login, R.layout.history,
			R.layout.calendar, R.layout.eye_test, R.layout.hearing_test, R.layout.comprehension1,
			R.layout.comprehension2, R.layout.comprehension3		};

	/**
	 * list main layout id that in medical test layout, for ignore click
	 */
	private final int[]					listTestMainLayoutId		= { R.id.history_main_layout,
			R.id.calendar_main_layout, R.id.eyetest_main_layout, R.id.hear_test_main_layout,
			R.id.RelativeLayoutComprehension1Main, R.id.RelativeLayoutComprehension2Main,
			R.id.RelativeLayoutComprehension3Main					};

	public FlipperMenuController(Activity activity, Handler handler)
	{
		super();
		init(activity, handler);
	}

	@Override
	protected void finalize() throws Throwable
	{
		loginController = null;
		eyeTestController = null;
		hearTestController = null;
		comprehension1Controller = null;
		comprehension2Controller = null;
		comprehension3Controller = null;
		super.finalize();
	}

	private void init(Activity activity, Handler handler)
	{
		flipperView = (FlipperView) activity.findViewById(R.id.fliper_menu_option);
		if (null == flipperView)
		{
			Logs.showTrace("Flipper view is invalid");
			return;
		}
		flipperView.setNotifyHandler(handler);

		for (int i = 0; i < listTestLayout.length; ++i)
		{
			flipperView.addChild(listTestLayout[i]);
		}

		loginController = new LoginController(activity, handler);
		eyeTestController = new EyeTestController(activity, selfHandler);
		hearTestController = new HearTestController(activity, selfHandler);
		comprehension1Controller = new Comprehension1Controller(activity, selfHandler);
		comprehension2Controller = new Comprehension2Controller(activity, selfHandler);
		comprehension3Controller = new Comprehension3Controller(activity, selfHandler);
		historyHandler(activity);

		for (int j = 0; j < listTestMainLayoutId.length; ++j)
		{
			flipperView.findViewById(listTestMainLayoutId[j]).setOnTouchListener(mainLayoutTouch);
		}
	}

	private int getLayoutIndex(int nLayoutResId)
	{
		int nIndex = 0;
		for (nIndex = 0; nIndex < listTestLayout.length; ++nIndex)
		{
			if (listTestLayout[nIndex] == nLayoutResId)
			{
				break;
			}
		}
		return (++nIndex);
	}

	public void setHideEnable(boolean bEnable)
	{
		flipperView.setHideEnable(bEnable);
	}

	public void showLogin()
	{
		loginController.init();

		flipperView.showView(getLayoutIndex(R.layout.login));
	}

	public void showHistory()
	{
		flipperView.showView(getLayoutIndex(R.layout.history));
	}

	public void showCalendar()
	{
		flipperView.showView(getLayoutIndex(R.layout.calendar));
	}

	public void showEyeTest()
	{
		eyeTestController.init();
		flipperView.showView(getLayoutIndex(R.layout.eye_test));
	}

	public void showHearTest()
	{
		hearTestController.init();
		flipperView.showView(getLayoutIndex(R.layout.hearing_test));
	}

	public void showComprehension1()
	{
		comprehension1Controller.init();
		flipperView.showView(getLayoutIndex(R.layout.comprehension1));
	}

	public void showComprehension2()
	{
		comprehension2Controller.init();
		flipperView.showView(getLayoutIndex(R.layout.comprehension2));
	}

	public void showComprehension3()
	{
		comprehension3Controller.init();
		flipperView.showView(getLayoutIndex(R.layout.comprehension3));
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
