package pri.file;

import java.io.File;

import pri.Resource;

public class Test {

	public static void main(String args[]) {
		File lrc_file = FOperator.getResourceFile(Resource.class, "Test.lrc");
		System.out.println(lrc_file.getPath());
		String lrc = FOperator.read(lrc_file, 2000);
		System.out.println(lrc);
	}
}
