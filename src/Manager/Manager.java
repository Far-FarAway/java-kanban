package Manager;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import Task.*;

public class Manager {
    protected int identifier = 1;
    public Map<Integer, Task> tasksMap = new HashMap<>();

    public void addTask(Task o) {
        if(!tasksMap.containsValue(o)) {
            while(tasksMap.containsKey(identifier)) {
                identifier++;
            }
            tasksMap.put(identifier, o);
        }
    }


    public void addTask(Epic epic, Subtask... subtasks) {
        for(Subtask subtask : subtasks){
            if (!epic.getSubtasksList().contains(subtask)) {
                epic.addSubtask(subtask);
            }
        }
    }

    public ArrayList<Object> getTasksList() {
        ArrayList<Object> tasksList = new ArrayList<>(tasksMap.values());
        return tasksList;
    }

    public void deleteAllTasks(){
        tasksMap.clear();
        System.out.println("Список задач очищен");
    }

    public void deleteByIds(int... indexes){
        for(int i : indexes) {
            if(identifier > 1) {
                identifier--;
            }
            tasksMap.remove(i);
        }
    }

    public Task getTask(int index){
        return tasksMap.get(index);
    }

    public List<Subtask> getSubtasks(Epic epic){
        List<Subtask> list = new ArrayList<>(epic.getSubtasksList());
        return list;
    }

    public void updateTask(int index, Task task){
        deleteByIds(index);
        tasksMap.put(index, task);
    }

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
                System.out.println("fadfadfadsafdsadasd");
            } else if(nameOrDescription.equals("description")) {
                ((Subtask)task).setSubtaskDescription(info);
            }
        }

        return task;
    }

    @Override
    public String toString(){
        String result = "";
        for(int i : tasksMap.keySet()){
            result += "\t\t" + i + "\t\t\n" + tasksMap.get(i);
        }
        return result;
    }

    public void updateStatus(Task task){
        if(task.getClass() == Task.class && ((Task)task).getStatus() == Status.NEW){
            task.setStatus(Status.DONE);
        } else if(task.getClass() == Subtask.class && ((Subtask)task).getSubtaskStatus() == Status.NEW) {
            ((Subtask)task).setSubtaskStatus(Status.DONE);
        } else if(task.getClass() == Epic.class) {
            ((Epic)task).checkStatus();
        } else {
            System.out.println("Такого статуса нет");
        }
    }
}