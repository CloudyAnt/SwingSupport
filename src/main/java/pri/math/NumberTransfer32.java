package pri.math;

import java.math.BigInteger;

/**
 * 进制任意转换类
 * 
 * @author 柴晓
 *
 */
public class NumberTransfer32 {
	public static final int plus = 1, minus = 2, multiply = 3, divide = 4;
	private static BigInteger zero = new BigInteger("0");

	/**
	 * 给定的进制的字符串转换为十进制
	 * 
	 * @param s   指定进制的字符串
	 * @param base 指定的进制
	 * @return 十进制大数字
	 */
	public static BigInteger getIntInDecimal(String s, int base) {
		BigInteger bi = new BigInteger("0"), x = null;
		if (s.charAt(0) == '-') {
			x = new BigInteger("-1");
			s = s.substring(1);
		}
		char[] chars = s.toCharArray();
		int len = chars.length;
		for (int i = 0; i < len; i++) {
			bi = bi.add(getX(toTen(chars[i]), base, len - i));
		}
		if (x == null)
			return bi;
		return bi.multiply(x);
	}

	/**
	 * 将十进制的大数字转换为指定进制
	 * 
	 * @param bi  十进制大数字
	 * @param base 指定的进制
	 * @return 转换后的字符串
	 */
	public static String getIntInPointHex(BigInteger bi, Integer base) {
		String show = "", x = "";
		if (bi.compareTo(zero) < 0)
			x = "-1";
		BigInteger hex_bi = new BigInteger(base.toString());
		if (!bi.equals(zero))
			while (!bi.equals(zero)) {
				BigInteger mod = bi.mod(hex_bi); // 取余数
				show = toChar_Z(mod.intValue()) + show; // intValue()返回对应的int值
				bi = bi.divide(hex_bi);
			}
		else
			show = "0";
		return x + show;
	}

	/**
	 * 获取三十六进制之下的每个字母代表数字值
	 * 
	 * @param c 字母
	 */
	public static int toTen(char c) {
		if (c >= '0' && c <= '9') {
			return c - '0';
		}
		if (c >= 'a' && (int) c <= 'z') {
			return c - 'a' + 10;
		}
		if (c >= 'A' && c <= 'Z') {
			return c - 'A' + 10;
		}
		return -1;
	}

	/**
	 * 得出在某一位上对应的十进制的大数
	 * 
	 * @param x        底数（由其他toTen函数得出）
	 * @param hex      进制
	 * @param position 位置（从右到左，1开始）
	 * @return 对应十进制的值
	 */
	private static BigInteger getX(Integer x, Integer hex, Integer position) {
		BigInteger Final = new BigInteger(x.toString()), Level = new BigInteger(hex.toString());
		for (int i = 0; i < position - 1; i++) {
			Final = Final.multiply(Level);
		}
		return Final;
	}

	/**
	 * 转化十进制的数字为36进制字符
	 * 
	 * @param i 要转化的数字
	 * @return 转化后的字符
	 */
	public static char toChar_Z(int i) {
		if (i < 10) {
			return (char) ('0' + i);
		}
		if (i < 36) {
			return (char) ('A' + i - 10);
		}
		return ' ';
	}

	/**
	 * 将任意进制的数转换为任意进制
	 * 
	 * @param a  当前字符串
	 * @param ah 当前进制
	 * @param bh 转换进制
	 * @return 转换后的字符串
	 */
	public static String transformation(String a, int ah, int bh) {
		return getIntInPointHex(getIntInDecimal(a, ah), bh);
	}

	/**
	 * 计算
	 * 
	 * @param a      A字符串
	 * @param aBase      A进制
	 * @param b      B字符串
	 * @param bBase      B进制
	 * @param returnBase      返回进制
	 * @param operation 操作
	 * @return 结果字符串
	 */
	public static String calculate(String a, int aBase, String b, int bBase, int operation, int returnBase) {
		BigInteger abi = getIntInDecimal(a, aBase), bbi = getIntInDecimal(b, bBase);
		System.out.println(abi + " " + bbi);
		String result = "0";
		switch (operation) {
		case plus:
			result = (abi.add(bbi)).toString();
			break;
		case minus:
			result = (abi.subtract(bbi)).toString();
			break;
		case multiply:
			result = (abi.multiply(bbi)).toString();
			break;
		case divide:
			result = (abi.divide(bbi)).toString();
			break;
		}
		if (returnBase != 0)
			result = transformation(result, 10, returnBase);
		return result;
	}

	public static void main(String args[]) {
		System.out.println(NumberTransfer32.transformation("CDE", 24, 10));
		System.out.println((NumberTransfer32.calculate("-ABC", 24, "ABCD", 24, plus, 36)));
	}
}
