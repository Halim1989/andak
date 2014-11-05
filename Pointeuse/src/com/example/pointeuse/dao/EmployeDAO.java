package com.example.pointeuse.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pointeuse.models.Employe;

public class EmployeDAO extends DAOBase{

	private Context context;
	
	public static final String TABLE_NAME = "employe";
	public static final String KEY = "id";
	public static final String FIRST_NAME = "first_name";
	public static final String LAST_NAME = "last_name";

	public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME
			+ " (" + KEY + " TEXT PRIMARY KEY, "
			+ 		FIRST_NAME + " TEXT,"
			+ 		LAST_NAME + " TEXT );";
	
	public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	
	public EmployeDAO(Context context) {
		
		super(context);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		super.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		super.onUpgrade(db, oldVersion, newVersion);
	}

	public Employe getEmploye(String id){
		
		open();
		Cursor c = mDb.rawQuery("select " + KEY + ", "+ FIRST_NAME + ", " + LAST_NAME + " from " + TABLE_NAME + " WHERE " + KEY + " = ?" , new String[]{id});
		Employe employe = null;
		while (c.moveToNext()) {
			employe = new Employe();
			employe.setId(id);
			employe.setFirstName(c.getString(1));
			employe.setLastName(c.getString(2));
		}
		c.close();
		close();
		if(employe == null){
			System.out.println("No Employe");
		}
		return employe;
		
	}
	
	public void create(Employe employe){
		open();
		ContentValues value = new ContentValues();
		value.put(KEY, employe.getId());
		value.put(FIRST_NAME, employe.getFirstName());
		value.put(LAST_NAME, employe.getLastName());
		mDb.insert(TABLE_NAME, null, value);
		close();
	}
	
	public void update(Employe employe){
		
		ContentValues value = new ContentValues();
		
		boolean doUpdate = false;
		if(!employe.getFirstName().equals("")){
			value.put(FIRST_NAME, employe.getFirstName());
			doUpdate = true;
		}
		if(!employe.getLastName().equals("")){
			value.put(LAST_NAME, employe.getLastName());
			doUpdate = true;
		}
		
		if(doUpdate){
			open();
			mDb.update(TABLE_NAME, value, KEY + " = ?", new String[]{employe.getId()});
			close();
		}
	}
	
	public void delete(){
		open();
		mDb.delete(TABLE_NAME, "", new String[]{});
		close();
	}
}
