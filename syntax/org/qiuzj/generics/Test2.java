package org.qiuzj.generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test2 {

	public static void main(String[] args) {
		Collection<? extends Number> cn;
		List<? extends Number> ln = null;
		Set<? extends Number> sn;
		
		cn = new ArrayList<Integer>();
		sn = new HashSet<Double>();
		
		cn = sn;
//		sn = cn; // wrong
		
		List[] list = new List[10];
//		List<String>[] list2 = new List<String>[10]; // wrong
		List<?>[] list3 = new List<?>[10];
		List[] list4 = new List<?>[10];
//		List<String>[] list5 = new List<?>[10]; // wrong
	}

}

class ClassTest<X extends Number, Y, Z> {    
    private X x;    
//    private static Y y; //编译错误，不能用在静态变量中    
    public X getFirst() {
        //正确用法        
        return x;    
    }    
    public void wrong() {        
//        Z z = new Z(); //编译错误，不能创建对象    
    }
    
    static <T> void fromArrayToCollection(T[] a, Collection<T> c){
        for (T o : a) {
            c.add(o); // correct
        }
    }

} 


