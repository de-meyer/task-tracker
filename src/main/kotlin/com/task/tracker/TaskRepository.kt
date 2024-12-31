package com.task.tracker

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.io.File
import java.time.Instant

class TaskRepository(private val file: File) {
    private val json = Json {
        serializersModule = SerializersModule {
            contextual(Instant::class, InstantSerializer)  // Register Instant serializer
        }
        prettyPrint = true }

    private fun readTasksFromFile(): List<Task> {
        if (!file.exists()) {
            return emptyList()  // Return an empty list if the file doesn't exist
        }
        val jsonContent = file.readText()
        return json.decodeFromString(jsonContent)
    }

    private fun writeTasksToFile(tasks: List<Task>) {
        val jsonContent = json.encodeToString(tasks)
        file.writeText(jsonContent)
    }

    fun getTasks(): List<Task> {
        return readTasksFromFile()
    }

    fun getTaskById(id: Int): Task? {
        return getTasks().find { it.id == id }
    }

    fun addTask(task: Task) {
        val tasks = getTasks().toMutableList()
        tasks.add(task)
        writeTasksToFile(tasks)
    }

    fun updateTask(updatedTask: Task) {
        val tasks = getTasks().toMutableList()
        val index = tasks.indexOfFirst { it.id == updatedTask.id }
        if (index != -1) {
            tasks[index] = updatedTask
            writeTasksToFile(tasks)
        }
    }

    fun deleteTask(id: Int) {
        val tasks = getTasks().toMutableList()
        tasks.removeIf { it.id == id }
        writeTasksToFile(tasks)
    }
}