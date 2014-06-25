package com.medici.app.controller;

import com.medici.app.R;
import com.medici.app.model.EventHandler;
import com.medici.app.model.EventMessage;
import com.medici.app.model.Global;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AudioExamController extends TestAreaController
{
	private ImageView		imageViewSpeaker	= null;
	private int				mnLevel				= 0;
	private MediaPlayer		mPlayer				= null;
	private AudioManager	audioManager		= null;
	private int				mnMaxVolume			= 0;
	private boolean			mbSpeakerShake		= false;
	private int				SPEAKER_NOTIFY		= Global.getUserId();
	private final int		TIMER_COUNTDOWN		= Global.getUserId();
	private ImageView		mivOk				= null;
	private ImageView		mivNo				= null;
	private TextView		mtvExamHint			= null;
	private TextView		mtvOnAir			= null;
	private int				mnCount				= 0;
	private Activity		theActivity			= null;
	private int				mnExamResult		= 0;

	private int[]			listImgViewResId	= { R.id.imageViewHearOk, R.id.imageViewHearNo, };
	private int[]			listSound			= { R.raw.sin_6000hz_6dbfs_5s, R.raw.sin_3500hz_6dbfs_5s,
			R.raw.sin_2000hz_6dbfs_5s, R.raw.sin_1000hz_6dbfs_5s, R.raw.sin_500hz_6dbfs_5s, R.raw.sin_300hz_6dbfs_5s,
			R.raw.sin_150hz_6dbfs_5s, R.raw.sin_60hz_6dbfs_3s, R.raw.sin_30hz_6dbfs_5s };

	public AudioExamController(Activity activity, Handler handler)
	{
		super(activity, handler);
		theActivity = activity;
		initView(activity);

		audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
		mnMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	}

	public void init()
	{
		release();
		showExamHint(false);
		showOnAir(false, null);
		showInfo(selfHandler);
	}

	@Override
	protected void finalize() throws Throwable
	{
		audioManager = null;
		if (null != mPlayer)
		{
			mPlayer.release();
			mPlayer = null;
		}
		super.finalize();
	}

	private void initView(Activity activity)
	{
		RelativeLayout mainLayout = (RelativeLayout) activity.findViewById(R.id.hear_test_main_layout);
		initHeader(mainLayout);
		addImageViewResId(mainLayout, listImgViewResId, selfHandler);

		imageViewSpeaker = (ImageView) mainLayout.findViewById(R.id.imageViewSpeaker);
		mtvExamHint = (TextView) mainLayout.findViewById(R.id.textViewHearHint);
		mtvOnAir = (TextView) mainLayout.findViewById(R.id.textViewOnAir);
		mivOk = (ImageView) mainLayout.findViewById(R.id.imageViewHearOk);
		mivNo = (ImageView) mainLayout.findViewById(R.id.imageViewHearNo);
	}

	private void showExamHint(boolean bShow)
	{
		if (bShow)
		{
			mtvExamHint.setVisibility(View.VISIBLE);
			mivOk.setVisibility(View.VISIBLE);
			mivNo.setVisibility(View.VISIBLE);
		}
		else
		{
			mtvExamHint.setVisibility(View.INVISIBLE);
			mivOk.setVisibility(View.INVISIBLE);
			mivNo.setVisibility(View.INVISIBLE);
		}
	}

	private void showOnAir(boolean bShow, String strText)
	{
		if (bShow)
		{
			if (null != strText)
			{
				mtvOnAir.setText(strText);
			}
			mtvOnAir.setVisibility(View.VISIBLE);
		}
		else
		{
			mtvOnAir.setVisibility(View.INVISIBLE);
		}
	}

	public void setExamination(boolean bSet)
	{
		hideHeader(bSet);
		setExaminationMode(bSet);
	}

	private void checkAnswer(int nAns)
	{
		switch (nAns)
		{
		case R.id.imageViewHearOk:
			mnExamResult = mnLevel;
			break;
		case R.id.imageViewHearNo:
			break;
		}
		++mnLevel;
		if (listSound.length <= mnLevel)
		{
			--mnLevel;
			if (getExaminationMode())
			{
				EventHandler.notify(notifyHandler, EventMessage.MSG_TEST_END_HEAR, 0, 0, null);
			}
			else
			{
				showExamResult();
			}
		}
		else
		{
			showExamHint(false);
			//	showOnAir(true, theActivity.getString(R.string.on_air));
			playSound(mnLevel);
		}
	}

	private void showExamResult()
	{
		Global.showDidlog(theActivity, selfHandler, null, "Result： Level is " + mnExamResult,
				EventMessage.MSG_DIALOG_CLOSE_RESULT);
	}

	private void touchHandler(final int nResId)
	{
		switch (nResId)
		{
		case R.id.imageViewHearOk:
		case R.id.imageViewHearNo:
			checkAnswer(nResId);
			break;

		}
	}

	private void soundComplete()
	{
		mbSpeakerShake = false;
		showExamHint(true);
		//	showOnAir(false, null);
	}

	private boolean playSound(int nLevel)
	{
		releasePlayer();
		mPlayer = MediaPlayer.create(theActivity, listSound[nLevel]);
		mPlayer.setOnCompletionListener(new OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer mp)
			{
				soundComplete();
			}
		});
		mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mbSpeakerShake = true;
		mPlayer.start();
		//		speakerShake(true);
		return mPlayer.isPlaying();
	}

	private void speakerShake(boolean bShake)
	{
		if (!mbSpeakerShake)
		{
			imageViewSpeaker.animate().setListener(null);
			imageViewSpeaker.animate().scaleX(1.0f).setDuration(100)
					.setInterpolator(new AccelerateDecelerateInterpolator());
			imageViewSpeaker.animate().scaleY(1.0f).setDuration(100)
					.setInterpolator(new AccelerateDecelerateInterpolator());
			imageViewSpeaker.clearAnimation();

			return;
		}
		if (bShake)
		{
			SPEAKER_NOTIFY = 666;
			imageViewSpeaker.animate().setListener(null);
			imageViewSpeaker.animate().scaleX(1.1f).setDuration(50)
					.setInterpolator(new AccelerateDecelerateInterpolator());
			imageViewSpeaker.animate().scaleY(1.1f).setDuration(50)
					.setInterpolator(new AccelerateDecelerateInterpolator()).setListener(shakeListener);

		}
		else
		{
			SPEAKER_NOTIFY = 777;
			imageViewSpeaker.animate().setListener(null);
			imageViewSpeaker.animate().scaleX(1.0f).setDuration(200)
					.setInterpolator(new AccelerateDecelerateInterpolator());
			imageViewSpeaker.animate().scaleY(1.0f).setDuration(200)
					.setInterpolator(new AccelerateDecelerateInterpolator()).setListener(shakeListener);

		}

	}

	private void showInfo(Handler notifyHandler)
	{
		Global.showDidlog(theActivity, notifyHandler, null, Global.str(R.string.exam_hear_info),
				EventMessage.MSG_DIALOG_CLOSE_INFO);
	}

	private void onTimer(int nId)
	{
		if (nId == TIMER_COUNTDOWN)
		{
			++mnCount;
			showOnAir(true, Integer.toString(Math.abs(4 - mnCount)));
			if (3 < mnCount)
			{
				Global.timerStop();
				showExamHint(false);
				showOnAir(false, theActivity.getString(R.string.on_air));
				mnLevel = 0;
				playSound(mnLevel);
			}
		}
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

	private void releasePlayer()
	{
		if (null != mPlayer)
		{
			mPlayer.release();
			mPlayer = null;
		}
	}

	public void release()
	{
		mnExamResult = 0;
		mnCount = 0;
		Global.timerStop();
		mbSpeakerShake = false;
		releasePlayer();
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mnMaxVolume / 2, AudioManager.FLAG_PLAY_SOUND);
	}

	private AnimatorListener	shakeListener	= new AnimatorListener()
												{
													@Override
													public void onAnimationStart(Animator animation)
													{
													}

													@Override
													public void onAnimationEnd(Animator animation)
													{
														EventHandler.notify(selfHandler, SPEAKER_NOTIFY, 0, 0, null);
													}

													@Override
													public void onAnimationCancel(Animator animation)
													{
													}

													@Override
													public void onAnimationRepeat(Animator animation)
													{
													}
												};

	private Handler				selfHandler		= new Handler()
												{
													@Override
													public void handleMessage(Message msg)
													{
														switch (msg.what)
														{
														case EventMessage.MSG_SELECTED:
															touchHandler(msg.arg1);
															break;
														case 666:
															speakerShake(false);
															break;
														case 777:
															speakerShake(true);
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
}
