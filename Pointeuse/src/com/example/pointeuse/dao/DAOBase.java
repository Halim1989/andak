package com.example.pointeuse.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class DAOBase extends SQLiteOpenHelper{

	protected final static int VERSION = 1;

	protected final static String NOM = "database.db";

	protected SQLiteDatabase mDb = null;

	protected MouvementDAO mHandler = null;

	public DAOBase(Context context) {
		
		super(context, NOM, null, VERSION);
		
	}
	
	public SQLiteDatabase open() {
		mDb = getWritableDatabase();
		return mDb;
	}

	public void close() {
		mDb.close();
	}

	public SQLiteDatabase getDb() {
		return mDb;
	}
	
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(MouvementDAO.TABLE_CREATE);
		System.out.println("Table " + MouvementDAO.TABLE_NAME + " created");
		db.execSQL(EmployeDAO.TABLE_CREATE);
		System.out.println("Table " + EmployeDAO.TABLE_NAME + " created");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL(MouvementDAO.TABLE_DROP);
		System.out.println("Table " + MouvementDAO.TABLE_NAME + " destroyed");
		db.execSQL(EmployeDAO.TABLE_DROP);
		System.out.println("Table " + EmployeDAO.TABLE_NAME + " destroyed");
		onCreate(db);
		
	}

}
