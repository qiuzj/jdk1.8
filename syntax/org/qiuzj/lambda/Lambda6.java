package org.qiuzj.lambda;

import java.util.Arrays;
import java.util.List;

public class Lambda6 {

	public static void main(String[] args) {// 为每个订单加上12%的税
		// 老方法：
		List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
		double total = 0;
		for (Integer cost : costBeforeTax) {
			double price = cost + .12 * cost;
			total = total + price;
		}
		System.out.println("Total : " + total);

		// 新方法：
//		List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
		double bill = costBeforeTax.stream().map((cost) -> cost + .12 * cost)
				.reduce((sum, cost) -> sum + cost).get();
		System.out.println("Total : " + bill);
		
		List<Integer> nums = Arrays.asList(1,null,3,4,null,6);
		System.out.println(nums.stream().filter(num -> num != null).count());
	}

}
