## 简介

AbstractQueuedSynchronizer（AQS）是一个同步器框架，在实现锁的时候，一般会实现一个继承自AQS的内部类sync，作为我们的自定义同步器。AQS内部维护了一个state成员和一个队列。其中state标识了共享资源的状态，队列则记录了等待资源的线程。以下这五个方法，在AQS中实现为直接抛出异常，这是我们自定义同步器需要重写的方法：

①isHeldExclusively()：该线程是否正在独占资源。只有用到condition才需要去实现它。

②tryAcquire(int)：独占方式。尝试获取资源，成功则返回true，失败返回false。

③tryRelease(int)：独占方式。尝试释放资源，成功则返回true，失败返回false。

④tryAcquireShared(int)：共享方式。尝试获取资源。成功返回true，失败返回false。

⑤tryReleaseShared(int)：共享方式。尝试释放资源，成功则返回true，失败返回false。

## 4.1 线程状态

AQS采用的是CLH队列，CLH队列是由一个一个结点构成的，前面提到结点中有一个状态位，这个状态位与线程状态密切相关，这个状态位(waitStatus)是一个32位的整型常量，它的取值如下：

static final int CANCELLED =  1;  
static final int SIGNAL    = -1;  
static final int CONDITION = -2;  
static final int PROPAGATE = -3;
                   INITIAL = 0
下面解释一下每个值的含义：

CANCELLED：因为超时或者中断，结点会被设置为取消状态，被取消状态的结点不应该去竞争锁，只能保持取消状态不变，不能转换为其他状态。处于这种状态的结点会被踢出队列，被GC回收；

SIGNAL：表示这个结点的继任结点被阻塞了，到时需要通知它；

CONDITION：表示这个结点在条件队列中，因为等待某个条件而被阻塞；

PROPAGATE：使用在共享模式头结点有可能处于这种状态，表示锁的下一次获取可以无条件传播；

0：None of the above，新结点会处于这种状态；

在我们的Mutex的例子中，节点的waitStatus只可能有CANCELLED、SIGNAL和0三中状态（事实上，独占模式下所有不使用Condition的同步器都是这样）。

独占模式：CANCELLED、SIGNAL和0
Condition应该是四种状态吧：CONDITION、CANCELLED、SIGNAL和0
共享模式：CANCELLED、SIGNAL、PROPAGATE和0

waitStatus：0 or PROPAGATE, indicate that we need a signal

## CONDITION

* lock()：n个线程add to Sync queue > park
* await()：第1个lock成功的 > await() > add to Condition queue > release独占锁 > unparkSuccessor（LockSupport.unpark(head..next.thread)） > 从parkAndCheckInterrupt中返回
* unlock()：

* lock()：n个线程add to Sync queue > park
* signal()：第1个lock成功的  > signal() > CAS firstWaiter from Node.CONDITION to 0 > enq(firstWaiter)
* unlock()：





wx845012375



