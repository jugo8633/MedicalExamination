package com.medical.medicalexamination.model;

import android.content.Context;
import android.util.SparseArray;

public class LoginHandler
{

	private SqliteHandler	sqliteHandler	= null;

	public LoginHandler(Context context)
	{
		super();
		sqliteHandler = new SqliteHandler(context);
	}

	@Override
	protected void finalize() throws Throwable
	{
		sqliteHandler.close();
		sqliteHandler = null;
		super.finalize();
	}

	public boolean isAccountExist(final String strAccount)
	{
		SparseArray<SqliteHandler.LoginData> listData = new SparseArray<SqliteHandler.LoginData>();
		int nCount = sqliteHandler.getAccountData(listData, strAccount);
		if (0 >= nCount)
		{
			return false;
		}
		return true;
	}

	public void addAccount(String strAccount, String strPassword)
	{
		if (!isAccountExist(strAccount))
		{
			sqliteHandler.addAccount(strAccount, strPassword);
		}
	}
}
