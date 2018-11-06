package javalang;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoopTest {

	private static List<String> list = new ArrayList<>();
	
	public static void main(String[] args) {
		Set<String> set = new HashSet<>();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 1000000000; i++) {
					for (int j = 0; j < 10; j++) {
						list.add("ll2222222222222222222222222222" + i);
						set.add("s12222222222222222222222222222" + i);
					}
					System.out.println(i);
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, "My-Thread").start();
	}
	
}
