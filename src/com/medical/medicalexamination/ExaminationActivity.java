package com.medical.medicalexamination;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;

public class ExaminationActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.examination);
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}

}
