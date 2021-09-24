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
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.work.Configuration;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

public class ReminderUtilities {
    private static final int REMINDER_INTERVAL_SECONDS = 15;

    private static final String REMINDER_JOB_TAG = "hydration_reminder_tag";

    private static boolean sInitialized;


    @RequiresApi(api = Build.VERSION_CODES.N)
    synchronized public static void scheduleChargingReminder(Context context){
        if(sInitialized) return;

        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(
                WaterReminderFirebaseJobService.class, REMINDER_INTERVAL_SECONDS, TimeUnit.MINUTES)
                .build();

        Log.v(context.getClass().getSimpleName(), "ENQUEUING WORKER");
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(REMINDER_JOB_TAG, ExistingPeriodicWorkPolicy.KEEP, request);

        sInitialized = true;
    }
}
