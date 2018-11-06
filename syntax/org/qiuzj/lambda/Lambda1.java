package org.qiuzj.lambda;

public class Lambda1 {

	public static void main(String[] args) {
		Lambda1 bean = new Lambda1();
		bean.run1();
		bean.run2();
		bean.run3();
	}
	
	public void run1() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("OK1");
			}
		}).start();
	}
	
	public void run2() {
		new Thread(() -> {System.out.println("OK2");}).start();
	}
	
	public void run3() {
		new A((a,b) -> {System.out.println(a+b);}).ok(2, 3);
	}
	
	// ------
	
	class A {
		B b;
		public A(B b) {
			this.b = b;
		}
		public void ok(int a, int c) {
			b.go(a, c);
		}
	}
	
	interface B {
		public void go(int a, int b);
	}

}
