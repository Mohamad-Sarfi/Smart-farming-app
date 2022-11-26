package com.example.smartfarming.data.room.daos

import androidx.room.*
import com.example.smartfarming.data.room.entities.Task
import com.example.smartfarming.data.room.entities.enums.TaskStatusEnum
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task : Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM task_table WHERE gardenIds LIKE :gardenIds AND status != :status ORDER BY id DESC")
    fun getTasksForGarden(gardenIds : String, status: String = TaskStatusEnum.DONE.name) : Flow<List<Task>>

    @Query("SELECT * FROM task_table ORDER BY id DESC")
    fun getAllTasks() : Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE status != :status ORDER BY id DESC")
    fun getAllUndoneTasks(status: String = TaskStatusEnum.DONE.name) : Flow<List<Task>>

    @Query("UPDATE task_table SET status = 'DONE' WHERE id = :taskId")
    suspend fun updateTaskStatus(taskId : Int)

    @Query("UPDATE task_table SET status = :status WHERE id = :taskId")
    suspend fun setTaskStatus(taskId: Int, status : String)
}