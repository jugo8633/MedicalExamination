package com.medici.app.model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

public abstract class HttpClientHandler
{
	protected Activity	theActivity	= null;
	private final int	HTTP_OK		= 200;

	public class Response
	{
		public int		mnCode		= 0;
		public String	mstrMessage	= null;
	}

	public HttpClientHandler(Activity activity)
	{
		theActivity = activity;
	}

	protected abstract boolean onResponse(int nCode, String strMessage);

	protected void sendGet(final String strURL, final String strParam, Response resp) throws Exception
	{
		String url = strURL + "?" + strParam;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		resp.mnCode = con.getResponseCode();

		if (HTTP_OK == resp.mnCode)
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			if (null != in)
			{
				while ((inputLine = in.readLine()) != null)
				{
					response.append(inputLine.trim());
				}
				in.close();
				resp.mstrMessage = response.toString();
			}
			return;
		}

		resp.mstrMessage = String.format("Code:%d , Error:%s", con.getResponseCode(), con.getResponseMessage());
	}

	// HTTP POST request
	protected void sendPost(final String strURL, final String strParam, Response resp) throws Exception
	{
		URL obj = new URL(strURL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");

		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(strParam);
		wr.flush();
		wr.close();

		resp.mnCode = con.getResponseCode();

		if (HTTP_OK == resp.mnCode)
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			if (null != in)
			{
				while ((inputLine = in.readLine()) != null)
				{
					response.append(inputLine.trim());
				}
				in.close();
				resp.mstrMessage = response.toString();
			}
		}
		else
		{
			resp.mstrMessage = String.format("Code:%d , Error:%s", con.getResponseCode(), con.getResponseMessage());
		}

		onResponse(resp.mnCode, resp.mstrMessage);
	}

	private Handler	selfHandler	= new Handler()
								{
									@Override
									public void handleMessage(Message msg)
									{

									}
								};
}
