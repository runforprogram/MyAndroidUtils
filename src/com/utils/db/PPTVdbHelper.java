package com.utils.db;

import java.util.ArrayList;
import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PPTVdbHelper extends SQLiteOpenHelper {
	private String tag = "PPTVdbHelper";
	private final static int DATABASE_VERSION = 1;
	private final static String database_name = "video_info.db";
	private String table_name = "video";
	String id = "id"; // 内容的ID,经过加密
	String type = "type";// type 内容的大类，movie-电影 tv-电视剧 cartoon-动漫 show-综艺
	String status = "status";// status online-代表新上线,需调用视频详情接口更新分集信息
								// offline-代表已经下线,不允许前端展示
	// update-代表分集内容有更新，重新调用视频详情接口更新分集
	String name = "name"; // name 内容的中文名称
	String downloadable = "downloadable";// downloadable 1-允许下载#0-不允许下载
	String imageUrl = "imageUrl"; // 是否增加进去？？？

	public PPTVdbHelper(Context context) {
		super(context, database_name, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String videoSql = "Create table " + table_name + "(" + this.id
				+ " text primary key," + this.type + " text ," + status
				+ " text ," + this.name + " text ," + this.downloadable
				+ " text ," + this.imageUrl + " text " + ");";
		Log.i("tag", "sql=" + videoSql);
		db.execSQL(videoSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql = " DROP TABLE IF EXISTS " + table_name;//如果数据库中已经存在这个表 删除
		db.execSQL(sql);
		onCreate(db);
	}

	/* 主键id是否存在数据库中 */
	public boolean ifInDb(String id) {

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + table_name
				+ " WHERE id LIKE " + "'" + id + "'", null);
		boolean value = cursor.getCount() == 0 ? false : true;
		cursor.close();
		return value;
	}

	public long insert() {
		if (ifInDb("value")) { // value是id的值
			return -1;
		}
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(this.id, "value"); // value是id的值
		long row = db.insert(table_name, null, cv);
		return row;
	}

	public void update(String id, String imageUrl) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = this.id + "=?";
		// where代表列的名字，
		// wherevalue代表列的值，结合起来找出哪一行，
		// wherevalue怎是一个数组
		String[] whereValue = { id };
		ContentValues cv = new ContentValues();
		cv.put(this.imageUrl, imageUrl);
		db.update(table_name, cv, where, whereValue);

	}

	/*
	 * 根据影片名字搜索
	 */

	public void search(String name) {

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + table_name
				+ " WHERE name LIKE " + "'%" + name + "%'", null);

		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToNext();
			cursor.getString(cursor.getColumnIndexOrThrow("id"));// 取出id的值

		}
	}

	public Cursor getTypeDataCursor(String type) {

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + table_name
				+ " WHERE type LIKE " + "'" + type + "'", null);
		return cursor;
	}

	public int getTypeNum(String type) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + table_name
				+ " WHERE type LIKE " + "'" + type + "'", null);
		return cursor.getCount();

	}

	public Cursor select(String type) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM video WHERE type LIKE "
				+ "'" + type + "'", null);
		return cursor;
	}

}
