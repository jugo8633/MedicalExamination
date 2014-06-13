package com.medical.medicalexamination;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;


public class HistoryActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}

}
