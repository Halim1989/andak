package com.example.pointeuse.dao;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pointeuse.models.Employe;
import com.example.pointeuse.models.Mouvement;
import com.example.pointeuse.util.Util;

public class MouvementDAO extends DAOBase{

	private Context context;
	
	public static final String TABLE_NAME = "mouvement";
	public static final String KEY = "id";
	public static final String EMPLOYE_ID = "employe_id";
	public static final String DATE = "date";
	public static final String INDATE = "in_date";
	public static final String OFFDATE = "off_date";
	
	public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME
			+ " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ 		EMPLOYE_ID + " TEXT,"
			+ 		DATE + " TEXT,"
			+ 		INDATE + " TEXT, " 
			+ 		OFFDATE + " TEXT);";
	
	public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	
	public MouvementDAO(Context context) {
		
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

	public void create(Mouvement mouvement) {
		open();
		ContentValues value = new ContentValues();
		value.put(MouvementDAO.EMPLOYE_ID, mouvement.getEmploye().getId());
		value.put(MouvementDAO.DATE, Util.getFormatedDate(mouvement.getDate()));
		value.put(MouvementDAO.INDATE, mouvement.getInDate());
		value.put(MouvementDAO.OFFDATE, mouvement.getOffDate());
		mDb.insert(TABLE_NAME, null, value);
		close();
	}
	
	public void update(Mouvement mouvement){
		open();
		ContentValues values = new ContentValues();
		values.put(OFFDATE, mouvement.getOffDate());
		mDb.update(TABLE_NAME, values, KEY + " = ?" , new String[]{String.valueOf(mouvement.getId())});
		close();
	}
	
	public void delete(long id) {
		open();
		mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
		close();
	}
	
	public Mouvement getMouvementOfEmploye(String idEmploye, Date date){
		open();
		Mouvement mouvement = null;
		EmployeDAO employeDAO = new EmployeDAO(context);
		Employe employe = employeDAO.getEmploye(idEmploye);
		if(employe != null){
			mouvement = new Mouvement();
			mouvement.setEmploye(employe);
			Cursor c = mDb.rawQuery("SELECT " + KEY + ", " + INDATE + ", " + OFFDATE + " FROM " + TABLE_NAME + " WHERE " + EMPLOYE_ID + " = ? AND " + DATE + " = ? " , new String[]{idEmploye, Util.getFormatedDate(date)});
			boolean found = false;
			while (c.moveToNext() && !found) {
				mouvement.setDate(date);
				mouvement.setId(c.getInt(0));
				mouvement.setInDate(c.getString(1));
				mouvement.setOffDate(c.getString(2));
				found = true;
			}
			c.close();
		}
		close();
		return mouvement;
	}

	public ArrayList<Mouvement> getMouvements(Date date) {
		open();
		Cursor c = mDb.rawQuery("SELECT " + EMPLOYE_ID + ", " + INDATE + ", " + OFFDATE + ", " + EmployeDAO.FIRST_NAME + ", " + EmployeDAO.LAST_NAME +  " FROM " + TABLE_NAME + " m JOIN " + EmployeDAO.TABLE_NAME + " e ON  m." + EMPLOYE_ID + " = e." + EmployeDAO.KEY + " WHERE " + DATE + " = ?", new String[]{Util.getFormatedDate(date)});
		ArrayList<Mouvement> mouvements = new ArrayList<Mouvement>(); 
		while (c.moveToNext()) {
			Mouvement mouvement = new Mouvement();
			mouvement.setInDate(c.getString(1));
			mouvement.setOffDate(c.getString(2));
			mouvement.setEmploye(new Employe(c.getString(0), c.getString(3), c.getString(4)));
			mouvements.add(mouvement);
		}
		close();		
		return mouvements;
	}
	
	public void delete(){
		open();
		mDb.delete(TABLE_NAME, "", new String[]{});
		close();
	}
}
