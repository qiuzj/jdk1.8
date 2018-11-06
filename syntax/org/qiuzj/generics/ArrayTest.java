package org.qiuzj.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人理解，之所以不能创建泛型类型的数组，那是因为编译擦除类型之后，运行时对数组元素赋值无法检查是否为该泛型类型（因为被擦除了）
 * 
 * @author qiuzj
 *
 */
public class ArrayTest {

	public static void main(String[] args) {
//		List<String>[] lsa = new List<String>[10]; // not really allowed 非法. Cannot create a generic array of List<String>

		List<?>[] lsa1 = new List<?>[10]; // ok, array of unbounded wildcard type 合法
		lsa1[0] = new ArrayList<String>();
		lsa1[1] = new ArrayList<Integer>();

		List<String>[] lsa2 = new ArrayList[10]; //unchecked warning - this is unsafe!未检查警告，不安全

	}
	
//	<T> T[] makeArray(T t) {
//		return new T[100]; // error. Cannot create a generic array of T
//	}
	<T> void makeArray2(T t) {
		T tt;
//		new T(); // Cannot instantiate the type T
	}

}
