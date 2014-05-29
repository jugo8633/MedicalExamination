package com.medical.medicalexamination.controller;

import java.io.IOException;

import com.medical.medicalexamination.R;
import com.medical.medicalexamination.model.EventHandler;
import com.medical.medicalexamination.model.EventMessage;
import com.medical.medicalexamination.model.ImageViewTouchHandler;
import com.medical.medicalexamination.model.Logs;

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

public class HearTestController
{
	private Handler					notifyHandler		= null;
	private ImageView				imgViewSpeakerLeft	= null;
	private ImageView				imgViewSpeakerRight	= null;
	private ImageView				imgViewLeft			= null;
	private ImageView				imgViewRight		= null;
	private ImageView				imgClose			= null;
	private ImageView				imgPlay				= null;
	private ImageViewTouchHandler	imageViewHandler	= null;
	private int						mnLevel				= 0;
	private MediaPlayer				mPlayer				= null;
	private AudioManager			audioManager		= null;
	private int						mnMaxVolume			= 0;
	private boolean					mbSpeakerShake		= false;
	private int						mnShakeMsg			= 666;

	public HearTestController(Activity activity, Handler handler)
	{
		super();
		notifyHandler = handler;
		imageViewHandler = new ImageViewTouchHandler();
		initView(activity);

		mPlayer = MediaPlayer.create(activity, R.raw.hear_test);
		mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try
		{
			mPlayer.prepare();
		}
		catch (IllegalStateException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		mPlayer.setOnCompletionListener(new OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer mp)
			{
				setPlayIcon(false);
			}
		});
		audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
		mnMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	}

	public void init()
	{
		mbSpeakerShake = false;
		mPlayer.setVolume(0.0f, 1.0f);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mnMaxVolume / 2, AudioManager.FLAG_PLAY_SOUND);
	}

	@Override
	protected void finalize() throws Throwable
	{
		audioManager = null;
		mPlayer.release();
		mPlayer = null;
		imageViewHandler = null;
		super.finalize();
	}

	private void initView(Activity activity)
	{
		imgViewSpeakerLeft = (ImageView) activity.findViewById(R.id.imageViewSpeakerLeft);
		imgViewSpeakerRight = (ImageView) activity.findViewById(R.id.imageViewSpeakerRight);
		imgViewLeft = (ImageView) activity.findViewById(R.id.imageViewHearLeft);
		imgViewRight = (ImageView) activity.findViewById(R.id.imageViewHearRight);
		imgClose = (ImageView) activity.findViewById(R.id.imageViewHearClose);
		imgPlay = (ImageView) activity.findViewById(R.id.imageViewHearPlay);

		imageViewHandler.setTouchEvent(imgViewLeft, selfHandler);
		imageViewHandler.setTouchEvent(imgViewRight, selfHandler);
		imageViewHandler.setTouchEvent(imgClose, selfHandler);
		imageViewHandler.setTouchEvent(imgPlay, selfHandler);
	}

	private void checkAnswer(int nArrow)
	{
		switch (nArrow)
		{
		case R.id.imageViewHearLeft:
			break;
		case R.id.imageViewHearRight:
			break;
		}
		++mnLevel;
		if (5 < mnLevel)
		{
			mnLevel = 0;
			close();
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

	private void close()
	{
		mbSpeakerShake = false;
		mPlayer.pause();
		mPlayer.seekTo(0);
		setPlayIcon(false);
		EventHandler.notify(notifyHandler, EventMessage.MSG_FLIPPER_CLOSE, 0, 0, null);
	}

	private void touchHandler(final int nResId)
	{
		switch (nResId)
		{
		case R.id.imageViewHearLeft:
		case R.id.imageViewHearRight:
			checkAnswer(nResId);
			break;
		case R.id.imageViewHearClose:
			close();
			break;
		case R.id.imageViewHearPlay:
			setPlayIcon(playSound());
			break;
		}
	}

	private void setPlayIcon(boolean bPlay)
	{
		if (bPlay)
		{
			imgPlay.setImageResource(R.drawable.pause);
		}
		else
		{
			imgPlay.setImageResource(R.drawable.play);
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
			imgViewSpeakerLeft.animate().setListener(null);
			imgViewSpeakerLeft.animate().scaleX(1.0f).setDuration(100)
					.setInterpolator(new AccelerateDecelerateInterpolator());
			imgViewSpeakerLeft.animate().scaleY(1.0f).setDuration(100)
					.setInterpolator(new AccelerateDecelerateInterpolator());
			imgViewSpeakerLeft.clearAnimation();

			imgViewSpeakerRight.animate().scaleX(1.0f).setDuration(100)
					.setInterpolator(new AccelerateDecelerateInterpolator());
			imgViewSpeakerRight.animate().scaleY(1.0f).setDuration(100)
					.setInterpolator(new AccelerateDecelerateInterpolator());
			imgViewSpeakerRight.clearAnimation();

			return;
		}
		if (bShake)
		{
			mnShakeMsg = 666;
			imgViewSpeakerLeft.animate().setListener(null);
			imgViewSpeakerRight.animate().scaleX(1.1f).setDuration(50)
					.setInterpolator(new AccelerateDecelerateInterpolator());
			imgViewSpeakerRight.animate().scaleY(1.1f).setDuration(50)
					.setInterpolator(new AccelerateDecelerateInterpolator());
			imgViewSpeakerLeft.animate().scaleX(1.1f).setDuration(50)
					.setInterpolator(new AccelerateDecelerateInterpolator());
			imgViewSpeakerLeft.animate().scaleY(1.1f).setDuration(50)
					.setInterpolator(new AccelerateDecelerateInterpolator()).setListener(shakeListener);

		}
		else
		{
			mnShakeMsg = 777;
			imgViewSpeakerLeft.animate().setListener(null);
			imgViewSpeakerRight.animate().scaleX(1.0f).setDuration(200)
					.setInterpolator(new AccelerateDecelerateInterpolator());
			imgViewSpeakerRight.animate().scaleY(1.0f).setDuration(200)
					.setInterpolator(new AccelerateDecelerateInterpolator());
			imgViewSpeakerLeft.animate().scaleX(1.0f).setDuration(200)
					.setInterpolator(new AccelerateDecelerateInterpolator());
			imgViewSpeakerLeft.animate().scaleY(1.0f).setDuration(200)
					.setInterpolator(new AccelerateDecelerateInterpolator()).setListener(shakeListener);

		}

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
}
