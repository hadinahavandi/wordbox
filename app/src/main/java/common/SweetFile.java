package common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
/*
 * last update:950314
 * 
 * 
 */
public class SweetFile {
	private Context theContext;
	public SweetFile(Context theContext)
	{
		this.theContext=theContext;
	}
	public void WriteInStorage(String Directory,String FileName,String Content)
	{
		File dir=new File(Environment.getExternalStorageDirectory().toString()+"/"+Directory);
		if(!(dir.exists() && dir.isDirectory()))
			dir.mkdirs();
		File sweetFile=new File(dir, FileName);
			FileWriter fileWriter;
			try {
				fileWriter = new FileWriter(sweetFile);
				BufferedWriter out=new BufferedWriter(fileWriter);
				out.write(Content.toString());
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	public void WriteInStorage(String Directory,String FileName,InputStream Stream)
	{
		File dir=new File(Environment.getExternalStorageDirectory().toString()+"/"+Directory);
		if(!(dir.exists() && dir.isDirectory()))
			dir.mkdirs();

		File sweetFile=new File(dir, FileName);
		FileOutputStream output;
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		int len = 0;
		try {
			output = new FileOutputStream(sweetFile);
			while ((len = Stream.read(buffer)) != -1) {
			    output.write(buffer, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	public InputStream getURLInputStream(String Url)
	{
		InputStream InputS=null;
		try {
			URL theUrl=new URL(Url);
			HttpURLConnection Connection=(HttpURLConnection) theUrl.openConnection();
			Connection.setDoInput(true);
			Connection.connect();
			InputS=Connection.getInputStream();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return InputS;
		
	}
	public Boolean DownloadFile(String Url,String Directory,String FileName)
	{
	/*	Net theNet=new Net(this.theContext);
		if(theNet.isNetworkConnected() && theNet.IsInternetAvailable())
		{
			InputStream InputS=null;
			try {
				URL theUrl=new URL(Url);
				HttpURLConnection Connection=(HttpURLConnection) theUrl.openConnection();
				Connection.setDoInput(true);
				Connection.setRequestProperty("Connection", "close");
				Connection.connect();
				int file_size_onserver = Connection.getContentLength();
				InputS=Connection.getInputStream();
				WriteInStorage(Directory, FileName+"tmp", InputS);
				File tmpfile=new File(Environment.getExternalStorageDirectory().toString()+"/"+Directory+"/"+FileName+"tmp");
				File finalfile=new File(Environment.getExternalStorageDirectory().toString()+"/"+Directory+"/"+FileName);
				if(finalfile.exists())
					finalfile.delete();
				if(tmpfile.exists() && !finalfile.exists())
					if(file_size_onserver==tmpfile.length())
						tmpfile.renameTo(finalfile);
					else
						tmpfile.delete();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	*/
		return false;
	}
	public static void copyfile(String srFile, String dtFile){
        try{
            File f1 = new File(srFile);
            File f2 = new File(dtFile);
            InputStream in = new FileInputStream(f1);

//                  If you want to append the file.
//          OutputStream out = new FileOutputStream(f2,true);

            //For Overwrite the file.
            OutputStream out = new FileOutputStream(f2);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0){
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            System.out.println("File copied.");
        }
        catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());

        }
        catch(IOException e){
            System.out.println(e.getMessage());         
        }
    }
	public static void deletefile(String FileName){
        File f1 = new File(FileName);
		f1.delete();
    }
	public static String ReadFileToString(String FilePath)
	{
		
		File file = new File(FilePath);

		StringBuilder text = new StringBuilder();

		try {
		    BufferedReader br = new BufferedReader(new FileReader(file));
		    String line;

		    while ((line = br.readLine()) != null) {
		    	//Log.d("Line Is:", line);
		        text.append(line);
		        text.append('\n');
		    }
		    return text.toString();
		}
		catch (IOException e) {
		    return null;
		}
	}
}
