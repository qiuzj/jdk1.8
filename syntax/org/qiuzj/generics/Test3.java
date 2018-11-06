package org.qiuzj.generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Test3 {

	public static void main(String[] args) {
		String[] a = {"a", "b", "c"};
		List<String> l = new ArrayList<>();
		System.out.println(l.size());
		fromArrayToCollection(a, l); // 
		System.out.println(l.size());
		System.out.println(l.get(1));
		
		List<Integer> l2 = new ArrayList<>();
//		fromArrayToCollection(a, l2); // wrong
		
		Object[] oa = new Object[100];
		Collection<Object> co = new ArrayList<Object>();
		Collection<String> co2 = new ArrayList<String>();
		fromArrayToCollection(oa, co);// T 指Object
		String[] sa = new String[100];
		Object[] sa2 = new Object[100];
		Collection<String> cs = new ArrayList<String>();
		fromArrayToCollection(sa, cs);// T inferred to be String
		fromArrayToCollection(sa, co);// T inferred to be Object
//		fromArrayToCollection(sa2, co2);// wrong , why?
		Integer[] ia = new Integer[100];
		Float[] fa = new Float[100];
		Number[] na = new Number[100];
		Collection<Number> cn = new ArrayList<Number>();
		fromArrayToCollection(ia, cn);// T inferred to be Number
		fromArrayToCollection(fa, cn);// T inferred to be Number
		fromArrayToCollection(na, cn);// T inferred to be Number
		fromArrayToCollection(na, co);// T inferred to be Object
//		fromArrayToCollection(na, cs);// compile-time error

	}

    static <T> void fromArrayToCollection(T[] a, Collection<T> c){
        for (T o : a) {
            c.add(o); // correct
        }
//        c.add("1"); // wrong
    }

} 


