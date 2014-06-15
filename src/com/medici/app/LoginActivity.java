package com.medici.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;

public class LoginActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}

//	private void showPersonInfo()
//	{
//		Intent intent = new Intent(LoginActivity.this, PersonInfoActivity.class);
//		intent.putExtra(PersonInfoActivity.RUN_TYPE, PersonInfoActivity.TYPE_REGISTER);
//		startActivity(intent);
//	}
}
