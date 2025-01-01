package com.task.tracker

import com.task.tracker.enums.TaskStatus
import org.springframework.stereotype.Service
import java.nio.file.Paths
import java.time.Instant
import java.util.*

@Service
class TaskService {
    val taskRepository = TaskRepository( Paths.get(System.getProperty("user.home"), "projects", "tracker", "src", "main", "resources", "taskDB.json").toFile())

    fun createTask(description: String) {
        taskRepository.addTask(Task(
            id = taskRepository.getTasks().size + 1,
            description = description,
            status = TaskStatus.TODO,
            createdAt = Date().toInstant(),
            updatedAt = null))
    }

    fun updateTask(id: Int, description: String) {
        val task = taskRepository.getTaskById(id)
        task?.let {
            it.description = description
            it.updatedAt = Instant.now() // Update additionally the updatedAt field with the current time
        }
        taskRepository.updateTask(task!!)
    }
    fun updateTask(id: Int, status: TaskStatus) {
        val task = taskRepository.getTaskById(id)
        task?.let {
            it.status = status
            it.updatedAt = Instant.now() // Update additionally the updatedAt field with the current time
        }
        taskRepository.updateTask(task!!)
    }

    fun deleteTask(id: Int) {
        taskRepository.deleteTask(id)
    }

    fun getTasks(): List<Task> {
        return taskRepository.getTasks()
    }

    fun getTasksByStatus(status: TaskStatus): List<Task> {
        return taskRepository.getTasksByStatus(status)
    }
}