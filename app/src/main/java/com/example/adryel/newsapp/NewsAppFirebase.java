package com.example.adryel.newsapp;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.RetryStrategy;


public class NewsAppFirebase extends JobService{
    static AsyncTask<Void, Void, Void> mSyncNewsTask;

    @Override
    public boolean onStartJob(final JobParameters job) {
        mSyncNewsTask = new AsyncTask<Void, Void, Void>(){
            @Override
            protected void onPreExecute(){
                Toast.makeText(NewsAppFirebase.this, "News refreshed", Toast.LENGTH_SHORT).show();
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                MainActivity.syncNewsData();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(job, false);
            }
        };
        mSyncNewsTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (mSyncNewsTask != null) {
            mSyncNewsTask.cancel(true);
        }
        return true;
    }

//    public static void refreshArticles(Context context) {
//        ArrayList<Article> result = null;
//        URL url = NetworkUtils.makeURL();
//
//        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
//
//        try {
//            DatabaseUtils.deleteAll(db);
//            String json = NetworkUtils.getResponseFromHttpUrl(url);
//            result = NetworkUtils.parseJSON(json);
//            DatabaseUtils.bulkInsert(db, result);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        db.close();
//    }
}
