package javalang;

public class StringTest {

	public static void main(String[] args) {
		String str1 = "droid";
		String str2 = "droid";
		System.out.println(str1 == str2); // true
		
		String str3 = new String("droid");
		System.out.println(str1 == str3); // false
		
		String str4 = str3.intern();
		System.out.println(str4 == str1); // true
		System.out.println(str1 == str3); // false
		
		System.out.println();
		String str5 = "a" + "b" + "c"; // 通过javap -c可以看到只创建了一个对象（ldc #39 // String abc）.
		String str6 = "abc";
		String str7 = "ab" + "c";
		System.out.println(str5 == str6); // true
		System.out.println(str5 == str7); // true

		System.out.println();
		String str8 = "abc" + "d";
		String str9 = "abcd";
		System.out.println(str8 == str9); // true
		
		System.out.println("\ngo1---");
		go();

		System.out.println("\ngo2---");
		go2();
		
		System.out.println("\ngo3---");
		go3();
	}

	private static void go() {
		String str1 = new String("hell") + new String("o"); // 可能是在编译期做了优化，因为两个new似乎上没用，直接拼成"hello"。但奇怪了，有没有进行str1.intern()会影响str1是否在常量池中
		String str2 = str1.intern();
		String str3 = "hello";
		System.out.println(str1 == str2); // true
		System.out.println(str2 == str3); // true
//		System.out.println(str1 == str3); // true. 如果没有str2，此处将为false，奇怪
	}

	private static void go2() {
		String s1 = new String("hell");
		String s2 = new String("o");
		
		String str1 = s1 + s2; // 非常量池
		String str2 = str1.intern(); 
		String str3 = "hello";
		System.out.println(str1 == str2); // false
		System.out.println(str2 == str3); // true
	}
	
	private static void go3() {
		String str3 = "hello";
		String str1 = new String("hell") + new String("o"); // go、go3之间如何理解？
		String str2 = str1.intern();
		System.out.println(str1 == str2); // false
		System.out.println(str2 == str3); // true
	}

}
