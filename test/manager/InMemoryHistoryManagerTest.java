package manager;

import task.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    TaskManager manager;

    Task task1 = new Task("Тренировка", "Вот так вот", 90, "13.10.2024 12:00");
    Epic epic1 = new Epic("Попасть в исекай", "Почему бы и нет");
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

        manager.addTask(task1);
        manager.addTask(epic1, epic1Subtask1);
        manager.addTask(epic2, epic2Subtask1);
        manager.addTask(epic2, epic2Subtask2);
    }

    @Test
    public void shouldAddInHistory() {
        manager.getTask(task1.getId());
        manager.getTask(epic1.getId());
        manager.getSubtask(epic1, epic1Subtask1.getId());
        manager.getSubtasks(epic2);

        assertEquals(5, manager.getHistory().size());
    }

    @Test
    public void shouldGiveCorrectHistory() {
        manager.getTask(task1.getId());
        manager.getSubtask(epic1, epic1Subtask1.getId());
        manager.getSubtasks(epic2);

        assertEquals(epic2Subtask2, manager.getHistory().getFirst());
        assertEquals(task1, manager.getHistory().getLast());
    }

    @Test
    public void shouldNotContainsDuplicate() {
        manager.getTask(task1.getId());
        manager.getTask(epic1.getId());
        manager.getTask(task1.getId());

        assertEquals(2, manager.getHistory().size());
    }
}