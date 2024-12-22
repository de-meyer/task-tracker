package com.task.tracker

import com.task.tracker.enums.TaskStatus
import org.springframework.stereotype.Service
import java.io.File
import java.util.*

@Service
class TaskService {
    val taskRepository = TaskRepository(File("src/main/resources/taskDB.json"))

    fun createTask(description: String) {
        taskRepository.addTask(Task(
            id = UUID.randomUUID(),
            description = description,
            status = TaskStatus.TODO,
            createdAt = Date().toInstant(),
            updatedAt = null))
    }

    fun updateTask(id: UUID, description: String, status: TaskStatus) {
        val task = taskRepository.getTaskById(id)
        task?.let {
            it.description = description
            it.status = status
        }
        taskRepository.updateTask(task!!)
    }

    fun deleteTask(id: UUID) {
        taskRepository.deleteTask(id)
    }

    fun getTasks(): List<Task> {
        return taskRepository.getTasks()
    }
}