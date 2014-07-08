package com.medici.app.controller;

import com.jugo.smartwidget.common.Logs;
import com.jugo.smartwidget.common.Utility;
import com.jugo.smartwidget.image.BitmapHandler;
import com.jugo.smartwidget.calendar.CalendarWidget;
import com.medici.app.R;
import com.medici.app.model.EventHandler;
import com.medici.app.model.EventMessage;
import com.medici.app.model.Global;
import com.medici.app.model.Type;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AttentionExamController extends TestAreaController
{
	private final int		RESULT_CLOSE		= Global.getUserId();
	private final int		EXAM_MONTH			= 0;
	private final int		EXAM_DAY			= 1;
	private final int		EXAM_WEEKDAY		= 2;
	private final int		EXAM_END			= 3;
	private final int		TIMER_COUNTDOWN		= Global.getUserId();
	private int				mnLevel				= 0;
	private int				mnExamMonthResult	= Type.INVALID;
	private int				mnExamDayResult		= Type.INVALID;
	private int				mnExamWeekDayResult	= Type.INVALID;
	private int[]			listImgViewResId	= { R.id.imageViewCheck };
	private Bitmap			bmpDay				= null;
	private Bitmap			bmpMonth			= null;
	private Bitmap			bmpWeekDay			= null;
	private Bitmap			bmpMonthMosaic		= null;
	private Bitmap			bmpDayMosaic		= null;
	private Bitmap			bmpWeekDayMosaic	= null;
	private ImageView		ivDay				= null;
	private ImageView		ivMonth				= null;
	private ImageView		ivWeekDay			= null;
	private Rect			rectMonth			= null;
	private Rect			rectDay				= null;
	private Rect			rectWeekDay			= null;
	private TextView		tvWhat				= null;
	private int				mnRunMode			= 0;
	private CalendarWidget	calendarWidget		= null;
	private int				mnDay				= Type.INVALID;

	private int[]			listBtnMonth		= { R.id.textViewJan, R.id.textViewFeb, R.id.textViewMar,
			R.id.textViewApr, R.id.textViewMay, R.id.textViewJun, R.id.textViewJuly, R.id.textViewAug,
			R.id.textViewSept, R.id.textViewOct, R.id.textViewNov, R.id.textViewDec };

	private int[]			listBtnWeekday		= { R.id.buttonSun, R.id.buttonMon, R.id.buttonTue, R.id.buttonWed,
			R.id.buttonThu, R.id.buttonFri, R.id.buttonSat };

	public AttentionExamController(Activity activity, Handler handler)
	{
		super(activity, handler);
		initView(activity);
	}

	private void initView(Activity activity)
	{
		ViewGroup parent = (ViewGroup) activity.findViewById(R.id.RelativeLayoutAttentionMain);
		initHeader(parent);
		addImageViewResId(parent, listImgViewResId, selfHandler);

		ivMonth = (ImageView) parent.findViewById(R.id.imageViewMonth);
		ivDay = (ImageView) parent.findViewById(R.id.imageViewDay);
		ivWeekDay = (ImageView) parent.findViewById(R.id.imageViewWeekDay);

		tvWhat = (TextView) parent.findViewById(R.id.textViewWhat);

		rectMonth = Utility.getViewRect(ivMonth);
		rectDay = Utility.getViewRect(ivDay);
		rectWeekDay = Utility.getViewRect(ivWeekDay);

		bmpMonth = BitmapFactory.decodeResource(theActivity.getResources(), R.drawable.jan);
		bmpDay = BitmapFactory.decodeResource(theActivity.getResources(), R.drawable.day13);
		bmpWeekDay = BitmapFactory.decodeResource(theActivity.getResources(), R.drawable.fri);
		mnDay = 13;

		calendarWidget = (CalendarWidget) parent.findViewById(R.id.calendarWeekDay);
		calendarWidget.showMonthYear(false);
		calendarWidget.showWeekDay(false);
		calendarWidget.setOnDaySelectedListener(new CalendarWidget.OnDaySelectedListener()
		{
			@Override
			public void onDaySelected(int nDay)
			{
				daySelected(nDay);
			}
		});

		initMonthBtn(parent);
		initWeekdayBtn(parent);
	}

	private void initMonthBtn(ViewGroup parent)
	{
		TextView tvBtnMonth = null;
		for (int i = 0; i < listBtnMonth.length; ++i)
		{
			tvBtnMonth = (TextView) parent.findViewById(listBtnMonth[i]);
			tvBtnMonth.setOnTouchListener(ButtonTouchListener);
		}
	}

	private void initWeekdayBtn(ViewGroup parent)
	{
		Button btn = null;
		for (int i = 0; i < listBtnWeekday.length; ++i)
		{
			btn = (Button) parent.findViewById(listBtnWeekday[i]);
			btn.setOnClickListener(ButtonClickListener);
		}
	}

	private void showInfo()
	{
		Global.showDidlog(theActivity, selfHandler, null, Global.str(R.string.attention_time_info),
				EventMessage.MSG_DIALOG_CLOSE_INFO);
	}

	public void setExamination(boolean bSet)
	{
		hideHeader(bSet);
		setExaminationMode(bSet);
	}

	private void displayMosaic(int nBlock)
	{
		switch (mnRunMode)
		{
		case EXAM_MONTH:
			bmpMonthMosaic = BitmapHandler.makeMosaic(bmpMonth, rectMonth, nBlock);
			break;
		case EXAM_DAY:
			bmpDayMosaic = BitmapHandler.makeMosaic(bmpDay, rectDay, nBlock);
			break;
		case EXAM_WEEKDAY:
			bmpWeekDayMosaic = BitmapHandler.makeMosaic(bmpWeekDay, rectWeekDay, nBlock);
			break;
		}
	}

	private void switchRunMode(int nMode)
	{
		mnRunMode = nMode;
		Global.timerStop();
		mnLevel = 0;

		switch (nMode)
		{
		case EXAM_MONTH:
			tvWhat.setText(theActivity.getString(R.string.what_month));
			break;
		case EXAM_DAY:
			theActivity.findViewById(R.id.RelativeLayoutMonthBtnMain).setVisibility(View.GONE);
			theActivity.findViewById(R.id.RelativeLayoutDayBtnMain).setVisibility(View.VISIBLE);
			tvWhat.setText(theActivity.getString(R.string.what_day));
			break;
		case EXAM_WEEKDAY:
			theActivity.findViewById(R.id.RelativeLayoutDayBtnMain).setVisibility(View.GONE);
			theActivity.findViewById(R.id.RelativeLayoutWeekDayBtnMain).setVisibility(View.VISIBLE);
			tvWhat.setText(theActivity.getString(R.string.what_weekday));
			break;
		case EXAM_END:
			if (getExaminationMode())
			{
				EventHandler.notify(notifyHandler, EventMessage.MSG_TEST_END_ATTENTION, 0, 0, null);
			}
			else
			{
				showExamResult();
			}
			break;
		default:
			tvWhat.setText(null);
			return;
		}
		Global.timerStart(2000, 2000, selfHandler, TIMER_COUNTDOWN);
	}

	private void showExamResult()
	{
		Global.showDidlog(theActivity, selfHandler, null, "Result： Level is\n" + mnExamMonthResult + "\n"
				+ mnExamDayResult + "\n" + mnExamWeekDayResult, RESULT_CLOSE);
	}

	private void setMosaic(int nLevel)
	{
		BitmapHandler.releaseBitmap(bmpMonthMosaic);
		BitmapHandler.releaseBitmap(bmpDayMosaic);
		BitmapHandler.releaseBitmap(bmpWeekDayMosaic);

		switch (nLevel)
		{
		case 0:
			bmpMonthMosaic = BitmapHandler.makeMosaic(bmpMonth, rectMonth, 45);
			bmpDayMosaic = BitmapHandler.makeMosaic(bmpDay, rectDay, 45);
			bmpWeekDayMosaic = BitmapHandler.makeMosaic(bmpWeekDay, rectWeekDay, 45);
			break;
		case 1:
			displayMosaic(18);
			break;
		case 2:
			displayMosaic(13);
			break;
		case 3:
			displayMosaic(8);
			break;
		case 4:
			displayMosaic(5);
			break;
		default:
			Global.timerStop();
			displayMosaic(0);
			break;
		}

		if (null != bmpMonthMosaic && !bmpMonthMosaic.isRecycled())
		{
			ivMonth.setImageBitmap(bmpMonthMosaic);
		}
		if (null != bmpDayMosaic && !bmpDayMosaic.isRecycled())
		{
			ivDay.setImageBitmap(bmpDayMosaic);
		}
		if (null != bmpWeekDayMosaic && !bmpWeekDayMosaic.isRecycled())
		{
			ivWeekDay.setImageBitmap(bmpWeekDayMosaic);
		}
	}

	@Override
	public void init()
	{
		mnRunMode = 0;
		mnLevel = 0;
		mnExamMonthResult = Type.INVALID;
		mnExamDayResult = Type.INVALID;
		mnExamWeekDayResult = Type.INVALID;
		tvWhat.setText(theActivity.getString(R.string.what_month));
		showInfo();
		setMosaic(mnLevel);
	}

	@Override
	public void release()
	{
		BitmapHandler.releaseBitmap(bmpMonth);
		BitmapHandler.releaseBitmap(bmpDay);
		BitmapHandler.releaseBitmap(bmpWeekDay);

		BitmapHandler.releaseBitmap(bmpMonthMosaic);
		BitmapHandler.releaseBitmap(bmpDayMosaic);
		BitmapHandler.releaseBitmap(bmpWeekDayMosaic);
	}

	@Override
	protected boolean onClose()
	{
		return false;
	}

	@Override
	protected boolean onInfo()
	{
		showInfo();
		return false;
	}

	private void touchHandler(final int nResId)
	{
		switch (nResId)
		{
		case R.id.imageViewCheck:
			break;

		}
	}

	private void onTimer(int nId)
	{
		if (nId == TIMER_COUNTDOWN)
		{
			setMosaic(++mnLevel);
		}
	}

	private void onDialog(int nId)
	{
		if (RESULT_CLOSE == nId)
		{
			close();
		}

		if (EventMessage.MSG_DIALOG_CLOSE_RESULT == nId)
		{
			close();
		}

		if (EventMessage.MSG_DIALOG_CLOSE_INFO == nId)
		{
			Global.timerStart(2000, 2000, selfHandler, TIMER_COUNTDOWN);
		}
	}

	private Handler	selfHandler	= new Handler()
								{
									@Override
									public void handleMessage(Message msg)
									{
										switch (msg.what)
										{
										case EventMessage.MSG_SELECTED:
											touchHandler(msg.arg1);
											break;
										case EventMessage.MSG_CLOSE_MESSAGE_DIALOG:
											onDialog(msg.arg1);
											break;
										case EventMessage.MSG_ON_TIMER:
											onTimer(msg.arg1);
											break;
										}
									}
								};

	private void daySelected(int nDay)
	{

		mnExamDayResult = -1;
		if (mnDay == nDay)
		{
			mnExamDayResult = mnLevel;
		}
		switchRunMode(EXAM_WEEKDAY);
	}

	private void ButtonHandler(int nResId)
	{

		mnExamMonthResult = -1;

		switch (nResId)
		{
		case R.id.textViewJan:
			mnExamMonthResult = mnLevel;
			break;
		case R.id.textViewFeb:
			break;
		case R.id.textViewMar:
			break;
		case R.id.textViewApr:
			break;
		case R.id.textViewMay:
			break;
		case R.id.textViewJun:
			break;
		case R.id.textViewJuly:
			break;
		case R.id.textViewAug:
			break;
		case R.id.textViewSept:
			break;
		case R.id.textViewOct:
			break;
		case R.id.textViewNov:
			break;
		case R.id.textViewDec:
			break;
		}
		switchRunMode(EXAM_DAY);
	}

	private void onButtonClick(int nId)
	{
		mnExamWeekDayResult = -1;
		switch (nId)
		{
		case R.id.buttonSun:
			break;
		case R.id.buttonMon:
			break;
		case R.id.buttonTue:
			break;
		case R.id.buttonWed:
			break;
		case R.id.buttonThu:
			break;
		case R.id.buttonFri:
			mnExamWeekDayResult = mnLevel;
			break;
		case R.id.buttonSat:
			break;
		}
		switchRunMode(EXAM_END);
	}

	private OnTouchListener	ButtonTouchListener	= new OnTouchListener()
												{
													@Override
													public boolean onTouch(View v, MotionEvent event)
													{
														if (v instanceof TextView)
														{
															switch (event.getAction())
															{
															case MotionEvent.ACTION_DOWN:
																v.animate()
																		.scaleY(0.8f)
																		.scaleX(0.8f)
																		.setDuration(100)
																		.setInterpolator(
																				new AccelerateDecelerateInterpolator());
																return true;
															case MotionEvent.ACTION_UP:
															case MotionEvent.ACTION_CANCEL:
																v.animate()
																		.scaleY(1.0f)
																		.scaleX(1.0f)
																		.setDuration(200)
																		.setInterpolator(
																				new AccelerateDecelerateInterpolator());
																v.clearAnimation();
																ButtonHandler(v.getId());
																break;
															}
														}
														return false;
													}

												};

	private OnClickListener	ButtonClickListener	= new OnClickListener()
												{
													@Override
													public void onClick(View v)
													{
														if (v instanceof Button)
														{
															onButtonClick(v.getId());
														}
													}
												};

}
