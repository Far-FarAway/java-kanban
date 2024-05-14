package Manager;

import Task.*;
import Manager.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InMemoryTaskManagerTest {
    HistoryManager historyManager;
    TaskManager manager;

    @BeforeEach
    public void beforeEach(){
        historyManager = Managers.getDefaultHistory();
        manager = Managers.getDefault();
    }

    @Test
    public void shouldAddAndGiveTask(){
        Task task1 = new Task("Тренировка", "Вот так вот");

        manager.addTask(task1);

        assertEquals(task1, manager.getTask(task1.getId()));
    }

    public void shouldAddAndGiveEpicWithSubtask(){
        TaskManager manager = Managers.getDefault();

        Epic epic1 = new Epic("Попасть в исекай", "Почему бы и нет");
        Subtask epic1Subtask1 = new Subtask("Найти белый грузовичок", "Самый надежный способ");

        Epic epic2 = new Epic("Сходить погулять", "Нет, дома ты не погуляешь");
        Subtask epic2Subtask1 = new Subtask("Найти мотивацию", "Чем больше, тем лучше");
        Subtask epic2Subtask2 = new Subtask("Выйти погулять", "Это ножками делается");


        manager.addTask(epic1);
        epic1.addSubtask(epic1Subtask1);
        
        manager.addTask(epic2, epic2Subtask1, epic2Subtask2);

        assertEquals(epic1, manager.getTask(epic1.getId()));
        assertEquals(epic1Subtask1, manager.getSubtask(epic1, epic1Subtask1.getSubtaskId()));

        assertEquals(epic2Subtask1, manager.getSubtask(epic2, epic2Subtask1.getSubtaskId()));
        assertEquals(epic2Subtask2, manager.getSubtask(epic2, epic2Subtask2.getSubtaskId()));

    }

    public void shouldAddAndGiveSubtask(){
        TaskManager manager = Managers.getDefault();

        Epic epic1 = new Epic("Попасть в исекай", "Почему бы и нет");
        Subtask epic1Subtask1 = new Subtask("Найти белый грузовичок", "Самый надежный способ");

        manager.addTask(epic1);
        epic1.addSubtask(epic1Subtask1);

        assertEquals(epic1Subtask1, manager.getSubtask(epic1, epic1Subtask1.getSubtaskId()));
    }


}
