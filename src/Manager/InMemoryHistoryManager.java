package Manager;

import Task.Task;
import HandMadeLinkedList.HistoryLinkedList;
import HandMadeLinkedList.Node;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private HistoryLinkedList<Task> historyList = new HistoryLinkedList<>();
    private Map<Integer, Node<Task>> history = new HashMap<>();

    @Override
    public void add(Task task) {
        checkHistory(task.getId());
        historyList.addFirst(task);
        history.put(task.getId(), historyList.getFirst());
    }

    @Override
    public String toString() {
        String result = "История просмотров:";
        if (!history.isEmpty()) {
            for (Task task : getHistory()) {
                result += task;
            }
        } else {
            result += "Пока пуста";
        }

        return result;
    }

    @Override
    public List<Task> getHistory() {
        List<Task> list = new ArrayList<>();
        Node<Task> next = historyList.getFirst();
        list.add(next.getData());

        for (int i = 1; i < historyList.getSize(); i++) {
            if(next.getNext() != null) {
                next = next.getNext();
                list.add(next.getData());
            }
        }

        return list;
    }

    @Override
    public void remove(int id){
        historyList.delete(history.get(id));
    }

    public void checkHistory(int id){
        if(history.containsKey(id)){
            remove(id);
            history.remove(id);
        }
    }
}
