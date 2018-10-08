package common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHandler extends SQLiteOpenHelper {
	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 3;
    private Context context;
    // Database Name
    private static final String DATABASE_NAME = "sweetsoft2.db";

    private String DBName;
    private int DBVersion;
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    	DBName=DATABASE_NAME;
    	DBVersion=DATABASE_VERSION;
        this.context=context;
    }
    public DatabaseHandler(Context context,String DatabaseName,int DatabaseVersion) {
        super(context, DatabaseName, null, DatabaseVersion);

    	DBName=DatabaseName;
    	DBVersion=DatabaseVersion;
        this.context=context;
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        /*String CREATE_POST_TABLE = "CREATE TABLE post(_id INTEGER PRIMARY KEY,title TEXT,content TEXT,summary TEXT,idinsite INTEGER UNIQUE)";
        String CREATE_CONSTANT_TABLE = "CREATE TABLE constant(_id INTEGER PRIMARY KEY,name TEXT,value TEXT)";
        db.execSQL(CREATE_POST_TABLE);
        db.execSQL(CREATE_CONSTANT_TABLE);*/
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
//        db.execSQL("CREATE TABLE `onlinephoto` (`_id`	INTEGER PRIMARY KEY AUTOINCREMENT,`url`	TEXT,`localpath`	TEXT,`isdeleted`	INTEGER DEFAULT 0);");
        //db.execSQL("DROP TABLE IF EXISTS constant");
        // Create tables again
        onCreate(db);
    }
    public boolean Initialize()
    {
    	return Initialize("init.db");
    }
    public boolean Initialize(String initDBName)
    {
    	boolean inited=false;
    	try {
    		String Dir="/data/data/" + context.getPackageName()
                    + "/databases/";
            String destPath = Dir+DBName;

            File f = new File(destPath);
            if(!f.exists())
            {
            	File dir=new File(Dir);
            	dir.mkdirs();
            	InputStream in = context.getAssets().open(initDBName);
            	OutputStream out = new FileOutputStream(destPath);

            	byte[] buffer = new byte[1024];
            	int length;
            	while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
                inited=true;
            }
            in.close();
            out.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    	return inited;
    }

    public void Import(String FilePath)
    {
    	try {
    		PackageManager m = context.getPackageManager();
    		String APPPath = context.getPackageName();
    		PackageInfo p = null;
			try {
				p = m.getPackageInfo(APPPath, 0);
				APPPath = p.applicationInfo.dataDir;
				Log.d("sweetsoft","importing DB In APPPath:\n"+APPPath);
				Log.d("sweetsoft","importing DB From:"+FilePath);
				
			} catch (NameNotFoundException e) {
				
				e.printStackTrace();
			}
    		
    		String Dir=APPPath+ "/databases/";
            String dbPath = Dir+DBName;
            
            File f = new File(FilePath);
			Log.d("sweetsoft","importing DB Size:"+String.valueOf(f.length()));
            File DBFile = new File(dbPath);
            if(DBFile.exists())
            {
            	f.mkdirs();
            	File dir=new File(Dir);
            	dir.mkdirs();
            	InputStream in = new FileInputStream(FilePath);
            	OutputStream out = new FileOutputStream(dbPath);

            	byte[] buffer = new byte[1024];
            	int length;
            	while ((length = in.read(buffer)) > 0) 
            	{
            		out.write(buffer, 0, length);
            	}
            	in.close();
            	out.close();
            }
            Log.d("sweetsoft", "Importing Database ");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean Export(String FileDirectory,String FileName)
    {
    	boolean inited=false;
    	try {
    		PackageManager m = context.getPackageManager();
    		String APPPath = context.getPackageName();
    		PackageInfo p = null;
			try {
				p = m.getPackageInfo(APPPath, 0);
				APPPath = p.applicationInfo.dataDir;
				Log.d("sweetsoft", "Exporting to AppPath:\n"+APPPath);
				Log.d("sweetsoft", "Exporting File:\n"+FileName);
				Log.d("sweetsoft", "Exporting FileDirectory:\n"+FileDirectory);
				
			} catch (NameNotFoundException e) {
				
				e.printStackTrace();
			}
    		
    		String Dir=APPPath+ "/databases/";
            String dbPath = Dir+DBName;
            

            File f = new File(FileDirectory);
            File DBFile = new File(dbPath);

			Log.d("sweetsoft","exporting DB Size:"+String.valueOf(DBFile.length()));
            if(DBFile.exists())
            {
            	f.mkdirs();
            	File dir=new File(Dir);
            	dir.mkdirs();
            	InputStream in = new FileInputStream(dbPath);
            	OutputStream out = new FileOutputStream(FileDirectory+FileName);

            	byte[] buffer = new byte[1024];
            	int length;
            	while ((length = in.read(buffer)) > 0) 
            	{
            		out.write(buffer, 0, length);
            		inited=true;
            	}
            	in.close();
            	out.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    	return inited;
    }
}
