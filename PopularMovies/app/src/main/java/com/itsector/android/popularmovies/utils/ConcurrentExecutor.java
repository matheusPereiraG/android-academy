package com.itsector.android.popularmovies.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentExecutor {

    static final int NUM_THREADS = 5;

    private static ExecutorService executor;

    public static ExecutorService getInstance(){
        if(executor == null) executor = Executors.newFixedThreadPool(NUM_THREADS);
        return executor;
    }

}
