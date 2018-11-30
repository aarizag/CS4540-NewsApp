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
package com.example.adryel.newsapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public class NewsSyncUtilities {

    private static final int SYNC_INTERVAL_SECONDS = 10;
    private static final int SYNC_FLEXTIME_SECONDS = 10;

    private static boolean sInitialized = false;

    private static final String NEWS_SYNC_TAG = "news-sync";

    static void scheduleFirebaseSync(@NonNull final Context context) {
        if (sInitialized) return;
        sInitialized = true;

        Log.d(NEWS_SYNC_TAG, "Initializing Scheduled Sync");

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job syncNewsJob = dispatcher.newJobBuilder()
                .setService(NewsAppFirebase.class)
                .setTag(NEWS_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        SYNC_INTERVAL_SECONDS,
                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(syncNewsJob);
    }

    public static void startImmediateSync(@NonNull final Context context) {
        Intent ignoreReminderIntent = new Intent(context, NewsSyncIntentService.class);
        ignoreReminderIntent.setAction("stop-news-sync");
    }
}