package javalang;

import java.util.UUID;

public class IntegerTest {

	public static void main(String[] args) {
		System.out.println(Integer.parseInt("02"));
		System.out.println(Integer.parseInt("-222"));
//		System.out.println(Integer.parseInt("1.1")); // java.lang.NumberFormatException
//		System.out.println(Integer.parseInt("a1")); // java.lang.NumberFormatException
//		System.out.println(Integer.parseInt("1a")); // java.lang.NumberFormatException
		Integer i = 1;
		System.out.println(i.hashCode());
		System.out.println(Integer.hashCode(100));
		System.out.println(Integer.hashCode(2147483647));
		
		System.out.println(UUID.randomUUID());
		
		// Integer.toString
		System.out.println(Integer.toString(10));
		System.out.println(Integer.toString(10, 10));
		// 将10转换为2进制
		System.out.println(Integer.toString(10, 2));
		// 将10转换为16进制
		System.out.println(Integer.toString(10, 16));
//		Character.MIN_RADIX;
//		Character.MAX_RADIX;
		
		// Integer.numberOfLeadingZeros
		for (int j = 0; j < 64; j++) {
			System.out.println(String.format("%s > %s", j, Integer.numberOfLeadingZeros(j)));
		}
	}

}
