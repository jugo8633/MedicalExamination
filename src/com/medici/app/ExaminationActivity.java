package com.medici.app;

import org.apache.http.HttpStatus;

import com.medici.app.controller.AttentionExamController;
import com.medici.app.controller.TremorExamController;
import com.medici.app.controller.VisualExamController;
import com.medici.app.controller.AudioExamController;
import com.medici.app.controller.Memory1Controller;
import com.medici.app.model.EventMessage;
import com.medici.app.model.Global;
import com.medici.app.model.HttpClientExamData;
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

	private ViewFlipper				flipper					= null;
	private VisualExamController	eyeTestController		= null;
	private AudioExamController		hearTestController		= null;
	private TremorExamController	tremorExamController	= null;
	private AttentionExamController	attentionExamController	= null;
	private Memory1Controller		memory1Controller		= null;
	private boolean					mbSingleRun				= false;
	private HttpClientExamData		httpExam				= null;

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
		}
		else
		{
			nRunExam = 0; // full examination
		}

		initExamination(!mbSingleRun);
		showExam(nRunExam);
		httpExam = new HttpClientExamData(this);
	}

	@Override
	protected void onDestroy()
	{
		releaseExamination();
		super.onDestroy();
	}

	private void initExamination(boolean bFullRun)
	{
		eyeTestController = new VisualExamController(this, selfHandler);
		eyeTestController.setExamination(bFullRun);
		hearTestController = new AudioExamController(this, selfHandler);
		hearTestController.setExamination(bFullRun);
		tremorExamController = new TremorExamController(this, selfHandler);
		tremorExamController.setExamination(bFullRun);
		attentionExamController = new AttentionExamController(this, selfHandler);
		attentionExamController.setExamination(bFullRun);

		memory1Controller = new Memory1Controller(this, selfHandler);
		memory1Controller.setExamination(bFullRun);
	}

	private void releaseExamination()
	{
		eyeTestController.release();
		hearTestController.release();
		tremorExamController.release();
		attentionExamController.release();
	}

	public void showEyeExamination()
	{
		flipper.setDisplayedChild(0);
	}

	public void showExam(int nExam)
	{
		flipper.setDisplayedChild(nExam);
		switch (nExam)
		{
		case 0: // eye examination
			eyeTestController.init();
			break;
		case 1: // hear examination
			hearTestController.init();
			break;
		case 2: // tremor examination
			tremorExamController.init();
			break;
		case 3: // attention examination
			attentionExamController.init();
			break;
		case 6:
			memory1Controller.init();
			break;
		}
	}

	private void endExam()
	{
		String strResult = String.format("Total Result:\nVisual：%d\nAudio：%d\nTremor：%d", Global.examData.mnVisual,
				Global.examData.mnAudio, Global.examData.mnTremor);
		Global.showDidlog(this, selfHandler, null, strResult, 777788);
	}

	private void sendExamData()
	{
		httpExam.sendExam(selfHandler);
	}

	private void onDialog(int nId)
	{
		switch (nId)
		{
		case 777788:
			sendExamData();
			break;
		case EventMessage.HTTP_FAIL:
		case HttpStatus.SC_OK:
			this.finish();
			break;
		}
	}

	private Handler	selfHandler	= new Handler()
								{

									@Override
									public void handleMessage(Message msg)
									{
										switch (msg.what)
										{
										case EventMessage.MSG_TEST_END_EYE:
										case EventMessage.MSG_TEST_END_HEAR:
										case EventMessage.MSG_TEST_END_TREMOR:
											showExam(flipper.getDisplayedChild() + 1);
											break;
										case EventMessage.MSG_TEST_END_ATTENTION: // end test
											endExam();
											break;
										case EventMessage.MSG_HEADER_SELECT_CLOSE:
											ExaminationActivity.this.finish();
											break;
										case EventMessage.MSG_HEADER_SELECT_INFO:
											break;
										case EventMessage.MSG_CLOSE_MESSAGE_DIALOG:
											onDialog(msg.arg1);
											break;

										}
									}

								};
}
