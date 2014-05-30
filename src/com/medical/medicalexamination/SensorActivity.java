package com.medical.medicalexamination;

import com.medical.medicalexamination.controller.SensorController;
import com.medical.medicalexamination.model.EventMessage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.WindowManager;
import android.widget.ImageView;

public class SensorActivity extends Activity implements SensorEventListener
{

	private CustomDrawableView	mCustomDrawableView	= null;
	private SensorController	sensorController	= null;
	private SensorManager		sensorManager		= null;
	private float				xPosition, xAcceleration, xVelocity = 0.0f;
	private float				yPosition, yAcceleration, yVelocity = 0.0f;
	private float				zAcceleration		= 0.0f;
	private float				xmax, ymax;
	private float				frameTime			= 0.666f;
	private Bitmap				mBitmap;
	private ImageView			sensorBall			= null;
	private final int			SENSOR_EDGE			= 5;
	private boolean				mbStartTest			= false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		mbStartTest = false;
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_sensor);
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		sensorController = new SensorController(this, selfHandler);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorBall = (ImageView) this.findViewById(R.id.imageViewSensorBall);

		//xmax = 400;
		//ymax = 400;

		//		mCustomDrawableView = new CustomDrawableView(this);
		//		RelativeLayout sensorArea = (RelativeLayout) this.findViewById(R.id.RelativeLayoutSensorArea);

		//	sensorArea.addView(mCustomDrawableView);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		SetSensor();
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		//解除感應器註冊
		sensorManager.unregisterListener(this);
	}

	@Override
	protected void onDestroy()
	{
		sensorBall.clearAnimation();
		sensorController = null;
		super.onDestroy();
	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		if (!mbStartTest)
		{
			return;
		}
		//		Logs.showTrace("onSensorChanged #############################");
		//		float[] values = event.values;
		//		Logs.showTrace("Z：" + String.valueOf(values[0])); // 水平旋轉
		//		Logs.showTrace("Y：" + String.valueOf(values[1])); // 前後翻轉
		//		Logs.showTrace("X：" + String.valueOf(values[2])); // 左右翻轉

		if (event.sensor.getType() == Sensor.TYPE_ORIENTATION)
		{
			// Set sensor values as acceleration
			zAcceleration = event.values[0];
			yAcceleration = event.values[1];
			xAcceleration = event.values[2];

			sensorBall.animate().translationX(xAcceleration * 10).translationY(yAcceleration * 10).setDuration(5)
					.setInterpolator(new AccelerateDecelerateInterpolator());

			if (SENSOR_EDGE < xAcceleration || SENSOR_EDGE < yAcceleration || (0 - SENSOR_EDGE) > xAcceleration
					|| (0 - SENSOR_EDGE) > yAcceleration)
			{
				sensorBall.setColorFilter(Color.parseColor("#3FFF0000"));
			}
			else
			{
				sensorBall.setColorFilter(Color.TRANSPARENT);
			}
			//	updateBall();
		}
	}

	/**
	 * 向量移動
	 */
	private void updateBall()
	{
		// Calculate new speed
		xVelocity += (xAcceleration * frameTime);
		yVelocity += (yAcceleration * frameTime);

		// Calc distance travelled in that time
		float xS = (xVelocity / 2) * frameTime;
		float yS = (yVelocity / 2) * frameTime;

		// Add to position negative due to sensor
		// readings being opposite to what we want!
		xPosition -= xS;
		yPosition -= yS;

		if (xPosition > xmax)
		{
			xPosition = xmax;
		}
		else if (xPosition < 0)
		{
			xPosition = 0;
		}
		if (yPosition > ymax)
		{
			yPosition = ymax;
		}
		else if (yPosition < 0)
		{
			yPosition = 0;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{

	}

	protected void SetSensor()
	{
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_GAME);

		//		List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
		//		//如果有取到該手機的方位感測器，就註冊他。
		//		if (sensors.size() > 0)
		//		{
		//			//registerListener必須要implements SensorEventListener，
		//			//而SensorEventListener必須實作onAccuracyChanged與onSensorChanged
		//			//感應器註冊
		//			Logs.showTrace("sensor registerListener ############################");
		//			sensorManager.registerListener(this, (Sensor) sensors.get(0), SensorManager.SENSOR_DELAY_NORMAL);
		//		}
	}

	private void close()
	{
		this.finish();
	}

	private void buttonClick(int nResId)
	{
		if (R.id.imageViewSensorStart == nResId)
		{
			mbStartTest = mbStartTest ? false : true;
			ImageView imgStart = (ImageView) this.findViewById(nResId);
			if (mbStartTest)
			{
				imgStart.setImageResource(R.drawable.pause);
			}
			else
			{
				imgStart.setImageResource(R.drawable.play);
				sensorBall.animate().translationX(0).translationY(0).setDuration(5)
				.setInterpolator(new AccelerateDecelerateInterpolator());
				sensorBall.clearAnimation();
				sensorBall.setColorFilter(Color.TRANSPARENT);
			}
		}
	}

	private Handler	selfHandler	= new Handler()
								{
									@Override
									public void handleMessage(Message msg)
									{
										switch (msg.what)
										{
										case EventMessage.MSG_FLIPPER_CLOSE:
											close();
											break;
										case EventMessage.MSG_SELECTED:
											buttonClick(msg.arg1);
											break;
										}
									}
								};

	public class CustomDrawableView extends View
	{
		public CustomDrawableView(Context context)
		{
			super(context);
			Bitmap ball = BitmapFactory.decodeResource(getResources(), R.drawable.eye);
			final int dstWidth = 50;
			final int dstHeight = 50;
			mBitmap = Bitmap.createScaledBitmap(ball, dstWidth, dstHeight, true);
			//	mWood = BitmapFactory.decodeResource(getResources(), R.drawable.wood);

		}

		protected void onDraw(Canvas canvas)
		{
			final Bitmap bitmap = mBitmap;
			//	canvas.drawBitmap(mWood, 0, 0, null);
			canvas.drawBitmap(bitmap, xPosition, yPosition, null);
			invalidate();
		}
	}
}
