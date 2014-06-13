package com.medical.medicalexamination;

import com.medical.medicalexamination.model.Logs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class PersonInfoActivity extends Activity
{
	public static final String	RUN_TYPE		= "run_type";
	public static final int		TYPE_INFO		= 0;
	public static final int		TYPE_REGISTER	= 1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_info);
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		Intent intent = getIntent();
		int nRunType = intent.getIntExtra(RUN_TYPE, 0);

		switch (nRunType)
		{
		case TYPE_INFO:
			setTitle(this.getString(R.string.person_info));
			this.findViewById(R.id.RelativeLayoutLoginMain).setVisibility(View.GONE);
			break;
		case TYPE_REGISTER:
			setTitle(this.getString(R.string.register));
			break;
		}
	}

	private void setTitle(String strTitle)
	{
		TextView title = (TextView) this.findViewById(R.id.textViewPersonInfoTitle);
		title.setText(strTitle);
	}
}
