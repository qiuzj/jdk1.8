package org.qiuzj.encoding;
import java.io.UnsupportedEncodingException;

import org.qiuzj.util.HexUtils;

public class Qiuzj {
	public static void main(String[] args) throws UnsupportedEncodingException  {
		String str = "中国";
		
		test(str, "UTF-8");
		System.out.println();
		
		test(str, "GBK");
		System.out.println();

		System.out.println();
		str = "abc";
		
		test(str, "UTF-8");
		System.out.println();
		
		test(str, "GBK");
		System.out.println();
		
		test(str, "ISO-8859-1");
		System.out.println();
		
		char a = 'a';
		System.out.println(a);
		System.out.println((int) a);
		System.out.println(HexUtils.byteToHexStr(new byte[]{(byte)a}));
		
//		System.out.println(new String(0xE4B8ADE59BBD,"utf-8"));

		//		System.out.println(new String(str.getBytes("UTF-8"), "GBK"));
	}
	
	static void test(String str, String encoding) throws UnsupportedEncodingException {
		byte[] bs = str.getBytes(encoding);
		System.out.println("字符串：" + str);
		System.out.println("编码：" + encoding);
		System.out.println("字节数：" + bs.length);
		System.out.print("16进制：");
		for (byte b : bs) {
			System.out.print(String.format("0x%s ", HexUtils.byteToHexStr(new byte[]{b})));
		}
		System.out.print("\n10进制：");
		for (byte b : bs) {
			System.out.print(String.format("%s ", b));
		}
		System.out.print("\n单字节表示的字符：");
		for (byte b : bs) {
			System.out.print((char)b + " ");
		}
		System.out.println();
	}
}