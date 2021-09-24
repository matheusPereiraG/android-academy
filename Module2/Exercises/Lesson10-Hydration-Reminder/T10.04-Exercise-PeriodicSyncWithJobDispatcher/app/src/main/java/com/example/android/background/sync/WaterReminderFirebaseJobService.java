/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.background.sync;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;

import com.google.common.util.concurrent.ListenableFuture;

public class WaterReminderFirebaseJobService extends ListenableWorker {

    private AsyncTask mBackgroundTask;

    public WaterReminderFirebaseJobService(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public ListenableFuture<Result> startWork() {
        /*mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Log.d(this.getClass().getSimpleName(), "Im doing in the background");
                ReminderTasks.executeTask(getApplicationContext(), ReminderTasks.ACTION_CHARGE_REMINDER_NOTIFICATION);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                return;
            }
        };*/
        //mBackgroundTask.execute();
        Log.d(getApplicationContext().getClass().getSimpleName(), "Executing...");
        ReminderTasks.executeTask(getApplicationContext(), ReminderTasks.ACTION_CHARGE_REMINDER_NOTIFICATION);
        return (ListenableFuture<Result>) Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
        //if(mBackgroundTask != null) mBackgroundTask.cancel(true);
    }
}
