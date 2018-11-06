package javautil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class HashMap11 {

	public static void main(String[] args) {
		HashMap<Integer, String> map = new HashMap<>();
		map.put(1, "1");
		map.put(2, "2");
		Set<Integer> keySet = map.keySet(); //
		Iterator<Integer> it = keySet.iterator(); //
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		
		boolean modified = false;
		System.out.println(modified |false|true|false|true|false);
	}
	
}
