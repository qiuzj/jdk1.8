package org.qiuzj.generics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Test1 {

	public static void main(String[] args) {
		System.out.println(List.class);
//		System.out.println(List<String>.class); // wrong
		
		List<String> ls = new ArrayList<>();
//		List<Object> la = ls; // wrong
		
		List<Object> la2 = new ArrayList<>();
		la2.add(1);
		la2.add("a");
		la2.add(true);
		
		ls.add("a");
		ls.add("b");
		List<?> lw = ls;
		for (Object o : lw) {
			System.out.println(o);
		}
		List<String> ls2 = (List<String>) lw;
		System.out.println(ls2.get(1));
		
		lw.add(null);
//		lw.add("a"); // wrong
//		lw.add(1); // wrong
		
		new ArrayList<String>();
//		new ArrayList<?>(); // wrong
		
		List<? extends Map> list = new ArrayList<>();
		HashMap map1 = new HashMap();
		TreeMap map2 = new TreeMap();
//		list.add(map1); // wrong
//		list.add(map2); // wrong
		
		List<? extends Map> list2 = new ArrayList<HashMap>();
		List<? extends Map> list3 = new ArrayList<TreeMap<String, Boolean>>();
//		List<? extends Map> list4 = new ArrayList<Set>(); // wrong
		
	}
	
	public void wrong(Set<?> set) {
		Object o = null;
		set.add(null);
		set.remove(null);
//		set.add(o); // wrong
//		set.add(1); // wrong
	}

}
