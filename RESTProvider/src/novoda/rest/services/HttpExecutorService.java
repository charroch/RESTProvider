package novoda.rest.services;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class HttpExecutorService extends Service {

	private static final int CORE_POOL_SIZE = 5;
	private static final int MAXIMUM_POOL_SIZE = 128;
	private static final int KEEP_ALIVE = 10;

	private static final ThreadFactory sThreadFactory = new ThreadFactory() {
		private final AtomicInteger mCount = new AtomicInteger(1);

		public Thread newThread(Runnable r) {
			return new Thread(r, "HttpExecutorService #"
					+ mCount.getAndIncrement());
		}
	};

	private static final BlockingQueue<Runnable> sWorkQueue = new LinkedBlockingQueue<Runnable>(
			10);

	private static final ThreadPoolExecutor sExecutor = new ThreadPoolExecutor(
			CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
			sWorkQueue, sThreadFactory);

	private static final int MESSAGE_RECEIVED_REQUEST = 0x1;
	private static final int MESSAGE_TIMEOUT_AFTER_FIRST_CALL = 0x2;

	// 5 Minutes
	public static final long SERVICE_LIFESPAN = 1000 * 60 * 5;

	private class LifecycleHandler extends Handler {

		private long lastCall = 0L;

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_RECEIVED_REQUEST:
				lastCall = System.currentTimeMillis();
				msg.what = MESSAGE_TIMEOUT_AFTER_FIRST_CALL;
				sendMessageDelayed(msg, SERVICE_LIFESPAN);
				break;
			case MESSAGE_TIMEOUT_AFTER_FIRST_CALL:
				// If we have not received another call in the last 5 minutes
				if (System.currentTimeMillis() - lastCall > SERVICE_LIFESPAN) {
					stopSelf(msg.arg1);
				}
				break;
			}
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
