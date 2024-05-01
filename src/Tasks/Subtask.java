package Tasks;

import java.util.Objects;

public class Subtask {
    protected String subtaskName;
    protected Statuses subtaskStatus;
    protected String description;

    public Subtask(String description, String subtaskName) {
        this.description = description;
        this.subtaskName = subtaskName;
        subtaskStatus = Statuses.NEW;
    }


    @Override
    public String toString() {
        String result = "";
        if(subtaskName == null){ result += ""; }
        else { result += subtaskName; }

        if (description == null) { result += ""; }
        else { result += "\n" + description; }

        return result + "\nСтатус:" + subtaskStatus;
    }

    @Override
    public int hashCode(){
        return Objects.hash(subtaskName, description, subtaskStatus);
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        Subtask sub = (Subtask)o;
        return Objects.equals(sub.subtaskName, this.subtaskName) && Objects.equals(sub.subtaskStatus, this.subtaskStatus);
    }

    public String getSubtaskName() {
        return subtaskName;
    }

    public Statuses getSubtaskStatus() {
        return subtaskStatus;
    }

    public void setSubtaskStatus(Statuses status) {
        if(status != Statuses.DONE && status != Statuses.IN_PROGRESS){
            System.out.println("Такого статуса нет");
        } else {
            this.subtaskStatus = status;
        }
    }
}
