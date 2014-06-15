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

import android.app.Activity;
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

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.examination);
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		flipper = (ViewFlipper) this.findViewById(R.id.viewFlipperExamination);
		initExamination();
	}

	private void initExamination()
	{
		eyeTestController = new EyeTestController(this, selfHandler);
		eyeTestController.setExamination(true);
		hearTestController = new HearTestController(this, selfHandler);
		hearTestController.setExamination(true);
		trembleTestController = new TrembleTestController(this, selfHandler);
		trembleTestController.setExamination(true);
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
