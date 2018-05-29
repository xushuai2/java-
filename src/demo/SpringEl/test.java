package demo.SpringEl;

public class test {
	public static void main(String[] args) {
		/**
		 * method
		 */
		String s  =  "D:/DownFile/#{T(demo.SpringEl.DateUtil).getDateString(0,"+"'yyyyMMdd')}";
		String localhostPath = ExpressionUtils.getCommExpression(s, null);
		System.out.println(localhostPath);
		
		String n  =  "D:/File/#{T(demo.SpringEl.demoMethod).getName()}";
		String name = ExpressionUtils.getCommExpression(n, null);
		System.out.println(name);
		
		/**
		 * 变量
		 */
		String n2  =  "#{#time}----#{#year}-#{#month}-#{#day}";
		String t = ExpressionUtils.getCommExpression(n2, null);
		System.out.println(t);
	}

}
