package Manager;

import Task.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager{

    protected ArrayList<Task> history = new ArrayList<>();

    @Override
    public void add(Task task){
        history.add(task);
        checkHistory();
    }

    @Override
    public String toString(){
        String result = "История просмотров:";
        if(!history.isEmpty()) {
            for (Task task : history) {
                result += task;
            }
        } else {
            result += "Пока пуста";
        }

        return result;
    }
    @Override
    public ArrayList<Task> getHistory(){
        return history;
    }

    private void checkHistory(){
        if (history.size() > 10){
            history.remove(history.getFirst());
        }
    }
}
