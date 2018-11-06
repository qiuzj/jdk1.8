package javautil;

import java.util.TreeMap;

public class TreeMapTest1 {

	public static void main(String[] args) {
		TreeMap map = new TreeMap();
		map.put(20, 20);
		
		map.put(10, 10);
		map.put(5, 5);
		map.put(2, 2);
		map.put(6, 6);
		map.put(15, 15);
		map.put(12, 12);
		map.put(18, 18);
		
		map.put(30, 30);
		map.put(25, 25);
		map.put(40, 40);
		map.put(38, 38);
		map.put(45, 45);
		map.put(36, 36);
		
		System.out.println(map.firstEntry());
		System.out.println(map.lastEntry());
		
		System.out.println();
		System.out.println(map.lowerEntry(46));
		System.out.println(map.lowerEntry(28));
		System.out.println(map.lowerEntry(16));
		System.out.println(map.lowerEntry(35));
		
		System.out.println();
		
		while(!map.isEmpty()) {
			System.out.println(map.pollFirstEntry());
		}
		
		System.out.println(computeRedLevel(11));
	}
	
	private static int computeRedLevel(int sz) {
        int level = 0;
        for (int m = sz - 1; m >= 0; m = m / 2 - 1)
            level++;
        return level;
    }

}

