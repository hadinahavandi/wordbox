package ir.sweetsoft.wordbox.wordbox;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
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
    private static String getAppPath(Context context) {
        PackageManager m = context.getPackageManager();
        String APPPath = context.getPackageName();
        PackageInfo p = null;
        try {
            p = m.getPackageInfo(APPPath, 0);
            APPPath = p.applicationInfo.dataDir;
            return APPPath;

        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }
        return "";
    }

    private static String getDBPath(Context context, String DBName) {
        String Dir = getAppPath(context) + "/databases/";
        String dbPath = Dir + DBName;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            dbPath = context.getDatabasePath(DBName).getPath();
        }
        return dbPath;
    }

    private static void removeMetaFiles( String dbPath)
    {
        File WalFile = new File(dbPath + "-wal");
        if (WalFile.exists())
            WalFile.delete();
        File SHMFile = new File(dbPath + "-shm");
        if (SHMFile.exists())
            SHMFile.delete();
    }
    public static void Import(String FilePath, Context context, String DBName) {
        try {

            String dbPath = getDBPath(context, DBName);
            File ImportingFile = new File(FilePath);
            Log.d("sweetsoft", "importing DB Size:" + String.valueOf(ImportingFile.length()));
            Log.d("sweetsoft", "importing DB File:" + dbPath);
//            File DBFile = new File(dbPath);
            if (ImportingFile.exists()) {
                removeMetaFiles(dbPath);
                InputStream in = new FileInputStream(FilePath);
                OutputStream out = new FileOutputStream(dbPath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
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

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public static void ImportFromAssets(String FileName, Context context, String DBName) {

        String dbPath = getDBPath(context, DBName);
        AssetManager assetManager = context.getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            removeMetaFiles(dbPath);
            in = assetManager.open(FileName);
            File outFile = new File(dbPath);
            out = new FileOutputStream(outFile);
            copyFile(in, out);
        } catch (IOException e) {
            Log.e("tag", "Failed to copy asset file: " + FileName, e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
        }


    }

    public static boolean Export(String FileDirectory, String FileName, Context context, String DBName) {
        boolean inited = false;
        try {
            String dbPath = getDBPath(context,DBName);

            File BackupFile = new File(FileDirectory);
            File DBFile = new File(dbPath);

            Log.d("sweetsoft", "exporting DB Size:" + String.valueOf(DBFile.length()));
            if (DBFile.exists()) {
                BackupFile.mkdirs();
                InputStream in = new FileInputStream(dbPath);
                OutputStream out = new FileOutputStream(FileDirectory + "/" + FileName);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                    inited = true;
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
