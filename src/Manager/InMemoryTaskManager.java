package Manager;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import Task.*;

public class InMemoryTaskManager implements TaskManager {
    protected int id = 1;
    protected Map<Integer, Task> tasksMap = new HashMap<>();
    protected HistoryManager historyManager = Managers.getDefaultHistory();



    @Override
    public void addTask(Task o) {
        if(!tasksMap.containsValue(o)) {
            while(tasksMap.containsKey(id)) {
                id++;
            }
            o.setId(id);
            tasksMap.put(id, o);
        }
    }


    @Override
    public void addTask(Epic epic, Subtask... subtasks) {
        for(Subtask subtask : subtasks){
            if (!epic.getSubtasksList().containsValue(subtask)) {
                id++;
                subtask.setSubtaskId(id);
                epic.addSubtask(subtask);
            }
        }
    }

    @Override
    public ArrayList<Task> getTasksList() {
        ArrayList<Task> tasksList = new ArrayList<>(tasksMap.values());
        for(Task task : tasksList){
            historyManager.add(task);
        }

        return tasksList;
    }

    @Override
    public void deleteAllTasks(){
        tasksMap.clear();
        System.out.println("Список задач очищен");
    }

    @Override
    public void deleteByIds(int... indexes){
        for(int i : indexes) {
            if(id > 1) {
                id--;
            }
            tasksMap.remove(i);
        }
    }

    @Override
    public Task getTask(int index){
        if(tasksMap.containsKey(index)) {
            historyManager.add(tasksMap.get(index));
            return tasksMap.get(index);
        } else {
            System.out.println("Такой задачи не существует");
            return null;
        }
    }

    @Override
    public Subtask getSubtask(Epic epic, int index){
        if(epic.getSubtasksList().containsKey(index)) {
            Subtask subtask = epic.getSubtasksList().get(index);
            historyManager.add(subtask);
            return subtask;
        } else {
            System.out.println("Такой задачи не существует");
            return null;
        }
    }

    @Override
    public HashMap<Integer, Subtask> getSubtasks(Epic epic){
        HashMap<Integer, Subtask> subtasksList = epic.getSubtasksList();
        for(Subtask subtask : subtasksList.values()){
            historyManager.add(subtask);
        }

        return subtasksList;
    }

    @Override
    public void updateTask(int index, Task task){
        deleteByIds(index);
        tasksMap.put(index, task);
    }

    @Override
    public Task updateInformation(Task task, String nameOrDescription, String info){

        if(task.getClass() == Task.class){
            if(nameOrDescription.equals("name")) {
                task.setName(info);
            } else if(nameOrDescription.equals("description")) {
                task.setDescription(info);
            }
        } else if(task.getClass() == Subtask.class){
            if(nameOrDescription.equals("name")) {
                ((Subtask)task).setSubtaskName(info);
                //Май бэд)
            } else if(nameOrDescription.equals("description")) {
                ((Subtask)task).setSubtaskDescription(info);
            }
        }

        return task;
    }

    @Override
    public String toString(){
        String result = "\n";
        for(int i : tasksMap.keySet()){
            result += "\n" + tasksMap.get(i);
        }
        return result;
    }

    @Override
    public void updateStatus(Task task){
        if(task.getClass() == Task.class && task.getStatus() == Status.NEW){
            task.setStatus(Status.DONE);
        } else if(task.getClass() == Subtask.class && ((Subtask)task).getSubtaskStatus() == Status.NEW) {
            ((Subtask)task).setSubtaskStatus(Status.DONE);
        } else if(task.getClass() == Epic.class) {
            ((Epic)task).checkStatus();
        } else {
            System.out.println("Такого статуса нет");
        }
    }

    @Override
    public String printHistory(){
        return historyManager.toString();
    }

    @Override
    public ArrayList<Task> getHistory(){
        return historyManager.getHistory();
    }
}