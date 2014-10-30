package com.utils.db;
 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.example.myandroidutils.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

/**
 *  数据库操作，用户把raw下面的文件写入数据库
 * @author Administrator
 *
 */
public class DBManager {
    private final int BUFFER_SIZE = 1024;
    public static final String DB_NAME = "city.db";
    public static final String VIDEO_INFO_DB="video_info.db";
    public static final String PACKAGE_NAME = "com.luyou.training";
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"+ PACKAGE_NAME;
    private SQLiteDatabase database;
    private Context context;
    private File file=null;
    
    public DBManager(Context context) {
    	
        this.context = context;
    }
 
    public void openDatabase() {
    	
        this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
    }
    public SQLiteDatabase getDatabase(){
    	
    	return this.database;
    }
 
    private SQLiteDatabase openDatabase(String dbfile) {
        try {
        	Log.e("cc", "open and return");
        	file = new File(dbfile);
            if (!file.exists()) {
            	
            	InputStream is = context.getResources().openRawResource(R.raw.city);
            	if(is!=null){
            		Log.e("hck", "is null");
            	}else{
            	}
            	FileOutputStream fos = new FileOutputStream(dbfile);
            	if(is!=null){
            		Log.e("hck", "fosnull");
            	}else{
            	}
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count =is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                		Log.e("hck", "while");
                	fos.flush();
                }
                fos.close();
                is.close();
            }
            database = SQLiteDatabase.openOrCreateDatabase(dbfile,null);
            return database;
        } catch (FileNotFoundException e) {
            Log.e("hck", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("hck", "IO exception");
            e.printStackTrace();
        } catch (Exception e){
        	Log.e("hck", "exception "+e.toString());
        }
        return null;
    }
    public void closeDatabase() {
    	Log.e("hck", "closeDatabase()");
    	if(this.database!=null)
    		this.database.close();
    }
}