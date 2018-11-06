package javautil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ToArrayMightIncorrectly6260652 {

	public static void main(String[] args) {
		ToArrayMightIncorrectly6260652 obj = new ToArrayMightIncorrectly6260652();
		obj.test1();
//		obj.test3();
		obj.test4();
//		obj.test5();
		obj.test6();

		//		ArrayList al = new ArrayList();
//		al.add(1);
//		al.add("2");
//		Object[] ao = al.toArray();
//		Object[] aoT = al.toArray(new String[0]);
	}
	
    public void test1 () {
        Son[] sons = new Son[]{new Son(), new Son()};
        System.out.println(sons.getClass());            // class [Lcom.johnnie.test.Test$Son;

        Father[] fathers = sons;
        System.out.println(fathers.getClass());     // class [Lcom.johnnie.test.Test$Son;

//        fathers[0] = new Father();                          // java.lang.ArrayStoreException 抛异常
        fathers[0] = new Son();
    }
    
    public void test3() {
        List<String> ss = new LinkedList<String>();             // LinkedList toArray() 返回的本身就是 Object[]
        ss.add("123");
        Object[] objs = ss.toArray();
        objs[0] = new Object();

        // 此处说明了：c.toArray might (incorrectly) not return Object[] (see 6260652)
        ss = new MyList<String>();
        objs = ss.toArray();
        System.out.println(objs.getClass());        // class [Ljava.lang.String;
        objs[0] = new Object();                         // java.lang.ArrayStoreException: java.lang.Object
    }
	
    public void test4() {
    	List<Object> l = new ArrayList<Object>(Arrays.asList("foo", "bar"));

    	// Arrays.asList("foo", "bar").toArray() produces String[], 
    	// and l is backed by that array

    	l.set(0, new Object());
    }
    
    public void test5()  
    {  
    	System.out.println("test5---");
        List<String> list = Arrays.asList("abc");
      
        // class java.util.Arrays$ArrayList  
        System.out.println(list.getClass());
      
        // class [Ljava.lang.String;  
        Object[] objArray = list.toArray();
        System.out.println(objArray.getClass());  
      
        objArray[0] = new Object(); // cause ArrayStoreException  
    }  
    
    public void test6()  
    {  
        List<String> dataList = new ArrayList<String>();  
        dataList.add("one");  
        dataList.add("two");  
      
        Object[] listToArray = dataList.toArray();
      
        // class [Ljava.lang.Object;返回的是Object数组  
        System.out.println(listToArray.getClass());  
        listToArray[0] = "";  
        listToArray[0] = 123;  
        listToArray[0] = new Object();  
          
    }  
}

class MyList<E> extends ArrayList<E> {

    // toArray() 的同名方法
    public String[] toArray() {
        return new String[]{"1", "2", "3"};
    }

}

class Father {

}

class Son extends Father {

}