package ir.sweetsoft.wordbox.wordbox;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Hadi on 30/03/2017.
 */

public class DBTools {
    public static void Import(String FilePath, Context context,String DBName)
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

            } catch (PackageManager.NameNotFoundException e) {

                e.printStackTrace();
            }


            String Dir=APPPath+ "/databases/";
            String dbPath = Dir+DBName;
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                dbPath=context.getDatabasePath(DBName).getPath();
            }
            File ImportingFile = new File(FilePath);
            Log.d("sweetsoft","importing DB Size:"+String.valueOf(ImportingFile.length()));
            Log.d("sweetsoft","importing DB File:"+dbPath);
            File DBFile = new File(dbPath);
            if(ImportingFile.exists())
            {
//                ImportingFile.mkdirs();
//                File dir=new File(Dir);
//                dir.mkdirs();
                File WalFile=new File(dbPath+"-wal");
                if(WalFile.exists())
                    WalFile.delete();
                File SHMFile=new File(dbPath+"-shm");
                if(SHMFile.exists())
                    SHMFile.delete();
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
    public static boolean Export(String FileDirectory,String FileName, Context context,String DBName)
    {
        boolean inited=false;
        try {
            PackageManager m = context.getPackageManager();
            String APPPath = context.getPackageName();
            PackageInfo p = null;
            try {
                p = m.getPackageInfo(APPPath, 0);
                APPPath = p.applicationInfo.dataDir;
                Log.d("sweetsoft", "Exporting from AppPath:\n"+APPPath);
                Log.d("sweetsoft", "Exporting File:\n"+FileName);
                Log.d("sweetsoft", "Exporting to FileDirectory:\n"+FileDirectory);

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            String Dir=APPPath+ "/databases/";
            String dbPath = Dir+DBName;
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                dbPath=context.getDatabasePath(DBName).getPath();
            }

            File f = new File(FileDirectory);
            File DBFile = new File(dbPath);

            Log.d("sweetsoft","exporting DB Size:"+String.valueOf(DBFile.length()));
            if(DBFile.exists())
            {
                f.mkdirs();
                File dir=new File(Dir);
                dir.mkdirs();
                InputStream in = new FileInputStream(dbPath);
                OutputStream out = new FileOutputStream(FileDirectory+"/"+FileName);

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
