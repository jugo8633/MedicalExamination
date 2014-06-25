package com.medici.app.model;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class HttpClientExamData extends HttpClientHandler
{
	private ProgressDialog	progress		= null;
	private Handler			parentHandler	= null;

	public HttpClientExamData(Activity activity)
	{
		super(activity);
	}

	public void sendExam(final Handler handler)
	{
		parentHandler = handler;
		progress = ProgressDialog.show(theActivity, "Send Examination Result", null, false);
		AsyncTask<Void, Void, String> shareRegidTask = new AsyncTask<Void, Void, String>()
		{
			@Override
			protected String doInBackground(Void... params)
			{
				org.json.JSONObject Output_Info = new org.json.JSONObject();
				try
				{
					Output_Info.put("visual", Global.examData.mnVisual);
					Output_Info.put("audio", Global.examData.mnAudio);
					Output_Info.put("tremor", Global.examData.mnTremor);
				}
				catch (JSONException e1)
				{
					e1.printStackTrace();
				}

				String strUrlParam = Output_Info.toString();
				Logs.showTrace("send exam:" + strUrlParam);
				try
				{
					HttpClientExamData.this.sendPost(HttpClientConfig.SERVER_URL, strUrlParam, response);
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

	private void onDialog(int nId)
	{
		EventHandler.notify(parentHandler, EventMessage.MSG_CLOSE_MESSAGE_DIALOG, nId, 0, null);
	}

	private Handler	selfHandler	= new Handler()
								{
									@Override
									public void handleMessage(Message msg)
									{
										switch (msg.what)
										{
										case EventMessage.HTTP_FAIL:
											Global.showDidlog(theActivity, selfHandler, null,
													"Send Examination Data Fail", Type.INVALID);
											break;
										case HttpStatus.SC_OK:
											Global.showDidlog(theActivity, selfHandler, null,
													"Send Examination Data Success", Type.INVALID);
											break;
										case EventMessage.MSG_CLOSE_MESSAGE_DIALOG:
											onDialog(msg.arg1);
											break;
										}
									}
								};
}
