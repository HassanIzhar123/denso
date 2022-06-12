package com.tech.denso.Helper;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskRunner {
    private final Executor executor = Executors.newSingleThreadExecutor(); // change according to your requirements
    private final Handler handler = new Handler(Looper.getMainLooper());

    public interface Callback<R> {
        void onStart();

        void onComplete(R result);

        void onError(Exception e);
    }

    public <R> void executeAsync(Callable<R> callable, Callback<R> callback) {
        callback.onStart();
        executor.execute(() -> {
            handler.post(() -> {
                try {
                    R result = callable.call();
                    callback.onComplete(result);
                } catch (Exception e) {
                    Log.e("issueintaskrunner", "" + e.getMessage() + " " + Arrays.toString(e.getStackTrace()));
                    callback.onError(e);
                    e.printStackTrace();
                }
            });
        });
    }
}