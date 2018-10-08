package ir.sweetsoft.wordbox.wordbox;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
