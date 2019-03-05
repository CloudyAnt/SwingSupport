package pri.math;

import java.math.BigInteger;
/**
 * ��������ת����
 * @author ����
 *
 */
public class Hex {
	public static final int plus=1,minus=2,multiply=3,divide=4;
	private static BigInteger zero=new BigInteger("0");
	/**
	 * �����Ľ��Ƶ��ַ���ת��Ϊʮ����
	 * @param s		ָ�����Ƶ��ַ���
	 * @param hex	ָ���Ľ���
	 * @return		ʮ���ƴ�����
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
	 * ��ʮ���ƵĴ�����ת��Ϊָ������
	 * @param bi	ʮ���ƴ�����
	 * @param hex	ָ���Ľ���
	 * @return		ת������ַ���
	 */
	public static String getInt_InPointHex(BigInteger bi,Integer hex){
		String show="",x="";
		if(bi.compareTo(zero) < 0)
			x="-1";
		BigInteger hex_bi=new BigInteger(hex.toString());
		if(!bi.equals(zero))
			while(!bi.equals(zero)){
				BigInteger mod=bi.mod(hex_bi);		//ȡ����
				show=toChar_Z(mod.intValue())+show;	//intValue()���ض�Ӧ��intֵ
				bi=bi.divide(hex_bi);
			}
		else
			show="0";
		return x+show;
	}
	/**
	 * ��ȡ��ʮ������֮�µ�ÿ����ĸ��������ֵ
	 * @param c ��ĸ
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
	 * �ó���ĳһλ�϶�Ӧ��ʮ���ƵĴ��� 
	 * @param x ������������toTen�����ó���
	 * @param hex ����
	 * @param position λ�ã����ҵ���1��ʼ��
	 * @return ��Ӧʮ���Ƶ�ֵ
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
	 * ת��ʮ���Ƶ�����Ϊ36�����ַ�
	 * @param i Ҫת��������
	 * @return ת������ַ�
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
	 * ��������Ƶ���ת��Ϊ�������
	 * @param a		��ǰ�ַ���
	 * @param ah	��ǰ����
	 * @param bh	ת������
	 * @return		ת������ַ���
	 */
	public static String transformation(String a,int ah,int bh){
		return getInt_InPointHex(getInt_InDecimal(a, ah),bh);
	}
	/**
	 * ����
	 * @param sa	A�ַ���
	 * @param ha	A����
	 * @param sb	B�ַ���
	 * @param hb	B����
	 * @param hr 	���ؽ���
	 * @param operate	����
	 * @return		����ַ���
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
