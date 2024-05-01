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
        String result = super.toString();
        if(subtasksList == null){
            result += "\nПодзадач пока нет";
        } else {
            result += "\n"+subtasksList;
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

    @Override
    public void executionTask(Statuses status) {
        if(status == Statuses.DONE) {
            int count = 0;
            for (Subtask subtask : subtasksList) {
                if (subtask.getSubtaskStatus() == Statuses.DONE) {
                    count++;
                }
            }

            if (count == subtasksList.size()) {
                System.out.println("Задача полностью выполнена");
                setStatus(Statuses.DONE);
            } else {
                System.out.println("Вы не выполнили некоторые задачи");
            }
        } else if(status == Statuses.IN_PROGRESS){
            System.out.println("Так держать");
            setStatus(status);
        }

    }
}
