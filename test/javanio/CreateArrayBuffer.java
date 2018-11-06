package javanio;

// $Id$

import java.nio.ByteBuffer;

public class CreateArrayBuffer {
	static public void main(String args[]) throws Exception {
		byte array[] = new byte[1024];

		ByteBuffer buffer = ByteBuffer.wrap(array);

		buffer.put((byte) 'a');
		buffer.put((byte) 'b');
		buffer.put((byte) 'c');

		buffer.flip();

		System.out.println((char) buffer.get());
		System.out.println((char) buffer.get());
		System.out.println((char) buffer.get());
		
		System.out.println();
		System.out.println((char) array[0]);
		System.out.println((char) array[1]);
		System.out.println((char) array[2]);

		System.out.println();
		array[0] = 'd';
		buffer.flip();
		System.out.println((char) buffer.get());
		System.out.println((char) buffer.get());
		System.out.println((char) buffer.get());
		
		System.out.println();
		buffer.rewind(); // 重读Buffer中的所有数据
		System.out.println((char) buffer.get());
		buffer.mark(); // 标记当前位置 
		System.out.println((char) buffer.get());
		System.out.println((char) buffer.get());
		
		System.out.println();
		buffer.reset(); // 恢复到最后一次标记的位置
		System.out.println((char) buffer.get());
		System.out.println((char) buffer.get());
	}
}
