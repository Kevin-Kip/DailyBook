package com.truekenyan.dailybook.utilities;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by password
 * on 6/28/18.
 */

public class AppExecutors {
    private Executor diskIO;
    private Executor networkIO;
    private Executor mainThread;

    private static AppExecutors appExecutors;

    public AppExecutors (Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public static AppExecutors getExecutors(){
        if (appExecutors == null){
            synchronized (new Object()){
                appExecutors = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThread());
            }
        }
        return appExecutors;
    }

    public Executor getDiskIO () {
        return diskIO;
    }

    public Executor getNetworkIO () {
        return networkIO;
    }

    public Executor getMainThread () {
        return mainThread;
    }

    static class MainThread implements Executor {

        Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute (@NonNull Runnable runnable) {
            handler.post(runnable);
        }
    }
}
