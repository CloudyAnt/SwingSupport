package pri.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

import org.mozilla.universalchardet.UniversalDetector;

import pri.util.Sys;
/**
 * 文件操作类<br/>
 * 导入<a>http://code.google.com/p/juniversalchardet<a/>判断文件编码格式
 * @author 柴晓
 * @version 1.1 增加了编码判断代码，根据编码进行读取 17/5/21
 */
public class FOperator {
	public static final String author="柴家琪";
	/**
	 * UseLess
	 * 获取文件夹中所有文件
	 */
	public static File[] getFiles(File folder){
		return folder.listFiles();
	}
	/**
	 * 获取URL指定的文件
	 * @param u URL
	 * @return 文件
	 */
	public static File getResoureFile(Class<?> cla,String source){
		try {
			return new File(URLDecoder.decode(cla.getResource(source).getPath(),"UTF-8"));
			//中文字符会被UFT-8编码，需解码
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取文件夹中所有指定的文件
	 * @param folder  	文件夹
	 * @param suffix	后缀
	 */
	public static ArrayList<File> getPointedFiles(File folder,String suffix){
		File files[]=getFiles(folder);
		ArrayList<File> al=new ArrayList<File>();
		for(File file:files){
			if(compare(file,suffix))
				al.add(file);
		}
		return al;
	}
	/**
	 * 检查文件是否为指定文件
	 * @param file 等待检验的文件
	 * @param compare 指定的后缀
	 */
	public static boolean compare(File file,String compare){
		char[]name=file.getName().toCharArray();
		String suffix="";
		boolean begin=false;
		for(char ch:name){
			if(begin)
				suffix+=ch;
			if(ch=='.')
				begin=true;
		}
		if(suffix.equals(compare))
			return true;
		return false;
	}
	/**
	 * 获取文件最后的修改时间
	 * @param file 要获取的文件
	 * @return 最后修改时间
	 */
	public static String getLastModifyTime(File file){
		Calendar cal = Calendar.getInstance();  
        long time =file.lastModified();  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");         
        cal.setTimeInMillis(time);    
        return formatter.format(cal.getTime());
	}
	/**
	 * 向文件中写入内容，清空之前的内容
	 * @param file 要写入的文件
	 * @param str 要写入的文字
	 */
	public static void write(File file,String str){
		try {
			file.createNewFile();
			FileWriter out=new FileWriter(file);
			out.write(str);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 向文件中写入指定格式的内容
	 * @param file 文件
	 * @param content 内容
	 * @param encoding 编码
	 */
	public static void write(File file, String content, String encoding){   
        try {
			file.createNewFile();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(  
	        new FileOutputStream(file), encoding));  
	        writer.write(content);  
	        writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
    }  
	/**
	 * 判断编码方式
	 * @param file 文件
	 * @return 编码方式
	 */
	public static String Encode(File file){
		byte[] buf = new byte[100];
	    FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	    UniversalDetector detector = new UniversalDetector(null);
	    int nread;
	    try {
			while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
			  detector.handleData(buf, 0, nread);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    detector.dataEnd();
	    String encoding = detector.getDetectedCharset();
	    detector.reset();
	    return encoding;
	}
	/**
	 * 从文件中读取数据
	 * @param file 要读取的文件
	 * @param length 要读的长度
	 * @return 读取的文字
	 */
//	public static String readOut(File file,int length){
//		return readOut(file,length,"");
//	}
	/**
	 * 从文件中读取数据
	 * @param file	读取的文件
	 * @param length 读取长度
	 * @param os 操作系统
	 * @return	读取的文字
	 */
	public static String read(File file,int length){
		String encoding=Encode(file);
	    if (encoding != null) {
	    	if(encoding.startsWith("GB"))
	    		return readGB(file,length);
	    	return read(file,encoding,length);
	    }else{
	    	JOptionPane.showMessageDialog(null, "文档编码解析错误，请联系"+author, "注意", JOptionPane.WARNING_MESSAGE);
	    	String os=Sys.getInfo(Sys.Os);
	    	if(os.startsWith("Mac")||os.startsWith("OS"))//获取系统
	    		return read(file,"UTF-8",length);
	    	return readGB(file,length);
	    }
	}
	/**
	 * 读取GB编码文件
	 */
	public static String readGB(File file,int length){
		String record="";
		FileReader in;
		try {
			in = new FileReader(file);
			char c[]=new char[length];
			int len=in.read(c);
			record=new String(c,0,len);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return record;
	}
	/**
	 * 读取encoding编码的文件（一般形式读取）
	 */
	public static String read(File file,String encoding,int length){
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(new FileInputStream(file), encoding);
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader read= new BufferedReader(isr);
		String result="";
		String lineText = null;
        try {
			while((lineText = read.readLine()) != null){
				result+=lineText+'\n';
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        //UTF8 文件 开头可能有特殊字符
		return result.charAt(0)==65279?result.substring(1):result;
	}
}
