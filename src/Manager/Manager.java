package Manager;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import Tasks.*;

public class Manager {
    protected int identifier = 1;
    public Map<Integer, Object> tasksMap = new HashMap<>();

    public void addTask(Object o) {
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

    public List getTasksList() {
        List<Object> tasksList = new ArrayList<>(tasksMap.values());
        return tasksList;
    }

    public void deleteAllTask(){
        for (int i : tasksMap.keySet()) {
            tasksMap.remove(i);
        }
    }

    public void deleteTask(int... indexes){
        for(int i : indexes) {
            identifier--;
            tasksMap.remove(i);
        }
    }

    public Object getTask(int index){
        return tasksMap.get(index);
    }

    public List<Subtask> getSubtasks(Epic epic){
        List<Subtask> list = new ArrayList<>(epic.getSubtasksList());
        return list;
    }

    public void updateTask(int index, Object o){
        deleteTask(index);
        tasksMap.put(index, o);
    }

    @Override
    public String toString(){
        String result = "";
        for(int i : tasksMap.keySet()){
            result += "\t\t" + i + "\t\t\n" + tasksMap.get(i);
        }
        return result;
    }

    public void updateStatus(Object o){
        if(o.getClass() == Task.class && ((Task)o).getStatus() == Statuses.NEW){
            ((Task)o).setStatus(Statuses.DONE);
        } else if(o.getClass() == Subtask.class && ((Subtask)o).getSubtaskStatus() == Statuses.NEW) {
            ((Subtask)o).setSubtaskStatus(Statuses.DONE);
        } else if(o.getClass() == Epic.class) {
            ((Epic)o).checkStatus();
        } else {
            System.out.println("Такого статуса нет");
        }
    }
}
