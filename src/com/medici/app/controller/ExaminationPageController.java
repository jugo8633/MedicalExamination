package com.medici.app.controller;

import com.medici.app.R;
import com.medici.app.model.EventHandler;
import com.medici.app.model.EventMessage;
import com.medici.app.model.Type;
import com.medici.app.model.Global;

import android.app.Activity;
import android.os.Handler;

public class ExaminationPageController extends ExaminationListController
{
	public int		EXAMINATION_EYE			= Type.INVALID;
	public int		EXAMINATION_HEAR		= Type.INVALID;
	public int		EXAMINATION_TREMBLE		= Type.INVALID;
	public int		EXAMINATION_ATTENTION	= Type.INVALID;
	public int		EXAMINATION_LANGUAGE	= Type.INVALID;
	public int		EXAMINATION_SPATIAL		= Type.INVALID;
	public int		EXAMINATION_MEMORY		= Type.INVALID;
	private Handler	theHandler				= null;

	public ExaminationPageController(Activity activity, Handler handler)
	{
		super(activity, R.id.listViewExamination);
		EXAMINATION_EYE = setListItem(R.drawable.sight, R.string.visual_acuity, R.string.where_the_E_opens,
				R.string.hold_device_at_arm_length, R.drawable.finger);
		EXAMINATION_HEAR = setListItem(R.drawable.headphones, R.string.audio_acuity, R.string.yes_no,
				R.string.provide_ear_phones, R.drawable.finger);
		EXAMINATION_TREMBLE = setListItem(R.drawable.hand_shake, R.string.tremor_test, R.string.holds_tablet,
				R.string.instructs_and_starts_test, R.drawable.tremor);
		EXAMINATION_ATTENTION = setListItem(R.drawable.expression, R.string.attention, R.string.yes_no,
				R.string.facilitate_answer_input, Type.INVALID);
		EXAMINATION_LANGUAGE = setListItem(R.drawable.talking_icon, R.string.language, R.string.yes_no,
				R.string.facilitate_answer_input, Type.INVALID);
		EXAMINATION_SPATIAL = setListItem(R.drawable.memory_card, R.string.visual_spatial,
				R.string.test_patient_visual_spatial, R.string.monitor, Type.INVALID);
		EXAMINATION_MEMORY = setListItem(R.drawable.memory_brain, R.string.memory,
				R.string.test_short_term_and_long_term_memory, R.string.monitor, Type.INVALID);
		super.updateList();

		theHandler = handler;
	}

	private int setListItem(int nIconResId, int nTitle, int nPatientDo, int nCaretakerDo, int nResIdPatientDoImage)
	{
		return super.addItem(nIconResId, Global.str(nTitle), Global.str(nPatientDo), Global.str(nCaretakerDo),
				nResIdPatientDoImage);
	}

	private void notifyTestItemSelected(int nPosition)
	{
		//		int nWhat = Type.INVALID;
		//		if (EXAMINATION_EYE == nPosition)
		//		{
		//			nWhat = EventMessage.MSG_SHOW_TEST_EYE;
		//		}
		//		else if (EXAMINATION_HEAR == nPosition)
		//		{
		//			nWhat = EventMessage.MSG_SHOW_TEST_HEAR;
		//		}
		//		else if (EXAMINATION_TREMBLE == nPosition)
		//		{
		//			nWhat = EventMessage.MSG_SHOW_TEST_TREMBLE;
		//		}
		//		else if (EXAMINATION_ATTENTION == nPosition)
		//		{
		//			nWhat = EventMessage.MSG_SHOW_TEST_ATTENTION;
		//		}
		//		else
		//		{
		//			return;
		//		}

		//EventHandler.notify(theHandler, EventMessage.MSG_EXAM_SELECTED, nPosition, 0, null);
	}

	@Override
	protected void onItemSelected(int nPosition)
	{
		EventHandler.notify(theHandler, EventMessage.MSG_EXAM_SELECTED, nPosition, 0, null);
		//notifyTestItemSelected(nPosition);
	}
}
