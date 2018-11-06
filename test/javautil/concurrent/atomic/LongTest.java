package javautil.concurrent.atomic;
import java.util.concurrent.atomic.AtomicLong;

public class LongTest {

    public static void main(String[] args){

        // 新建AtomicLong对象
        AtomicLong mAtoLong = new AtomicLong();

        mAtoLong.set(0x0123456789ABCDEFL);
        
        // 16进制打印
        System.out.printf("%20s : 0x%016X\n", "get()", mAtoLong.get());
        System.out.printf("%20s : 0x%016X\n", "intValue()", mAtoLong.intValue());
        System.out.printf("%20s : 0x%016X\n", "longValue()", mAtoLong.longValue());
        
        // 10进制打印
        System.out.printf("%20s : %s\n", "doubleValue()", mAtoLong.doubleValue());
        System.out.printf("%20s : %s\n", "floatValue()", mAtoLong.floatValue());

        System.out.printf("%20s : 0x%016X\n", "getAndDecrement()", mAtoLong.getAndDecrement());
        System.out.printf("%20s : 0x%016X\n", "decrementAndGet()", mAtoLong.decrementAndGet());
        System.out.printf("%20s : 0x%016X\n", "getAndIncrement()", mAtoLong.getAndIncrement());
        System.out.printf("%20s : 0x%016X\n", "incrementAndGet()", mAtoLong.incrementAndGet());

        System.out.printf("%20s : 0x%016X\n", "addAndGet(0x10)", mAtoLong.addAndGet(0x10));
        System.out.printf("%20s : 0x%016X\n", "getAndAdd(0x10)", mAtoLong.getAndAdd(0x10));

        System.out.printf("\n%20s : 0x%016X\n", "get()", mAtoLong.get());

        System.out.printf("%20s : %s\n", "compareAndSet()", mAtoLong.compareAndSet(0x12345679L, 0xFEDCBA9876543210L));
        System.out.printf("%20s : 0x%016X\n", "get()", mAtoLong.get());
    }
}
//get() : 0x0123456789ABCDEF
//intValue() : 0x0000000089ABCDEF
//longValue() : 0x0123456789ABCDEF
//doubleValue() : 8.1985529216486896E16
//floatValue() : 8.1985531E16
//getAndDecrement() : 0x0123456789ABCDEF
//decrementAndGet() : 0x0123456789ABCDED
//getAndIncrement() : 0x0123456789ABCDED
//incrementAndGet() : 0x0123456789ABCDEF
//addAndGet(0x10) : 0x0123456789ABCDFF
//getAndAdd(0x10) : 0x0123456789ABCDFF
//
//get() : 0x0123456789ABCE0F
//compareAndSet() : false
//get() : 0x0123456789ABCE0F
