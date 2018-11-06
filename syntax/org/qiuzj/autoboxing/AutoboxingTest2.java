package org.qiuzj.autoboxing;

public class AutoboxingTest2 {

	public static void main(String[] args) {
		Integer sum = 0;
		for (int i = 1000; i < 5000; i++) {
			sum += i;
		}
		System.out.println(sum);

		// 以上代码相当于
//		int result = sum.intValue() + i;
//		Integer sum = new Integer(result);
		
		Integer sum2 = 0;
		for (int i = 1000; i < 5000; i++) {
			int result = sum2.intValue() + i;
			sum2 = new Integer(result);
		}
		System.out.println(sum2);
		
//		---
		
		long startTime = System.currentTimeMillis();
		sum = 0;
		for (int i = 1; i < 50000000; i++) {
			sum += i;
		}
		System.out.println(System.currentTimeMillis() - startTime);

		startTime = System.currentTimeMillis();
		int sum3 = 0;
		for (int i = 1; i < 50000000; i++) {
			sum3 += i;
		}
		System.out.println(System.currentTimeMillis() - startTime);
	}

}
