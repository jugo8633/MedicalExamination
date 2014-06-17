package com.medici.app;

import com.medici.app.controller.Comprehension1Controller;
import com.medici.app.controller.Comprehension2Controller;
import com.medici.app.controller.Comprehension3Controller;
import com.medici.app.controller.EyeTestController;
import com.medici.app.controller.HearTestController;
import com.medici.app.controller.Memory1Controller;
import com.medici.app.controller.Memory2Controller;
import com.medici.app.controller.TrembleTestController;
import com.medici.app.model.EventMessage;
import com.medici.app.model.Type;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup.LayoutParams;
import android.widget.ViewFlipper;

public class ExaminationActivity extends Activity
{

	private ViewFlipper					flipper						= null;
	private EyeTestController			eyeTestController			= null;
	private HearTestController			hearTestController			= null;
	private TrembleTestController		trembleTestController		= null;
	private Comprehension1Controller	comprehension1Controller	= null;
	private Comprehension2Controller	comprehension2Controller	= null;
	private Comprehension3Controller	comprehension3Controller	= null;
	private Memory1Controller			memory1Controller			= null;
	private Memory2Controller			memory2Controller			= null;
	private boolean						mbSingleRun					= false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.examination);
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		flipper = (ViewFlipper) this.findViewById(R.id.viewFlipperExamination);

		Intent intent = getIntent();
		int nRunExam = intent.getIntExtra("EXAM", 0);
		if (Type.INVALID != nRunExam)
		{
			mbSingleRun = true;
			flipper.setDisplayedChild(nRunExam);
		}

		initExamination(!mbSingleRun);
	}

	private void initExamination(boolean bFullRun)
	{
		eyeTestController = new EyeTestController(this, selfHandler);
		eyeTestController.setExamination(bFullRun);
		hearTestController = new HearTestController(this, selfHandler);
		hearTestController.setExamination(bFullRun);
		trembleTestController = new TrembleTestController(this, selfHandler);
		trembleTestController.setExamination(bFullRun);
	}

	public void showEyeExamination()
	{
		flipper.setDisplayedChild(0);
	}

	private Handler	selfHandler	= new Handler()
								{

									@Override
									public void handleMessage(Message msg)
									{
										switch (msg.what)
										{
										case EventMessage.MSG_FLIPPER_CLOSE:
											//close();
											break;
										case EventMessage.MSG_TEST_END_EYE:
										case EventMessage.MSG_TEST_END_HEAR:
											flipper.showNext();
											if (flipper.getDisplayedChild() == 2)
											{
												trembleTestController.init();
											}
											break;

										}
									}

								};
}
