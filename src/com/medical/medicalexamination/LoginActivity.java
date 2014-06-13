package com.medical.medicalexamination;

import com.medical.medicalexamination.view.ShapButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;

public class LoginActivity extends Activity
{

	//	private ShapButton	btnRegister	= null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		initRegisterButton();
	}

	private void initRegisterButton()
	{
		ShapButton btnRegister = (ShapButton) this.findViewById(R.id.shapButtonRegister);
		btnRegister.setOnButtonClickedListener(new ShapButton.OnButtonClickedListener()
		{

			@Override
			public void OnButtonClicked(boolean bSelected)
			{
				if (bSelected)
				{
					showPersonInfo();
					finish();
				}
			}

		});
	}

	private void showPersonInfo()
	{
		Intent intent = new Intent(LoginActivity.this, PersonInfoActivity.class);
		intent.putExtra(PersonInfoActivity.RUN_TYPE, PersonInfoActivity.TYPE_REGISTER);
		startActivity(intent);
	}
}
