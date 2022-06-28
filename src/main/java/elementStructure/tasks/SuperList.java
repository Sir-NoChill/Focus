package elementStructure.tasks;

import elementStructure.TaskSuperclass;

public class SuperList extends TaskSuperclass {

    public SuperList() {
        super();
        this.title = "empty";
    }
    @Override
    public String toString() {
        if (this.title == null) {
            this.title = "";
        }
        return "SuperClass: " + this.getTitle();
    }
}
