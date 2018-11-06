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
	}

}
