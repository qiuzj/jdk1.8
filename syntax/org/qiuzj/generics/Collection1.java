package org.qiuzj.generics;

public interface Collection1<E> {

	boolean containsAll(Collection1<?> c);

	boolean addAll(Collection1<? extends E> c);
	
}
