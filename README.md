# Task Tracker
This is a simple task tracker application that allows users to add, delete, and update tasks. The application is built using Spring Boot and Kotlin.

## Running the Application
First check if gradlew is executable, if not make it executable by running the following command:
```shell
chmod +x gradlew
```
To run the application, execute the following command:
```shell
./gradlew clean # if you have built the project before
./gradlew installDist
```
Then, add the freshly build `bin` folder to your path `/your-path-to-project/tracker/build/install/tracker/bin` navigate to the `build/install/task-tracker` directory and run the following command:
```shell
export PATH=$PATH:/your-path-to-project/tracker/build/install/tracker/bin
```
or add the path to your `.bashrc` or `.zshrc` file.
```.bashrc or .zshrc
path+=('/your-path-to-project/tracker/build/install/tracker/bin')
```
after adding the path to your `.bashrc` or `.zshrc` file reload your shell. By running the following command:
```shell
source ~/.bashrc
```
or 
```shell
source ~/.zshrc
```
Depending on the shell you are using.

Then, run the application using the following command:
```shell
# In this example you create your first task with the ID 1 and then update it,
# delete it, mark it as todo, mark it as in progress and mark it as done.


tracker add "Task 1"
tracker update 1 "Task 1 Updated"
tracker delete 1
tracker mark-todo 1
tracker mark-in-progress 1
tracker mark-done 1

# When you created multiple tasks you can list all tasks, list all tasks in a specific state
tracker list
tracker list-todo 
tracker list-in-progress
tracker list-done

```
