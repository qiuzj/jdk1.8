package org.qiuzj.generics;

public interface Collection2<E> {

	<T> boolean containsAll(Collection2<T> c);

	<T extends E> boolean addAll(Collection2<T> c);

//	<T extends E> public boolean addAll2(Collection2<T> c); // wrong

//	boolean <T extends E> addAll3(Collection2<T> c); // wrong
	
}
