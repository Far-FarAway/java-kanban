package Tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    protected List<Subtask> subtasksList = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }


    public void addSubtask(Subtask subtask) {
        subtasksList.add(subtask);
    }

    public void printSubtasks() {
        for (Subtask subtask : subtasksList) {
            subtask.toString();
        }
    }

    @Override
    public String toString() {
        String result = "ID: " + id;
        if (name == null){ result += ""; }
        else { result += "\n" + name; }

        if (description == null) { result += ""; }
        else { result += "\n" + description; }

        checkStatus();
        result += "\nСтатус: " + status;

        if(subtasksList == null){ result += "\nПодзадач пока нет"; }
        else { result += "\n"+subtasksList; }

        return result;
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        Epic epic = (Epic)o;
        return Objects.equals(epic.subtasksList, this.subtasksList);
    }

    public void checkStatus() {
        int count = 0;
        for (Subtask subtask : subtasksList) {
            if (subtask.getSubtaskStatus() == Statuses.DONE) {
                count++;
            }
        }

        if (count == subtasksList.size()) {
            setStatus(Statuses.DONE);
        } else {
            setStatus(Statuses.IN_PROGRESS);
        }

    }
}

