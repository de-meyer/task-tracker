package com.task.tracker

import com.task.tracker.enums.TaskStatus
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TrackerApplication

fun main(args: Array<String>) {
    val context = runApplication<TrackerApplication>(*args)
    val taskService = context.getBean(TaskService::class.java)
    if (args.isEmpty()) {
        println("Usage: tracker <command> [options]")
        return
    }
    when (val command = args[0]) {
        "list" -> {
            val tasks = taskService.getTasks()
            println("Listing all tasks")
            showHeader()
            tasks.forEach { showTaskInline(it) }
        }

        "add" -> {
            if (args.size < 2) {
                println("Usage: tracker add <description> <status>")
                return
            }
            println("Adding a new task")
            val description = args[1]
            taskService.createTask(description)
        }

        "update" -> {
            println("Updating a task")
            if (args.size < 3) {
                println("Usage: tracker update <id> <description>")
                return
            }
            val id = args[1].toInt()
            val description = args[2]
            taskService.updateTask(id, description)
        }

        "mark-todo" -> {
            println("Marking a task as todo")
            if (args.size < 2) {
                println("Usage: tracker mark-todo <id>")
                return
            }
            val id = args[1].toInt()
            taskService.updateTask(id, TaskStatus.TODO)
        }

        "mark-in-progress" -> {
            println("Marking a task as in-progress")
            if (args.size < 2) {
                println("Usage: tracker mark-in-progress <id>")
                return
            }
            val id = args[1].toInt()
            taskService.updateTask(id, TaskStatus.IN_PROGRESS)
        }

        "mark-done" -> {
            println("Marking a task as done")
            if (args.size < 2) {
                println("Usage: tracker mark-done <id>")
                return
            }
            val id = args[1].toInt()
            taskService.updateTask(id, TaskStatus.DONE)
        }
        "list-todo" -> {
            val tasks = taskService.getTasksByStatus(TaskStatus.TODO)
            println("Listing all tasks with status todo")
            showHeader()
            tasks.forEach { showTaskInline(it) }
        }
        "list-in-progress" -> {
            val tasks = taskService.getTasksByStatus(TaskStatus.IN_PROGRESS)
            println("Listing all tasks with status in-progress")
            showHeader()
            tasks.forEach { showTaskInline(it) }
        }
        "list-done" -> {
            val tasks = taskService.getTasksByStatus(TaskStatus.DONE)
            println("Listing all tasks with status done")
            showHeader()
            tasks.forEach { showTaskInline(it) }
        }
        "delete" -> {
            println("Deleting a task")
            if (args.size < 2) {
                println("Usage: tracker delete <id>")
                return
            }
            val id = args[1].toInt()
            taskService.deleteTask(id)
        }

        "exit" -> {
            println("Exiting the application")
            return
        }

        else -> {
            println("Unknown command: $command")
        }
    }
}

fun showHeader() {
    println("ID | Description | Status | Created At | Updated At")
}
fun showTaskInline(task: Task) {
    println("${task.id} | ${task.description} | ${task.status} | ${task.createdAt} | ${task.updatedAt}")
}