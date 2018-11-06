package javalang;

public class StringTest3 {
	public static void main(String[] args) {
		String userName = "Andy";
		String age = "24";
		String job = "Developer";
		String info = userName + age + job; // javap -c to look here
		System.out.println(info);
	}

	public void implicitUseStringBuilder(String[] values) {
		String result = "";
		for (int i = 0; i < values.length; i++) { // 循环内重复创建java/lang/StringBuilder
			result += values[i];
		}
		System.out.println(result);
	}

	public void explicitUseStringBuider(String[] values) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			result.append(values[i]);
		}
	}
}
