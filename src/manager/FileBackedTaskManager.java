package manager;

import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Path;

import task.Task;

import static task.Task.FORMATTER;

import task.Epic;
import task.Subtask;
import task.Status;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    protected String filename;

    public FileBackedTaskManager(String filename) {
        this.filename = filename;
    }

    private void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write("id,type,name,status,description,epic,startTime,duration\n");
            for (Task task : tasksMap.values()) {
                if (task instanceof Epic epic) {
                    bw.write(epic.getId() + ",EPIC," + epic.getName() + "," + epic.getStatus() + "," +
                            epic.getDescription() + ",," + epic.getStartTime().format(FORMATTER) + "," +
                            epic.getDuration().toMinutes() + "\n");
                    if (!epic.getSubtasksMap().isEmpty()) {
                        for (Subtask subtask : epic.getSubtasksMap().values()) {
                            bw.write(subtask.getId() + ",SUBTASK," + subtask.getSubtaskName() + "," +
                                    subtask.getSubtaskStatus() + "," + subtask.getSubtaskDescription() + "," +
                                    epic.getId() + "," + subtask.getStartTime().format(FORMATTER) + "," +
                                    subtask.getDuration().toMinutes() + "\n");
                        }
                    }
                } else {
                    bw.write(task.getId() + ",TASK," + task.getName() + "," + task.getStatus() +
                            "," + task.getDescription() + ",," + task.getStartTime().format(FORMATTER) + "," +
                            task.getDuration().toMinutes() + "\n");
                }
            }
        } catch (IOException ex) {
            System.out.println("Исключение во время попытки автосохранения");
        }
    }

    public void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(Path.of(filename).toFile()))) {
            br.readLine();
            while (br.ready()) {
                String[] info = br.readLine().split(",");
                if (info.length > 1) {
                    if (info[1].equals("TASK")) {
                        Task task = new Task(info[2], info[4], Status.valueOf(info[3]), Integer.parseInt(info[7]),
                                info[6]);
                        int id = Integer.parseInt(info[0]);
                        task.setId(id);
                        tasksMap.put(id, task);
                    } else if (info[1].equals("EPIC")) {
                        Epic epic = new Epic(info[2], info[4], Status.valueOf(info[3]));
                        int id = Integer.parseInt(info[0]);
                        epic.setId(id);
                        tasksMap.put(id, epic);
                    } else {
                        Epic epic = (Epic) tasksMap.get(Integer.parseInt(info[5]));
                        Subtask subtask = new Subtask(info[2], info[4], Status.valueOf(info[3]),
                                Integer.parseInt(info[7]), info[6]);
                        int id = Integer.parseInt(info[0]);
                        subtask.setId(id);
                        epic.addSubtask(id, subtask);
                    }
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
