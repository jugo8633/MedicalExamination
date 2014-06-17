package com.medici.app.controller;

import com.medici.app.R;
import com.medici.app.model.EventMessage;

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
import android.widget.ImageView;

public class TrembleTestController extends TestAreaController
{

	private SensorManager	sensorManager		= null;
	private int[]			listImgViewResId	= { R.id.imageViewSensorStart };
	private final int		SENSOR_EDGE			= 2;
	private ImageView		sensorBall			= null;
	private boolean			mbRegisterSensor	= false;

	public TrembleTestController(Activity activity, Handler handler)
	{
		super(activity, handler);
		activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		initView(activity, handler);
		sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
	}

	private void initView(Activity activity, Handler handler)
	{
		ViewGroup parent = (ViewGroup) activity.findViewById(R.id.RelativeLayoutSensorMain);
		initHeader(parent);
		addImageViewResId(parent, listImgViewResId, selfHandler);
		sensorBall = (ImageView) parent.findViewById(R.id.imageViewSensorBall);
	}

	public void setExamination(boolean bSet)
	{
		hideHeader(bSet);
		setExaminationMode(bSet);
	}

	@Override
	protected boolean onClose()
	{

		return false;
	}

	public void init()
	{

	}

	private void startSensor()
	{
		mbRegisterSensor = mbRegisterSensor ? false : true;
		if (mbRegisterSensor)
		{
			sensorManager.registerListener(sensorEventListener,
					sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_NORMAL);
		}
		else
		{
			sensorManager.unregisterListener(sensorEventListener);
			sensorBall.animate().translationX(0).translationY(0).setDuration(100);
			sensorBall.setColorFilter(Color.TRANSPARENT);
			sensorBall.clearAnimation();
		}
	}

	private void touchHandler(final int nResId)
	{
		switch (nResId)
		{
		case R.id.imageViewSensorStart:
			startSensor();
			break;
		}
	}

	private SensorEventListener	sensorEventListener	= new SensorEventListener()
													{

														@Override
														public void onSensorChanged(SensorEvent event)
														{
															if (event.sensor.getType() == Sensor.TYPE_ORIENTATION)
															{
																// Set sensor values as acceleration
																float yAcceleration = event.values[1];
																float xAcceleration = event.values[2];

																sensorBall.animate().translationX(xAcceleration * 10)
																		.translationY(yAcceleration * 10)
																		.setDuration(100);

																if (SENSOR_EDGE < xAcceleration
																		|| SENSOR_EDGE < yAcceleration
																		|| (0 - SENSOR_EDGE) > xAcceleration
																		|| (0 - SENSOR_EDGE) > yAcceleration)
																{
																	sensorBall.setColorFilter(Color
																			.parseColor("#3FFF0000"));
																}
																else
																{
																	sensorBall.setColorFilter(Color.TRANSPARENT);
																}
															}
														}

														@Override
														public void onAccuracyChanged(Sensor sensor, int accuracy)
														{

														}
													};

	private Handler				selfHandler			= new Handler()
													{
														@Override
														public void handleMessage(Message msg)
														{
															switch (msg.what)
															{
															case EventMessage.MSG_SELECTED:
																touchHandler(msg.arg1);
																break;
															}
														}
													};

	@Override
	protected boolean onInfo()
	{
		// TODO Auto-generated method stub
		return false;
	}
}
