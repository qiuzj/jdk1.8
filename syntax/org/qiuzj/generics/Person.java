package org.qiuzj.generics;

import java.util.Map;

public interface Person<T> {

	T execute(Map<?,?> map);

	<K,V> T execute2(Map<K,V> map);

	<K,V> void execute3(Map<K,V> map);
	
}
