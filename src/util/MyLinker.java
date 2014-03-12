package util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;

import GUI.MyGui;

public class MyLinker {
	
	protected static MyGui gui;
	protected static File compiler, assembler, linker;
	protected static String path, format;
	
	public static void getExecutable(File output, MyGui gui) {
		File dir = gui.getCurrentDirectory();
		ArrayList<File> files = new ArrayList<File>();
		ArrayList<File> objectFiles = new ArrayList<File>();
		getCompleteFileList(dir, files);
		for (File f : files) {
			System.out.print("Compiling : " + f.getName());
			File object = MyCompiler.getObjectFile(f);
			if (object != null)
				objectFiles.add(object);
			System.out.println(" ... Finished");
		}
		File linker = null;
		try {
			linker = new File(MyCompiler.class.getClassLoader().getResource("gcc-win.exe").toURI().getPath());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		String link = "\"" + linker.getPath() + "\" -o \"" + output.getPath() +"\"";
		
		for (File o : objectFiles) {
			System.out.print("Linking : " + o.getName());
			link += " \"" + o.getPath() + "\"";
			System.out.println(" ... Finished");
		}
		
		Process p = null;
		
		try {
			System.out.println(link);
			p = Runtime.getRuntime().exec(link);
			new Thread(new SyncPipe(p.getErrorStream(), gui.getConsoleStream())).start();
		    new Thread(new SyncPipe(p.getInputStream(), gui.getConsoleStream())).start();
			int returnVal = p.waitFor();
			if (returnVal == 0)
				System.out.println("Fertig");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			p.destroy();
		}
		
		
		for (File f : objectFiles) {
			f.delete();
		}
	}
	
	private static void getCompleteFileList(File dir, ArrayList<File> files) {
		for (File f : dir.listFiles()) {
			if (f.isDirectory())
				getCompleteFileList(f, files);
			else
				if (f.getName().endsWith(".c") || f.getName().endsWith(".asm"))
					files.add(f);
		}
	}
	
	public static void init(MyGui gui) {
		MyLinker.gui = gui;
		if (gui.getSystem().matches("linux")) {
			MyLinker.path = "linux/";
			MyLinker.format = "coff";
		} if (gui.getSystem().matches("win.*")) {
			MyLinker.path = "win/";
			MyLinker.format = "win32";
		}
		
		try {
			MyLinker.compiler = new File(MyCompiler.class.getClassLoader().getResource(path + "gcc").toURI().getPath());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private static class MyCompiler {
		
		private static File assemble(File toAssemble) throws IOException {
			File objectFile = new File(toAssemble.getPath().substring(0, toAssemble.getPath().indexOf(".asm")) + "1.o");
			String cmd = "\"" + MyLinker.assembler.getPath() + "\" -o \"" + objectFile.getPath() + "\" -f + "+ MyLinker.format + " " + toAssemble.getName();
			Process p = Runtime.getRuntime().exec(cmd, null, toAssemble.getParentFile());
			new Thread(new SyncPipe(p.getErrorStream(), MyLinker.gui.getConsoleStream())).start();
		    new Thread(new SyncPipe(p.getInputStream(), MyLinker.gui.getConsoleStream())).start();
			p.destroy();
			return objectFile;
		}
		
		private static File compile(File toCompile) throws IOException {
			
			File objectFile = new File(toCompile.getPath().substring(0, toCompile.getPath().indexOf(".c")) + ".o");
			String cmd = MyLinker.compiler.getPath() + "/gcc -o " + objectFile.getPath() + "\" -c " + toCompile.getPath();
			Process p = Runtime.getRuntime().exec(cmd, null, MyLinker.compiler);
			new Thread(new SyncPipe(p.getErrorStream(), MyLinker.gui.getConsoleStream())).start();
		    new Thread(new SyncPipe(p.getInputStream(), MyLinker.gui.getConsoleStream())).start();
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
