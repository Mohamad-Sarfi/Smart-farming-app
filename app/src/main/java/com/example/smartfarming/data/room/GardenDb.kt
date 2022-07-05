package com.example.smartfarming.data.room

import android.content.Context
import android.util.Log
import androidx.constraintlayout.compose.override
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.smartfarming.data.room.daos.*
import com.example.smartfarming.data.room.entities.*
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [
        Garden::class,
        Task::class,
        FertilizationEntity::class,
        IrrigationEntity::class,
        PesticideEntity::class,
        Harvest::class
               ],
    version = 3,
    exportSchema = false
)
abstract class GardenDb : RoomDatabase() {

    abstract fun gardenDao() : GardenDao
    abstract fun taskDao() : TaskDao
    abstract fun fertilizationDao() : FertilizationDao
    abstract fun irrigationDao() : IrrigationDao
    abstract fun pesticideDao() : PesticideDao
    abstract fun harvestDao() : HarvestDao

    companion object {
        @Volatile
        private var INSTANCE : GardenDb? = null

        fun getDatabase(context: Context) : GardenDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    GardenDb::class.java,
                    "app_database"
                ).addCallback(object : RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        Log.i("DB", "DB created")
                        super.onCreate(db)
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        Log.i("DB", "DB opened")
                        super.onOpen(db)
                    }
                })
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}