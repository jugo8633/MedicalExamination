package com.medici.app.controller;

import com.medici.app.R;
import com.medici.app.model.EventMessage;
import com.medici.app.model.Global;
import com.medici.app.view.TremorView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

public class TremorExamController extends TestAreaController
{
	private final int		TIMER_COUNTDOWN	= Global.getUserId();
	private SensorManager	sensorManager	= null;
	private TremorView		tremorView		= null;
	private TextView		tvTremorValue	= null;
	private int				mnExamResult	= 0;
	private int				mnCount			= 0;
	private final int		SENSOR_EDGE		= 2;

	public TremorExamController(Activity activity, Handler handler)
	{
		super(activity, handler);
		activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		initView(activity, handler);
		sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
	}

	private void initView(Activity activity, Handler handler)
	{
		ViewGroup parent = (ViewGroup) activity.findViewById(R.id.RelativeLayoutTremorMain);
		initHeader(parent);
		tremorView = (TremorView) parent.findViewById(R.id.tremorViewTremor);
		tremorView.setMaxValue(60);
		tvTremorValue = (TextView) parent.findViewById(R.id.textViewTremorValue);
	}

	private void showInfo(Handler notifyHandler)
	{
		Global.showDidlog(theActivity, notifyHandler, null, Global.str(R.string.holds_tablet),
				EventMessage.MSG_DIALOG_CLOSE_INFO);
	}

	public void setExamination(boolean bSet)
	{
		hideHeader(bSet);
		setExaminationMode(bSet);
	}

	private void setTremorValue(String strValue)
	{
		tvTremorValue.setText(strValue);
	}

	private void onDialog(int nId)
	{
		if (EventMessage.MSG_DIALOG_CLOSE_RESULT == nId)
		{
			close();
		}

		if (EventMessage.MSG_DIALOG_CLOSE_INFO == nId)
		{
			Global.timerStart(1000, 1000, selfHandler, TIMER_COUNTDOWN);
		}
	}

	private void startSensor()
	{

		sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_NORMAL);

	}

	private void stopSensor()
	{
		sensorManager.unregisterListener(sensorEventListener);
	}

	private void onTimer(int nId)
	{
		if (nId == TIMER_COUNTDOWN)
		{
			++mnCount;
			setTremorValue(Integer.toString(Math.abs(4 - mnCount)));
			if (3 < mnCount)
			{
				Global.timerStop();
				setTremorValue(theActivity.getString(R.string.start));
				startSensor();
			}
		}
	}

	private void addPoint(final float point)
	{
		theActivity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				tremorView.addDataPoint(point);
			}
		});
	}

	private SensorEventListener	sensorEventListener	= new SensorEventListener()
													{

														private float	yAcceleration	= 0.0f;
														private float	xAcceleration	= 0.0f;

														@Override
														public void onSensorChanged(SensorEvent event)
														{
															if (event.sensor.getType() == Sensor.TYPE_ORIENTATION)
															{
																yAcceleration = event.values[1];
																xAcceleration = event.values[2];

																if (SENSOR_EDGE < xAcceleration
																		|| SENSOR_EDGE < yAcceleration

																		|| (0 - SENSOR_EDGE) > xAcceleration
																		|| (0 - SENSOR_EDGE) > yAcceleration)
																{
																	float nAvg = (xAcceleration + yAcceleration) / 2;
																	setTremorValue(Float.toString(nAvg));
																	addPoint(Math.abs(nAvg));
																}
																else
																{
																	setTremorValue(Float.toString(0.0f));
																}
															}
														}

														@Override
														public void onAccuracyChanged(Sensor sensor, int accuracy)
														{

														}
													};

	@Override
	public void init()
	{
		release();
		showInfo(selfHandler);
	}

	@Override
	public void release()
	{
		mnCount = 0;
		Global.timerStop();
		stopSensor();
		//mbRegisterSensor = false;
		setTremorValue(theActivity.getString(R.string.ready));
	}

	@Override
	protected boolean onClose()
	{
		release();
		return false;
	}

	@Override
	protected boolean onInfo()
	{
		showInfo(null);
		return false;
	}

	private Handler	selfHandler	= new Handler()
								{
									@Override
									public void handleMessage(Message msg)
									{
										switch (msg.what)
										{
										case EventMessage.MSG_CLOSE_MESSAGE_DIALOG:
											onDialog(msg.arg1);
											break;
										case EventMessage.MSG_ON_TIMER:
											onTimer(msg.arg1);
											break;
										}
									}
								};

}
