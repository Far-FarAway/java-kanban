package Task;

import java.util.Objects;

public class Subtask extends Task {
    protected String subtaskName;
    protected Status subtaskStatus;
    protected String subtaskDescription;

    public Subtask(String subtaskName, String description) {
        super("","");
        this.subtaskDescription = description;
        this.subtaskName = subtaskName;
        subtaskStatus = Status.NEW;
    }


    @Override
    public String toString() {
        String result = "";
        if(subtaskName == null){ result += ""; }
        else { result += subtaskName; }

        if (subtaskDescription == null) { result += ""; }
        else { result += "\nОписание: " + subtaskDescription; }

        return result + "\nСтатус:" + subtaskStatus;
    }

    @Override
    public int hashCode(){
        return Objects.hash(subtaskName, subtaskDescription, subtaskStatus);
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        Subtask sub = (Subtask)o;
        return Objects.equals(sub.subtaskName, this.subtaskName) && Objects.equals(sub.subtaskStatus, this.subtaskStatus);
    }

    public Status getSubtaskStatus() {
        return subtaskStatus;
    }

    public void setSubtaskStatus(Status status) {
        if(status != Status.DONE && status != Status.IN_PROGRESS && status != Status.NEW){
            System.out.println("Такого статуса нет");
        } else {
            this.subtaskStatus = status;
        }
    }

    public void setSubtaskName(String subtaskName) {
        this.subtaskName = subtaskName;
    }

    public void setSubtaskDescription(String subtaskDescription) {
        this.subtaskDescription = subtaskDescription;
    }
}