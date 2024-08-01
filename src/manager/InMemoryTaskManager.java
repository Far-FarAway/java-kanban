package manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import task.*;

public class InMemoryTaskManager implements TaskManager {
    protected int id = 0;
    protected Map<Integer, Task> tasksMap = new HashMap<>();
    protected HistoryManager historyManager = Managers.getDefaultHistory();
    protected Set<Task> prioritizedSet = new TreeSet<>((task1, task2) -> {
        LocalDateTime time1 = task1.getStartTime();
        LocalDateTime time2 = task2.getStartTime();

        if (time1.isAfter(time2)) {
            return 3;
        } else if (time1.isBefore(time2)) {
            return -3;
        } else {
            return 0;
        }
    });


    @Override
    public void addTask(Task o) {
        List<Task> list = new ArrayList<>();
        if (!(o instanceof Epic)) {
            list = prioritizedSet.stream().filter(task -> findTimeCrossing(task, o)).toList();
        }
        if (list.isEmpty()) {
            if (!tasksMap.containsValue(o)) {
                id++;
                o.setId(id);
                tasksMap.put(id, o);
            }
            if (o.getStartTime().isAfter(LocalDateTime.of(2000, 1, 1, 0, 0))) {
                prioritizedSet.add(o);
            }
        } else {
            System.out.println("Задачи не могут пересекаться по временени");
        }
    }


    @Override
    public void addTask(Epic epic, Subtask subtask) {
        if (!tasksMap.containsValue(epic)) {
            addTask(epic);
        }

        Map<Integer, Subtask> subMap = epic.getSubtasksMap();

        List<Subtask> list = subMap.values().stream().filter(sub -> findTimeCrossing(sub, subtask)).toList();

        if (list.isEmpty()) {
            if (!subMap.containsValue(subtask)) {
                id++;
                epic.addSubtask(id, subtask);
            }
            prioritizedSet.add(epic);
        } else {
            System.out.println("Задачи не могут пересекаться по времени");
        }
    }

    @Override
    public void printPrioritizedTasks() {
        for (Task task : prioritizedSet) {
            System.out.println(task);
            if (task instanceof Epic epic) {
                System.out.println("Подзадачи: ");
                for (Subtask sub : epic.getPrioritizedSubSet()) {
                    System.out.println(sub);
                }
            }
        }
    }

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return (TreeSet<Task>) prioritizedSet;
    }

    @Override
    public ArrayList<Task> getTasksList() {
        return (ArrayList<Task>) tasksMap.values().stream().peek(task -> historyManager.add(task))
                .collect(Collectors.toList());
    }

    @Override
    public boolean findTimeCrossing(Task task1, Task task2) {
        if (!task1.equals(task2)) {
            LocalDateTime startTask1 = task1.getStartTime();
            LocalDateTime endTask1 = task1.getEndTime();
            LocalDateTime startTask2 = task2.getStartTime();
            LocalDateTime endTask2 = task2.getEndTime();
            if (!endTask1.isAfter(startTask2)) {
                return false;
            } else return endTask2.isAfter(startTask1);
        } else {
            return false;
        }
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
        if (task.getClass() == Subtask.class) {
            if (nameOrDescription.equals("name")) {
                ((Subtask) task).setSubtaskName(info);
            } else if (nameOrDescription.equals("description")) {
                ((Subtask) task).setSubtaskDescription(info);
            }
        } else {
            if (nameOrDescription.equals("name")) {
                task.setName(info);
            } else if (nameOrDescription.equals("description")) {
                task.setDescription(info);
            }
        }

        return task;
    }

    @Override
    public Task updateTime(Task task, String durationOrStartTime, String time) {
        if (task.getClass() == Subtask.class) {
            Subtask subtask = (Subtask) task; //Проверить, будет ли корректно менятся информация из за приведения типов
            if (durationOrStartTime.equals("duration")) {
                subtask.setDuration(Integer.parseInt(time));
            } else if (durationOrStartTime.equals("startTime")) {
                subtask.setStartTime(time);
            }
            Epic epic = (Epic) tasksMap.get(subtask.getEpicId());
            epic.fillDurationAndStartEndTime();
        } else {
            if (durationOrStartTime.equals("duration")) {
                task.setDuration(Integer.parseInt(time));
            } else if (durationOrStartTime.equals("startTime")) {
                task.setStartTime(time);
            }
        }

        return task;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("\n");
        for (Task task : tasksMap.values()) {
            result.append("\n").append(task);
            if (task instanceof Epic epic) {
                result.append("\nПодзадачи: \n");

                if (epic.getSubtasksMap().isEmpty()) {
                    result.append("\nПодзадач пока нет\n");
                } else {
                    for (Subtask subtask : epic.getSubtasksMap().values()) {
                        result.append(subtask).append("\n");
                    }
                }
            }
        }
        return result.toString();
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