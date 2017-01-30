package pl.kordiaczynski.testTask.AdditionalTools;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * Created by kordiaczynski on 27.11.2016.
 */

public class BackgroundTask {

    private Handler backgroundHandler;
    private Thread backgroundThread;
    private static Object object = new Object();

    private BackgroundTask() {
        backgroundThread = new Thread() {
            @Override
            public void run() {
                super.run();

                synchronized (object) {
                    Looper.prepare();
                    backgroundHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);

                            if (msg.arg1 < 0) {
                                Looper.myLooper().quit();
                            }
                            Log.d("Handler", "Handle");
                        }
                    };
                    object.notify();
                }
                Looper.loop();
            }
        };
        backgroundThread.start();
    }


    public Handler getBackgroundHandler() {
        return backgroundHandler;
    }


    private static BackgroundTask backgroundTask;

    public static BackgroundTask getInstance() {

        if (null == backgroundTask) {

            synchronized (object) {
                    backgroundTask = new BackgroundTask();
                try {
                    object.wait();
                } catch (InterruptedException exception) {

                }
            }
        }

        return backgroundTask;
    }
}
