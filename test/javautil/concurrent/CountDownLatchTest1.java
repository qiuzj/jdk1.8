package javautil.concurrent;
import java.util.concurrent.CountDownLatch;

/**
 * 裁判等待所有运动员到达终点，然后吹口哨结束比赛。不关注运动员的起跑时间，只关注是否都已到达终点。只有裁判在等待，可以有1或N名裁判。
 * 
 * @author 二进制之路
 *
 */
public class CountDownLatchTest1 {

    private static int LATCH_SIZE = 5;
    private static CountDownLatch doneSignal;
    
    public static void main(String[] args) {

        try {
            doneSignal = new CountDownLatch(LATCH_SIZE);

            // 新建5个任务
            for(int i=0; i<LATCH_SIZE; i++)
                new InnerThread().start();

            new Thread(new Runnable() {
				public void run() {
					System.out.println("submain await begin.");
					try {
						doneSignal.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("submain await finished.");
				}
			}).start();
            
            System.out.println("main await begin.");
            // "主线程"等待线程池中5个任务的完成
            doneSignal.await();

            System.out.println("main await finished.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class InnerThread extends Thread{
        public void run() {
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + " sleep 1000ms.");
                // 将CountDownLatch的数值减1
                doneSignal.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}