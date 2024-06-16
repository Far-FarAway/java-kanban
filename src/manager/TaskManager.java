package manager;

import task.*;

import java.util.List;
import java.util.Map;

public interface TaskManager {
    void addTask(Task o);

    void addTask(Epic epic, Subtask... subtasks);

    List<Task> getTasksList();

    void deleteAllTasks();

    void deleteByIds(int... indexes);

    Task getTask(int index);

    Subtask getSubtask(Epic epic, int index);

    Map<Integer, Subtask> getSubtasks(Epic epic);

    void updateTask(int index, Task task);

    Task updateInformation(Task task, String nameOrDescription, String info);

    @Override
    String toString();

    void updateStatus(Task task);

    String printHistory();

    List<Task> getHistory();
}
