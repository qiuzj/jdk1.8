package org.qiuzj.generics;

import java.util.ArrayList;
import java.util.List;

public class Test4<O, P extends O> {

	public static <T> void copy1(List<T> dest, List<? extends T> src) {
		for (T s : src) {
			dest.add(s);
		}
		
		for (Object s : src) {
			dest.add((T)s); // Type safety: Unchecked cast from Object to T
		}
		
	}

	public static <T, S extends T>  void copy2(List<T> dest, List<S> src) {
		for (S s : src) {
			dest.add(s);
		}
		for (T s : src) {
			dest.add(s);
		}
	}

	public static void copy3(List<Number> dest, List<? extends Number> src) {
		for (Number s : src) {
			dest.add(s);
		}
	}

	public static <T extends Number> void copy4(List<Number> dest, List<T> src) {
		for (Number s : src) {
			dest.add(s);
		}
	}
	
	public void copy5(List<O> dest, List<P> src) {
		for (P s : src) {
			dest.add(s);
		}
		for (O s : src) {
			dest.add(s);
		}
		
		new ArrayList<O>();
	}

//	public static void copy6(List<O> dest, List<P> src) { // wrong. Cannot make a static reference to the non-static type O
//	}
	
	
}
