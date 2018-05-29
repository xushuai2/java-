package 开启两个线程a一个模拟数据提交b另一个模拟数据读取;

import java.util.LinkedList;
import java.util.Random;
import java.util.Queue;

public class demo{
	private Queue<String> queue = new LinkedList<String>();
	private static int m = 1;
	public demo(Queue queue1){
		queue1.add("55555");
		queue1.add("55555");
		queue1.add("55555");
		this.queue = queue1; 
	}

}
