package org.qiuzj.lambda;

import java.util.Arrays;
import java.util.List;

public class Lambda3 {

	public static void main(String[] args) {
		// Java 8之前：
		List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
		
		for (String feature : features) {
		    System.out.println(feature);
		}
		
		System.out.println();
		features.forEach((n) -> System.out.println(n));
		
		System.out.println();
		features.forEach(System.out::println);
	}

}
