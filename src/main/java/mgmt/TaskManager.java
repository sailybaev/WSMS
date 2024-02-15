package mgmt;

import models.Task;

import java.util.List;

public class TaskManager {
    private ITaskDB db;

    public TaskManager() {
        this.db = new TaskDB();
    }

    public void addTask(Task task) {
        db.addTask(task);
    }

    public void updateTask(Task task) {
        db.updateTask(task);
    }

    public void deleteTask(int taskId) {
        db.deleteTask(taskId);
    }

    public Task getTask(int taskId) {
        return db.getTask(taskId);
    }

    public List<Task> getAllTasks() {
        return db.getAllTasks();
    }

}
