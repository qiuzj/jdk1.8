package org.qiuzj.lambda;

import java.util.Arrays;
import java.util.List;

public class Lambda5 {

	public static void main(String[] args) {
		List<Integer> costs = Arrays.asList(100, 200, 300, 400, 500);
		for (Integer cost : costs) {
			double price = cost + .12 * cost;
			System.out.println(price);
		}
		
		System.out.println();
		costs.stream().map((cost) -> cost + .12 * cost).forEach((n) -> System.out.println(n));
	}

}
