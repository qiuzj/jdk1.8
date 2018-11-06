/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.util;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <pre>
 * HashMap 是一个散列表，它存储的内容是键值对(key-value)映射。（实现方式：位桶+链表/红黑树）
 * HashMap 继承于AbstractMap，实现了Map、Cloneable、java.io.Serializable接口。
 * HashMap 的实现不是同步的，这意味着它不是线程安全的。它的key、value都可以为null。此外，HashMap中的映射不是有序的。
 * HashMap 的实例有两个参数影响其性能："初始容量" 和 "加载因子"。容量 是哈希表中桶的数量，初始容量 只是哈希表在创建时的容量。
 * 加载因子 是哈希表在其容量自动增加之前可以达到多满的一种尺度。
 * 当哈希表中的条目数超出了加载因子与当前容量的乘积时，则要对该哈希表进行 rehash 操作（即重建内部数据结构），从而哈希表将具有大约两倍的桶数。
 * 通常，默认加载因子是 0.75, 这是在时间和空间成本上寻求一种折衷。
 * 加载因子过高虽然减少了空间开销，但同时也增加了查询成本（在大多数 HashMap 类的操作中，包括 get 和 put 操作，都反映了这一点）。
 * 在设置初始容量时应该考虑到映射中所需的条目数及其加载因子，以便最大限度地减少 rehash 操作次数。
 * 如果初始容量大于最大条目数除以加载因子，则不会发生 rehash 操作。
 * </pre>
 * Hash table based implementation of the <tt>Map</tt> interface.  This
 * implementation provides all of the optional map operations, and permits
 * <tt>null</tt> values and the <tt>null</tt> key.  (The <tt>HashMap</tt>
 * class is roughly equivalent to <tt>Hashtable</tt>, except that it is
 * unsynchronized and permits nulls.)  This class makes no guarantees as to
 * the order of the map; in particular, it does not guarantee that the order
 * will remain constant over time.
 *
 * <p>This implementation provides constant-time performance for the basic
 * operations (<tt>get</tt> and <tt>put</tt>), assuming the hash function
 * disperses the elements properly among the buckets.  Iteration over
 * collection views requires time proportional to the "capacity" of the
 * <tt>HashMap</tt> instance (the number of buckets) plus its size (the number
 * of key-value mappings).  Thus, it's very important not to set the initial
 * capacity too high (or the load factor too low) if iteration performance is
 * important.
 *
 * <p>An instance of <tt>HashMap</tt> has two parameters that affect its
 * performance: <i>initial capacity</i> and <i>load factor</i>.  The
 * <i>capacity</i> is the number of buckets in the hash table, and the initial
 * capacity is simply the capacity at the time the hash table is created.  The
 * <i>load factor</i> is a measure of how full the hash table is allowed to
 * get before its capacity is automatically increased.  When the number of
 * entries in the hash table exceeds the product of the load factor and the
 * current capacity, the hash table is <i>rehashed</i> (that is, internal data
 * structures are rebuilt) so that the hash table has approximately twice the
 * number of buckets.
 *
 * <p>As a general rule, the default load factor (.75) offers a good
 * tradeoff between time and space costs.  Higher values decrease the
 * space overhead but increase the lookup cost (reflected in most of
 * the operations of the <tt>HashMap</tt> class, including
 * <tt>get</tt> and <tt>put</tt>).  The expected number of entries in
 * the map and its load factor should be taken into account when
 * setting its initial capacity, so as to minimize the number of
 * rehash operations.  If the initial capacity is greater than the
 * maximum number of entries divided by the load factor, no rehash
 * operations will ever occur.
 *
 * <p>If many mappings are to be stored in a <tt>HashMap</tt>
 * instance, creating it with a sufficiently large capacity will allow
 * the mappings to be stored more efficiently than letting it perform
 * automatic rehashing as needed to grow the table.  Note that using
 * many keys with the same {@code hashCode()} is a sure way to slow
 * down performance of any hash table. To ameliorate impact, when keys
 * are {@link Comparable}, this class may use comparison order among
 * keys to help break ties.
 *
 * <p><strong>Note that this implementation is not synchronized.</strong>
 * If multiple threads access a hash map concurrently, and at least one of
 * the threads modifies the map structurally, it <i>must</i> be
 * synchronized externally.  (A structural modification is any operation
 * that adds or deletes one or more mappings; merely changing the value
 * associated with a key that an instance already contains is not a
 * structural modification.)  This is typically accomplished by
 * synchronizing on some object that naturally encapsulates the map.
 *
 * If no such object exists, the map should be "wrapped" using the
 * {@link Collections#synchronizedMap Collections.synchronizedMap}
 * method.  This is best done at creation time, to prevent accidental
 * unsynchronized access to the map:<pre>
 *   Map m = Collections.synchronizedMap(new HashMap(...));</pre>
 *
 * <p>The iterators returned by all of this class's "collection view methods"
 * are <i>fail-fast</i>: if the map is structurally modified at any time after
 * the iterator is created, in any way except through the iterator's own
 * <tt>remove</tt> method, the iterator will throw a
 * {@link ConcurrentModificationException}.  Thus, in the face of concurrent
 * modification, the iterator fails quickly and cleanly, rather than risking
 * arbitrary, non-deterministic behavior at an undetermined time in the
 * future.
 *
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw <tt>ConcurrentModificationException</tt> on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness: <i>the fail-fast behavior of iterators
 * should be used only to detect bugs.</i>
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @author  Doug Lea
 * @author  Josh Bloch
 * @author  Arthur van Hoff
 * @author  Neal Gafter
 * @see     Object#hashCode()
 * @see     Collection
 * @see     Map
 * @see     TreeMap
 * @see     Hashtable
 * @since   1.2
 */
public class HashMap<K,V> extends AbstractMap<K,V>
    implements Map<K,V>, Cloneable, Serializable {

    private static final long serialVersionUID = 362498820763181265L;

    /*
     * Implementation notes.
     *
     * This map usually acts as a binned (bucketed) hash table, but
     * when bins get too large, they are transformed into bins of
     * TreeNodes, each structured similarly to those in
     * java.util.TreeMap. Most methods try to use normal bins, but
     * relay to TreeNode methods when applicable (simply by checking
     * instanceof a node).  Bins of TreeNodes may be traversed and
     * used like any others, but additionally support faster lookup
     * when overpopulated. However, since the vast majority of bins in
     * normal use are not overpopulated, checking for existence of
     * tree bins may be delayed in the course of table methods.
     *
     * Tree bins (i.e., bins whose elements are all TreeNodes) are
     * ordered primarily by hashCode, but in the case of ties, if two
     * elements are of the same "class C implements Comparable<C>",
     * type then their compareTo method is used for ordering. (We
     * conservatively check generic types via reflection to validate
     * this -- see method comparableClassFor).  The added complexity
     * of tree bins is worthwhile in providing worst-case O(log n)
     * operations when keys either have distinct hashes or are
     * orderable, Thus, performance degrades gracefully under
     * accidental or malicious usages in which hashCode() methods
     * return values that are poorly distributed, as well as those in
     * which many keys share a hashCode, so long as they are also
     * Comparable. (If neither of these apply, we may waste about a
     * factor of two in time and space compared to taking no
     * precautions. But the only known cases stem from poor user
     * programming practices that are already so slow that this makes
     * little difference.)
     *
     * Because TreeNodes are about twice the size of regular nodes, we
     * use them only when bins contain enough nodes to warrant use
     * (see TREEIFY_THRESHOLD). And when they become too small (due to
     * removal or resizing) they are converted back to plain bins.  In
     * usages with well-distributed user hashCodes, tree bins are
     * rarely used.  Ideally, under random hashCodes, the frequency of
     * nodes in bins follows a Poisson distribution
     * (http://en.wikipedia.org/wiki/Poisson_distribution) with a
     * parameter of about 0.5 on average for the default resizing
     * threshold of 0.75, although with a large variance because of
     * resizing granularity. Ignoring variance, the expected
     * occurrences of list size k are (exp(-0.5) * pow(0.5, k) /
     * factorial(k)). The first values are:
     *
     * 0:    0.60653066
     * 1:    0.30326533
     * 2:    0.07581633
     * 3:    0.01263606
     * 4:    0.00157952
     * 5:    0.00015795
     * 6:    0.00001316
     * 7:    0.00000094
     * 8:    0.00000006
     * more: less than 1 in ten million
     *
     * The root of a tree bin is normally its first node.  However,
     * sometimes (currently only upon Iterator.remove), the root might
     * be elsewhere, but can be recovered following parent links
     * (method TreeNode.root()).
     *
     * All applicable internal methods accept a hash code as an
     * argument (as normally supplied from a public method), allowing
     * them to call each other without recomputing user hashCodes.
     * Most internal methods also accept a "tab" argument, that is
     * normally the current table, but may be a new or old one when
     * resizing or converting.
     *
     * When bin lists are treeified, split, or untreeified, we keep
     * them in the same relative access/traversal order (i.e., field
     * Node.next) to better preserve locality, and to slightly
     * simplify handling of splits and traversals that invoke
     * iterator.remove. When using comparators on insertion, to keep a
     * total ordering (or as close as is required here) across
     * rebalancings, we compare classes and identityHashCodes as
     * tie-breakers.
     *
     * The use and transitions among plain vs tree modes is
     * complicated by the existence of subclass LinkedHashMap. See
     * below for hook methods defined to be invoked upon insertion,
     * removal and access that allow LinkedHashMap internals to
     * otherwise remain independent of these mechanics. (This also
     * requires that a map instance be passed to some utility methods
     * that may create new nodes.)
     *
     * The concurrent-programming-like SSA-based coding style helps
     * avoid aliasing errors amid all of the twisty pointer operations.
     */

    /**
     * 默认的初始容量是16，必须是2的幂
     * <p>
     * The default initial capacity - MUST be a power of two.
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    /**
     * 最大容量（必须是2的幂且小于等于2的30次方，传入容量过大将被这个值替换）
     * <p>
     * The maximum capacity, used if a higher value is implicitly specified
     * by either of the constructors with arguments.
     * MUST be a power of two <= 1<<30.
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * 默认加载因子
     * <p>
     * The load factor used when none specified in constructor.
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 这是jdk1.8新加的，这是链表的最大长度，当大于这个长度时，就会将链表转成红黑树
     * <p>
     * The bin count threshold for using a tree rather than list for a
     * bin.  Bins are converted to trees when adding an element to a
     * bin with at least this many nodes. The value must be greater
     * than 2 and should be at least 8 to mesh with assumptions in
     * tree removal about conversion back to plain bins upon
     * shrinkage.
     */
    static final int TREEIFY_THRESHOLD = 8;

    /**
     * 位桶节点数<=6时转换为链表，位桶节点数>6时转换为红黑树（split方法使用）
     * <p>
     * The bin count threshold for untreeifying a (split) bin during a
     * resize operation. Should be less than TREEIFY_THRESHOLD, and at
     * most 6 to mesh with shrinkage detection under removal.
     */
    static final int UNTREEIFY_THRESHOLD = 6;

    /**
     * 仅当table的元素达到该值时，才将链表转换为二叉树
     * <p>
     * The smallest table capacity for which bins may be treeified.
     * (Otherwise the table is resized if too many nodes in a bin.)
     * Should be at least 4 * TREEIFY_THRESHOLD to avoid conflicts
     * between resizing and treeification thresholds.
     */
    static final int MIN_TREEIFY_CAPACITY = 64;

    /**
     * 普通散列桶节点
     * <p>
     * Basic hash bin node, used for most entries.  (See below for
     * TreeNode subclass, and in LinkedHashMap for its Entry subclass.)
     */
    static class Node<K,V> implements Map.Entry<K,V> {
    	/**
    	 * 结点的哈希值，不可变
    	 */
        final int hash;
        /**
         * 健
         */
        final K key;
        /**
         * 值
         */
        V value;
        /**
         * 下一个节点
         */
        Node<K,V> next;

        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey()        { return key; }
        public final V getValue()      { return value; }
        public final String toString() { return key + "=" + value; }

        /**
         * 获取哈希码. key ^ value
         */
        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value); // key.hashCode() ^ value.hashCode()
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        /**
         * 同一对象，或key和value都相等的情况下，返回true
         */
        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?,?> e = (Map.Entry<?,?>)o;
                if (Objects.equals(key, e.getKey()) &&
                    Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }
    }

    /* ---------------- Static utilities -------------- */

    /**
     * 计算key的hash值
     * <p>
     * Computes key.hashCode() and spreads (XORs) higher bits of hash
     * to lower.  Because the table uses power-of-two masking, sets of
     * hashes that vary only in bits above the current mask will
     * always collide. (Among known examples are sets of Float keys
     * holding consecutive whole numbers in small tables.)  So we
     * apply a transform that spreads the impact of higher bits
     * downward. There is a tradeoff between speed, utility, and
     * quality of bit-spreading. Because many common sets of hashes
     * are already reasonably distributed (so don't benefit from
     * spreading), and because we use trees to handle large sets of
     * collisions in bins, we just XOR some shifted bits in the
     * cheapest possible way to reduce systematic lossage, as well as
     * to incorporate impact of the highest bits that would otherwise
     * never be used in index calculations because of table bounds.
     */
    static final int hash(Object key) {
        int h;
        /*
         * 扰动函数.
         * 理论上散列值是一个int型值，如果直接拿散列值作为下标访问HashMap主数组的话，2的32次数共40亿的空间，内存放不下，浪费空间。
         * 高半区和低半区做异或，混合原始哈希码的高位和低位，以此来加大低位的随机性。
         */
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * 通过反射判断对象x是否实现Comparable<C>接口.
     * 如果实现了Comparable，返回x的实际类型，也就是Class<C>，否则返回null.
     * <p>
     * Returns x's Class if it is of the form "class C implements
     * Comparable<C>", else null.
     */
    static Class<?> comparableClassFor(Object x) {
        if (x instanceof Comparable) { // 首先检查对象x是否为Comparable的实例
            Class<?> c; 
            Type[] ts, as; 
            Type t; 
            ParameterizedType p;
            if ((c = x.getClass()) == String.class) // bypass checks. String最常用，加个速
                return c;
            if ((ts = c.getGenericInterfaces()) != null) {
                for (int i = 0; i < ts.length; ++i) {
                    if (((t = ts[i]) instanceof ParameterizedType) && // 接口为参数化接口，如A<>
                        ((p = (ParameterizedType)t).getRawType() == // 参数化类型为Comparable
                         Comparable.class) &&
                        (as = p.getActualTypeArguments()) != null && // 实际类型参数不为null，即存在<>，且参数个数为1个并为x.getClass
                        as.length == 1 && as[0] == c) // type arg is c
                        return c;
                }
            }
        }
        return null;
    }

    /**
     * Returns k.compareTo(x) if x matches kc (k's screened comparable
     * class), else 0.
     */
    @SuppressWarnings({"rawtypes","unchecked"}) // for cast to Comparable
    static int compareComparables(Class<?> kc, Object k, Object x) {
        return (x == null || x.getClass() != kc ? 0 :
                ((Comparable)k).compareTo(x));
    }

    /**
     * 根据capacity，计算阈值大小（必须是2的幂），取>=capacity的最小值
     * <p>
     * 性能优化：<br>
     * 1. length为2的整数幂保证了length - 1 最后一位（二进制表示）为1，从而保证了索引位置index即（ hash &length-1）的最后一位同时有为0和为1的可能性，保证了散列的均匀性。<br>
     * 2. 反过来讲，若length为奇数，length-1最后一位为0，这样与h按位与【同1为1】的最后一位肯定为0，即索引位置肯定是偶数，这样数组的奇数位置全部没有放置元素，浪费了大量空间。<br>
     * * 简而言之：length为2的幂保证了按位与最后一位的有效性，使哈希表散列更均匀。
     * <p>
     * Returns a power of two size for the given target capacity.
     */
    static final int tableSizeFor(int cap) { // JDK 1.6实现逻辑：int capacity = 1; while (capacity < initialCapacity) capacity <<= 1;
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1; // >= 1
    }

    /* ---------------- Fields -------------- */

    /**
     * 哈希桶数组.
     * 存储数据的Entry数组，长度是2的幂。<br>
     * HashMap是采用拉链法实现的，每一个Entry本质上是一个单向链表。<br>
     * 单向链表，哈希表的"key-value键值对"都是存储在数组中，是对Map.Entry的实现
     * <p>
     * The table, initialized on first use, and resized as
     * necessary. When allocated, length is always a power of two.
     * (We also tolerate length zero in some operations to allow
     * bootstrapping mechanics that are currently not needed.)
     */
    transient Node<K,V>[] table;

    /**
     * 节点集合
     * <p>
     * Holds cached entrySet(). Note that AbstractMap fields are used
     * for keySet() and values().
     */
    transient Set<Map.Entry<K,V>> entrySet;

    /**
     * HashMap的大小，它是HashMap保存的键值对的数量
     * <p>
     * The number of key-value mappings contained in this map.
     */
    transient int size;

    /**
     * HashMap内部结构发生变化的次数（覆盖值不属于结构变化）. 用来实现fail-fast机制
     * <p>
     * The number of times this HashMap has been structurally modified
     * Structural modifications are those that change the number of mappings in
     * the HashMap or otherwise modify its internal structure (e.g.,
     * rehash).  This field is used to make iterators on Collection-views of
     * the HashMap fail-fast.  (See ConcurrentModificationException).
     */
    transient int modCount;

    /**
     * <pre>
     * HashMap所能容纳的最大数据量的Node(键值对)个数。
     * threshold = length * Load factor。也就是说，在数组定义好长度之后，负载因子越大，所能容纳的键值对个数越多。
     * HashMap的阈值，用于判断是否需要调整HashMap的容量（threshold = 容量*加载因子）。<br>
     * threshold的值="容量*加载因子"，当HashMap中存储数据的数量达到threshold时，就需要将HashMap的容量加倍。
     * 容量=threshold/loadFactor
     * </pre>
     * The next size value at which to resize (capacity * load factor).
     *
     * @serial
     */
    // (The javadoc description is true upon serialization.
    // Additionally, if the table array has not been allocated, this
    // field holds the initial array capacity, or zero signifying
    // DEFAULT_INITIAL_CAPACITY.)
    int threshold;

    /**
     * 加载因子实际大小
     * <p>
     * The load factor for the hash table.
     *
     * @serial
     */
    final float loadFactor;

    /* ---------------- Public operations -------------- */

    /**
     * 指定"容量大小"和"加载因子"的构造函数
     * <p>
     * Constructs an empty <tt>HashMap</tt> with the specified initial
     * capacity and load factor.
     *
     * @param  initialCapacity the initial capacity
     * @param  loadFactor      the load factor
     * @throws IllegalArgumentException if the initial capacity is negative
     *         or the load factor is nonpositive
     */
    public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                                               initialCapacity);
        
        // 如果初始容量太大，则用MAXIMUM_CAPACITY替代
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        
        // 加载因子不合法，小于等于0，或非数字
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                                               loadFactor);
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity); // 根据initialCapacity，计算阈值大小（必须是2的幂），取>=initialCapacity的最小值
    }

    /**
     * 指定"容量大小"的构造函数
     * <p>
     * Constructs an empty <tt>HashMap</tt> with the specified initial
     * capacity and the default load factor (0.75).
     *
     * @param  initialCapacity the initial capacity.
     * @throws IllegalArgumentException if the initial capacity is negative.
     */
    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 默认构造函数
     * <p>
     * Constructs an empty <tt>HashMap</tt> with the default initial capacity
     * (16) and the default load factor (0.75).
     */
    public HashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
    }

    /**
     * 包含"子Map"的构造函数
     * <p>
     * Constructs a new <tt>HashMap</tt> with the same mappings as the
     * specified <tt>Map</tt>.  The <tt>HashMap</tt> is created with
     * default load factor (0.75) and an initial capacity sufficient to
     * hold the mappings in the specified <tt>Map</tt>.
     *
     * @param   m the map whose mappings are to be placed in this map
     * @throws  NullPointerException if the specified map is null
     */
    public HashMap(Map<? extends K, ? extends V> m) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        putMapEntries(m, false); // false表示初始化构造Map
    }

    /**
     * Implements Map.putAll and Map constructor
     *
     * @param m the map
     * @param evict false when initially constructing this map, else
     * true (relayed to method afterNodeInsertion). false表示初始化构造Map
     */
    final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
        int s = m.size();
        if (s > 0) {
        	// 初始化阈值
            if (table == null) { // pre-size
                float ft = ((float)s / loadFactor) + 1.0F; // 把s当作阈值，s/loadFactor=容量，容量+1作为新阈值
                int t = ((ft < (float)MAXIMUM_CAPACITY) ?
                         (int)ft : MAXIMUM_CAPACITY);
                if (t > threshold)
                    threshold = tableSizeFor(t);
            }
            // 传入的Map大小超过阈值，需要扩容rehash
            else if (s > threshold)
                resize();
            
            for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
                K key = e.getKey();
                V value = e.getValue();
                putVal(hash(key), key, value, false, evict);
            }
        }
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
    public int size() {
        return size;
    }

    /**
     * Returns <tt>true</tt> if this map contains no key-value mappings.
     *
     * @return <tt>true</tt> if this map contains no key-value mappings
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 根据key获取value
     * <p>
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that {@code (key==null ? k==null :
     * key.equals(k))}, then this method returns {@code v}; otherwise
     * it returns {@code null}.  (There can be at most one such mapping.)
     *
     * <p>A return value of {@code null} does not <i>necessarily</i>
     * indicate that the map contains no mapping for the key; it's also
     * possible that the map explicitly maps the key to {@code null}.
     * The {@link #containsKey containsKey} operation may be used to
     * distinguish these two cases.
     *
     * @see #put(Object, Object)
     */
    public V get(Object key) {
        Node<K,V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }

    /**
     * 根据hash值和key值查找节点
     * <p>
     * Implements Map.get and related methods
     *
     * @param hash hash for key
     * @param key the key
     * @return the node, or null if none
     */
    final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab; 
        Node<K,V> first, e; 
        int n; 
        K k;
        
        // table不为空，hash对应的桶不为空，则查找节点
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (first = tab[(n - 1) & hash]) != null) {
        	
        	// 是否为桶的第一个节点
            if (first.hash == hash && // always check first node
                ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            
            // 如果不是第一个节点，则往后找
            if ((e = first.next) != null) { // e代表当前正在处理的节点
            	// 红黑树，从根节点开始查找
                if (first instanceof TreeNode)
                    return ((TreeNode<K,V>)first).getTreeNode(hash, key);
                // 如果是链表. 从链头往链尾查找
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }

    /**
     * 指定key是否存在
     * <p>
     * Returns <tt>true</tt> if this map contains a mapping for the
     * specified key.
     *
     * @param   key   The key whose presence in this map is to be tested
     * @return <tt>true</tt> if this map contains a mapping for the specified
     * key.
     */
    public boolean containsKey(Object key) {
        return getNode(hash(key), key) != null;
    }

    /**
     * 将键值对添加到HashMap中，如果键存在，则更新值
     * <p>
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
     *         (A <tt>null</tt> return can also indicate that the map
     *         previously associated <tt>null</tt> with <tt>key</tt>.)
     */
    public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }

    /**
     * put方法内部实现类.
     * 
     * Implements Map.put and related methods
     *
     * @param hash hash for key. key的哈希码
     * @param key the key. 待添加的键
     * @param value the value to put. 待添加的值
     * @param onlyIfAbsent if true, don't change existing value. 如果key已存在，则true表示不改变该key的值, false表示改变该key的值
     * @param evict if false, the table is in creation mode. false表示初始化, true表示添加元素
     * @return previous value, or null if none 旧节点值
     */
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; 
        Node<K,V> p; // 暂存正在处理的tab节点
        int n, i;
        
        // 如果table为空则初始化，table的初始化是通过resize来实现的
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        
        /*
         * 位桶为空.
         * 这里就是HASH算法了，用来定位桶位的方式，可以看到是采用容量-1与键hash值进行与运算
         * 当length总是2的n次方时，h & (length-1)运算等价于对length取模，也就是h%length，但是&比%具有更高的效率。
         * 并且(n - 1) & hash也能保证散列均匀，不会产生只有偶数位有值的现象
         */
        if ((p = tab[i = (n - 1) & hash]) == null)
        	// 当这里是空桶位时，就直接构造新的Node节点，将其放入桶位中
            // newNode()方法，就是对new Node(,,,)的包装
        	// 同时也可以看到Node中的hash值就是重新计算的hash(key)
            tab[i] = newNode(hash, key, value, null);
        // 位桶不为空
        else {
            Node<K,V> e;  // key对应的节点
            K k;
            
            // key相等，节点已存在，刚好为桶位的第一个节点
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            // 当该桶位是红黑树结构时，则应该按照红黑树方式插入
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            // 当该桶位为链表结构时，进行链表的插入操作，但是当链表长度大于TREEIFY_THRESHOLD - 1，就要将链表转换成红黑树
            else {
            	// 这里binCount记录链表的长度
                for (int binCount = 0; ; ++binCount) {
                	// 如果键值不存在链表中，则找到链表尾端，创建新节点添加到链尾
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        // 链表长度大于8转换为红黑树进行处理（当binCount=7时，表示链表已有8个节点，加上新节点共9个节点）
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    // key相等，节点已存在
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }

            /*
             * 在table中找到节点，替换节点值，替换规则：
             * 1. 允许替换已存在的节点值. onlyIfAbsent 这里其实只是为了给putIfAbsent方法提供支持，这也是jdk1.8新增的方法
             * 2. 或原来的节点值为null
             */
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value; // 更新值
                afterNodeAccess(e);
                return oldValue;
            }
        }
        
        // 如果节点key不存在
        
        ++modCount;
        if (++size > threshold) // 当达到容量上限时，进行扩容
            resize();
        afterNodeInsertion(evict);
        return null;
    }

    /**
     * 将表大小初始化或加倍。 
     * 如果为null，则按照字段阈值中保存的初始容量目标进行分配。 
     * 否则，因为我们使用二次幂扩展，来自每个bin的元素必须保持在相同的索引，或者在新表中以两个偏移的幂移动。
     * <p>
     * Initializes or doubles table size.  If null, allocates in
     * accord with initial capacity target held in field threshold.
     * Otherwise, because we are using power-of-two expansion, the
     * elements from each bin must either stay at same index, or move
     * with a power of two offset in the new table.
     *
     * @return the table
     */
    final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table; // 旧哈希数组
        int oldCap = (oldTab == null) ? 0 : oldTab.length; // 旧容量
        int oldThr = threshold; // 旧阈值
        
        int newCap, newThr = 0;
        
        // 初始化或扩容（容量和阈值）
        
        if (oldCap > 0) {
        	// 这时已经无法扩容了，返回旧哈希数组
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            // 采用二倍扩容（容量、阈值）
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold.
        }
        // 这里就是构造器只是给了容量时的情况，将阈值直接给成新容量
        else if (oldThr > 0) // initial capacity was placed in threshold
            newCap = oldThr;
        // 这个是采用HashMap()这个方式构造容器时，可以看到就只是采用默认值就行初始化
        else { // zero initial threshold signifies using defaults
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        
        // 这里统一处理上面未计算新阈值的情况
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
        
        threshold = newThr; // 新的阈值
        @SuppressWarnings({"rawtypes","unchecked"})
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap]; // table的初始化, 创建新的桶
        table = newTab;
        
        // 开始搬家了
        
        // 这里就是当原始table不为空时，要有一个搬家的过程，所以这里是最浪费效率的
        // table初始化，bucket copy到新bucket，分链表和红黑树
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) { // 挨个copy
                Node<K,V> e;
                // 一次处理一个位桶（一条链表）
                if ((e = oldTab[j]) != null) {
                	// 先释放，解脱它，而不是等着JVM自己收集，因为有可能导致根本没有被收集，因为原始引用还在
                    oldTab[j] = null; // help GC
                    // 如果该桶只有一个元素，重新计算桶位，则直接赋到新的桶里面
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e; // hash&(length-1)
                    // 当桶位为红黑树时
                    else if (e instanceof TreeNode)
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap); // 根据调整后的容量，rehash分割原桶节点到新的桶位 
                    // 链表（保存顺序）
                    else { // preserve order. 链表，preserve order保持顺序
                    	// 一个桶中有多个元素，遍历将它们移到新的bucket或原bucket
                        Node<K,V> loHead = null, loTail = null; // lo原bucket的链表指针
                        Node<K,V> hiHead = null, hiTail = null; // hi新bucket的链表指针
                        Node<K,V> next;
                        
                        // 这里又是一个理解不太清楚的地方了
                        // (e.hash & oldCap) == 0，应该是表示原hash值小于oldcap，则其桶位不变，链表还是在原位置
                        // 若>0,则表示原hash值大于该oldCap，则桶位变为j + oldCap
                        // 从结果来看等效于e.hash & (newCap - 1)，只是不知道为何这样计算
                        // 而且与jdk1.6比，这里链表并没有被倒置，而jdk1.6中，每次扩容链表都会被倒置
                        do {
                            next = e.next; // 暂存下一节点（当前桶节点所在链表的下一节点）
                            // 我们使用的是2次幂的扩展(指长度扩为原来2倍)，所以，元素的位置要么是在原位置，要么是在原位置再移动2次幂的位置。
                            // 只需要看看原来的hash值新增的那个bit是1还是0就好了，是0的话索引没变，是1的话索引变成"原索引+oldCap"
                            if ((e.hash & oldCap) == 0) { // 还放在原来的桶（注意，是oldCap而非oldCap-1，即判断hash值新增的那个bit是1还是0，是0的话索引没变）
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            // 放在新桶
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        
                        // 原索引放到bucket里
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        // 原索引+oldCap放到bucket里，新增的高1位bit值为1
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead; // j + oldCap等价于e.hash & (newCap - 1)
                        }
                    }
                }
            }
        }
        return newTab;
    }

    /**
     * 将链表转换为红黑树
     * <p>
     * Replaces all linked nodes in bin at index for given hash unless
     * table is too small, in which case resizes instead.
     */
    final void treeifyBin(Node<K,V>[] tab, int hash) {
        int n, index;
        Node<K,V> e;
        if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
            resize();
        else if ((e = tab[index = (n - 1) & hash]) != null) {
            TreeNode<K,V> hd = null, tl = null;
            // 将单向链表的节点类型从Node类型转换为TreeNode类型
            do {
            	// 根据链表节点重新构造树节点
                TreeNode<K,V> p = replacementTreeNode(e, null);
                if (tl == null)
                    hd = p;
                else {
                    p.prev = tl; // 支持前驱
                    tl.next = p;
                }
                tl = p;
            } while ((e = e.next) != null);
            
            // 链表转为二叉树
            if ((tab[index] = hd) != null) // hd可能为空吗？除非replacementTreeNode可能返回空？
                hd.treeify(tab);
        }
    }

    /**
     * Copies all of the mappings from the specified map to this map.
     * These mappings will replace any mappings that this map had for
     * any of the keys currently in the specified map.
     *
     * @param m mappings to be stored in this map
     * @throws NullPointerException if the specified map is null
     */
    public void putAll(Map<? extends K, ? extends V> m) {
        putMapEntries(m, true);
    }

    /**
     * 删除
     * <p>
     * Removes the mapping for the specified key from this map if present.
     *
     * @param  key key whose mapping is to be removed from the map
     * @return the previous value associated with <tt>key</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
     *         (A <tt>null</tt> return can also indicate that the map
     *         previously associated <tt>null</tt> with <tt>key</tt>.)
     */
    public V remove(Object key) {
        Node<K,V> e;
        return (e = removeNode(hash(key), key, null, false, true)) == null ?
            null : e.value;
    }

    /**
     * 删除节点
     * <p>
     * Implements Map.remove and related methods
     *
     * @param hash hash for key. 待删除键的哈希码
     * @param key the key. 待删除的键
     * @param value the value to match if matchValue, else ignored. 用于匹配的值，仅当matchValue为true时有效
     * @param matchValue if true only remove if value is equal. 是否需要匹配值, true表示仅当value相等时才删除，false则不需要匹配
     * @param movable if false do not move other nodes while removing. 是否移动节点
     * @return the node, or null if none. 返回被删除的节点
     */
    final Node<K,V> removeNode(int hash, Object key, Object value,
                               boolean matchValue, boolean movable) {
        Node<K,V>[] tab; 
        Node<K,V> p; 
        int n, index;
        
        // table不为空，hash对应的桶不为空，则查找删除节点
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (p = tab[index = (n - 1) & hash]) != null) {
            
        	Node<K,V> node = null, e; 
            K k; 
            V v;
            
            // 查找节点，方法与getNode相同
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                node = p;
            else if ((e = p.next) != null) {
            	// 红黑树查找
                if (p instanceof TreeNode)
                    node = ((TreeNode<K,V>)p).getTreeNode(hash, key);
                // 链表查找
                else {
                    do {
                        if (e.hash == hash &&
                            ((k = e.key) == key ||
                             (key != null && key.equals(k)))) {
                            node = e;
                            break;
                        }
                        p = e; // 用于暂存node的前驱节点
                    } while ((e = e.next) != null);
                }
            }
            
            // 如果找到key对应的节点，且不需要匹配值 || 值相等，则删除节点
            if (node != null && (!matchValue || (v = node.value) == value ||
                                 (value != null && value.equals(v)))) {
            	// 红黑树节点删除
                if (node instanceof TreeNode)
                    ((TreeNode<K,V>)node).removeTreeNode(this, tab, movable);
                // 表示移除的节点为链表头节点，直接将链头摘除
                else if (node == p)
                    tab[index] = node.next;
                // 非链头则直接摘除
                else
                    p.next = node.next;
                ++modCount;
                --size;
                afterNodeRemoval(node);
                return node;
            }
        }
        return null;
    }

    /**
     * 清空所有键值对
     * <p>
     * Removes all of the mappings from this map.
     * The map will be empty after this call returns.
     */
    public void clear() {
        Node<K,V>[] tab;
        modCount++;
        if ((tab = table) != null && size > 0) {
            size = 0; // 大小置0
            // 清空所有桶位
            for (int i = 0; i < tab.length; ++i)
                tab[i] = null;
        }
    }

    /**
     * 指定值是否存在
     * <p>
     * Returns <tt>true</tt> if this map maps one or more keys to the
     * specified value.
     *
     * @param value value whose presence in this map is to be tested
     * @return <tt>true</tt> if this map maps one or more keys to the
     *         specified value
     */
    public boolean containsValue(Object value) {
        Node<K,V>[] tab; 
        V v;
        if ((tab = table) != null && size > 0) {
            for (int i = 0; i < tab.length; ++i) {
                for (Node<K,V> e = tab[i]; e != null; e = e.next) {
                    if ((v = e.value) == value ||
                        (value != null && value.equals(v)))
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取键集合. HashSet的迭代封装就是使用它
     * <p>
     * Returns a {@link Set} view of the keys contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation), the results of
     * the iteration are undefined.  The set supports element removal,
     * which removes the corresponding mapping from the map, via the
     * <tt>Iterator.remove</tt>, <tt>Set.remove</tt>,
     * <tt>removeAll</tt>, <tt>retainAll</tt>, and <tt>clear</tt>
     * operations.  It does not support the <tt>add</tt> or <tt>addAll</tt>
     * operations.
     *
     * @return a set view of the keys contained in this map
     */
    public Set<K> keySet() {
        Set<K> ks;
        return (ks = keySet) == null ? (keySet = new KeySet()) : ks;
    }

    /**
     * 键的集合
     * 
     * @author qiuzj
     *
     */
    final class KeySet extends AbstractSet<K> {
        public final int size()                 { return size; }
        public final void clear()               { HashMap.this.clear(); }
        public final Iterator<K> iterator()     { return new KeyIterator(); }
        public final boolean contains(Object o) { return containsKey(o); }
        public final boolean remove(Object key) {
            return removeNode(hash(key), key, null, false, true) != null;
        }
        public final Spliterator<K> spliterator() {
            return new KeySpliterator<>(HashMap.this, 0, -1, 0, 0);
        }
        public final void forEach(Consumer<? super K> action) {
            Node<K,V>[] tab;
            if (action == null)
                throw new NullPointerException();
            if (size > 0 && (tab = table) != null) {
                int mc = modCount;
                for (int i = 0; i < tab.length; ++i) {
                    for (Node<K,V> e = tab[i]; e != null; e = e.next)
                        action.accept(e.key);
                }
                if (modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }
    }

    /**
     * Returns a {@link Collection} view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are
     * reflected in the collection, and vice-versa.  If the map is
     * modified while an iteration over the collection is in progress
     * (except through the iterator's own <tt>remove</tt> operation),
     * the results of the iteration are undefined.  The collection
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Collection.remove</tt>, <tt>removeAll</tt>,
     * <tt>retainAll</tt> and <tt>clear</tt> operations.  It does not
     * support the <tt>add</tt> or <tt>addAll</tt> operations.
     *
     * @return a view of the values contained in this map
     */
    public Collection<V> values() {
        Collection<V> vs;
        return (vs = values) == null ? (values = new Values()) : vs;
    }

    final class Values extends AbstractCollection<V> {
        public final int size()                 { return size; }
        public final void clear()               { HashMap.this.clear(); }
        public final Iterator<V> iterator()     { return new ValueIterator(); }
        public final boolean contains(Object o) { return containsValue(o); }
        public final Spliterator<V> spliterator() {
            return new ValueSpliterator<>(HashMap.this, 0, -1, 0, 0);
        }
        public final void forEach(Consumer<? super V> action) {
            Node<K,V>[] tab;
            if (action == null)
                throw new NullPointerException();
            if (size > 0 && (tab = table) != null) {
                int mc = modCount;
                for (int i = 0; i < tab.length; ++i) {
                    for (Node<K,V> e = tab[i]; e != null; e = e.next)
                        action.accept(e.value);
                }
                if (modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }
    }

    /**
     * 获取HashMap的Entry集合.
     * <pre>
     * 返回该映射中包含的映射集合视图。
     * 该集合由映射支持，因此对映射的改变会反映到集合中，反之亦然。
     * 如果在迭代集合的过程中修改了映射（除非通过迭代器自己的remove操作，或者通过迭代器返回的映射条目上的setValue操作），
     * 那么迭代的结果将是不确定的。 
     * 集合支持元素删除，它通过Iterator.remove，Set.remove，removeAll，retainAll和clear操作从映射中删除相应的映射。
     * 它不支持add或addAll操作。
     * </pre>
     * Returns a {@link Set} view of the mappings contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation, or through the
     * <tt>setValue</tt> operation on a map entry returned by the
     * iterator) the results of the iteration are undefined.  The set
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Set.remove</tt>, <tt>removeAll</tt>, <tt>retainAll</tt> and
     * <tt>clear</tt> operations.  It does not support the
     * <tt>add</tt> or <tt>addAll</tt> operations.
     *
     * @return a set view of the mappings contained in this map
     */
    public Set<Map.Entry<K,V>> entrySet() {
        Set<Map.Entry<K,V>> es;
        return (es = entrySet) == null ? (entrySet = new EntrySet()) : es; // 只初始化一次，entrySet能够感受到HashMap的改变
    }

    /**
     * Entry元素类型的Set集合类
     * 
     * @author qiuzj
     *
     */
    final class EntrySet extends AbstractSet<Map.Entry<K,V>> {
        public final int size()                 { return size; }
        public final void clear()               { HashMap.this.clear(); }
        
        public final Iterator<Map.Entry<K,V>> iterator() {
            return new EntryIterator();
        }
        
        /**
         * 对象是否存在
         */
        public final boolean contains(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry<?,?>) o;
            Object key = e.getKey();
            Node<K,V> candidate = getNode(hash(key), key); // 根据o.hash、o.key查找节点
            return candidate != null && candidate.equals(e); // 如果key和value都相等则返回true
        }
        
        /**
         * 删除元素（键和值都必须相等）
         */
        public final boolean remove(Object o) {
            if (o instanceof Map.Entry) {
                Map.Entry<?,?> e = (Map.Entry<?,?>) o;
                Object key = e.getKey();
                Object value = e.getValue();
                return removeNode(hash(key), key, value, true, true) != null;
            }
            return false;
        }
        
        public final Spliterator<Map.Entry<K,V>> spliterator() {
            return new EntrySpliterator<>(HashMap.this, 0, -1, 0, 0);
        }
        
        /**
         * 对所有元素执行action指定的操作 
         */
        public final void forEach(Consumer<? super Map.Entry<K,V>> action) {
            Node<K,V>[] tab;
            if (action == null)
                throw new NullPointerException();
            
            if (size > 0 && (tab = table) != null) {
                int mc = modCount;
                for (int i = 0; i < tab.length; ++i) {
                    for (Node<K,V> e = tab[i]; e != null; e = e.next)
                        action.accept(e);
                }
                if (modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }
    }

    // Overrides of JDK8 Map extension methods

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        Node<K,V> e;
        return (e = getNode(hash(key), key)) == null ? defaultValue : e.value;
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return putVal(hash(key), key, value, true, true);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return removeNode(hash(key), key, value, true, true) != null;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        Node<K,V> e; V v;
        if ((e = getNode(hash(key), key)) != null &&
            ((v = e.value) == oldValue || (v != null && v.equals(oldValue)))) {
            e.value = newValue;
            afterNodeAccess(e);
            return true;
        }
        return false;
    }

    @Override
    public V replace(K key, V value) {
        Node<K,V> e;
        if ((e = getNode(hash(key), key)) != null) {
            V oldValue = e.value;
            e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
        return null;
    }

    @Override
    public V computeIfAbsent(K key,
                             Function<? super K, ? extends V> mappingFunction) {
        if (mappingFunction == null)
            throw new NullPointerException();
        int hash = hash(key);
        Node<K,V>[] tab; Node<K,V> first; int n, i;
        int binCount = 0;
        TreeNode<K,V> t = null;
        Node<K,V> old = null;
        if (size > threshold || (tab = table) == null ||
            (n = tab.length) == 0)
            n = (tab = resize()).length;
        if ((first = tab[i = (n - 1) & hash]) != null) {
            if (first instanceof TreeNode)
                old = (t = (TreeNode<K,V>)first).getTreeNode(hash, key);
            else {
                Node<K,V> e = first; K k;
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k)))) {
                        old = e;
                        break;
                    }
                    ++binCount;
                } while ((e = e.next) != null);
            }
            V oldValue;
            if (old != null && (oldValue = old.value) != null) {
                afterNodeAccess(old);
                return oldValue;
            }
        }
        V v = mappingFunction.apply(key);
        if (v == null) {
            return null;
        } else if (old != null) {
            old.value = v;
            afterNodeAccess(old);
            return v;
        }
        else if (t != null)
            t.putTreeVal(this, tab, hash, key, v);
        else {
            tab[i] = newNode(hash, key, v, first);
            if (binCount >= TREEIFY_THRESHOLD - 1)
                treeifyBin(tab, hash);
        }
        ++modCount;
        ++size;
        afterNodeInsertion(true);
        return v;
    }

    public V computeIfPresent(K key,
                              BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        if (remappingFunction == null)
            throw new NullPointerException();
        Node<K,V> e; V oldValue;
        int hash = hash(key);
        if ((e = getNode(hash, key)) != null &&
            (oldValue = e.value) != null) {
            V v = remappingFunction.apply(key, oldValue);
            if (v != null) {
                e.value = v;
                afterNodeAccess(e);
                return v;
            }
            else
                removeNode(hash, key, null, false, true);
        }
        return null;
    }

    @Override
    public V compute(K key,
                     BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        if (remappingFunction == null)
            throw new NullPointerException();
        int hash = hash(key);
        Node<K,V>[] tab; Node<K,V> first; int n, i;
        int binCount = 0;
        TreeNode<K,V> t = null;
        Node<K,V> old = null;
        if (size > threshold || (tab = table) == null ||
            (n = tab.length) == 0)
            n = (tab = resize()).length;
        if ((first = tab[i = (n - 1) & hash]) != null) {
            if (first instanceof TreeNode)
                old = (t = (TreeNode<K,V>)first).getTreeNode(hash, key);
            else {
                Node<K,V> e = first; K k;
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k)))) {
                        old = e;
                        break;
                    }
                    ++binCount;
                } while ((e = e.next) != null);
            }
        }
        V oldValue = (old == null) ? null : old.value;
        V v = remappingFunction.apply(key, oldValue);
        if (old != null) {
            if (v != null) {
                old.value = v;
                afterNodeAccess(old);
            }
            else
                removeNode(hash, key, null, false, true);
        }
        else if (v != null) {
            if (t != null)
                t.putTreeVal(this, tab, hash, key, v);
            else {
                tab[i] = newNode(hash, key, v, first);
                if (binCount >= TREEIFY_THRESHOLD - 1)
                    treeifyBin(tab, hash);
            }
            ++modCount;
            ++size;
            afterNodeInsertion(true);
        }
        return v;
    }

    @Override
    public V merge(K key, V value,
                   BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        if (value == null)
            throw new NullPointerException();
        if (remappingFunction == null)
            throw new NullPointerException();
        int hash = hash(key);
        Node<K,V>[] tab; Node<K,V> first; int n, i;
        int binCount = 0;
        TreeNode<K,V> t = null;
        Node<K,V> old = null;
        if (size > threshold || (tab = table) == null ||
            (n = tab.length) == 0)
            n = (tab = resize()).length;
        if ((first = tab[i = (n - 1) & hash]) != null) {
            if (first instanceof TreeNode)
                old = (t = (TreeNode<K,V>)first).getTreeNode(hash, key);
            else {
                Node<K,V> e = first; K k;
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k)))) {
                        old = e;
                        break;
                    }
                    ++binCount;
                } while ((e = e.next) != null);
            }
        }
        if (old != null) {
            V v;
            if (old.value != null)
                v = remappingFunction.apply(old.value, value);
            else
                v = value;
            if (v != null) {
                old.value = v;
                afterNodeAccess(old);
            }
            else
                removeNode(hash, key, null, false, true);
            return v;
        }
        if (value != null) {
            if (t != null)
                t.putTreeVal(this, tab, hash, key, value);
            else {
                tab[i] = newNode(hash, key, value, first);
                if (binCount >= TREEIFY_THRESHOLD - 1)
                    treeifyBin(tab, hash);
            }
            ++modCount;
            ++size;
            afterNodeInsertion(true);
        }
        return value;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        Node<K,V>[] tab;
        if (action == null)
            throw new NullPointerException();
        if (size > 0 && (tab = table) != null) {
            int mc = modCount;
            for (int i = 0; i < tab.length; ++i) {
                for (Node<K,V> e = tab[i]; e != null; e = e.next)
                    action.accept(e.key, e.value);
            }
            if (modCount != mc)
                throw new ConcurrentModificationException();
        }
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        Node<K,V>[] tab;
        if (function == null)
            throw new NullPointerException();
        if (size > 0 && (tab = table) != null) {
            int mc = modCount;
            for (int i = 0; i < tab.length; ++i) {
                for (Node<K,V> e = tab[i]; e != null; e = e.next) {
                    e.value = function.apply(e.key, e.value);
                }
            }
            if (modCount != mc)
                throw new ConcurrentModificationException();
        }
    }

    /* ------------------------------------------------------------ */
    // Cloning and serialization

    /**
     * Returns a shallow copy of this <tt>HashMap</tt> instance: the keys and
     * values themselves are not cloned.
     *
     * @return a shallow copy of this map
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
        HashMap<K,V> result;
        try {
            result = (HashMap<K,V>)super.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
        result.reinitialize();
        result.putMapEntries(this, false); // 用当前映射对象初始化克隆出来对象
        return result;
    }

    // These methods are also used when serializing HashSets
    final float loadFactor() { return loadFactor; }
    final int capacity() {
        return (table != null) ? table.length :
            (threshold > 0) ? threshold :
            DEFAULT_INITIAL_CAPACITY;
    }

    /**
     * Save the state of the <tt>HashMap</tt> instance to a stream (i.e.,
     * serialize it).
     *
     * @serialData The <i>capacity</i> of the HashMap (the length of the
     *             bucket array) is emitted (int), followed by the
     *             <i>size</i> (an int, the number of key-value
     *             mappings), followed by the key (Object) and value (Object)
     *             for each key-value mapping.  The key-value mappings are
     *             emitted in no particular order.
     */
    private void writeObject(java.io.ObjectOutputStream s)
        throws IOException {
        int buckets = capacity();
        // Write out the threshold, loadfactor, and any hidden stuff
        s.defaultWriteObject();
        s.writeInt(buckets); // 数组容量
        s.writeInt(size); // 键值对数量
        internalWriteEntries(s); // 将所有key、value写入输出流
    }

    /**
     * Reconstitute the {@code HashMap} instance from a stream (i.e.,
     * deserialize it).
     */
    private void readObject(java.io.ObjectInputStream s)
        throws IOException, ClassNotFoundException {
        // Read in the threshold (ignored), loadfactor, and any hidden stuff
        s.defaultReadObject();
        reinitialize();
        
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new InvalidObjectException("Illegal load factor: " +
                                             loadFactor);
        s.readInt();                // Read and ignore number of buckets
        int mappings = s.readInt(); // Read number of mappings (size)
        if (mappings < 0)
            throw new InvalidObjectException("Illegal mappings count: " +
                                             mappings);
        else if (mappings > 0) { // (if zero, use defaults)
            // Size the table using given load factor only if within
            // range of 0.25...4.0
            float lf = Math.min(Math.max(0.25f, loadFactor), 4.0f); // 加载因子
            float fc = (float)mappings / lf + 1.0f;
            int cap = ((fc < DEFAULT_INITIAL_CAPACITY) ?
                       DEFAULT_INITIAL_CAPACITY :
                       (fc >= MAXIMUM_CAPACITY) ?
                       MAXIMUM_CAPACITY :
                       tableSizeFor((int)fc)); // 容量
            float ft = (float)cap * lf;
            threshold = ((cap < MAXIMUM_CAPACITY && ft < MAXIMUM_CAPACITY) ?
                         (int)ft : Integer.MAX_VALUE); // 阈值
            
            @SuppressWarnings({"rawtypes","unchecked"})
            Node<K,V>[] tab = (Node<K,V>[])new Node[cap];
            table = tab;

            // Read the keys and values, and put the mappings in the HashMap
            for (int i = 0; i < mappings; i++) {
                @SuppressWarnings("unchecked")
                    K key = (K) s.readObject();
                @SuppressWarnings("unchecked")
                    V value = (V) s.readObject();
                putVal(hash(key), key, value, false, false);
            }
        }
    }

    /* ------------------------------------------------------------ */
    // iterators

    /**
     * 哈希迭代器抽象类
     * 
     * @author qiuzj
     *
     */
    abstract class HashIterator {
        Node<K,V> next;        // next entry to return. 下一个节点
        Node<K,V> current;     // current entry. 当前节点
        int expectedModCount;  // for fast-fail. 期望的modCount，如果不一致则快速失败
        int index;             // current slot. 当前位桶索引号

        /**
         * 默认构造函数. 将next指向table中第一个不为null的元素
         */
        HashIterator() {
            expectedModCount = modCount; // 初始化修改次数
            Node<K,V>[] t = table; // 位桶数组
            current = next = null; // 当前及下一节点置null
            index = 0; // 当前位桶索引号置0
            
            if (t != null && size > 0) { // advance to first entry
            	// 将next指向table中第一个不为null的元素。
                // 这里利用了index的初始值为0，从0开始依次向后遍历，直到找到不为null的元素就退出循环。
                do {} while (index < t.length && (next = t[index++]) == null);
            }
        }

        /**
         * 是否存在下一个元素
         * 
         * @return
         */
        public final boolean hasNext() {
            return next != null;
        }

        /**
         * 获取下一个节点
         * 
         * @return
         */
        final Node<K,V> nextNode() {
            Node<K,V>[] t;
            Node<K,V> e = next; // 下一个节点，即当前要获取的节点
            
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            if (e == null)
                throw new NoSuchElementException();

            // 一个Entry就是一个单向链表
            // 若该Entry的下一个节点不为空，就将next指向下一个节点;
            // 否则，将next指向下一个链表(也是下一个Entry)的不为null的节点。
            if ((next = (current = e).next) == null && (t = table) != null) {
                do {} while (index < t.length && (next = t[index++]) == null);
            }
            return e; // 返回当前要获取的节点
        }

        /**
         * 删除当前元素
         */
        public final void remove() {
            Node<K,V> p = current;
            if (p == null)
                throw new IllegalStateException();
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            
            current = null; // help GC
            K key = p.key;
            removeNode(hash(key), key, null, false, false);
            expectedModCount = modCount; // 由于removeNode之后modCount更新了，所以此处需要同步更新
        }
    }

    /**
     * 键迭代器
     * 
     * @author qiuzj
     *
     */
    final class KeyIterator extends HashIterator
        implements Iterator<K> {
        public final K next() { return nextNode().key; }
    }

    /**
     * 值迭代器
     * 
     * @author qiuzj
     *
     */
    final class ValueIterator extends HashIterator
        implements Iterator<V> {
        public final V next() { return nextNode().value; }
    }

    /**
     * Entry节点迭代器
     * 
     * @author qiuzj
     *
     */
    final class EntryIterator extends HashIterator implements Iterator<Map.Entry<K,V>> {
        public final Map.Entry<K,V> next() { return nextNode(); }
    }

    /* ------------------------------------------------------------ */
    // spliterators

    /**
     * HashMap并行迭代器基础类
     * 
     * @author qiuzj
     *
     * @param <K>
     * @param <V>
     */
    static class HashMapSpliterator<K,V> {
        final HashMap<K,V> map;
        Node<K,V> current;          // current node. 默认及初始化时为null
        int index;                  // current index, modified on advance/split. 当前索引号（初始化时为开始索引号），调用advance方法时递增，调用split方法时变成折半后的中间索引号(index+fence)/2
        int fence;                  // one past last index. 结束索引号+1
        int est;                    // size estimate. 待处理的估算大小. 这个参数似乎用处不大
        int expectedModCount;       // for comodification checks. 期望的修改次数，用于并发修改时fail-fast

        /**
    	 * 构造函数
    	 * 
    	 * @param m HashMap对象
    	 * @param origin 开始索引号. 对应于内部变量index
    	 * @param fence 结束索引号+1
    	 * @param est 待处理大小
    	 * @param expectedModCount 期望的修改计数
    	 */
        HashMapSpliterator(HashMap<K,V> m, int origin,
                           int fence, int est,
                           int expectedModCount) {
            this.map = m;
            this.index = origin;
            this.fence = fence;
            this.est = est;
            this.expectedModCount = expectedModCount;
        }

        /**
         * 获取结束索引号fence.
         * 初始化fence（结束索引号）、est（大小）的值，fence要么为0，要么为table.length
         * 
         * @return
         */
        final int getFence() { // initialize fence and size on first use
            int hi;
            // fence小于0时初始化，例如KeySet.spliterator方法传-1
            if ((hi = fence) < 0) {
                HashMap<K,V> m = map;
                est = m.size; // 初始化估计大小为Map的大小
                expectedModCount = m.modCount;
                Node<K,V>[] tab = m.table;
                hi = fence = (tab == null) ? 0 : tab.length; // 结束索引号，要么为0，要么为table.length
            }
            return hi;
        }

        /**
         * 获取估计大小
         * 
         * @return
         */
        public final long estimateSize() {
            getFence(); // force init
            return (long) est;
        }
    }

    /**
     * 键并行迭代器. 可参考EntrySpliterator
     * 
     * @author qiuzj
     *
     * @param <K>
     * @param <V>
     */
    static final class KeySpliterator<K,V>
        extends HashMapSpliterator<K,V>
        implements Spliterator<K> {
    	
    	/**
    	 * 构造函数
    	 * 
    	 * @param m HashMap对象
    	 * @param origin 开始索引号. 对应于内部变量index
    	 * @param fence 结束索引号+1
    	 * @param est 待处理大小
    	 * @param expectedModCount 期望的修改计数
    	 */
        KeySpliterator(HashMap<K,V> m, int origin, int fence, int est,
                       int expectedModCount) {
            super(m, origin, fence, est, expectedModCount);
        }

        /**
         * 每次取低半部分集合
         * <p>
         * {@inheritDoc}}
         */
        public KeySpliterator<K,V> trySplit() {
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
            return (lo >= mid || current != null) ? null : // 目前只发现tryAdvance时会出现current != null，说明正在操作Map中的元素
                new KeySpliterator<>(map, lo, index = mid, est >>>= 1,
                                        expectedModCount);
        }

        public void forEachRemaining(Consumer<? super K> action) {
            int i, hi, mc;
            if (action == null)
                throw new NullPointerException();
            HashMap<K,V> m = map;
            Node<K,V>[] tab = m.table;
            if ((hi = fence) < 0) {
                mc = expectedModCount = m.modCount;
                hi = fence = (tab == null) ? 0 : tab.length;
            }
            else
                mc = expectedModCount;
            if (tab != null && tab.length >= hi &&
                (i = index) >= 0 && (i < (index = hi) || current != null)) {
                Node<K,V> p = current;
                current = null;
                do {
                    if (p == null)
                        p = tab[i++];
                    else {
                        action.accept(p.key);
                        p = p.next;
                    }
                } while (p != null || i < hi);
                if (m.modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }

        public boolean tryAdvance(Consumer<? super K> action) {
            int hi;
            if (action == null)
                throw new NullPointerException();
            Node<K,V>[] tab = map.table;
            if (tab != null && tab.length >= (hi = getFence()) && index >= 0) {
                while (current != null || index < hi) {
                    if (current == null)
                        current = tab[index++];
                    else {
                        K k = current.key;
                        current = current.next;
                        action.accept(k);
                        if (map.modCount != expectedModCount)
                            throw new ConcurrentModificationException();
                        return true;
                    }
                }
            }
            return false;
        }

        public int characteristics() {
            return (fence < 0 || est == map.size ? Spliterator.SIZED : 0) |
                Spliterator.DISTINCT;
        }
    }

    /**
     * 值并行迭代器
     * 
     * @author qiuzj
     *
     * @param <K>
     * @param <V>
     */
    static final class ValueSpliterator<K,V>
        extends HashMapSpliterator<K,V>
        implements Spliterator<V> {
        ValueSpliterator(HashMap<K,V> m, int origin, int fence, int est,
                         int expectedModCount) {
            super(m, origin, fence, est, expectedModCount);
        }

        public ValueSpliterator<K,V> trySplit() {
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
            return (lo >= mid || current != null) ? null :
                new ValueSpliterator<>(map, lo, index = mid, est >>>= 1,
                                          expectedModCount);
        }

        public void forEachRemaining(Consumer<? super V> action) {
            int i, hi, mc;
            if (action == null)
                throw new NullPointerException();
            HashMap<K,V> m = map;
            Node<K,V>[] tab = m.table;
            if ((hi = fence) < 0) {
                mc = expectedModCount = m.modCount;
                hi = fence = (tab == null) ? 0 : tab.length;
            }
            else
                mc = expectedModCount;
            if (tab != null && tab.length >= hi &&
                (i = index) >= 0 && (i < (index = hi) || current != null)) {
                Node<K,V> p = current;
                current = null;
                do {
                    if (p == null)
                        p = tab[i++];
                    else {
                        action.accept(p.value);
                        p = p.next;
                    }
                } while (p != null || i < hi);
                if (m.modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }

        public boolean tryAdvance(Consumer<? super V> action) {
            int hi;
            if (action == null)
                throw new NullPointerException();
            Node<K,V>[] tab = map.table;
            if (tab != null && tab.length >= (hi = getFence()) && index >= 0) {
                while (current != null || index < hi) {
                    if (current == null)
                        current = tab[index++];
                    else {
                        V v = current.value;
                        current = current.next;
                        action.accept(v);
                        if (map.modCount != expectedModCount)
                            throw new ConcurrentModificationException();
                        return true;
                    }
                }
            }
            return false;
        }

        public int characteristics() {
            return (fence < 0 || est == map.size ? Spliterator.SIZED : 0);
        }
    }

    /**
     * Entry并行迭代器
     * 
     * @author qiuzj
     *
     * @param <K>
     * @param <V>
     */
    static final class EntrySpliterator<K,V>
        extends HashMapSpliterator<K,V>
        implements Spliterator<Map.Entry<K,V>> {
    	
    	/**
    	 * 构造函数
    	 * 
    	 * @param m HashMap对象
    	 * @param origin 开始索引号. 对应于内部变量index
    	 * @param fence 结束索引号+1
    	 * @param est 待处理大小
    	 * @param expectedModCount 期望的修改计数
    	 */
        EntrySpliterator(HashMap<K,V> m, int origin, int fence, int est,
                         int expectedModCount) {
            super(m, origin, fence, est, expectedModCount);
        }

        /**
         * 将Map划分成两部分，用低半部分创建新的Spliterator后返回
         */
        public EntrySpliterator<K,V> trySplit() { // 分割情况参考：java.util.md/HashMap的trySplit方法.jpg
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1; // 分别对应高、低和中间位置索引号
            return (lo >= mid || current != null) ? null : // current != null应该是表示正在被处理（tryAdvance）
                new EntrySpliterator<>(map, lo, index = mid, est >>>= 1, // 更新高半区开始索引号，大小都减半
                                          expectedModCount); // 划分成两部分，用低半区创建新的Spliterator后返回
        }

        public void forEachRemaining(Consumer<? super Map.Entry<K,V>> action) {
            int i, hi, mc;
            if (action == null)
                throw new NullPointerException();
            
            HashMap<K,V> m = map;
            Node<K,V>[] tab = m.table;
            
            // fence小于0则初始化
            if ((hi = fence) < 0) {
                mc = expectedModCount = m.modCount;
                hi = fence = (tab == null) ? 0 : tab.length;
            }
            else
                mc = expectedModCount;
            
            /*
             * 确认在table有元素，开始与结束索引号未越界的情况下，遍历该范围的元素，执行相应操作
             */
            if (tab != null && tab.length >= hi &&
                (i = index) >= 0 && (i < (index = hi) || current != null)) {
                Node<K,V> p = current;
                current = null;
                do {
                	// p为null表示第一次遍历（current为null时），或者当前位桶已处理完毕，则获取下一个桶位的第一个元素
                    if (p == null)
                        p = tab[i++];
                    // 对当前桶位的相应范围内元素执行相应操作
                    else {
                        action.accept(p); // 对当前节点执行相应操作
                        p = p.next; // 移动到链表或树的下一节点
                    }
                } while (p != null || i < hi);
                if (m.modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }

        public boolean tryAdvance(Consumer<? super Map.Entry<K,V>> action) {
            int hi;
            if (action == null)
                throw new NullPointerException();
            
            Node<K,V>[] tab = map.table;
            // table不为空，且开始与结束索引没有越界，仍有元素需要处理
            if (tab != null && tab.length >= (hi = getFence()) && index >= 0) {
            	// 遍历范围：index<=索引号<=hi-1，共hi个元素
                while (current != null || index < hi) {
                	// 获取下一个位桶的第1个元素
                    if (current == null)
                        current = tab[index++];
                    // 对当前节点执行相应操作(action)，并将current指向下一个元素
                    else {
                        Node<K,V> e = current;
                        current = current.next; // 保存下一个节点
                        action.accept(e); // 对当前节点执行相应操作
                        if (map.modCount != expectedModCount)
                            throw new ConcurrentModificationException();
                        return true; // 处理过多一个节点则立即返回true
                    }
                }
            }
            return false; // 没有处理任何节点
        }

        public int characteristics() {
            return (fence < 0 || est == map.size ? Spliterator.SIZED : 0) |
                Spliterator.DISTINCT;
        }
    }

    /* ------------------------------------------------------------ */
    // LinkedHashMap support


    /*
     * The following package-protected methods are designed to be
     * overridden by LinkedHashMap, but not by any other subclass.
     * Nearly all other internal methods are also package-protected
     * but are declared final, so can be used by LinkedHashMap, view
     * classes, and HashSet.
     */

    // Create a regular (non-tree) node
    Node<K,V> newNode(int hash, K key, V value, Node<K,V> next) {
        return new Node<>(hash, key, value, next);
    }

    // For conversion from TreeNodes to plain nodes
    Node<K,V> replacementNode(Node<K,V> p, Node<K,V> next) {
        return new Node<>(p.hash, p.key, p.value, next);
    }

    // Create a tree bin node
    TreeNode<K,V> newTreeNode(int hash, K key, V value, Node<K,V> next) {
        return new TreeNode<>(hash, key, value, next);
    }

    // For treeifyBin
    TreeNode<K,V> replacementTreeNode(Node<K,V> p, Node<K,V> next) {
        return new TreeNode<>(p.hash, p.key, p.value, next);
    }

    /**
     * 重置变量为默认状态. clone 和 readObject调用
     * <p>
     * Reset to initial default state.  Called by clone and readObject.
     */
    void reinitialize() {
        table = null;
        entrySet = null;
        keySet = null;
        values = null;
        modCount = 0;
        threshold = 0;
        size = 0;
    }

    // Callbacks to allow LinkedHashMap post-actions
    void afterNodeAccess(Node<K,V> p) { }
    void afterNodeInsertion(boolean evict) { }
    void afterNodeRemoval(Node<K,V> p) { }

    /**
     * 将所有key、value写入输出流. 仅被writeObject调用
     * 
     * @param s
     * @throws IOException
     */
    // Called only from writeObject, to ensure compatible ordering.
    void internalWriteEntries(java.io.ObjectOutputStream s) throws IOException {
        Node<K,V>[] tab;
        if (size > 0 && (tab = table) != null) {
            for (int i = 0; i < tab.length; ++i) {
                for (Node<K,V> e = tab[i]; e != null; e = e.next) {
                    s.writeObject(e.key);
                    s.writeObject(e.value);
                }
            }
        }
    }

    /* ------------------------------------------------------------ */
    // Tree bins

    /**
     * 红黑树
     * <p>
     * Entry for Tree bins. Extends LinkedHashMap.Entry (which in turn
     * extends Node) so can be used as extension of either regular or
     * linked node.
     */
    static final class TreeNode<K,V> extends LinkedHashMap.Entry<K,V> {
    	/**
    	 * 父节点
    	 */
        TreeNode<K,V> parent;  // red-black tree links
        /**
         * 左孩子
         */
        TreeNode<K,V> left;
        /**
         * 右孩子
         */
        TreeNode<K,V> right;
        /**
         * 前驱
         */
        TreeNode<K,V> prev;    // needed to unlink next upon deletion
        /**
         * 节点颜色. true:红色，false:黑色
         */
        boolean red;
        
        TreeNode(int hash, K key, V val, Node<K,V> next) {
            super(hash, key, val, next);
        }

        /**
         * 获取树的根节点
         * <p>
         * Returns root of tree containing this node.
         */
        final TreeNode<K,V> root() {
            for (TreeNode<K,V> r = this, p;;) { // 从当前节点往父节点找
                if ((p = r.parent) == null) // 父节点为null的节点即为根节点
                    return r;
                r = p;
            }
        }

        /**
         * 确保root是桶中的第一个元素，将root移到桶中的第一个【平衡思想】
         * <p>
         * Ensures that the given root is the first node of its bin.
         */
        static <K,V> void moveRootToFront(Node<K,V>[] tab, TreeNode<K,V> root) {
            int n;
            if (root != null && tab != null && (n = tab.length) > 0) {
                int index = (n - 1) & root.hash;
                TreeNode<K,V> first = (TreeNode<K,V>) tab[index];
                
                // 如果root非桶的第一个元素，则更新为第一个元素
                if (root != first) {
                    Node<K,V> rn;
                    tab[index] = root; // 桶第一个元素存放root节点
                    
                    // 摘除根节点链表关系，将根节点的前驱与后继节点相连
                    TreeNode<K,V> rp = root.prev;
                    if ((rn = root.next) != null)
                        ((TreeNode<K,V>)rn).prev = rp;
                    if (rp != null)
                        rp.next = rn;
                    
                    // 根节点移至链头
                    if (first != null)
                        first.prev = root;
                    root.next = first;
                    root.prev = null;
                }
                assert checkInvariants(root);
            }
        }

        /**
         * 查找hash为h，key为k的节点
         * <p>
         * Finds the node starting at root p with the given hash and key.
         * The kc argument caches comparableClassFor(key) upon first use
         * comparing keys.
         */
        final TreeNode<K,V> find(int h, Object k, Class<?> kc) { // 详见get相关
            TreeNode<K,V> p = this; // 当前节点
            do {
                int ph, dir; 
                K pk;
                TreeNode<K,V> pl = p.left, pr = p.right, q;
                
                // 开始二叉树查找
                if ((ph = p.hash) > h) // 在左子树
                    p = pl;
                else if (ph < h) // 在右子树
                    p = pr;
                else if ((pk = p.key) == k || (k != null && k.equals(pk))) // hash、key都相等，找到了
                    return p;
                else if (pl == null) // 左子树找不到则继续从右子树进行查找
                    p = pr;
                else if (pr == null) // 右子树找不到则继续从左子树进行查找
                    p = pl;
                else if ((kc != null ||
                          (kc = comparableClassFor(k)) != null) &&
                         (dir = compareComparables(kc, k, pk)) != 0) // 如果k实现了Comparable<T>接口，则采用compareTo进行比较
                    p = (dir < 0) ? pl : pr;
                else if ((q = pr.find(h, k, kc)) != null) // 优先尝试从右子树查找
                    return q;
                else
                    p = pl; // 后继都找不到，那只能从从左子树查找了. 这里为什么就不用find?
            } while (p != null);
            return null;
        }

        /**
         * 获取树节点，通过根节点查找
         * <p>
         * Calls find for root node.
         */
        final TreeNode<K,V> getTreeNode(int h, Object k) {
            return ((parent != null) ? root() : this).find(h, k, null); // 从根节点开始查找
        }

        /**
         * 通过类名或hashcode来比较2个对象的大小
         * <p>
         * Tie-breaking utility for ordering insertions when equal
         * hashCodes and non-comparable. We don't require a total
         * order, just a consistent insertion rule to maintain
         * equivalence across rebalancings. Tie-breaking further than
         * necessary simplifies testing a bit.
         */
        static int tieBreakOrder(Object a, Object b) {
            int d;
            if (a == null || b == null ||
                (d = a.getClass().getName().
                 compareTo(b.getClass().getName())) == 0) // 比较类名
                d = (System.identityHashCode(a) <= System.identityHashCode(b) ?
                     -1 : 1);
            return d;
        }

        /**
         * 将链表转为红黑树
         * <p>
         * Forms tree of the nodes linked from this node.
         * @return root of tree
         */
        final void treeify(Node<K,V>[] tab) {
            TreeNode<K,V> root = null; // 根节点
            
            // 从当前节点，开始将单向链表转化为红黑树
            for (TreeNode<K,V> x = this, next; x != null; x = next) {
                next = (TreeNode<K,V>) x.next; // 下一节点
                x.left = x.right = null; // 左右子节点初始化为null
                
                // 初始化根节点
                if (root == null) {
                    x.parent = null;
                    x.red = false; // 黑色
                    root = x;
                }
                // 非根节点
                else {
                    K k = x.key;
                    int h = x.hash;
                    Class<?> kc = null;
                    
                    // 从根节点开始查找插入的位置，并添加节点
                    for (TreeNode<K,V> p = root;;) {
                        int dir, ph; // dir:插入方向，-1:左子树，1:右子树
                        K pk = p.key;
                        
                        /*
                         * key比较规则：
                         * 1.先比较hash，如果相等执行2
                         * 2.如果key实现了Comparable<C>接口，则使用compareTo方法进行比较
                         * 3.如果key未实现Comparable<C>接口，或compareTo结果相等，则a.getClass().getName().compareTo(b.getClass().getName())
                         * 4.如果name相等，则System.identityHashCode(k) <= System.identityHashCode(pk)
                         */
                        
                        // 插入到p的左子树
                        if ((ph = p.hash) > h)
                            dir = -1;
                        // 插入到p的右子树
                        else if (ph < h)
                            dir = 1;
                        // 如果hash相等
                        else if ((kc == null &&
                                  (kc = comparableClassFor(k)) == null) ||
                                 (dir = compareComparables(kc, k, pk)) == 0) // 如果k实现了Comparable<C>接口，则使用compareTo方法进行比较
                            dir = tieBreakOrder(k, pk); // System.identityHashCode(k) <= System.identityHashCode(pk) ? -1 : 1);

                        TreeNode<K,V> xp = p; // xp表示即将有可能成为x的父节点
                        // p赋值为下一节点，为null时表示找到插入的位置，否则继续下一轮查找
                        if ((p = (dir <= 0) ? p.left : p.right) == null) {
                            x.parent = xp;
                            if (dir <= 0)
                                xp.left = x;
                            else
                                xp.right = x;
                            // 着色、旋转，平衡树
                            root = balanceInsertion(root, x);
                            break;
                        }
                    }
                }
            }
            // 插入平衡之后，root节点可能改变了
            moveRootToFront(tab, root);
        }

        /**
         * 将二叉树转为链表
         * <p>
         * Returns a list of non-TreeNodes replacing those linked from
         * this node.
         */
        final Node<K,V> untreeify(HashMap<K,V> map) {
            Node<K,V> hd = null, tl = null;
            for (Node<K,V> q = this; q != null; q = q.next) {
                Node<K,V> p = map.replacementNode(q, null); // 树节点转换为普通链表节点
                if (tl == null)
                    hd = p;
                else
                    tl.next = p;
                tl = p;
            }
            return hd;
        }

        /**
         * 添加一个键值对
         * <p>
         * Tree version of putVal.
         */
        final TreeNode<K,V> putTreeVal(HashMap<K,V> map, Node<K,V>[] tab,
                                       int h, K k, V v) {
            Class<?> kc = null;
            boolean searched = false;
            
            // 找到根节点
            TreeNode<K,V> root = (parent != null) ? root() : this;
            
            for (TreeNode<K,V> p = root;;) {
                int dir, ph; 
                K pk;
                
                // 左子树
                if ((ph = p.hash) > h)
                    dir = -1;
                // 右子树
                else if (ph < h)
                    dir = 1;
                // 找到
                else if ((pk = p.key) == k || (k != null && k.equals(pk)))
                    return p;
                else if ((kc == null &&
                          (kc = comparableClassFor(k)) == null) ||
                         (dir = compareComparables(kc, k, pk)) == 0) { // 条件说明参考treeify方法
                    if (!searched) { // 为何要在这里find，如果找不到，那么会重复遍历很多节点？
                        TreeNode<K,V> q, ch;
                        searched = true;
                        if (((ch = p.left) != null &&
                             (q = ch.find(h, k, kc)) != null) ||
                            ((ch = p.right) != null &&
                             (q = ch.find(h, k, kc)) != null))
                            return q;
                    }
                    dir = tieBreakOrder(k, pk);
                }

                TreeNode<K,V> xp = p; // xp表示即将有可能成为k的父节点
                // p赋值为下一节点，为null时表示找到插入的位置，否则继续下一轮查找
                if ((p = (dir <= 0) ? p.left : p.right) == null) { // 将k插入到xp与xp.next之间
                    Node<K,V> xpn = xp.next;
                    TreeNode<K,V> x = map.newTreeNode(h, k, v, xpn); // 构造树节点, x.next=xp.next
                    if (dir <= 0)
                        xp.left = x;
                    else
                        xp.right = x;
                    xp.next = x; // 更新xp的后继
                    x.parent = x.prev = xp; // 更新x的父节点和前驱
                    if (xpn != null)
                        ((TreeNode<K,V>)xpn).prev = x; // 更新xp的前驱
                    
                    // 先平衡树，再确保root是桶中的第一个元素
                    moveRootToFront(tab, balanceInsertion(root, x));
                    return null;
                }
            }
        }

        /**
         * 删除当前树节点
         * <p>
         * Removes the given node, that must be present before this call.
         * This is messier than typical red-black deletion code because we
         * cannot swap the contents of an interior node with a leaf
         * successor that is pinned by "next" pointers that are accessible
         * independently during traversal. So instead we swap the tree
         * linkages. If the current tree appears to have too few nodes,
         * the bin is converted back to a plain bin. (The test triggers
         * somewhere between 2 and 6 nodes, depending on tree structure).
         */
        final void removeTreeNode(HashMap<K,V> map, Node<K,V>[] tab, boolean movable) {
            int n;
            if (tab == null || (n = tab.length) == 0)
                return;
            
            int index = (n - 1) & hash; // 待删除节点的位桶索引号
            TreeNode<K,V> first = (TreeNode<K,V>)tab[index], // 位桶第一个元素
            		root = first, // root指向第一个元素
            		rl;
            TreeNode<K,V> succ = (TreeNode<K,V>)next, // 后继
            		pred = prev; // 前驱
            
            // 如果前驱为null，说明删除的是根节点
            if (pred == null)
                tab[index] = first = succ;
            // 如果删除的是非根节点，则前驱与后继相连（摘除当前节点的前驱后继关系）
            else
                pred.next = succ;
            if (succ != null)
                succ.prev = pred;

            // 当前位桶已无节点
            if (first == null)
                return;
            
            // 此处应该是再一次确保root指向根节点（可能first不是根节点）
            if (root.parent != null)
                root = root.root();
            
            // 如果树高太小，即节点比较少，则将二叉树转为链表
            if (root == null || root.right == null || // 为何判断rl.left而不断送rr.left?
                (rl = root.left) == null || rl.left == null) {
                tab[index] = first.untreeify(map);  // too small
                return;
            }
            
            TreeNode<K,V> p = this, // 当前节点
            		pl = left, // 左孩子
            		pr = right, // 右孩子
            		replacement; // 用于暂存新的替代节点
            
            /*
             * 如果存在左孩子和右孩子.
             * 处理逻辑与算法导论第3版的伪代码不一样，对于存在左右孩子，节点的移动替换逻辑如下：
             * 当前节点：p，后继节点：s，后继节点右孩子：sr
             * 1.互换p节点与s节点的位置
             * 2.sr节点替换p的位置（事实上是sr节点替换s节点原来的位置）
             * 3.彻底断开p节点，释放p节点
             * 
             * 也就是说，将p不断往下移，最后摘除掉
             */
            if (pl != null && pr != null) {
                TreeNode<K,V> s = pr, // 用于暂存后继节点
                		sl;
                
                // 找到待删除节点的后继节点（比当前节点值大的最小值）
                while ((sl = s.left) != null) // find successor
                    s = sl;
                
                // swap colors. 后继节点s与待删除节点p互换颜色
                boolean c = s.red;
                s.red = p.red; 
                p.red = c;
                
                TreeNode<K,V> sr = s.right; // 后继节点s的右孩子
                TreeNode<K,V> pp = p.parent; // 待删除节点p的父节点
                
                // 如果后继s为直接右孩子pr，先更新p与s之间的父子关系（反转关系）
                if (s == pr) { // p was s's direct parent
                    p.parent = s;
                    s.right = p;
                }
                // 如果后继非直接右孩子
                else {
                    TreeNode<K,V> sp = s.parent; // 后继节点的父节点
                    // 建立p与后继的父节点sp之间的父子关系（与前几行的if处理逻辑类似）
                    if ((p.parent = sp) != null) {
                        if (s == sp.left)
                            sp.left = p;
                        // 这种情况可能出现？如果后继非直接右孩子，不是应该都是left节点了吗？
                        else
                            sp.right = p;
                    }
                    // s替换p（将p的右子树作为s的右子树）
                    // 建立后继s与p的右孩子pr之间的父子关系（与前几行的if处理逻辑类似）
                    if ((s.right = pr) != null)
                        pr.parent = s;
                }
                
                // 断开p的左子树
                p.left = null;
                
                // 将后继s原来的右子树作为p(已替代原来s的位置)的右子树
                // 建立p(已替代原来s的位置)与后继s原来的右孩子之间的父子关系
                if ((p.right = sr) != null)
                    sr.parent = p;
                
                // 将p的左子树作为s(已替代原来p的位置)的左子树
                // 建立后继s(已替代原来p的位置)与p原来的左孩子之间的父子关系
                if ((s.left = pl) != null)
                    pl.parent = s;
                
                // p原来的父节点作为s的父节点. 如果父节点为null，则设置s为根节点
                if ((s.parent = pp) == null)
                    root = s;
                // 如果p的父节点非null，则根据p原来的位置设置s的左右孩子关系
                else if (p == pp.left)
                    pp.left = s;
                else
                    pp.right = s;
                
                // 后继节点s原来的右孩子，作为后续处理的待替换节点
                if (sr != null)
                    replacement = sr;
                else
                    replacement = p;
            }
            // 如果仅有一个左孩子，则用左孩子替换
            else if (pl != null)
                replacement = pl;
            // 如果仅有一个右孩子，则用右孩子替换
            else if (pr != null)
                replacement = pr;
            // 如果没有孩子，则replacement指向自己，表示不需要替换
            else
                replacement = p;
            
            // replacement != p表示有需要替换的节点
            // 处理有替代节点的情况，即用节点replacement替换p节点
            if (replacement != p) {
                TreeNode<K,V> pp = replacement.parent = p.parent; // 替代节点的父节点指向待删除节点的父节点
                // 如果删除的节点为根节点，则replacement成为新的根节点
                if (pp == null)
                    root = replacement;
                // 替换原来的左或右孩子位置
                else if (p == pp.left)
                    pp.left = replacement;
                else
                    pp.right = replacement;
                // 彻底摘除节点关系，help GC
                p.left = p.right = p.parent = null;
            }

            /*
             * 若记录的颜色是红色，说明红黑性质未改变；如果是黑色，说明红黑性质一定被打破了，需要再平衡。
             * 概括为三种情况。
             * 1.情况说明：replacement是"红+黑"节点。
             *    处理方法：直接把replacement设为黑色，结束。此时红黑树性质全部恢复。
             * 2.情况说明：replacement是"黑+黑"节点，且是根。
             *    处理方法：什么都不做，结束。此时红黑树性质全部恢复。
             * 3.情况说明：replacement是"黑+黑"节点，且不是根。
             *    处理方法：这种情况又可以划分为4种子情况
             */
            TreeNode<K,V> r = p.red ? root : balanceDeletion(root, replacement); // 获取新的根节点

            // 如果没有需要替换的节点，直接摘除节点即可（p的父节点、左或右孩子设置为null）
            if (replacement == p) {  // detach
                TreeNode<K,V> pp = p.parent;
                p.parent = null;
                if (pp != null) {
                    if (p == pp.left)
                        pp.left = null;
                    else if (p == pp.right)
                        pp.right = null;
                }
            }
            if (movable)
                moveRootToFront(tab, r);
        }

        /**
         * 将结点太多的桶分割
         * <p>
         * Splits nodes in a tree bin into lower and upper tree bins,
         * or untreeifies if now too small. Called only from resize;
         * see above discussion about split bits and indices.
         *
         * @param map the map
         * @param tab the table for recording bin heads
         * @param index the index of the table being split
         * @param bit the bit of hash to split on
         */
        final void split(HashMap<K,V> map, Node<K,V>[] tab, int index, int bit) {
            TreeNode<K,V> b = this;
            // Relink into lo and hi lists, preserving order
            TreeNode<K,V> loHead = null, loTail = null;
            TreeNode<K,V> hiHead = null, hiTail = null;
            int lc = 0, hc = 0;
            
            for (TreeNode<K,V> e = b, next; e != null; e = next) {
                next = (TreeNode<K,V>)e.next;
                e.next = null;
                // 原桶位节点
                if ((e.hash & bit) == 0) {
                    if ((e.prev = loTail) == null)
                        loHead = e;
                    else
                        loTail.next = e;
                    loTail = e;
                    ++lc;
                }
                // 新桶位节点
                else {
                    if ((e.prev = hiTail) == null)
                        hiHead = e;
                    else
                        hiTail.next = e;
                    hiTail = e;
                    ++hc;
                }
            }

            // 位桶节点数<=6时转换为链表
            // 位桶节点数>6时转换为红黑树
            
            // 更新旧桶位节点信息
            if (loHead != null) {
                if (lc <= UNTREEIFY_THRESHOLD)
                    tab[index] = loHead.untreeify(map);
                else {
                    tab[index] = loHead;
                    if (hiHead != null) // (else is already treeified)
                        loHead.treeify(tab);
                }
            }
            // 设置新桶位节点信息
            if (hiHead != null) {
                if (hc <= UNTREEIFY_THRESHOLD)
                    tab[index + bit] = hiHead.untreeify(map);
                else {
                    tab[index + bit] = hiHead;
                    if (loHead != null)
                        hiHead.treeify(tab);
                }
            }
        }

        /* ------------------------------------------------------------ */
        // Red-black tree methods, all adapted from CLR

        /**
         * 左旋转
         * 
         * @param root
         * @param p
         * @return
         */
        static <K,V> TreeNode<K,V> rotateLeft(TreeNode<K,V> root,
                                              TreeNode<K,V> p) {
            TreeNode<K,V> r, pp, rl;
            // p及p的右孩子存在
            if (p != null && (r = p.right) != null) {
            	// r的左孩子作为p的右孩子
                if ((rl = p.right = r.left) != null)
                    rl.parent = p;
                // p的父节点作为r的父节点
                if ((pp = r.parent = p.parent) == null)
                    (root = r).red = false; // r成为新的根节点
                // 如果原来p是左孩子，则r设置为左孩子
                else if (pp.left == p)
                    pp.left = r;
                // 如果原来p是右孩子，则r设置为右孩子
                else
                    pp.right = r;
                r.left = p; // p作为r的左孩子
                p.parent = r; // r设置为p的父节点
            }
            return root;
        }

        /**
         * 右旋转
         * 
         * @param root
         * @param p
         * @return
         */
        static <K,V> TreeNode<K,V> rotateRight(TreeNode<K,V> root,
                                               TreeNode<K,V> p) {
            TreeNode<K,V> l, pp, lr;
            if (p != null && (l = p.left) != null) {
                if ((lr = p.left = l.right) != null)
                    lr.parent = p;
                if ((pp = l.parent = p.parent) == null)
                    (root = l).red = false;
                else if (pp.right == p)
                    pp.right = l;
                else
                    pp.left = l;
                l.right = p;
                p.parent = l;
            }
            return root;
        }

        /**
         * 保证插入后平衡，共5种插入情况
         * 
         * @param root 根节点
         * @param x 插入的节点
         * @return 根节点
         */
        static <K,V> TreeNode<K,V> balanceInsertion(TreeNode<K,V> root,
                                                    TreeNode<K,V> x) {
            // 新节点初始化为红色
        	x.red = true;
        	
        	// 从当前节点开始， 往根节点方向，通过着色、旋转让树达到平衡
            for (TreeNode<K,V> xp, xpp, xppl, xppr;;) {
            	// 遇到根节点，直接设置为黑色后返回
                if ((xp = x.parent) == null) {
                    x.red = false;
                    return x;
                }
                // !xp.red:父节点(xp)为黑色时不需要再平衡
                // xp.parent == null是什么情况？此时xp是红色，且是根节点？
                else if (!xp.red || (xpp = xp.parent) == null)
                    return root;
                
                // 以下处理父节点(xp)为红色的情况
                
                // 如果父节点(xp)为祖父节点(xpp)的左孩子
                if (xp == (xppl = xpp.left)) { // xppl:祖父节点左孩子，xppr:祖父节点右孩子
                	// 祖父节点右孩子（叔叔节点）为红色
                    if ((xppr = xpp.right) != null && xppr.red) {
                        xppr.red = false; // 叔叔节点设置为黑色
                        xp.red = false; // 父节点设置为黑色
                        xpp.red = true; // 祖父节点设置为红色
                        x = xpp; // 由于祖父节点由黑色调整为红色，故需要从祖父节点开始往根部处理平衡问题
                    }
                    // 祖父节点右孩子为黑色
                    else {
                    	// 如果插入节点是右孩子
                        if (x == xp.right) {
                            root = rotateLeft(root, x = xp); // 父节点左旋，此时xp成为新的子节点
                            xpp = (xp = x.parent) == null ? null : xp.parent; // 什么情况下为null?
                        }
                        // 插入节点为左孩子或右孩子都会进入这段处理逻辑（右孩子通过左旋之后，转化为左孩子的场景）
                        if (xp != null) {
                            xp.red = false; // 父节点设置为黑色
                            if (xpp != null) {
                                xpp.red = true; // 祖父节点设置为红色
                                root = rotateRight(root, xpp); // 祖父节点右旋
                            }
                        }
                    }
                }
                // 如果父节点为祖父节点的右孩子
                else {
                    if (xppl != null && xppl.red) {
                        xppl.red = false;
                        xp.red = false;
                        xpp.red = true;
                        x = xpp;
                    }
                    else {
                        if (x == xp.left) {
                            root = rotateRight(root, x = xp);
                            xpp = (xp = x.parent) == null ? null : xp.parent;
                        }
                        if (xp != null) {
                            xp.red = false;
                            if (xpp != null) {
                                xpp.red = true;
                                root = rotateLeft(root, xpp);
                            }
                        }
                    }
                }
            }
        }

        /**
         * 删除后调整平衡 ，共6种删除情况
         * 
         * @param root
         * @param x
         * @return
         */
        static <K,V> TreeNode<K,V> balanceDeletion(TreeNode<K,V> root,
                                                   TreeNode<K,V> x) {
        	
        	/*
        	 * 红黑树的几个特性：
        	 * (1) 每个节点或者是黑色，或者是红色。
        	 * (2) 根节点是黑色。
        	 * (3) 每个叶子节点是黑色。 [注意：这里叶子节点，是指为空的叶子节点！]
        	 * (4) 如果一个节点是红色的，则它的子节点必须是黑色的。
        	 * (5) 从一个节点到该节点的子孙节点的所有路径上包含相同数目的黑节点。
        	 */
        	
        	/*
        	 * 若记录的颜色是红色，说明红黑性质未改变；如果是黑色，说明红黑性质一定被打破了。
        	 * 1）如果y原来是根结点，而y的一个红孩子成为了新的根结点，将会违背性质(1)。
        	 * 2）如果x和x的父结点都为红色，将违背性质(2)。
        	 * 3）所有的情况（除了y原来是根结点外）都将导致之前包含y结点的简单路径上的黑结点数少1，将违背性质(3)。
        	 *    修正这一问题的方式是我们将现在占据y结点位置的x结点"再涂上一层黑色"，当然，"涂色"操作并不反映在代码上，
        	 *    即我们不会修改x的color属性，我们只是"在心中记住"，适当的时候会把x的这层黑色涂到某个红色结点上以达到目的。
        	 *    "涂了两层色"的x结点可能是双层黑色或红黑色，它们分别会"贡献"2或1个黑色结点数。
        	 * 
             * 假设"x包含一个额外的黑色"(x原本的颜色还存在)，这样就不会违反"特性(5)"。
             * 现在，x不仅包含它原本的颜色属性，x还包含一个额外的黑色。即x的颜色属性是"红+黑"或"黑+黑"
             *
             * 概括为三种情况。
             * 1.情况说明：x是"红+黑"节点。
             *    处理方法：直接把x设为黑色，结束。此时红黑树性质全部恢复。
             * 2.情况说明：x是"黑+黑"节点，且是根。
             *    处理方法：什么都不做，结束。此时红黑树性质全部恢复。
             * 3.情况说明：x是"黑+黑"节点，且不是根。
             *    处理方法：这种情况又可以划分为4种子情况。（x为待处理的双色节点，w为兄弟节点，balance的思想是将x所包含的额外的黑色不断沿树上移(向根方向移动)）
             *    Case 1	x的右兄弟w是红色，说明x的父结点一定是黑色。
             *    	所作的操作是：交换w和其父结点的颜色，即把w换为黑色，其父结点换位红色；然后对父结点左旋，w重新指向x的右兄弟（该结点原本是w的左孩子，所以一定为黑色）。这是Case 1过度到Case 2。
             *    Case 2	w的孩子都为黑色（w也是黑色）。
             *    	所作的操作是：将w换为红色，x指向其父结点。
             *    Case 3	w的左孩子是红色，右孩子是黑色（w也是黑色）。
             *    	所作的操作是：交换w和其左孩子的颜色，即把w换位红色，其左孩子换为黑色；然后对w右旋，w重新指向x的右兄弟。
             *    Case 4	w的右孩子是黑色（w是黑色）。
             *      w与x的父结点交换颜色；并把w的右孩子设为黑色，对x的父结点左旋，x直接指向根结点，循环结束。
             */
        	
            for (TreeNode<K,V> xp, xpl, xpr;;) {
            	// 等于root说明已经处理到根节点，并且根节点没有变化，直接返回，调整结束
                if (x == null || x == root)
                    return root;
                // 如果已经处理到根节点，根节点设置为黑色后返回，调整结束
                else if ((xp = x.parent) == null) {
                    x.red = false;
                    return x;
                }
                // 如果x节点为红色，则着色为黑色，调整结束
                // 如果是Case 1转换为Case 2，将会进入这段逻辑（此时x为红黑节点，即原来为红色，那么直接设置黑色即可完成平衡）
                else if (x.red) {
                    x.red = false;
                    return root;
                }
                // 以下两个else处理情况3：x是"黑+黑"节点，且不是根
                // 如果x是左孩子
                else if ((xpl = xp.left) == x) {
                	// Case 1: 如果x的右兄弟节点xpr为红色（父、左右孩子均为黑色），交换xpr与xp的颜色. 目的是将Case 1转换为Case 2、3或4
                    if ((xpr = xp.right) != null && xpr.red) {
                        xpr.red = false; // 兄弟节点着色为黑色
                        xp.red = true; // 父节点着色为红色
                        root = rotateLeft(root, xp); // 对父节点左旋
                        xpr = (xp = x.parent) == null ? null : xp.right; // xpr指向新的兄弟节点. 经过左旋后，xp.right指向原xpr的左孩子（一定为黑色）
                    }
                    // 如果兄弟节点为null，则黑色上移到父节点，继续下一轮平衡调整
                    if (xpr == null)
                        x = xp;
                    // Case 2、3或4: 如果x的右兄弟节点xpr为黑色
                    else {
                        TreeNode<K,V> sl = xpr.left, sr = xpr.right;
                        
                        // Case 2：如果右兄弟节点xpr的孩子都为黑色（或为null）
                        if ((sr == null || !sr.red) &&
                            (sl == null || !sl.red)) {
                            xpr.red = true; // 右兄弟节点着色为红色，xpr子树黑色减少1，将x的黑色上移到父节点xp(只是"在心中记住"就行了)，实现平衡
                            x = xp; // 黑色上移到父节点，继续下一轮平衡调整（如果为红黑，则在下一次for开始就完成平衡了；如果为黑黑，则需要继续调整）
                        }
                        // 如果xpr的左孩子或右孩子为红色
                        else {
                        	// Case 3: 如果右兄弟节点xpr的左孩子是红色，右孩子是黑色（或为null）。可转换为Case 4
                            if (sr == null || !sr.red) {
                                if (sl != null)
                                    sl.red = false; // xpr的左孩子由红变黑
                                xpr.red = true; // xpr节点由黑变红 
                                root = rotateRight(root, xpr); // 对xpr右旋
                                xpr = (xp = x.parent) == null ?
                                    null : xp.right; // xpr指向新的兄弟节点（指向原来的sl）
                            }
                            
                            // Case 4: 如果右兄弟节点xpr的右孩子是红色，xpr与x的父结点交换颜色；并把xpr的右孩子设为黑色，对x的父结点左旋，x直接指向根结点，调整结束
                            if (xpr != null) {
                                xpr.red = (xp == null) ? false : xp.red; // xpr更新为父节点的颜色
                                if ((sr = xpr.right) != null)
                                    sr.red = false; // xpr右孩子更新为黑色
                            }
                            if (xp != null) {
                                xp.red = false; // 父节点更新为黑色
                                root = rotateLeft(root, xp); // 对父节点左旋
                            }
                            x = root;
                        }
                    }
                }
                // 如果x是右孩子，与左孩子对称的处理方式
                else { // symmetric
                    if (xpl != null && xpl.red) {
                        xpl.red = false;
                        xp.red = true;
                        root = rotateRight(root, xp);
                        xpl = (xp = x.parent) == null ? null : xp.left;
                    }
                    if (xpl == null)
                        x = xp;
                    else {
                        TreeNode<K,V> sl = xpl.left, sr = xpl.right;
                        if ((sl == null || !sl.red) &&
                            (sr == null || !sr.red)) {
                            xpl.red = true;
                            x = xp;
                        }
                        else {
                            if (sl == null || !sl.red) {
                                if (sr != null)
                                    sr.red = false;
                                xpl.red = true;
                                root = rotateLeft(root, xpl);
                                xpl = (xp = x.parent) == null ?
                                    null : xp.left;
                            }
                            if (xpl != null) {
                                xpl.red = (xp == null) ? false : xp.red;
                                if ((sl = xpl.left) != null)
                                    sl.red = false;
                            }
                            if (xp != null) {
                                xp.red = false;
                                root = rotateRight(root, xp);
                            }
                            x = root;
                        }
                    }
                }
            }
        }

        /**
         * 检测是否符合红黑树
         * <p>
         * Recursive invariant check
         */
        static <K,V> boolean checkInvariants(TreeNode<K,V> t) {
            TreeNode<K,V> 
            		tp = t.parent, // 父节点
            		tl = t.left, // 左孩子节点
            		tr = t.right, // 右孩子节点
            		tb = t.prev, // 前驱节点
            		tn = (TreeNode<K,V>)t.next; // 后继节点
            if (tb != null && tb.next != t) // 前驱后继
                return false;
            if (tn != null && tn.prev != t) // 后继前驱
                return false;
            if (tp != null && t != tp.left && t != tp.right) // 父节点、左右孩子
                return false;
            if (tl != null && (tl.parent != t || tl.hash > t.hash)) // 左孩子、父节点及hash
                return false;
            if (tr != null && (tr.parent != t || tr.hash < t.hash)) // 右孩子、父节点及hash
                return false;
            if (t.red && tl != null && tl.red && tr != null && tr.red) // 节点、左孩子、右孩子全为红色（左和右之间不是应该用或吗？）
                return false;
            if (tl != null && !checkInvariants(tl)) // 递归检查左孩子
                return false;
            if (tr != null && !checkInvariants(tr)) // 递归检查右孩子
                return false;
            return true;
        }
    }

}
