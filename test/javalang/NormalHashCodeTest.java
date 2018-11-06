package javalang;

/**
 * @desc 比较equals() 返回true 以及 返回false时， hashCode()的值。
 *
 * @author skywang
 * @emai kuiwu-wang@163.com
 */
public class NormalHashCodeTest{

    public static void main(String[] args) {
        // 新建2个相同内容的Person对象，
        // 再用equals比较它们是否相等
        Person p1 = new Person("eee", 100);
        Person p2 = new Person("eee", 100);
        Person p3 = new Person("aaa", 200);
        System.out.printf("p1.equals(p2) : %s; p1(%d) p2(%d)\n", p1.equals(p2), p1.hashCode(), p2.hashCode());
        System.out.printf("p1.equals(p3) : %s; p1(%d) p3(%d)\n", p1.equals(p3), p1.hashCode(), p3.hashCode());
        
        System.out.println(System.identityHashCode(p1));
        System.out.println(System.identityHashCode(p2));
        System.out.println();
        
        char a = 'a';
        char b = 'c';
        System.out.println((int) a);
        System.out.println((int) b);
        System.out.println(a - b);
    }

    /**
     * @desc Person类。
     */
    private static class Person {
        int age;
        String name;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String toString() {
            return name + " - " +age;
        }

        /** 
         * @desc 覆盖equals方法 
         */  
        public boolean equals(Object obj){  
            if(obj == null){  
                return false;  
            }  

            //如果是同一个对象返回true，反之返回false  
            if(this == obj){  
                return true;  
            }  

            //判断是否类型相同  
            if(this.getClass() != obj.getClass()){  
                return false;  
            }  

            Person person = (Person)obj;  
            return name.equals(person.name) && age==person.age;  
        } 
    }
}