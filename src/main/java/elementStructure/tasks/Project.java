package elementStructure.tasks;

import elementStructure.TaskSuperclass;

public class Project extends TaskSuperclass {

    public Project() {
        super();
        this.title = "empty";
    }

    @Override
    public String toString() {
        if (this.title == null) {
            title = "";
        }
        return "Project: " + this.getTitle();
    }
}
