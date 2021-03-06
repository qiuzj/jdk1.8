## 第1部分 List概括

* (01) List 是一个接口，它继承于Collection的接口。它代表着有序的队列。
* (02) AbstractList 是一个抽象类，它继承于AbstractCollection。AbstractList实现List接口中除size()、get(int location)之外的函数。
* (03) AbstractSequentialList 是一个抽象类，它继承于AbstractList。AbstractSequentialList 实现了“链表中，根据index索引值操作链表的全部函数”。
* (04) ArrayList, LinkedList, Vector, Stack是List的4个实现类。

* ArrayList 是一个数组队列，相当于动态数组。它由数组实现，随机访问效率高，随机插入、随机删除效率低。
* LinkedList 是一个双向链表。它也可以被当作堆栈、队列或双端队列进行操作。LinkedList随机访问效率低，但随机插入、随机删除效率低。
* Vector 是矢量队列，和ArrayList一样，它也是一个动态数组，由数组实现。但是ArrayList是非线程安全的，而Vector是线程安全的。
* Stack 是栈，它继承于Vector。它的特性是：后进先出(LIFO, Last In First Out)。

## 第2部分 List使用场景

学东西的最终目的是为了能够理解、使用它。下面先概括的说明一下各个List的使用场景，后面再分析原因。

如果涉及到“栈”、“队列”、“链表”等操作，应该考虑用List，具体的选择哪个List，根据下面的标准来取舍。

* (01) 对于需要快速插入，删除元素，应该使用LinkedList。
* (02) 对于需要快速随机访问元素，应该使用ArrayList。
* (03) 对于“单线程环境” 或者 “多线程环境，但List仅仅只会被单个线程操作”，此时应该使用非同步的类(如ArrayList)。
对于“多线程环境，且List可能同时被多个线程操作”，此时，应该使用同步的类(如Vector)。

ArrayList真正耗时的操作是移动元素：System.arraycopy

