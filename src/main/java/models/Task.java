package models;

import lombok.Data;

@Data
public class Task implements Comparable<Task>{
    private int id;
    private String name;
    private String description;
    private String deadline;

    public Task(){}
    public Task(int id , String name, String description, String deadline) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
    }
    public Task(String name, String description, String deadline) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
    }



    @Override
    public int compareTo(Task otherTask) {
        return this.name.compareTo(otherTask.name);
    }
}
