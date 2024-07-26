package manager;

import task.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class InMemoryTaskManagerTest {
    TaskManager manager;

    Task task1 = new Task("Тренировка", "Вот так вот", 90, "13.10.2024 12:00");
    Task task2 = new Task("Вымыть кота", "Найти и запихать в ванну", 240,
            "14.10.2024 08:00");    Epic epic1 = new Epic("Попасть в исекай", "Почему бы и нет");
    Subtask epic1Subtask1 = new Subtask("Найти белый грузовичок", "Самый надежный способ",
            10, "31.12.2024 23:50");
    Epic epic2 = new Epic("Сходить погулять", "Нет, дома ты не погуляешь");
    Subtask epic2Subtask1 = new Subtask("Найти мотивацию", "Чем больше, тем лучше",
            360, "05.01.2025 12:00");
    Subtask epic2Subtask2 = new Subtask("Выйти погулять", "Это ножками делается",
            3, "05.01.2025 18:00");


    @BeforeEach
    public void beforeEach() {
        manager = Managers.getDefault();
    }

    @Test
    public void shouldAddAndGiveTask() {
        manager.addTask(task1);

        assertEquals(task1, manager.getTask(task1.getId()));
    }

    @Test
    public void shouldAddAndGiveEpicWithSubtask() {
        manager.addTask(epic1, epic1Subtask1);

        manager.addTask(epic2, epic2Subtask1);
        manager.addTask(epic2, epic2Subtask2);

        assertEquals(epic1, manager.getTask(epic1.getId()));
        assertEquals(epic1Subtask1, manager.getSubtask(epic1, epic1Subtask1.getId()));

        assertEquals(epic2Subtask1, manager.getSubtask(epic2, epic2Subtask1.getId()));
        assertEquals(epic2Subtask2, manager.getSubtask(epic2, epic2Subtask2.getId()));

    }

    @Test
    public void shouldAddAndGiveSubtask() {
        manager.addTask(epic1, epic1Subtask1);

        assertEquals(epic1Subtask1, manager.getSubtask(epic1, epic1Subtask1.getId()));
    }

    @Test
    public void shouldGiveSubtasksMap() {
        manager.addTask(epic2, epic2Subtask1, epic2Subtask2);

        Map<Integer, Subtask> map = manager.getSubtasks(epic2);

        assertEquals(epic2Subtask1, map.get(epic2Subtask1.getId()));
        assertEquals(epic2Subtask2, map.get(epic2Subtask2.getId()));
    }

    @Test
    public void shouldUpdateStatusInformationAndTask() {
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
    public void shouldUpdateEpicAndSubtaskStatus() {
        manager.addTask(epic2, epic2Subtask1, epic2Subtask2);

        manager.updateStatus(epic2Subtask1);
        manager.updateStatus(epic2);

        assertEquals(Status.DONE, epic2Subtask1.getSubtaskStatus());
        assertEquals(Status.IN_PROGRESS, epic2.getStatus());

        manager.updateStatus(epic2Subtask2);
        manager.updateStatus(epic2);

        assertEquals(Status.DONE, epic2.getStatus());
    }


    @Test
    public void shouldDeleteAllTasks() {
        manager.addTask(task1);
        manager.addTask(task2);
        manager.addTask(epic1, epic1Subtask1);
        manager.addTask(epic2, epic2Subtask1, epic2Subtask2);

        manager.deleteAllTasks();

        assertEquals(0, manager.getTasksList().size());
    }

    @Test
    public void shouldGiveNullWithNonexistentTask() {
        assertNull(manager.getTask(task1.getId()));
        assertNull(manager.getSubtask(epic1, -3));
        assertNull(manager.getSubtasks(epic1));
    }

    @Test
    public void shouldGiveTrueWithSimilarTaskId() {
        int id = task1.getId();
        assertEquals(id, task1.getId());
    }

    @Test
    public void shouldGiveTrueWithSimilarSubtaskId() {
        int id = epic1Subtask1.getId();
        assertEquals(id, epic1Subtask1.getId());
    }

    @Test
    public void shouldGiveReadyToWorkManagers() {
        manager.addTask(task1);
        manager.getTask(task1.getId());

        HistoryManager historyManager = Managers.getDefaultHistory();

        historyManager.add(task2);

        assertEquals(task1, manager.getHistory().getFirst());
        assertEquals(task2, historyManager.getHistory().getFirst());
    }

    @Test
    public void shouldNotChangeInformationWhenAddNewTaskAndAddToHistory() {
        String name = task1.getName();
        String description = task1.getDescription();

        manager.addTask(task1);
        manager.getTask(task1.getId());

        assertEquals(name, task1.getName());
        assertEquals(description, task1.getDescription());
    }

}
