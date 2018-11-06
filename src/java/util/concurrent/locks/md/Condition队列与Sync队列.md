### Condition队列与Sync队列（锁等待队列）有几点不同

- Condition队列是一个单向链表，而Sync队列是一个双向链表；
- Sync队列在初始化的时候，会在队列头部添加一个空的dummy节点，它不持有任何线程，而Condition队列初始化时，头结点就开始持有等待线程了。
