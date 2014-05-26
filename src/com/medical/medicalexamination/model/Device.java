package com.medical.medicalexamination.model;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class Device
{

	private Context	theContext	= null;

	public Device(Context context)
	{
		super();
		theContext = context;
	}

	public float getScaleSize()
	{
		float nDeviceWidth = (float) getDeviceWidth();
		float nDisplayWidth = (float) getDisplayWidth();
		float nScale = nDisplayWidth / nDeviceWidth;
		if (0 >= nScale)
		{
			nScale = 1;
		}
		return nScale;
	}

	public int getDeviceWidth()
	{
		DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
		int dpWidth = (int) (displayMetrics.widthPixels / displayMetrics.density + 0.5);
		return dpWidth;
	}

	public int getDeviceHeight()
	{
		DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
		int dpHeight = (int) (displayMetrics.heightPixels / displayMetrics.density + 0.5);
		return dpHeight;
	}

	public int getDisplayWidth()
	{
		int nWidth;
		WindowManager windowManager = (WindowManager) theContext.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		nWidth = metrics.widthPixels;
		metrics = null;
		return nWidth;
	}

	public int getDisplayHeight()
	{
		int nHeight;
		WindowManager windowManager = (WindowManager) theContext.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		nHeight = metrics.heightPixels;
		metrics = null;
		return nHeight;
	}

	public int ScaleSize(int nSize)
	{
		float fScale = getScaleSize();
		int nResultSize = (int) Math.floor(nSize * fScale);
		return nResultSize;
	}
}
