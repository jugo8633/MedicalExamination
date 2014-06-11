package com.medical.medicalexamination;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;

public class PersonInfoActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_info);
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}

}
