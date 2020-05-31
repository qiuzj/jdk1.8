根据修改的数据类型，可以将JUC包中的原子操作类可以分为4类。

* 基本类型: AtomicInteger, AtomicLong, AtomicBoolean ;

* 数组类型: AtomicIntegerArray, AtomicLongArray, AtomicReferenceArray ;
与基本的主要区别，是要有个基地址base，对于每个元素下标offset的移位计算

* 引用类型: AtomicReference, AtomicStampedRerence, AtomicMarkableReference ;
比基本类型还要简单，set直接赋值，get直接返回，CAS直接调用unsafe的方法

* 对象的属性修改类型: AtomicIntegerFieldUpdater, AtomicLongFieldUpdater, AtomicReferenceFieldUpdater 。
原子更新对象内部的volatile字段，道理和基本类型是一致的，只不过需要通过反射获取字段对象，然后检查访问权限、检查数据类型、检查是否为volatile等

这些类存在的目的是对相应的数据进行原子操作。所谓原子操作，是指操作过程不会被中断，保证数据操作是以原子方式进行的
