package pri.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
/**
 * Jar包操作类<br/>
 * Unchecked
 * @author Shinelon
 */
public class JarOperator {
	/**
	 * 读取jar中文件
	 * @see {@link pri.file.JarOperator#readJar(JarFile)}
	 * @param path 路径
	 */
	public static HashMap<String,String> readJar(String path){
		try {
			return readJar(new JarFile(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 读取JarFile中的文件
	 * @param file 要读取的jar文件
	 * @return {@code HashMap<String,String>}第一个String为包中路径，第二个是内容
	 */
	public static HashMap<String,String> readJar(JarFile file){
		HashMap<String,String> hss = new HashMap<>();
		Enumeration<JarEntry> entry = file.entries();
		while (entry.hasMoreElements()) {
			JarEntry e = entry.nextElement();
			if (!e.isDirectory()) {
			    InputStream stream;
				try {
					stream = file.getInputStream(e);
					byte[] bs = new byte[stream.available()];
					stream.read(bs);
					hss.put(e.toString(),new String(bs));
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		try {
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hss;
	}
	/**
	 * 写入JarFile
	 * @param path 文件路径
	 * @param file 要写入的文件（文件夹）
	 */
	public static void writeJar(String path,File file){
		JarOutputStream stream;
		try {
			stream = new JarOutputStream(new FileOutputStream(path));
			write(stream,"",file);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void write(JarOutputStream stream,String pathInJar,File file){
		if(file.isDirectory()){
			File files[]=file.listFiles();
			for(int i=0;i<files.length;i++){
				write(stream,pathInJar+"/"+file.getName(),file);
			}
		}
		JarEntry entry=new JarEntry(pathInJar+"/"+file.getName());
		try {
			stream.putNextEntry(entry);
			stream.write(FOperator.read(file,100000).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 写入JarFile
	 * @param path 文件路径
	 * @param hss <{@code HashMap<String,String>}第一个String为文件名，第二个为内容
	 */
	public static void writeJar(String path,HashMap<String,String> hss){
		JarOutputStream stream;
		try {
			stream = new JarOutputStream(new FileOutputStream(path));
			Iterator<String> is=hss.keySet().iterator();
			JarEntry entry;
			while(is.hasNext()){
				String next=is.next();
				entry=new JarEntry(next);
				stream.putNextEntry(entry);
				stream.write(hss.get(next).getBytes());
			}
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
