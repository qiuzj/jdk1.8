### CLH锁

CLH锁也是一种基于链表的可扩展、高性能、公平的自旋锁，申请线程只在本地变量上自旋，它不断轮询前驱的状态，如果发现前驱释放了锁就结束自旋。

### 添加到列尾

	node.prev = t; // set prev
	if (compareAndSetTail(t, node)) { // cas set as tail
    	t.next = node; // set as next

### 公平锁和非公平锁的区别

公平锁和非公平锁的区别，是在获取锁的机制上的区别。

表现在，在尝试获取锁时（tryAcquire） —— 公平锁，只有在当前线程是CLH等待队列的表头时，才获取锁；
而非公平锁，只要当前锁处于空闲状态，则直接获取锁，而不管CLH等待队列中的顺序。

只有当非公平锁尝试获取锁失败的时候，它才会像公平锁一样，进入CLH等待队列排序等待。
