package Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    protected List<Subtask> subtasksList = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public List<Subtask> getSubtasksList(){
        return subtasksList;
    }


    public void addSubtask(Subtask subtask) {
        subtasksList.add(subtask);
    }

    @Override
    public String toString() {
        String result = "ID: " + id;
        if (name == null){ result += ""; }
        else { result += "\n" + name; }

        if (description == null) { result += ""; }
        else { result += "\nОписание: " + description; }

        result += "\nСтатус: " + status + "\nПодзадачи: \n";

        if(subtasksList == null){ result += "\nПодзадач пока нет\n"; }
        else {
            for(int i = 0; i < subtasksList.size(); i++){
                result += (i+1)+". " + subtasksList.get(i) + "\n";
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