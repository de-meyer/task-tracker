package com.task.tracker

import com.task.tracker.enums.TaskStatus
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TrackerApplication

fun main(args: Array<String>) {

	val context = runApplication<TrackerApplication>(*args)
	val taskService = context.getBean(TaskService::class.java)
	var read = ""
	while (read != "q") {
		println("(a)dd, (u)pdate, (d)elete task?")
		read = readln()
		println("You said: $read")
		when (read) {
			"a" -> {
				println("Creating new task -> enter your description")
				read = readln()
				taskService.createTask(read)
				println("Task created successfully ")
				showTask(taskService.getTasks().last())
			}
			"u" -> {
				println("Which task do you want to update?")
				val taskList = taskService.getTasks()
				taskList.forEachIndexed() { index, it ->
					println("($index) -> Task id: ${it.id} description: ${it.description}")
				}
				read = readln()
				read.toIntOrNull() ?: continue // skip if not a number or null value
				val task = taskList[read.toInt()]
				println("enter new description")
				val readDesc = readln()
				println("Updating task ${taskService.getTasks().last().id}")
				println("Do you want to update the status? (y/n)")
				val readStatus = readln()
				if (readStatus.lowercase() == "y") {
					println("Enter new status (TODO, IN_PROGRESS, DONE)")
					val readStat = readln()
					taskService.updateTask(task.id,readDesc,TaskStatus.valueOf(readStat))
				} else {
					taskService.updateTask(task.id, readDesc, task.status)
				}
				println("Task updated successfully")
				showTask(task)
			}
			"d" -> {
				val taskList = taskService.getTasks()
				taskList.forEachIndexed() { index, it ->
					println("($index) -> Task id: ${it.id} description: ${it.description}")
				}
				println("Which task do you want to delete?")
				read = readln()
				read.toIntOrNull() ?: continue // skip if not a number or null value
				val task = taskList[read.toInt()]
				println("Deleting task ${task.id} with description ${task.description}")
				taskService.deleteTask(task.id)
				println("Task deleted successfully")
				showTask(task)
			}
		}
	}
}
fun showTask(task: Task) {
	println("Task id: ${task.id}")
	println("Task description: ${task.description}")
	println("Task status: ${task.status}")
	println("Task created at: ${task.createdAt}")
	println("Task updated at: ${task.updatedAt}")
}