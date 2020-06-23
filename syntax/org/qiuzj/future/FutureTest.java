package org.qiuzj.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * JDK 1.8之前，使用Future来异步执行任务
 * 
 * @author Binary life
 *
 */
public class FutureTest {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		// Future原理：里面维护了一个状态字段state，每到达一个阶段，会更新该字段的状态
		// get的时候LockSupport.parkNanos等待超时
		// Future执行完成后，会唤醒等待的线程，或者get超时结束
		Future<String> future = executor.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(3000);
//				int a = 0, b = 1/a; // java.util.concurrent.ExecutionException
				return "OK";
			}
		});
		
		// 并发干点其他事儿
		System.out.println(1);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(2);
		
		// 计算结果
		try {
			String result = future.get(1000, TimeUnit.MILLISECONDS);
			System.out.println("result: " + result);
		} catch (InterruptedException e) { // 线程被中断
			e.printStackTrace();
		} catch (ExecutionException e) { // 任务执行异常
			e.printStackTrace();
		} catch (TimeoutException e) { // 超时异常
			e.printStackTrace();
		}
		
		executor.shutdown();
	}
	
}
