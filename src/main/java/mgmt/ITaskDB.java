package mgmt;

import models.Task;

import java.util.List;

public interface ITaskDB {
    void addTask(Task task);

    void updateTask(Task task);

    void deleteTask(int taskId);

    Task getTask(int taskId);

    List<Task> getAllTasks();
}