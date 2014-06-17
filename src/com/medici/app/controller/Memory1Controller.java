package com.medici.app.controller;

import java.util.Timer;
import java.util.TimerTask;

import com.medici.app.R;
import com.medici.app.model.EventHandler;
import com.medici.app.model.EventMessage;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Memory1Controller extends TestAreaController
{

	private final int	MSG_CARD_ROTATE		= 6667;
	private Timer		mTimer				= null;
	private ViewGroup	parent				= null;
	private int[]		listImgViewResId	= { R.id.imageViewCard1, R.id.imageViewCard2, R.id.imageViewCard3,
			R.id.imageViewCard4, R.id.imageViewCard5, R.id.imageViewCard6, R.id.imageViewCard7, R.id.imageViewCard8,
			R.id.imageViewCard9, R.id.imageViewComprehension1OK };

	private int[]		listCard1			= { R.drawable.bird, R.drawable.turtle, R.drawable.shark, R.drawable.shark,
			R.drawable.turtle, R.drawable.bird, R.drawable.bird, R.drawable.shark, R.drawable.turtle };

	private boolean		mbStartGame			= false;
	private int			mnBirdCount			= 0;
	private int			mnSharkCount		= 0;
	private int			mnTurtleCount		= 0;

	public Memory1Controller(Activity activity, Handler handler)
	{
		super(activity, handler);
		initView(activity, handler);
	}

	@Override
	protected void init()
	{
		initCardGame();
		setTimerTask();
	}

	private void setTimerTask()
	{
		mTimer = new Timer();
		mTimer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				EventHandler.notify(selfHandler, MSG_CARD_ROTATE, 0, 0, null);
			}
		}, 5000);
	}

	@Override
	protected boolean onClose()
	{
		if (null != mTimer)
		{
			mTimer.cancel();
		}
		return false;
	}

	private void initView(Activity activity, Handler handler)
	{
		parent = (ViewGroup) activity.findViewById(R.id.RelativeLayoutMemory1Main);
		initHeader(parent);
		addImageViewResId(parent, listImgViewResId, selfHandler);
	}

	private void onButtonClicked(int nResId)
	{

		switch (nResId)
		{
		case R.id.imageViewComprehension1OK:
			close();
			break;
		case R.id.imageViewCard1:
		case R.id.imageViewCard2:
		case R.id.imageViewCard3:
		case R.id.imageViewCard4:
		case R.id.imageViewCard5:
		case R.id.imageViewCard6:
		case R.id.imageViewCard7:
		case R.id.imageViewCard8:
		case R.id.imageViewCard9:
			rotateCard(nResId);
			break;
		}
	}

	private void showAllShark()
	{
		ImageView imageView = null;
		imageView = (ImageView) parent.findViewById(R.id.imageViewCard3);
		imageView.setImageResource(R.drawable.shark);
		imageView = (ImageView) parent.findViewById(R.id.imageViewCard4);
		imageView.setImageResource(R.drawable.shark);
		imageView = (ImageView) parent.findViewById(R.id.imageViewCard8);
		imageView.setImageResource(R.drawable.shark);
		mnSharkCount = 3;
	}

	private void showAllTurtle()
	{
		ImageView imageView = null;
		imageView = (ImageView) parent.findViewById(R.id.imageViewCard2);
		imageView.setImageResource(R.drawable.turtle);
		imageView = (ImageView) parent.findViewById(R.id.imageViewCard5);
		imageView.setImageResource(R.drawable.turtle);
		imageView = (ImageView) parent.findViewById(R.id.imageViewCard9);
		imageView.setImageResource(R.drawable.turtle);
		mnTurtleCount = 3;
	}

	private void showAllBird()
	{
		ImageView imageView = null;
		imageView = (ImageView) parent.findViewById(R.id.imageViewCard1);
		imageView.setImageResource(R.drawable.bird);
		imageView = (ImageView) parent.findViewById(R.id.imageViewCard6);
		imageView.setImageResource(R.drawable.bird);
		imageView = (ImageView) parent.findViewById(R.id.imageViewCard7);
		imageView.setImageResource(R.drawable.bird);
		mnBirdCount = 3;
	}

	private void showCard(int nResId)
	{
		ImageView imageView = (ImageView) parent.findViewById(nResId);
		switch (nResId)
		{
		case R.id.imageViewCard1:
		case R.id.imageViewCard6:
		case R.id.imageViewCard7:
			if (0 != mnSharkCount && 3 != mnSharkCount)
			{
				showAllShark();
				return;
			}

			if (0 != mnTurtleCount && 3 != mnTurtleCount)
			{
				showAllTurtle();
				return;
			}
			imageView.setImageResource(listCard1[0]);
			++mnBirdCount;
			break;
		case R.id.imageViewCard2:
		case R.id.imageViewCard5:
		case R.id.imageViewCard9:
			if (0 != mnSharkCount && 3 != mnSharkCount)
			{
				showAllShark();
				return;
			}

			if (0 != mnBirdCount && 3 != mnBirdCount)
			{
				showAllBird();
				return;
			}
			imageView.setImageResource(listCard1[1]);
			++mnTurtleCount;
			break;
		case R.id.imageViewCard3:
		case R.id.imageViewCard4:
		case R.id.imageViewCard8:
			if (0 != mnTurtleCount && 3 != mnTurtleCount)
			{
				showAllTurtle();
				return;
			}

			if (0 != mnBirdCount && 3 != mnBirdCount)
			{
				showAllBird();
				return;
			}
			imageView.setImageResource(listCard1[2]);
			++mnSharkCount;
			break;
		}
	}

	private void rotateCard(final int nResId)
	{
		if (!mbStartGame)
		{
			return;
		}
		final ImageView imageView = (ImageView) parent.findViewById(nResId);
		imageView.clearAnimation();
		imageView.animate().rotationY(90).setDuration(300).setListener(new AnimatorListener()
		{

			@Override
			public void onAnimationStart(Animator animation)
			{
			}

			@Override
			public void onAnimationEnd(Animator animation)
			{
				showCard(nResId);
				imageView.animate().rotationY(180).setDuration(300).setListener(new AnimatorListener()
				{
					@Override
					public void onAnimationStart(Animator animation)
					{

					}

					@Override
					public void onAnimationEnd(Animator animation)
					{
						imageView.animate().rotationY(0).setDuration(10);
						imageView.clearAnimation();
					}

					@Override
					public void onAnimationCancel(Animator animation)
					{
					}

					@Override
					public void onAnimationRepeat(Animator animation)
					{
					}
				});
			}

			@Override
			public void onAnimationCancel(Animator animation)
			{
			}

			@Override
			public void onAnimationRepeat(Animator animation)
			{
			}
		});

	}

	private void initCardGame()
	{
		mbStartGame = false;
		mnBirdCount = 0;
		mnSharkCount = 0;
		mnTurtleCount = 0;
		ImageView imageView = null;
		for (int i = 0; i < 9; ++i)
		{
			imageView = (ImageView) parent.findViewById(listImgViewResId[i]);
			imageView.setImageResource(listCard1[i]);
		}
	}

	private void startCardGame()
	{
		coverCard();
		mbStartGame = true;
	}

	private void coverCard()
	{
		for (int i = 0; i < 9; ++i)
		{
			final ImageView imageView = (ImageView) parent.findViewById(listImgViewResId[i]);
			imageView.clearAnimation();
			imageView.animate().rotationY(90).setDuration(300).setListener(new AnimatorListener()
			{
				@Override
				public void onAnimationStart(Animator animation)
				{
				}

				@Override
				public void onAnimationEnd(Animator animation)
				{
					imageView.setImageResource(R.drawable.love);
					imageView.animate().rotationY(180).setDuration(300).setListener(new AnimatorListener()
					{
						@Override
						public void onAnimationStart(Animator animation)
						{
						}

						@Override
						public void onAnimationEnd(Animator animation)
						{
							imageView.animate().rotationY(0).setDuration(10);
							imageView.clearAnimation();
						}

						@Override
						public void onAnimationCancel(Animator animation)
						{
						}

						@Override
						public void onAnimationRepeat(Animator animation)
						{
						}
					});
				}

				@Override
				public void onAnimationCancel(Animator animation)
				{
				}

				@Override
				public void onAnimationRepeat(Animator animation)
				{
				}
			});
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
											onButtonClicked(msg.arg1);
											break;
										case MSG_CARD_ROTATE:
											if (null != mTimer)
											{
												mTimer.cancel();
											}
											startCardGame();
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
