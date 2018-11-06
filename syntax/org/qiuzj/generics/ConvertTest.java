package org.qiuzj.generics;

import java.util.ArrayList;
import java.util.Collection;

public class ConvertTest {

	public static void main(String[] args) {
		Collection cs = new ArrayList<String>();
//	    if (cs instanceof Collection<String>) {} // 非法. 运行时环境不会为你作这样的检查
	}

	<T> T badCast(T t, Object o) {
		return (T) o; // unchecked warning
	}
	// 类型参数在运行时并不存在。这意味着它们不会添加任何的时间或者空间上的负担，这很好。
	// 不幸的是，这也意味着你不能依靠他们进行类型转换。
}
