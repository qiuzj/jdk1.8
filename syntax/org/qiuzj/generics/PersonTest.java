package org.qiuzj.generics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonTest {
	
	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<>();
		map.put("me", 2);
		Person<String> p = new Person<String>() {
			@Override
			public String execute(Map<?,?> map) {
				return map.get("me") + "";
			}

			@Override
			public <M, D> String execute2(Map<M, D> map) {
				return map.get("me") + "";
			}
			// or
//			public String execute2(Map map) {
//				return map.get("me") + "";
//			}

			@Override
			public <K, V> void execute3(Map<K, V> map) {
			}
		};
		System.out.println(p.execute(map));
		System.out.println(p.execute2(map));
		
		List l = null;
		List<String> l2 = l;
		List<?> l3 = l;
		l3 = l2;
		l = l2;
		l = l3;
	}
	
}
