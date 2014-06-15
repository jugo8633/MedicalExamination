package com.medici.app.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.SparseArray;

public class SqliteHandler extends SQLiteOpenHelper
{

	public static final String	TABLE_NAME			= "login";
	public static final String	COLUMN_ACCOUNT		= "account";
	public static final String	COLUMN_PASSWORD		= "password";
	private final static String	DATABASE_NAME		= "medicalexamination.db";
	private final static int	DATABASE_VERSION	= 1;

	public class LoginData
	{
		public String	mstrAccount		= null;
		public String	mstrPassword	= null;

		public LoginData(String strAccount, String strPassword)
		{
			mstrAccount = strAccount;
			mstrPassword = strPassword;
		}
	}

	public SqliteHandler(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public SqliteHandler(Context context, String name, CursorFactory factory, int version)
	{
		super(context, name, factory, version);
	}

	public SqliteHandler(Context context, String name, CursorFactory factory, int version,
			DatabaseErrorHandler errorHandler)
	{
		super(context, name, factory, version, errorHandler);
	}

	/**
	 * 如果資料庫不存在 則呼叫onCreate
	 */
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		final String DATABASE_CREATE_TABLE = "create table " + TABLE_NAME + "("
				+ "_ID INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL," + COLUMN_ACCOUNT + " TEXT," + COLUMN_PASSWORD
				+ " TEXT" + ")";
		try
		{
			db.execSQL(DATABASE_CREATE_TABLE);
			Logs.showTrace("Create Database Success!!");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * 版本更新時被呼叫 oldVersion=舊的資料庫版本；newVersion=新的資料庫版本
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //刪除舊有的資料表
		onCreate(db);
	}

	/**
	 * 每次成功打開數據庫後首先被執行     
	 */
	@Override
	public void onOpen(SQLiteDatabase db)
	{
		super.onOpen(db);
	}

	@Override
	public synchronized void close()
	{
		super.close();
	}

	public void addAccount(String strAccount, String strPassword)
	{
		long nRet = Type.INVALID;
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_ACCOUNT, strAccount);
		values.put(COLUMN_PASSWORD, strPassword);
		nRet = db.insert(TABLE_NAME, null, values);
		if (-1 == nRet)
		{
			Logs.showTrace("Add Database Data Fail!!");
		}
		db.close();
	}

	public void deleteAccount(String strAccount, String strPassword)
	{
		int nRet = Type.INVALID;
		SQLiteDatabase db = this.getWritableDatabase();
		nRet = db.delete(TABLE_NAME, COLUMN_ACCOUNT + " = " + strAccount + " and " + COLUMN_PASSWORD + " = "
				+ strPassword, null);
		if (0 == nRet)
		{
			Logs.showTrace("Delete Database Data Fail!!");
		}
		db.close();
	}

	public void updateAccount(String strOldAccount, String strOldPassword, String strNewAccount, String strNewPassword)
	{
		int nRet = Type.INVALID;
		ContentValues values = new ContentValues();
		values.put(COLUMN_ACCOUNT, strNewAccount);
		values.put(COLUMN_PASSWORD, strNewPassword);

		SQLiteDatabase db = this.getWritableDatabase();
		nRet = db.update(TABLE_NAME, values, COLUMN_ACCOUNT + " = " + strOldAccount + " and " + COLUMN_PASSWORD + " = "
				+ strOldPassword, null);
		if (0 >= nRet)
		{
			Logs.showTrace("Update Database Fail!!");
		}
		db.close();
	}

	public void getAccountData(SparseArray<LoginData> listData)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT " + COLUMN_ACCOUNT + " , " + COLUMN_PASSWORD + " FROM " + TABLE_NAME
				+ " ORDER BY _ID DESC", null);
		int rows_num = cursor.getCount();//取得資料表列數
		if (rows_num != 0)
		{
			LoginData loginData = null;
			cursor.moveToFirst(); //將指標移至第一筆資料
			for (int i = 0; i < rows_num; ++i)
			{
				loginData = new LoginData(cursor.getString(0), cursor.getString(1));
				listData.put(listData.size(), loginData);
				loginData = null;
				cursor.moveToNext();//將指標移至下一筆資料
			}
		}
		cursor.close(); //關閉Cursor
		db.close();
	}

	public int getAccountData(SparseArray<LoginData> listData, String strAccount)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT " + COLUMN_ACCOUNT + " , " + COLUMN_PASSWORD + " FROM " + TABLE_NAME
				+ " WHERE " + COLUMN_ACCOUNT + "=?" + " ORDER BY _ID DESC", new String[] { strAccount });

		int rows_num = cursor.getCount();//取得資料表列數
		if (rows_num != 0)
		{
			LoginData loginData = null;
			cursor.moveToFirst(); //將指標移至第一筆資料
			for (int i = 0; i < rows_num; ++i)
			{
				loginData = new LoginData(cursor.getString(0), cursor.getString(1));
				listData.put(listData.size(), loginData);
				loginData = null;
				cursor.moveToNext();//將指標移至下一筆資料
			}
		}
		cursor.close(); //關閉Cursor
		db.close();
		return rows_num;
	}
}
