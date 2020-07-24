package com.example.demoandroid.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.demoandroid.entity.DatabaseGlobal;


/**
 * 
 * @author tangjiabing
 * 
 * @see ��Դʱ�䣺2016��03��31��
 * 
 *      �ǵø��Ҹ�starŶ~
 * 
 */
public class DbOpenHelperUtil extends SQLiteOpenHelper {

	public DbOpenHelperUtil(Context context) {
		super(context, DatabaseGlobal.DATABASE_NAME, null,
				DatabaseGlobal.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuilder builder = new StringBuilder();
		builder.append("create table ").append(DatabaseGlobal.TABLE_EMAIL_OBJS)
				.append(" ( ").append(DatabaseGlobal.FIELD_ID)
				.append(" integer primary key autoincrement,")
				.append(DatabaseGlobal.FIELD_NAME).append(" text,")
				.append(DatabaseGlobal.FIELD_ADDRESS).append(" text,")
				.append(DatabaseGlobal.FIELD_OUT_KEY).append(" text")
				.append(" )");
		db.execSQL(builder.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
