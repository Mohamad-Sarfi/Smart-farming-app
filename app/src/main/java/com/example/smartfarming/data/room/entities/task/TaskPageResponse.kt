package com.example.smartfarming.data.room.entities.task

import com.example.smartfarming.data.room.entities.Task

data class TaskPageResponse(
    val taskPage : TaskPage
)

data class TaskPage(
    val content : List<Task>,
    val empty : Boolean,
    val first : Boolean,
    val last : Boolean,
    val number : Int,
    val numberOfElements : Int,
    val pageable : Pageable,
    val size : Int,
    val sort : Sort,
    val totalElements : Int,
    val totalPages : Int
)

data class Pageable(
    val offset : Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged : Boolean,
    val sort : Sort,
    val unpaged : Boolean
)

data class Sort(
    val empty : Boolean,
    val sorted : Boolean,
    val unsorted : Boolean
)