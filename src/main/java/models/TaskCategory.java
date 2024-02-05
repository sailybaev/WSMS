package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TaskCategory extends Task {
    private String category;

    public TaskCategory(){}

    public TaskCategory(String category) {
        this.category = category;
    }

    public TaskCategory(int id , String name, String description, String deadline, String category) {
        super(id,name, description, deadline);
        this.category = category;
    }
    public TaskCategory(String name, String description, String deadline, String category) {
        super(name, description, deadline);
        this.category = category;
    }


}