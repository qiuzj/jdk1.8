package org.qiuzj.autoboxing;

public class AutoboxingTest3 {

	public static void main(String[] args) {
		//calling overloaded method
		AutoboxingTest3 autoTest = new AutoboxingTest3();
		
		int value = 3;
		autoTest.test(value); //no autoboxing 
		
		Integer iValue = value + 1;
		autoTest.test(iValue); //no autoboxing
	}
	
	public void test(int num){
	    System.out.println("method with primitive argument " + num);
	}

	public void test(Integer num){
	    System.out.println("method with wrapper argument " + num);
	}

}
