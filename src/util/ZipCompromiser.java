package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collections;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipCompromiser {
	
	static File root;
	private static final byte[] buffer = new byte[8192];
	
    public static void zip(File input, File output) {
    	try {
    		root = input;
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(output));
			zipFile(input, zos);
			zos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static File unzip(File input) {
    	try {
			ZipFile zin = new ZipFile(input);
			File output = Files.createTempDirectory(null).toFile();
			output.deleteOnExit();
			for ( ZipEntry entry : Collections.list( zin.entries() ) )
	        {
	          extractEntry( zin, entry, output.getAbsolutePath() );
	        }
			return output;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    	
    }
    
    private static void zipFile(File input, ZipOutputStream zos) {
    	if (input.isDirectory()) {
    		String path = getRelative(input);
    		if (path != null) {
	    		if (!path.endsWith(File.separator))
	    			path += '/';
	    		else {
	    			path.replaceAll(File.separator, "/");
	    		}
	    		try {
					zos.putNextEntry(new ZipEntry(path));
					zos.closeEntry();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    		File[] files = input.listFiles();
    		for (File f : files) {
    			if (f != null)
    				zipFile(f, zos);
    		}
    	} else {
    		FileInputStream fis;
			try {
				byte[] readBuffer = new byte[8192];
		    	int bytesIn = 0;
				fis = new FileInputStream(input);
	        	ZipEntry anEntry = new ZipEntry(getRelative(input));
	        	zos.putNextEntry(anEntry);
	        	while((bytesIn = fis.read(readBuffer)) != -1) 
	            { 
	                zos.write(readBuffer, 0, bytesIn); 
	            }
	        	zos.closeEntry();
	        	fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }
    
    private static String getRelative(File child) {
    	if (root.equals(child))
    		return null;
    	return child.getAbsolutePath().substring(root.getAbsolutePath().length()+1);
    }
    
    private static void extractEntry( ZipFile zipFile, ZipEntry entry, String destDir ) throws IOException
    {
    	File file = new File( destDir, entry.getName() );

    	if ( entry.isDirectory() )
    		file.mkdirs();
    	else {
    		new File( file.getParent() ).mkdirs();

    		InputStream  is = null;
    		OutputStream os = null;

    		try
    		{
    			is = zipFile.getInputStream( entry );
    			os = new FileOutputStream( file );

    			for ( int len; (len = is.read(buffer)) != -1; )
    				os.write( buffer, 0, len );
    		}
    		finally
    		{
    			if ( os != null ) os.close();
    			if ( is != null ) is.close();
    		}
    	}
    }
} 
