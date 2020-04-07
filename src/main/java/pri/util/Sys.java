package pri.util;

import java.util.Iterator;
import java.util.Properties;

public class Sys {
	public static final int Os = 1, Path = 2;

	public static String getInfo(int thing) {
		switch (thing) {
		case Os:
			return System.getProperty("os.name");
		case Path:
			return System.getProperty("java.class.path");
		default:
			return "error";
		}
	}

	public static void main(String args[]) {
		Properties p = System.getProperties();
		Iterator<Object> ip = p.keySet().iterator();
		while (ip.hasNext()) {
			Object o = ip.next();
			System.out.println(o.toString() + " :: " + p.getProperty(o.toString()));
		}
	}
}
