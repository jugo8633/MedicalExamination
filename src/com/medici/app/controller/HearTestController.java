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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class HearTestController extends TestAreaController
{
	private ImageView		imageViewSpeaker	= null;

	private int				mnLevel				= 0;
	private MediaPlayer		mPlayer				= null;
	private AudioManager	audioManager		= null;
	private int				mnMaxVolume			= 0;
	private boolean			mbSpeakerShake		= false;
	private int				mnShakeMsg			= 666;

	private int[]			listImgViewResId	= { R.id.imageViewHearOk, R.id.imageViewHearNo, };
	private int[]			listSound			= { R.raw.sin_6000hz_6dbfs_5s, R.raw.sin_3500hz_6dbfs_5s,
			R.raw.sin_2000hz_6dbfs_5s, R.raw.sin_1000hz_6dbfs_5s, R.raw.sin_500hz_6dbfs_5s, R.raw.sin_300hz_6dbfs_5s,
			R.raw.sin_150hz_6dbfs_5s, R.raw.sin_60hz_6dbfs_3s, R.raw.sin_30hz_6dbfs_5s };

	public HearTestController(Activity activity, Handler handler)
	{
		super(activity, handler);
		initView(activity);

		mPlayer = MediaPlayer.create(activity, R.raw.hear_test);
		mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		/*
		 * try { mPlayer.prepare(); } catch (IllegalStateException e) {
		 * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
		 */
		mPlayer.setOnCompletionListener(new OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer mp)
			{

			}
		});
		audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
		mnMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	}

	public void init()
	{
		mbSpeakerShake = false;
		mPlayer.setVolume(1.0f, 1.0f);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mnMaxVolume / 2, AudioManager.FLAG_PLAY_SOUND);
		showInfo();
	}

	@Override
	protected void finalize() throws Throwable
	{
		audioManager = null;
		mPlayer.release();
		mPlayer = null;
		super.finalize();
	}

	private void initView(Activity activity)
	{
		RelativeLayout mainLayout = (RelativeLayout) activity.findViewById(R.id.hear_test_main_layout);
		initHeader(mainLayout);
		addImageViewResId(mainLayout, listImgViewResId, selfHandler);

		imageViewSpeaker = (ImageView) mainLayout.findViewById(R.id.imageViewSpeaker);

	}

	public void setExamination(boolean bSet)
	{
		hideHeader(bSet);
		setExaminationMode(bSet);
	}

	private void checkAnswer(int nArrow)
	{
		if (!mPlayer.isPlaying())
		{
			return;
		}

		switch (nArrow)
		{
		case R.id.imageViewHearOk:
			break;
		case R.id.imageViewHearNo:
			break;
		}
		++mnLevel;
		if (5 < mnLevel)
		{
			mnLevel = 0;
			if (getExaminationMode())
			{
				EventHandler.notify(notifyHandler, EventMessage.MSG_TEST_END_HEAR, 0, 0, null);
			}
			else
			{
				close();
			}
		}
		setLevel(mnLevel);
	}

	private void setLevel(int nLevel)
	{
		int nCurrentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

		switch (nLevel)
		{
		case 1:
			mPlayer.setVolume(1.0f, 0.0f);
			break;
		case 2:
			mPlayer.setVolume(0.0f, 1.0f);
			break;
		case 3:
			mPlayer.setVolume(0.0f, 1.0f);
			break;
		case 4:
			mPlayer.setVolume(1.0f, 0.0f);
			break;
		case 5:
			mPlayer.setVolume(0.0f, 1.0f);
			break;

		}

		nCurrentVolume -= nLevel;
		if (0 >= nCurrentVolume)
		{
			nCurrentVolume = 1;
		}
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, nCurrentVolume, AudioManager.FLAG_PLAY_SOUND);

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

	private boolean playSound()
	{
		if (!mPlayer.isPlaying())
		{
			mbSpeakerShake = true;
			mPlayer.start();
			speakerShake(true);
		}
		else
		{
			mbSpeakerShake = false;
			mPlayer.pause();
		}

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
			mnShakeMsg = 666;
			imageViewSpeaker.animate().setListener(null);
			imageViewSpeaker.animate().scaleX(1.1f).setDuration(50)
					.setInterpolator(new AccelerateDecelerateInterpolator());
			imageViewSpeaker.animate().scaleY(1.1f).setDuration(50)
					.setInterpolator(new AccelerateDecelerateInterpolator()).setListener(shakeListener);

		}
		else
		{
			mnShakeMsg = 777;
			imageViewSpeaker.animate().setListener(null);
			imageViewSpeaker.animate().scaleX(1.0f).setDuration(200)
					.setInterpolator(new AccelerateDecelerateInterpolator());
			imageViewSpeaker.animate().scaleY(1.0f).setDuration(200)
					.setInterpolator(new AccelerateDecelerateInterpolator()).setListener(shakeListener);

		}

	}

	private void showInfo()
	{
		Global.showDidlog(theActivity, selfHandler, null, Global.str(R.string.exam_hear_info));
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
														EventHandler.notify(selfHandler, mnShakeMsg, 0, 0, null);
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
														}
													}
												};

	@Override
	protected boolean onClose()
	{
		mbSpeakerShake = false;
		mPlayer.pause();
		mPlayer.seekTo(0);

		mPlayer.setVolume(1.0f, 1.0f);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mnMaxVolume / 2, AudioManager.FLAG_PLAY_SOUND);

		return false;
	}

	@Override
	protected boolean onInfo()
	{
		showInfo();
		return false;
	}
}
