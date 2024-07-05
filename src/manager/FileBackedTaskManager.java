package manager;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import task.*;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    protected String filename;

    public FileBackedTaskManager(String filename) {
        this.filename = filename;
    }

    private void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write("id,type,name,status,description\n");
            for (Task task : tasksMap.values()) { //проверь, что подзадачи корректно дополняются
                if (task instanceof Epic epic) {
                    bw.write(epic.getId() + ",EPIC," + epic.getName() + "," + epic.getStatus() + "," +
                            epic.getDescription() + "\n");
                } else if (task instanceof Subtask subtask) {
                    bw.write(subtask.getId() + ",SUBTASK," + subtask.getName() + "," + subtask.getSubtaskStatus() +
                            "," + subtask.getDescription() + "\n");
                } else {
                    bw.write(task.getId() + ",TASK," + task.getName() + "," + task.getStatus() +
                            "," + task.getDescription() + "\n");
                }
            }
        } catch (IOException ex) {
            System.out.println("Исключение во время попытки автосохранения");
            ex.printStackTrace();
        }
    }

    public static void loadFromFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.ready()){
                String[] info = br.readLine().split(",");
                if (info[1].equals("TASK")){
                    tasksMap.put(Integer.parseInt(info[0]), new Task(info[2], info[4], Status.valueOf(info[3])));
                } else if (info[1].equals("EPIC")) {
                    tasksMap.put(Integer.parseInt(info[0]), new Epic(info[2], info[4], Status.valueOf(info[3])));
                } else {
                    tasksMap.put(Integer.parseInt(info[0]), new Subtask(info[2], info[4], Status.valueOf(info[3])));
                }
            }
        } catch (IOException ex) {
            System.out.println("Исключение во время попытки загрузить данные задач из файла");
        }
    }

    @Override
    public void addTask(Task o) {
        super.addTask(o);
        save();
    }

    @Override
    public void addTask(Epic epic, Subtask... subtasks) {
        super.addTask(epic, subtasks);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteByIds(int... indexes) {
        super.deleteByIds(indexes);
        save();
    }

    @Override
    public void updateTask(int index, Task task) {
        super.updateTask(index, task);
        save();
    }

    @Override
    public Task updateInformation(Task task, String nameOrDescription, String info) {
        Task updatedTask = super.updateInformation(task, nameOrDescription, info);
        save();
        return updatedTask;
    }

    @Override
    public void updateStatus(Task task) {
        super.updateStatus(task);
        save();
    }

}
