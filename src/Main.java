import manager.*;
import task.*;

public class Main {
    public static void main(String[] args) {
        FileBackedTaskManager manager = new FileBackedTaskManager("src/saveFile/savedTasks.txt");

        Task task1 = new Task("Тренировка", "Вот так вот", 90, "13.10.2024 12:00");
        Task task2 = new Task("Вымыть кота", "Найти и запихать в ванну", 240,
                "14.10.2024 08:00");
        Task task3 = new Task("Найти планету сокровищ", "Нам нужен парень с великими задатками",
                30, "11.10.2024 15:00");
        Epic epic1 = new Epic("Попасть в исекай", "Почему бы и нет");
        Subtask epic1Subtask1 = new Subtask("Найти белый грузовичок", "Самый надежный способ",
                10, "31.12.2024 23:50");
        Epic epic2 = new Epic("Сходить погулять", "Нет, дома ты не погуляешь");
        Subtask epic2Subtask1 = new Subtask("Найти мотивацию", "Чем больше, тем лучше",
                360, "05.01.2025 12:00");
        Subtask epic2Subtask2 = new Subtask("Выйти погулять", "Это ножками делается",
                3, "05.01.2025 18:00");

        manager.addTask(task1);
        manager.addTask(task1);
        manager.addTask(task2);
        manager.addTask(task3);
        manager.addTask(epic1);
        manager.addTask(epic1, epic1Subtask1);
        manager.addTask(epic2);
        manager.addTask(epic2, epic2Subtask2);
        manager.addTask(epic2, epic2Subtask1);

        System.out.println("\n");
        manager.getPrioritizedTasks();
    }
}
