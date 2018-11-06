package javautil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyListTest {
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		List<Integer> list1 = new ArrayList<>();
		for (int i = 0; i < 100000; i++) {
			list1.add(i);
		}
		System.out.println(System.currentTimeMillis()-startTime);

		System.out.println("---");
		startTime = System.currentTimeMillis();
		List<Integer> list2 = new CopyOnWriteArrayList<>();
		for (int i = 0; i < 100000; i++) {
			list2.add(i); // 好慢啊
		}
		System.out.println(System.currentTimeMillis()-startTime);

		System.out.println("======");
		startTime = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			list1.get(i);
		}
		System.out.println(System.currentTimeMillis()-startTime);
		
		System.out.println("---");
		startTime = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			list2.get(i);
		}
		System.out.println(System.currentTimeMillis()-startTime);
	}
}
