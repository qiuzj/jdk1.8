package javanio;

// $Id$

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {
	static public void main(String args[]) throws Exception {
		RandomAccessFile aFile = new RandomAccessFile("F:/truncate.txt", "rw");
		FileChannel inChannel = aFile.getChannel();
		ByteBuffer buf = ByteBuffer.allocate(3);
		buf.put((byte) 'a');
		buf.put((byte) 'b');
		buf.put((byte) 'c');
		buf.flip();
		inChannel.write(buf);
		inChannel.truncate(1);
		inChannel.force(true); // 将通道里尚未写入磁盘的数据强制写到磁盘上。
	}
}
