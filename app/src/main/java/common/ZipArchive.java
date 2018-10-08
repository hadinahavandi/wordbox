package common;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipArchive { 
	  private String _zipFile; 
	  private String _location; 
	 
	  public ZipArchive(String zipFile, String location) { 
	    _zipFile = zipFile; 
	    _location = location; 
	    Log.v("sweetsoft", "ZipArchive-ZipFile :\n" + _zipFile); 
    	Log.v("sweetsoft", "ZipArchive-Location :\n" + _location); 
	    _dirChecker(""); 
	  } 
	 
	  public void unzip() { 
	    try  { 
	    	
	      FileInputStream fin = new FileInputStream(_zipFile); 
	      ZipInputStream zin = new ZipInputStream(fin); 
	      ZipEntry ze = null; 
	      while ((ze = zin.getNextEntry()) != null) { 
	        Log.v("sweetsoft", "Unzipping :\n" + ze.getName()); 
	 
	        if(ze.isDirectory()) { 
	          _dirChecker(ze.getName()); 
	        } else { 
	          FileOutputStream fout = new FileOutputStream(_location + ze.getName()); 
	          int count=0;
	          byte[] buffer = new byte[4096];
	          while ((count = zin.read(buffer)) != -1) 
	             {
	                 fout.write(buffer, 0, count);             
	             }
	          /*for (int c = zin.read(); c != -1; c = zin.read()) { 
	            fout.write(c); 
	          } */
	 
	          zin.closeEntry(); 
	          fout.close(); 
	        } 
	         
	      } 
	      zin.close(); 
	    } catch(Exception e) { 
	      Log.e("sweetsoft", "Error In Zip Archive Unzip:", e); 
	    } 
	 
	  } 
	 
	  private void _dirChecker(String dir) { 
	    File f = new File(_location + dir); 
	 
	    if(!f.isDirectory()) { 
	      f.mkdirs(); 
	    } 
	  } 
	  public static void zipFolder(String inputFolderPath, String outZipPath) {
		    try {
		        FileOutputStream fos = new FileOutputStream(outZipPath);
		        ZipOutputStream zos = new ZipOutputStream(fos);
		        File srcFile = new File(inputFolderPath);
		        File[] files = srcFile.listFiles();
		        Log.d("sweetsoft", "Zip directory: " + srcFile.getName());
		        for (int i = 0; i < files.length; i++) {
		            Log.d("sweetsoft", "Adding file: " + files[i].getName());
		            byte[] buffer = new byte[40960];
		            FileInputStream fis = new FileInputStream(files[i]);
		            zos.putNextEntry(new ZipEntry(files[i].getName()));
		            int length;
		            while ((length = fis.read(buffer)) > 0) {
		                zos.write(buffer, 0, length);
		            }
		            zos.closeEntry();
		            fis.close();
		        }
		        zos.close();
		    } catch (IOException ioe) {
		        Log.e("sweetsoft","Error In Zip Archive:"+ ioe.getMessage());
		    }
		}
	} 
