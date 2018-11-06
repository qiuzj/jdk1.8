package org.qiuzj.generics;

import java.util.ArrayList;
import java.util.List;

public class UnSafeTest {

	public static void main(String[] args) {
		List<String> strings = new ArrayList<>();
		unsafeAdd(strings, new Integer(42)); // 这句为什么没报错? 应该是因为编译后类型都被擦除了，add的时候不会理会参数是任何类型（因为使用了原生态类型List？为了兼容性？）。但get返回的时候确能够保证返回指定类型，这是由泛型的特性保障的
		String s = strings.get(0); // java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String
		System.out.println(s);
	}

	private static void unsafeAdd(List strings, Object o) {
		strings.add(o);
	}

}
