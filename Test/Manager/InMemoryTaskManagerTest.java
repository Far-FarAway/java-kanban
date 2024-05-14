package Manager;

import Task.*;
import Manager.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class InMemoryTaskManagerTest {
    HistoryManager historyManager;
    TaskManager manager;

    Task task1 = new Task("Тренировка", "Вот так вот");
    Task task2 = new Task("Why are we still here", "Just to suffer");
    Epic epic1 = new Epic("Попасть в исекай", "Почему бы и нет");
    Subtask epic1Subtask1 = new Subtask("Найти белый грузовичок", "Самый надежный способ");

    Epic epic2 = new Epic("Сходить погулять", "Нет, дома ты не погуляешь");
    Subtask epic2Subtask1 = new Subtask("Найти мотивацию", "Чем больше, тем лучше");
    Subtask epic2Subtask2 = new Subtask("Выйти погулять", "Это ножками делается");


    @BeforeEach
    public void beforeEach(){
        historyManager = Managers.getDefaultHistory();
        manager = Managers.getDefault();
    }

    @Test
    public void shouldAddAndGiveTask(){
        manager.addTask(task1);

        assertEquals(task1, manager.getTask(task1.getId()));
    }

    @Test
    public void shouldAddAndGiveEpicWithSubtask(){
        manager.addTask(epic1);
        epic1.addSubtask(epic1Subtask1);

        manager.addTask(epic2, epic2Subtask1, epic2Subtask2);

        assertEquals(epic1, manager.getTask(epic1.getId()));
        assertEquals(epic1Subtask1, manager.getSubtask(epic1, epic1Subtask1.getSubtaskId()));

        assertEquals(epic2Subtask1, manager.getSubtask(epic2, epic2Subtask1.getSubtaskId()));
        assertEquals(epic2Subtask2, manager.getSubtask(epic2, epic2Subtask2.getSubtaskId()));

    }

    @Test
    public void shouldAddAndGiveSubtask(){
        manager.addTask(epic1);
        epic1.addSubtask(epic1Subtask1);

        assertEquals(epic1Subtask1, manager.getSubtask(epic1, epic1Subtask1.getSubtaskId()));
    }

    @Test
    public void shouldGiveSubtasksMap(){
        manager.addTask(epic2, epic2Subtask1, epic2Subtask2);

        Map<Integer, Subtask> map = manager.getSubtasks(epic2);

        assertEquals(epic2Subtask1, map.get(epic2Subtask1.getSubtaskId()));
        assertEquals(epic2Subtask2, map.get(epic2Subtask2.getSubtaskId()));
    }

    @Test
    public void shouldUpdateStatusInformationAndTask(){
        manager.addTask(task1);
        manager.addTask(task2);

        int index = task1.getId();
        manager.updateTask(index, task2);
        manager.addTask(task1);

        assertEquals(task2, manager.getTask(index));

        manager.updateStatus(task1);

        assertEquals(Status.DONE, task1.getStatus());

        String name = "What can I saaay except";
        String description = "You're weeelcome";
        manager.updateInformation(task2, "name", "What can I saaay except");
        manager.updateInformation(task2, "description", "You're weeelcome");

        assertEquals(name, task2.getName());
        assertEquals(description, task2.getDescription());
    }

    @Test
    public void shouldDeleteAllTasks(){
        manager.addTask(task1);
        manager.addTask(task2);
        manager.addTask(epic1, epic1Subtask1);
        manager.addTask(epic2, epic2Subtask1, epic2Subtask2);

        manager.deleteAllTasks();

        assertEquals(0, manager.getTasksList().size());
    }

    @Test
    public void shouldGiveNullWithNonexistentTask(){
        assertNull(manager.getTask(task1.getId()));
        assertNull(manager.getSubtask(epic1, -3));
        assertNull(manager.getSubtasks(epic1));
    }
}
