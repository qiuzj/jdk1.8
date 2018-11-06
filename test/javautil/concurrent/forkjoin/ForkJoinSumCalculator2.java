package javautil.concurrent.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinSumCalculator2 extends RecursiveTask<Long> {

    public static final long THRESHOLD = 10_000;

    private final long[] numbers;
    private final int start;
    private final int end;

    public ForkJoinSumCalculator2(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinSumCalculator2(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            return computeSequentially();
        }
        
        ForkJoinSumCalculator2 leftTask = new ForkJoinSumCalculator2(numbers, start, start + length/2);
        ForkJoinSumCalculator2 rightTask = new ForkJoinSumCalculator2(numbers, start + length/2, end);
        invokeAll(leftTask, rightTask);
        Long leftResult = leftTask.join();
        Long rightResult = rightTask.join();
        
        return leftResult + rightResult;
    }

    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static void main(String[] args) {
    	int n = 100;
    	long[] numbers = LongStream.rangeClosed(1, n).toArray();
    	ForkJoinTask<Long> task = new ForkJoinSumCalculator2(numbers);
    	long sum = new ForkJoinPool(20).invoke(task);
    	System.out.println(sum);
	}
}