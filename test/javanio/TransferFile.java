package javanio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class TransferFile {

	public static void main(String[] args) throws IOException {
		RandomAccessFile fromFile = new RandomAccessFile("F:/fromFile.txt", "rw");
		FileChannel      fromChannel = fromFile.getChannel();
		RandomAccessFile toFile = new RandomAccessFile("F:/toFile.txt", "rw");
		FileChannel      toChannel = toFile.getChannel();
		long position = 0;
		long count = fromChannel.size();
		fromChannel.transferTo(position, count, toChannel);
	}
	
}
