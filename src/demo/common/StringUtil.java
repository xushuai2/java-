package demo.common;

public class StringUtil {
	
	public static void main(String[] args) {
		System.out.println(stringformat(12322));
		System.out.println(stringformat2(1232211111));
	}
	
	public static String stringformat(long a){
		String s = String.format("%05d", a); 
		return s;
	}
	
	public static String stringformat2(long a){
		String s = String.format("%,d", a);
		return s;
	}
}
