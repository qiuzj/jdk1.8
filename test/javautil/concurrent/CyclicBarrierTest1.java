package javautil.concurrent;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;

/**
 * 所有运动员相互等待彼此准备就绪，然后开始比赛。无裁判，运动员同时起跑。
 * 
 * @author 二进制之路
 *
 */
public class CyclicBarrierTest1 {

    private static int SIZE = 5;
    private static CyclicBarrier cb;
    
    public static void main(String[] args) {

        cb = new CyclicBarrier(SIZE);

        // 新建5个任务
        for(int i=0; i<SIZE; i++)
            new InnerThread().start();
    }

    static class InnerThread extends Thread{
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " wait for CyclicBarrier.");

                // 将cb的参与者数量加1
                cb.await();

                // cb的参与者数量等于5时，才继续往后执行
                System.out.println(Thread.currentThread().getName() + " continued.");
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}