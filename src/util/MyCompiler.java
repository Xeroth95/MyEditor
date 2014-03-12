package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;

import GUI.MyGui;

public class MyCompiler {
    static MyGui gui;
    static String path;
    static File gcc;
    static File nasm;
    static String format;
	
    public static void setGui(MyGui gui) {
	MyCompiler.gui = gui;
	if (gui.getSystem().matches("linux")) {
	    MyCompiler.path = "linux/";
	    MyCompiler.format = "coff";
	} if (gui.getSystem().matches("win.*")) {
	    MyCompiler.path = "win/";
	    MyCompiler.format = "win32";
	}
		
	try {
	    MyCompiler.gcc = new File(MyCompiler.class.getClassLoader().getResource(path + "gcc").toURI().getPath());
	} catch (URISyntaxException e) {
	    e.printStackTrace();
	}
    }
	
    private static File assemble(File toAssemble) throws IOException {
	File objectFile = new File(toAssemble.getPath().substring(0, toAssemble.getPath().indexOf(".asm")) + "1.o");
	String cmd = "\"" + MyCompiler.nasm.getPath() + "\" -o \"" + objectFile.getPath() + "\" -f + "+ MyCompiler.format + " " + toAssemble.getName();
	Process p = Runtime.getRuntime().exec(cmd, null, toAssemble.getParentFile());
	new Thread(new SyncPipe(p.getErrorStream(), MyCompiler.gui.getConsoleStream())).start();
	new Thread(new SyncPipe(p.getInputStream(), MyCompiler.gui.getConsoleStream())).start();
	p.destroy();
	return objectFile;
    }
	
    private static File compile(File toCompile) throws IOException {
		
	File objectFile = new File(toCompile.getPath().substring(0, toCompile.getPath().indexOf(".c")) + ".o");
	String cmd = MyCompiler.gcc.getPath() + "/gcc -o \"" + objectFile.getPath() + "\" -c \"" + toCompile.getPath() + "\"";
	Process p = Runtime.getRuntime().exec(cmd, null, gcc);
	new Thread(new SyncPipe(p.getErrorStream(), MyCompiler.gui.getConsoleStream())).start();
	new Thread(new SyncPipe(p.getInputStream(), MyCompiler.gui.getConsoleStream())).start();
	p.destroy();
	return objectFile;
    }
	
    public static File getObjectFile(File toCompile) {
	try {
	    if (toCompile.getName().endsWith(".asm")) {
		return assemble(toCompile);
	    } else if (toCompile.getName().endsWith(".c")) {
		return compile(toCompile);
	    } else {
		return null;
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	    return null;
	}
    }
	
    private static class SyncPipe implements Runnable
    {
	private final OutputStream ostrm_;
	private final InputStream istrm_;
		  
	public SyncPipe(InputStream istrm, OutputStream ostrm) {
	    istrm_ = istrm;
	    ostrm_ = ostrm;
	}
	public void run() {
	    try
		{
		    final byte[] buffer = new byte[1024];
		    for (int length = 0; (length = istrm_.read(buffer)) != -1; )
			{
			    ostrm_.write(buffer, 0, length);
			}
		}
	    catch (Exception e)
		{
		    e.printStackTrace();
		}
	}
    }
}
