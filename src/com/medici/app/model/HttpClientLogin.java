package com.medici.app.model;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import com.medici.app.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class HttpClientLogin extends HttpClientHandler
{
	private ProgressDialog	progress	= null;

	public HttpClientLogin(Activity activity)
	{
		super(activity);
	}

	public void login(final String strAccount, final String strPassword, final Handler handler)
	{
		progress = ProgressDialog.show(theActivity, theActivity.getString(R.string.sign_in), null, false);
		AsyncTask<Void, Void, String> shareRegidTask = new AsyncTask<Void, Void, String>()
		{
			@Override
			protected String doInBackground(Void... params)
			{
				String strUrlParam = HttpClientConfig.LOGIN_PARAM + "=" + strAccount;
				try
				{
					HttpClientLogin.this.sendPost(HttpClientConfig.SERVER_URL, strUrlParam, response);
				}
				catch (Exception e)
				{
					onResponse(-1, null);
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result)
			{

			}

		};
		shareRegidTask.execute(null, null, null);
	}

	@Override
	protected boolean onResponse(int nCode, String strMessage)
	{
		if (null != progress)
		{
			progress.dismiss();
			progress = null;
		}

		if (HttpStatus.SC_OK != nCode)
		{
			EventHandler.notify(selfHandler, EventMessage.HTTP_FAIL, 0, 0, null);
		}
		else
		{
			JSONObject jsonobj = null;
			try
			{
				jsonobj = new JSONObject(strMessage);
				String strResult = jsonobj.getString("result");
				if (strResult.equals("success"))
				{
					EventHandler.notify(selfHandler, HttpStatus.SC_OK, 0, 0, null);
				}
				else
				{
					EventHandler.notify(selfHandler, EventMessage.HTTP_FAIL, 0, 0, null);
				}
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}

		}

		return false;
	}

	private Handler	selfHandler	= new Handler()
								{
									@Override
									public void handleMessage(Message msg)
									{
										switch (msg.what)
										{
										case EventMessage.HTTP_FAIL:
											Global.showDidlog(theActivity, null, null, "Login Fail", Type.INVALID);
											break;
										case HttpStatus.SC_OK:
											Global.showDidlog(theActivity, null, null, "Login Success", Type.INVALID);
											break;
										}
									}
								};

}
