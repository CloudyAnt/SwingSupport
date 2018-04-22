package sql;

import java.lang.reflect.Field;
import java.sql.Connection;

public class AutoCreate {
	
	public static boolean AutoCreateTable(Connection con,Class<?> entity) {
		Field fs[]=entity.getDeclaredFields();
		for(int i=0;i<fs.length;i++) {
			System.out.println(fs[i].getName());
		}
		return false;
	}
	public static void main(String[] args) {
		AutoCreateTable(null,User.class);
	}
}
