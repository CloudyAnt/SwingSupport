package pri.math;

import java.math.BigInteger;
/**
 * 进制任意转换类
 * @author 柴晓
 *
 */
public class Hex {
	public static final int plus=1,minus=2,multiply=3,divide=4;
	private static BigInteger zero=new BigInteger("0");
	/**
	 * 给定的进制的字符串转换为十进制
	 * @param s		指定进制的字符串
	 * @param hex	指定的进制
	 * @return		十进制大数字
	 */
	public static BigInteger getInt_InDecimal(String s,int hex){
		BigInteger bi=new BigInteger("0"),x=null;
		if(s.charAt(0)=='-'){
			x=new BigInteger("-1");
			s=s.substring(1);
		}
		char sc[]=s.toCharArray();
		int len=sc.length;
		for(int i=0;i<len;i++){
			bi=bi.add(getX(toTen(sc[i]),hex,len-i));
		}
		if(x==null)
			return bi;
		return bi.multiply(x);
	}
	/**
	 * 将十进制的大数字转换为指定进制
	 * @param bi	十进制大数字
	 * @param hex	指定的进制
	 * @return		转换后的字符串
	 */
	public static String getInt_InPointHex(BigInteger bi,Integer hex){
		String show="",x="";
		if(bi.compareTo(zero) < 0)
			x="-1";
		BigInteger hex_bi=new BigInteger(hex.toString());
		if(!bi.equals(zero))
			while(!bi.equals(zero)){
				BigInteger mod=bi.mod(hex_bi);		//取余数
				show=toChar_Z(mod.intValue())+show;	//intValue()返回对应的int值
				bi=bi.divide(hex_bi);
			}
		else
			show="0";
		return x+show;
	}
	/**
	 * 获取三十六进制之下的每个字母代表数字值
	 * @param c 字母
	 */
	public static int toTen(char c){
		int i=(int)c;
		if(i>=48&&i<=57){
			for(int t=0;t<=9;t++){
				if((i-48)==t)
					return t;
			}
		}
		else if(i>=97&&i<=122){
			for(int t=10;t<=36;t++){
				if((i-87)==t)
					return t;
			}
		}
		else if(i>=65&&i<=90){
			for(int t=10;t<=36;t++){
				if((i-55)==t)
					return t;
			}
		}
		return -1;
	}
	/**
	 * 得出在某一位上对应的十进制的大数 
	 * @param x 底数（由其他toTen函数得出）
	 * @param hex 进制
	 * @param position 位置（从右到左，1开始）
	 * @return 对应十进制的值
	 */
	private static BigInteger getX(Integer x,Integer hex,Integer position){
		BigInteger Final=new BigInteger(x.toString()),
				Level=new BigInteger(hex.toString());
		for(int i=0;i<position-1;i++){
			Final=Final.multiply(Level);
		}
		return Final;
	}
	/**
	 * 转化十进制的数字为36进制字符
	 * @param i 要转化的数字
	 * @return 转化后的字符
	 */
	public static char toChar_Z(int i){
		if(i<10){
			for(int ii=48;ii<58;ii++){
				if((i+48)==ii)
					return (char)ii;
			}
		}
		else
			for(int ii=65;ii<91;ii++){
				if((i+55)==ii)
					return (char)ii;
			}
		return ' ';
	}
	/**
	 * 将任意进制的数转换为任意进制
	 * @param a		当前字符串
	 * @param ah	当前进制
	 * @param bh	转换进制
	 * @return		转换后的字符串
	 */
	public static String transformation(String a,int ah,int bh){
		return getInt_InPointHex(getInt_InDecimal(a, ah),bh);
	}
	/**
	 * 计算
	 * @param sa	A字符串
	 * @param ha	A进制
	 * @param sb	B字符串
	 * @param hb	B进制
	 * @param hr 	返回进制
	 * @param operate	操作
	 * @return		结果字符串
	 */
	public static String calculate(String sa,int ha,String sb,int hb,int operate,int hr){
		BigInteger abi=getInt_InDecimal(sa,ha),bbi=getInt_InDecimal(sb, hb);
		System.out.println(abi+" "+bbi);
		String result="0";
		switch(operate){
		case plus:
			result=(abi.add(bbi)).toString();break;
		case minus:
			result=(abi.subtract(bbi)).toString();break;
		case multiply:
			result=(abi.multiply(bbi)).toString();break;
		case divide:
			result=(abi.divide(bbi)).toString();break;
		}
		if(hr!=0)
			result=transformation(result, 10, hr);
		return result;
	}
	public static void main(String args[]){
		System.out.println(Hex.transformation("CDE", 24, 10));
		System.out.println((Hex.calculate("-ABC", 24, "ABCD", 24,plus,36)));
	}
}
