package com.example.smartfarming.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class GardenWorker(ctx : Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        return try {
            Result.success()
        } catch (throwable : Throwable){
            Result.failure()
        }
    }

}