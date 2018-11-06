package javalang;

/**
 * NaN与任何浮点数（包括自身）的比较结果都为假
 * 
 * Java虚拟机在处理浮点数运算时，不会抛出任何运行时异常（这里所讲的是java语言中的异常，请勿与IEEE 754规范中的浮点异常相互混淆，IEEE 754的浮点异常是一种运算信号），
 * 当一个操作产生溢出时，将会使用有符号的无穷大来表示，如果某个操作结果没有明确的数学定义的话，将会使用NaN值来表示，所有使用NaN值作为操作数的算术操作，结果都返回NaN。
 * Java中的Double和Float中都有isNaN函数，判断一个数是不是NaN，其实现都是通过上述 v != v 的方式，因为NaN是唯一与自己不相等的值，NaN与任何值都不相等。有些操作会使isNaN返回True
 * 
 * @author qiuzj
 *
 */
public class FloatTest {
	
	/*
	定义：　　

	　　NaN（Not a Number，非数）是计算机科学中数值数据类型的一个值，表示未定义或不可表示的值。常在浮点数运算中使用。首次引入NaN的是1985年的IEEE 754浮点数标准。

	会返回NaN的运算：

	操作数中至少有一个是 NaN 的运算
	未定义操作
	下列除法运算：0/0、∞/∞、∞/−∞、−∞/∞、−∞/−∞
	下列乘法运算：0×∞、0×-∞
	下列加法运算：∞ + (−∞)、(−∞) + ∞
	下列减法运算：∞ - ∞、(−∞) - (−∞)
	产生复数结果的实数运算。例如：
	对负数进行开方运算
	对负数进行对数运算
	对比-1小或比+1大的数进行反正弦或反余弦运算
	*/
	
	public static void main(String[] args) {
        Float f1 = new Float(-1.0 / 0.0);
        Float f2 = new Float(0.0 / 0.0);
        Double f3 = Math.sqrt(-1);

        // Returns true if this Float value is a Not-a-Number (NaN), false otherwise.
        System.out.println(f1 + " = " + f1.isNaN());
        System.out.println(f2 + " = " + f2.isNaN());
        System.out.println(f3 + " = " + f3.isNaN());
        System.out.println(Double.isNaN(Double.longBitsToDouble(0x7ff0000000000011L)));
        
        System.out.println();
        System.out.println(Float.POSITIVE_INFINITY);
        System.out.println(Float.NEGATIVE_INFINITY);
        System.out.println(Float.NaN);
        System.out.println(Float.intBitsToFloat(0x7fc00000));
        
        System.out.println();
        float f4 = 0/0.0f;
        Float f5 = new Float(0.0 / Float.POSITIVE_INFINITY);
        System.out.println(f5);
        f5 = new Float(0 * Float.POSITIVE_INFINITY);
        System.out.println(f5);
        f5 = new Float(Float.POSITIVE_INFINITY / Float.POSITIVE_INFINITY);
        System.out.println(f5);
        f5 = new Float(Float.POSITIVE_INFINITY + Float.NEGATIVE_INFINITY);
        System.out.println(f5);
        
        System.out.println();
        double d1 = 0.0 / 0.0;
        System.out.println(d1);
	}
}
