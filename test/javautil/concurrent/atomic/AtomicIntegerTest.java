package javautil.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

public class AtomicIntegerTest {

	public static void main(String[] args) {
		AtomicInteger a = new AtomicInteger(2);
		a.updateAndGet(new IntUnaryOperator() {
			@Override
			public int applyAsInt(int operand) {
				return operand * 3;
			}
		});
		System.out.println(a);
	}
	
}
