package org.qiuzj.lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Lambda2 {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(2);
		list.add(3);
		list.add(1);
		list.add(5);
		list.add(6);
		list.add(4);
		System.out.println(list);
		Collections.sort(list, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1 > o2 ? 1 : o1 < o2 ? -1 : 0;
			}
		});
		System.out.println(list);
		
		System.out.println("---------");
		
		list.clear();
		list.add(2);
		list.add(3);
		list.add(1);
		list.add(5);
		list.add(6);
		list.add(4);
		System.out.println(list);
		Collections.sort(list, (o1, o2) -> {return o1 > o2 ? 1 : o1 < o2 ? -1 : 0;});
		System.out.println(list);
	}

}
