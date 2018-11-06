package org.qiuzj.autoboxing;

import java.util.ArrayList;

/**
 * 装箱拆箱场景：
 * <ol>
 * <li>赋值时
 * <li>访问调用时
 * </ol>
 * 
 * @author qiuzj
 *
 */
public class AutoboxingTest1 {

	public static void main(String[] args) {
		ArrayList<Integer> intList = new ArrayList<Integer>();
		intList.add(1); //autoboxing - primitive to object
		intList.add(2); //autoboxing

		ThreadLocal<Integer> intLocal = new ThreadLocal<Integer>();
		intLocal.set(4); //autoboxing

		int number = intList.get(0); // unboxing
		int local = intLocal.get(); // unboxing in Java
		
		
		// before autoboxing
		Integer iObject = Integer.valueOf(3);
		int iPrimitive = iObject.intValue();

		// after java5
		Integer iObject1 = 3; //autobxing - primitive to wrapper conversion
		int iPrimitive2 = iObject1; //unboxing - object to primitive conversion
		
		Object o = 1;
		Object o1 = "a";
		
		
		//autoboxing and unboxing in method invocation
		show(3); //autoboxing
		int result = show(3); //unboxing because return type of method is Integer
	}
	
	public static Integer show(Integer iParam){
	   System.out.println("autoboxing example - method invocation i: " + iParam);
	   return iParam;
	}

}
