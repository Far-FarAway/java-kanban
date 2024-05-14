package Task;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Epic extends Task {
    protected Map<Integer, Subtask> subtasksList = new HashMap<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public HashMap<Integer, Subtask> getSubtasksList(){return new HashMap<>(subtasksList);}


    public void addSubtask(Subtask subtask) {
        if (!subtasksList.containsValue(subtask)) {
            id++;
            subtask.setSubtaskId(id);
            subtasksList.put(id, subtask);
        }
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
            for(Subtask subtask : subtasksList.values()){
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
        for (Subtask subtask : subtasksList.values()) {
            if (subtask.getSubtaskStatus() == Status.DONE) {
                count++;
            }
        }

        if (count == subtasksList.size()) {
            setStatus(Status.DONE);
    