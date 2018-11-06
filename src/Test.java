import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Test {

	public static void a(Integer... ab) {
		System.out.println(ab.length);
		System.out.println(ab[0]);
	}
	
	public static void main(String[] args) throws InterruptedException {
		Integer[] iys = {1,2,3};
		Object[] oys = iys;
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
//		t.start();
		t.join();
		
		System.out.println(oys.getClass().getComponentType());
//		a();
		a(new Integer(1));
//		T[] copy = ((Object)newType == (Object)Object[].class)
//	            ? (T[]) new Object[newLength]
//	            : (T[]) Array.newInstance(newType.getComponentType(), newLength);
		Class newType = Object[].class;
		System.out.println(((Object)newType == (Object)Object[].class));

		System.out.println(newType == Object[].class);
		System.out.println(newType.getComponentType());
		System.out.println(Object.class.getComponentType());
		
		newType = Integer[].class;
		System.out.println(((Object)newType == (Object)Object[].class));
		newType = int[].class;
		System.out.println(((Object)newType == (Object)Object[].class));
		
		newType = Integer[].class;
		System.out.println(((Object)newType == (Object)Integer[].class));
		newType = int[].class;
		System.out.println(((Object)newType == (Object)Integer[].class));
		newType = int[].class;
		System.out.println(((Object)newType == (Object)int[].class));
		
		System.out.println(int[].class);
		System.out.println(Integer[].class);
		System.out.println(Object[].class);
		System.out.println(List.class);
		System.out.println(int[].class.getName());
		System.out.println(Integer[].class.getName());
		System.out.println(Object[].class.getName());
		System.out.println(List.class.getName());
		
		List l2 = new ArrayList();
		List l1 = new ArrayList();
		System.out.println(l1.getClass() == l2.getClass());
		
		Object[] o = {1,"2",true};
		Object[] oo = Arrays.copyOf(o, 2);
		for (Object a : oo) {
			System.out.println(a);
		}
		Integer[] ii = Arrays.copyOf(o, 1, Integer[].class);

		System.out.println("---ooo---");
		Object[] ooo = Arrays.copyOf(o, 4, Object[].class);
		for (Object a : ooo) {
			System.out.println(a);
		}
		
		List<Integer> list = new ArrayList<>();
		list.add(1);
		Object[] oa = list.toArray();
		for (Object a : oa) {
			System.out.println(a);
		}
		Object obj = 1;
		System.out.println((Integer) obj);
		obj = true;
		System.out.println((Boolean) obj);
		Object[] oaa = {1,2,3};
//		Integer[] iaa = (Integer[]) oaa; // java.lang.ClassCastException
	}
	
}
