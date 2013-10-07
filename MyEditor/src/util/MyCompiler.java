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
	
	public static void setGui(MyGui gui) {
		MyCompiler.gui = gui;
	}
	
	private static File assemble(File toAssemble) throws IOException {
		File f = null;
		try {
			f = new File(MyCompiler.class.getClassLoader().getResource("nasm-win.exe").toURI().getPath());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		File objectFile = new File(toAssemble.getPath().substring(0, toAssemble.getPath().indexOf(".asm")) + "1.o");
		String cmd = "\"" + f.getPath() + "\" -o \"" + objectFile.getPath() + "\" -f win32 " + toAssemble.getName();
		Process p = Runtime.getRuntime().exec(cmd, null, toAssemble.getParentFile());
		new Thread(new SyncPipe(p.getErrorStream(), gui.getConsoleStream())).start();
	    new Thread(new SyncPipe(p.getInputStream(), gui.getConsoleStream())).start();
		p.destroy();
		return objectFile;
	}
	
	private static File compile(File toCompile) throws IOException {
		File f = null;
		try {
			f = new File(MyCompiler.class.getClassLoader().getResource("gcc-win.exe").toURI().getPath());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		File objectFile = new File(toCompile.getPath().substring(0, toCompile.getPath().indexOf(".c")) + ".o");
		String cmd = "\"" + f.getPath() + "\" -o -f win32 \"" + objectFile.getPath() + "\" -c " + toCompile.getName();
		Process p = Runtime.getRuntime().exec(cmd, null, toCompile.getParentFile());
		new Thread(new SyncPipe(p.getErrorStream(), gui.getConsoleStream())).start();
	    new Thread(new SyncPipe(p.getInputStream(), gui.getConsoleStream())).start();
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
