package fengyu.cn.library.net;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * �̳߳� ���������
 */
public class DefaultThreadPool {
    // �������������������
    static final int BLOCKING_QUEUE_SIZE = 20;
    static final int THREAD_POOL_MAX_SIZE = 10;

    static final int THREAD_POOL_SIZE = 6;
    /**
     * ����BaseRequest�������
     */
    static ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(
            DefaultThreadPool.BLOCKING_QUEUE_SIZE);

    private static DefaultThreadPool instance = null;
    /**
     * �̳߳أ�Ŀǰ��ʮ���̣߳�
     */
    static AbstractExecutorService pool = new ThreadPoolExecutor(
            DefaultThreadPool.THREAD_POOL_SIZE,
            DefaultThreadPool.THREAD_POOL_MAX_SIZE, 15L, TimeUnit.SECONDS,
            DefaultThreadPool.blockingQueue,
            new ThreadPoolExecutor.DiscardOldestPolicy());

    public static DefaultThreadPool getInstance() {

        if (DefaultThreadPool.instance == null) {
            synchronized (DefaultThreadPool.class) {

                if (DefaultThreadPool.instance == null) {
                    DefaultThreadPool.instance = new DefaultThreadPool();
                }
            }
        }
        return DefaultThreadPool.instance;
    }

    public static void removeAllTask() {
        DefaultThreadPool.blockingQueue.clear();
    }

    public static void removeTaskFromQueue(final Object obj) {
        DefaultThreadPool.blockingQueue.remove(obj);
    }

    /**
     * �رգ����ȴ�����ִ����ɣ�������������
     */
    public static void shutdown() {
        if (DefaultThreadPool.pool != null) {
            DefaultThreadPool.pool.shutdown();
        }
    }

    /**
     * �رգ������رգ���������������ִ�е��̣߳�������������
     */
    public static void shutdownRightnow() {
        if (DefaultThreadPool.pool != null) {
            DefaultThreadPool.pool.shutdownNow();
            try {
                // ���ó�ʱ���̣�ǿ�ƹر���������
                DefaultThreadPool.pool.awaitTermination(1,
                        TimeUnit.MICROSECONDS);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ִ������
     *
     * @param r
     */
    public void execute(final Runnable r) {
        if (r != null) {
            try {
                DefaultThreadPool.pool.execute(r);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }
}
