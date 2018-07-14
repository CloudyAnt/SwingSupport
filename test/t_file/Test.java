package t_file;

import java.io.File;

import pri.file.FOperator;

public class Test {
	
	public static void main(String args[]){
		File lrc_file=FOperator.getResoureFile(Test.class, "Test.lrc");
		System.out.println(lrc_file.getPath());
		String lrc=FOperator.read(lrc_file,2000);
		System.out.println(lrc);
	}
}
