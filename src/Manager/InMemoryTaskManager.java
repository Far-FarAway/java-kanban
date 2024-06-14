package Manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import task.*;

public class InMemoryTaskManager implements TaskManager {
    protected int id = 0;
    protected Map<Integer, Task> tasksMap = new HashMap<>();
    protected HistoryManager historyManager = Managers.getDefaultHistory();


    @Override
    public void addTask(Task o) {
        if (!tasksMap.containsValue(o)) {
            id++;
            o.setId(id);
            tasksMap.put(id, o);
        }
    }


    @Override
    public void addTask(Epic epic, Subtask... subtasks) {
        if (!tasksMap.containsValue(epic)) {
            addTask(epic);
        }

        for (Subtask subtask : subtasks) {
            Map<Integer, Subtask> subMap = epic.getSubtasksMap();

            if (!subMap.containsValue(subtask)) {
                id++;
                epic.addSubtask(id, subtask);
            }
        }
    }

    @Override
    public ArrayList<Task> getTasksList() {
        ArrayList<Task> tasksList = new ArrayList<>(tasksMap.values());
        for (Task task : tasksList) {
            historyManager.add(task);
        }

        return tasksList;
    }

    @Override
    public void deleteAllTasks() {
        tasksMap.clear();
        System.out.println("Список задач очищен");
    }

    @Override
    public void deleteByIds(int... indexes) {
        for (int i : indexes) {
            tasksMap.remove(i);
        }
    }

    @Override
    public Task getTask(int index) {
        if (tasksMap.containsKey(index)) {
            historyManager.add(tasksMap.get(index));
            return tasksMap.get(index);
        } else {
            System.out.println("Такой задачи не существует");
            return null;
        }
    }

    @Override
    public Subtask getSubtask(Epic epic, int index) {
        if (tasksMap.containsValue(epic)) {
            if (epic.getSubtasksMap().containsKey(index)) {
                Subtask subtask = epic.getSubtasksMap().get(index);
                historyManager.add(subtask);
                return subtask;
            } else {
                System.out.println("Такой подзадачи не существует");
                return null;
            }
        } else {
            System.out.println("Такого эпика не существует");
            return null;
        }
    }

    @Override
    public HashMap<Integer, Subtask> getSubtasks(Epic epic) {
        if (tasksMap.containsValue(epic)) {
            for (Subtask subtask : epic.getSubtasksMap().values()) {
                historyManager.add(subtask);
            }
            return epic.getSubtasksMap();
        } else {
            System.out.println("Такого эпика не сущесвтует");
            return null;
        }
    }

    @Override
    public void updateTask(int index, Task task) {
        if (tasksMap.containsKey(index)) {
            deleteByIds(task.getId());
            task.setId(index);

            deleteByIds(index);
            tasksMap.put(task.getId(), task);
        } else {
            System.out.println("Такого id не существует");
        }

    }

    @Override
    public Task updateInformation(Task task, String nameOrDescription, String info) {

        if (task.getClass() == Task.class) {
            if (nameOrDescription.equals("name")) {
                task.setName(info);
            } else if (nameOrDescription.equals("description")) {
                task.setDescription(info);
            }
        } else if (task.getClass() == Subtask.class) {
            if (nameOrDescription.equals("name")) {
                ((Subtask) task).setSubtaskName(info);
            } else if (nameOrDescription.equals("description")) {
                ((Subtask) task).setSubtaskDescription(info);
            }
        }

        return task;
    }

    @Override
    public String toString() {
        String result = "\n";
        for (Task task : tasksMap.values()) {
            result += "\n" + task;
            if (task instanceof Epic) {
                result += "\nПодзадачи: \n";

                if (((Epic) task).getSubtasksMap().isEmpty()) {
                    result += "\nПодзадач пока нет\n";
                } else {
                    for (Subtask subtask : ((Epic) task).getSubtasksMap().values()) {
                        result += subtask + "\n";
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void updateStatus(Task task) {
        if (task.getClass() == Task.class && task.getStatus() == Status.NEW) {
            task.setStatus(Status.DONE);
        } else if (task.getClass() == Subtask.class && ((Subtask) task).getSubtaskStatus() == Status.NEW) {
            ((Subtask) task).setSubtaskStatus(Status.DONE);
        } else if (task.getClass() == Epic.class) {
            ((Epic) task).checkStatus();
        } else {
            System.out.println("Такого статуса нет");
        }
    }

    @Override
    public String printHistory() {
        return historyManager.toString();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}