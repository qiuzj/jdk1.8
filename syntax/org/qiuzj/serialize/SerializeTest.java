package org.qiuzj.serialize;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializeTest {

	public static void main(String[] args) {
		try {
			Address address = new Address();
			address.setAddress("where");
		    User user = new User("中国", 26, address);
		    ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("F:/user.bin"));
		    output.writeObject(user);
		    output.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		try {
		    ObjectInputStream input = new ObjectInputStream(new FileInputStream("F:/user.bin"));
		    User user = (User) input.readObject();
		    System.out.println(user);
		} catch (Exception e) {
		    e.printStackTrace();
		} 
	}
	
}

class User implements Serializable {
	transient String name;
	int age;
	Address address;
	
	public User(String name, int age, Address address) {
		this.name = name;
		this.age = age;
		this.address = address;
	}
	@Override
	public String toString() {
		return name + " " + age + " " + address.getAddress();
	}
	
	// 自定义序列化
	
	private void writeObject(java.io.ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeObject(name);
	}
	private void readObject(java.io.ObjectInputStream s)
	        throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        name = (String) s.readObject();
	}
}

class Address implements Serializable {
	String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}