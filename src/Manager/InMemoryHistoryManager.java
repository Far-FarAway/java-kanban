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
        historyList.addFirst(task);
        history.put(task.getId(), historyList.getFirst());
        checkHistory(task.getId());
    }

    @Override
    public String toString() {
        String result = "История просмотров:";
        if (!history.isEmpty()) {
            for (Node<Task> node : history.values()) {
                result += node.getData();
            }
        } else {
            result += "Пока пуста";
        }

        return result;
    }

    @Override
    public List<Task> getHistory() {
        List<Task> list = new ArrayList<>();

        for (Node<Task> node : history.values()) {
            list.add(node.getData());
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
