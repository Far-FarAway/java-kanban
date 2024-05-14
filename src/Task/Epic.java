package Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    protected List<Subtask> subtasksList = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public ArrayList<Subtask> getSubtasksList(){return new ArrayList<>(subtasksList);}


    public void addSubtask(Subtask subtask) {
        subtask.setSubtaskId(id);
        subtasksList.add(subtask);
    }

    @Override
    public String toString() {
        String result = "\n\nID: " + id;
        if (name == null){ result += ""; }
        else { result += "\n" + name; }

        if (description == null) { result += ""; }
        else { result += "\nОписание: " + description; }

        result += "\nСтатус: " + status + "\nПодзадачи: \n";

        if(subtasksList == null){ result += "\nПодзадач пока нет\n"; }
        else {
            for(Subtask subtask : subtasksList){
                result += subtask.getSubtaskId()+". " + subtasksList + "\n";
            }
        }

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
            if (subtask.getSubtaskStatus() == Status.DONE) {
                count++;
            }
        }

        if (count == subtasksList.size()) {
            setStatus(Status.DONE);
        } else if(count > 0) {
            setStatus(Status.IN_PROGRESS);
        } else {
            setStatus(Status.NEW);
        }

    }
}