package com.medical.medicalexamination.model;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;

public class AudioPlayer
{
	private MediaPlayer	mediaPlayer		= null;
	private boolean		mbAudioPrepared	= false;

	public AudioPlayer()
	{
		init();
	}

	private void init()
	{
		mediaPlayer = new MediaPlayer();
		mediaPlayer.reset();
		mediaPlayer.setOnPreparedListener(new OnPreparedListener()
		{
			@Override
			public void onPrepared(MediaPlayer mp)
			{
				mbAudioPrepared = true;
			}
		});

		mediaPlayer.setOnErrorListener(new OnErrorListener()
		{
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra)
			{
				Logs.showTrace("Audio player error, error=" + what + " extra=" + extra + " !!!!!!!!!!!!!!!!!");
				return false;
			}
		});
	}

	@Override
	protected void finalize() throws Throwable
	{
		release();
		super.finalize();
	}

	public void setAudioFile(String strFile)
	{
		try
		{
			mbAudioPrepared = false;
			mediaPlayer.setDataSource(strFile);
			mediaPlayer.prepare();
			Logs.showTrace("Audio play set data source=" + strFile);
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (IllegalStateException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void play()
	{
		if (!mbAudioPrepared)
		{
			Logs.showTrace("Audio play fail, audio not prepared");
			return;
		}
		try
		{
			if (!mediaPlayer.isPlaying())
			{
				mediaPlayer.start();
			}
		}
		catch (IllegalStateException e)
		{
			e.printStackTrace();
		}
	}

	public void pause()
	{
		if (mediaPlayer.isPlaying())
		{
			mediaPlayer.pause();
		}
	}

	public void stop()
	{
		if (mediaPlayer.isPlaying())
		{
			mediaPlayer.pause();
		}
		mediaPlayer.seekTo(0);
	}

	public void release()
	{
		mediaPlayer.release();
	}
}
