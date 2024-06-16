package manager;

import task.Task;
import list.ownLinkedList.HistoryLinkedList;
import list.ownLinkedList.Node;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    protected HistoryLinkedList<Task> historyList = new HistoryLinkedList<>();
    protected Map<Integer, Node<Task>> history = new HashMap<>();

    @Override
    public void add(Task task) {
        checkHistory(task.getId());
        historyList.addFirst(task);
        history.put(task.getId(), historyList.getFirst());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("История просмотров:");
        if (!history.isEmpty()) {
            for (Task task : getHistory()) {
                result.append(task);
            }
        } else {
            result.append("Пока пуста");
        }

        return result.toString();
    }

    @Override
    public List<Task> getHistory() {
        List<Task> list = new ArrayList<>();
        Node<Task> next = historyList.getFirst();
        list.add(next.getData());

        for (int i = 1; i < historyList.getSize(); i++) {
            if (next.getNext() != null) {
                next = next.getNext();
                list.add(next.getData());
            }
        }

        return list;
    }

    @Override
    public void remove(int id) {
        historyList.delete(history.get(id));
    }

    public void checkHistory(int id) {
        if (history.containsKey(id)) {
            remove(id);
            history.remove(id);
        }
    }
}
