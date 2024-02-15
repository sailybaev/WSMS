package mgmt;

import models.Task;
import models.TaskCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDB implements ITaskDB{
    private Connection connection;
    public TaskDB() {
        try {
            this.connection = DatabaseConnector.getConnection();
            createTaskTable();
        }
        catch (SQLException e) {
            e.printStackTrace();
            ExceptionManager.handleException(e);
        }
    }

    private void createTaskTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tasks (id SERIAL PRIMARY KEY,task_name VARCHAR(255) NOT NULL,description TEXT,deadline VARCHAR(20) NOT NULL,category VARCHAR(255))";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
        catch (SQLException e) {
            ExceptionManager.handleException(e);
        }
    }




    public void addTask(Task task) {
        if (task == null || task.getName() == null || task.getDescription() == null || task.getDeadline() == null) {
            System.err.println("Task qate");
            return;
        }

        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO tasks (task_name, description, deadline, category) VALUES (?, ?, ?, ?)"
        )) {
            stmt.setString(1, task.getName());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getDeadline());

            if (task instanceof TaskCategory) {
                stmt.setString(4, ((TaskCategory) task).getCategory());
            }
            else {
                stmt.setNull(4, java.sql.Types.VARCHAR);
            }

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            ExceptionManager.handleException(e);
        }
    }

    public void updateTask(Task task) {
        if (task == null || task.getName() == null || task.getDescription() == null || task.getDeadline() == null || task.getId() <= 0) {
            System.err.println("Task qate");
            return;
        }

        try (PreparedStatement stmt = connection.prepareStatement(
                "UPDATE tasks SET task_name = ?, description = ?, deadline = ?, category = ? WHERE id = ?"
        )) {

            stmt.setString(1, task.getName());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getDeadline());

            if (task instanceof TaskCategory) {
                stmt.setString(4, ((TaskCategory) task).getCategory());
            }
            else {
                stmt.setString(4 , "-");
            }

            stmt.setInt(5, task.getId());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            ExceptionManager.handleException(e);
        }
    }



    public void deleteTask(int taskId) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM tasks WHERE id = ?"
        )) {
            stmt.setInt(1, taskId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            ExceptionManager.handleException(e);
        }
    }

    public Task getTask(int taskId) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM tasks WHERE id = ?"
        )) {
            stmt.setInt(1, taskId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("task_name");
                    String description = rs.getString("description");
                    String deadline = rs.getString("deadline");
                    String category = rs.getString("category");
                    if(category != null) {
                        return new TaskCategory(id,name ,description,deadline,category);
                    }
                    else {
                        return new Task(id ,name, description, deadline);
                    }
                }
            }
        } catch (SQLException e) {
            ExceptionManager.handleException(e);
        }
        return null;
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM tasks");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("task_name");
                String description = rs.getString("description");
                String deadline = rs.getString("deadline");
                String category = rs.getString("category");

                if(category != null) {
                    tasks.add(new TaskCategory(id , name , description, deadline,category));
                }
                else {
                    tasks.add(new Task(id, name,description, deadline));
                }


            }
        } catch (SQLException e) {
            ExceptionManager.handleException(e);
        }
        return tasks;
    }
}
