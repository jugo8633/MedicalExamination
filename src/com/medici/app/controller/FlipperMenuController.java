package com.medici.app.controller;

import com.medici.app.R;
import com.medici.app.model.EventMessage;
import com.medici.app.model.Logs;
import com.medici.app.view.FlipperView;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class FlipperMenuController
{

	private FlipperView					flipperView					= null;
	private LoginController				loginController				= null;
	private VisualExamController			eyeTestController			= null;
	private AudioExamController			hearTestController			= null;
	private Comprehension1Controller	comprehension1Controller	= null;
	private Comprehension2Controller	comprehension2Controller	= null;
	private Comprehension3Controller	comprehension3Controller	= null;
	private Memory1Controller			memory1Controller			= null;
	private Memory2Controller			memory2Controller			= null;

	/**
	 * list android layout of medical test
	 */
	private final int[]					listTestLayout				= { R.layout.login, R.layout.history,
			R.layout.calendar, R.layout.test_eye, R.layout.test_hear, R.layout.comprehension1,
			R.layout.comprehension2, R.layout.comprehension3, R.layout.memory1, R.layout.memory2 };

	/**
	 * list main layout id that in medical test layout, for ignore click
	 */
	private final int[]					listTestMainLayoutId		= { R.id.login_main_layout,
			R.id.history_main_layout, R.id.calendar_main_layout, R.id.eyetest_main_layout, R.id.hear_test_main_layout,
			R.id.RelativeLayoutComprehension1Main, R.id.RelativeLayoutComprehension2Main,
			R.id.RelativeLayoutComprehension3Main, R.id.RelativeLayoutMemory1Main, R.id.RelativeLayoutMemory2Main };

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
		memory1Controller = null;
		memory2Controller = null;
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
		eyeTestController = new VisualExamController(activity, selfHandler);
		eyeTestController.setExamination(false);
		hearTestController = new AudioExamController(activity, selfHandler);
		hearTestController.setExamination(false);
		comprehension1Controller = new Comprehension1Controller(activity, selfHandler);
		comprehension2Controller = new Comprehension2Controller(activity, selfHandler);
		comprehension3Controller = new Comprehension3Controller(activity, selfHandler);
		memory1Controller = new Memory1Controller(activity, selfHandler);
		memory2Controller = new Memory2Controller(activity, selfHandler);
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
		flipperView.showView(getLayoutIndex(R.layout.test_eye));
	}

	public void showHearTest()
	{
		hearTestController.init();
		flipperView.showView(getLayoutIndex(R.layout.test_hear));
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

	public void showMemory1()
	{
		memory1Controller.init();
		flipperView.showView(getLayoutIndex(R.layout.memory1));
	}

	public void showMemory2()
	{
		memory2Controller.init();
		flipperView.showView(getLayoutIndex(R.layout.memory2));
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
