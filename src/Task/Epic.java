package Task;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Epic extends Task {
    protected Map<Integer, Subtask> subtasksMap = new HashMap<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public HashMap<Integer, Subtask> getSubtasksMap(){return new HashMap<>(subtasksMap);}


    public void addSubtask(Subtask subtask) {
        if (!subtasksMap.containsValue(subtask)) {
            int subtaskId = 1;
            while(subtasksMap.containsKey(subtaskId)) {
                subtaskId++;
            }
            subtask.setSubtaskId(subtaskId);
            subtasksMap.put(subtaskId, subtask);
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

        if(subtasksMap == null){ result += "\nПодзадач пока нет\n"; }
        else {
            for(Subtask subtask : subtasksMap.values()){
                result += subtask + "\n";
            }
        }

        return result;
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        Epic epic = (Epic)o;
        return Objects.equals(epic.subtasksMap, this.subtasksMap);
    }

    public void checkStatus() {
        int count = 0;
        for (Subtask subtask : subtasksMap.values()) {
            if (subtask.getSubtaskStatus() == Status.DONE) {
                count++;
            }
        }

        if (count == subtasksMap.size()) {
            setStatus(Status.DONE);
        } else if(count > 0) {
            setStatus(Status.IN_PROGRESS);
        } else {
            setStatus(Status.NEW);
        }

    }
}