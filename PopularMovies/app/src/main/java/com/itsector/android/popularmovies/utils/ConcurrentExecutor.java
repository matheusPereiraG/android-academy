package com.itsector.android.popularmovies.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentExecutor {

    final static int NUM_THREADS = 3;

    private static ExecutorService executor;

    public static ExecutorService getInstance(){
        if(executor == null) executor = Executors.newFixedThreadPool(NUM_THREADS);
        return executor;
    }

}
