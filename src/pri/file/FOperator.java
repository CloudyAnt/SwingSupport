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
 * �ļ�������<br/>
 * ����<a>http://code.google.com/p/juniversalchardet<a/>�ж��ļ������ʽ
 * @author ����
 * @version 1.1 �����˱����жϴ��룬���ݱ�����ж�ȡ 17/5/21
 */
public class FOperator {
	public static final String author="�����";
	/**
	 * UseLess
	 * ��ȡ�ļ����������ļ�
	 */
	public static File[] getFiles(File folder){
		return folder.listFiles();
	}
	/**
	 * ��ȡURLָ�����ļ�
	 * @param u URL
	 * @return �ļ�
	 */
	public static File getResoureFile(Class<?> cla,String source){
		try {
			return new File(URLDecoder.decode(cla.getResource(source).getPath(),"UTF-8"));
			//�����ַ��ᱻUFT-8���룬�����
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ��ȡ�ļ���������ָ�����ļ�
	 * @param folder  	�ļ���
	 * @param suffix	��׺
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
	 * ����ļ��Ƿ�Ϊָ���ļ�
	 * @param file �ȴ�������ļ�
	 * @param compare ָ���ĺ�׺
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
	 * ��ȡ�ļ������޸�ʱ��
	 * @param file Ҫ��ȡ���ļ�
	 * @return ����޸�ʱ��
	 */
	public static String getLastModifyTime(File file){
		Calendar cal = Calendar.getInstance();  
        long time =file.lastModified();  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");         
        cal.setTimeInMillis(time);    
        return formatter.format(cal.getTime());
	}
	/**
	 * ���ļ���д�����ݣ����֮ǰ������
	 * @param file Ҫд����ļ�
	 * @param str Ҫд�������
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
	 * ���ļ���д��ָ����ʽ������
	 * @param file �ļ�
	 * @param content ����
	 * @param encoding ����
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
	 * �жϱ��뷽ʽ
	 * @param file �ļ�
	 * @return ���뷽ʽ
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
	 * ���ļ��ж�ȡ����
	 * @param file Ҫ��ȡ���ļ�
	 * @param length Ҫ���ĳ���
	 * @return ��ȡ������
	 */
//	public static String readOut(File file,int length){
//		return readOut(file,length,"");
//	}
	/**
	 * ���ļ��ж�ȡ����
	 * @param file	��ȡ���ļ�
	 * @param length ��ȡ����
	 * @param os ����ϵͳ
	 * @return	��ȡ������
	 */
	public static String read(File file,int length){
		String encoding=Encode(file);
	    if (encoding != null) {
	    	if(encoding.startsWith("GB"))
	    		return readGB(file,length);
	    	return read(file,encoding,length);
	    }else{
	    	JOptionPane.showMessageDialog(null, "�ĵ����������������ϵ"+author, "ע��", JOptionPane.WARNING_MESSAGE);
	    	String os=Sys.getInfo(Sys.Os);
	    	if(os.startsWith("Mac")||os.startsWith("OS"))//��ȡϵͳ
	    		return read(file,"UTF-8",length);
	    	return readGB(file,length);
	    }
	}
	/**
	 * ��ȡGB�����ļ�
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
	 * ��ȡencoding������ļ���һ����ʽ��ȡ��
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
        //UTF8 �ļ� ��ͷ�����������ַ�
		return result.charAt(0)==65279?result.substring(1):result;
	}
}
