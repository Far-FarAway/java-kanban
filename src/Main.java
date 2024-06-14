import Manager.*;
import task.*;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager manager = new InMemoryTaskManager();

        Task task1 = new Task("Тренировка", "Вот так вот");
        Task task2 = new Task("Вымыть кота", "Найти и запихать в ванну");
        Task task3 = new Task("Найти планету сокровищ", "Нам нужен парень с большими задатками");
        Epic epic1 = new Epic("Попасть в исекай", "Почему бы и нет");
        Subtask epic1Subtask1 = new Subtask("Найти белый грузовичок", "Самый надежный способ");
        Epic epic2 = new Epic("Сходить погулять", "Нет, дома ты не погуляешь");
        Subtask epic2Subtask1 = new Subtask("Найти мотивацию", "Чем больше, тем лучше");
        Subtask epic2Subtask2 = new Subtask("Выйти погулять", "Это ножками делается");

        manager.addTask(task1);
        manager.addTask(task1);
        manager.addTask(task2);
        manager.addTask(epic1);
        manager.addTask(epic1, epic1Subtask1);
        manager.addTask(epic2);
        manager.addTask(epic2, epic2Subtask1);
        manager.addTask(epic2, epic2Subtask1, epic2Subtask2);

        System.out.println(manager);

        System.out.println("\nУдаление задач");
        manager.deleteByIds(2,3);

        System.out.println("Обновление статусов");
        manager.updateStatus(task1);
        manager.updateStatus(epic2Subtask1);
        manager.updateStatus(epic2);

        System.out.println(manager);

        System.out.println("\nДобавление удаленных и новых задач ");
        manager.addTask(epic1);
        manager.addTask(task2);
        manager.addTask(task3);

        System.out.println(manager);

        System.out.println("Получение списка задач\n" + manager.getTasksList());

        System.out.println("Получение определенной задачи\n" + manager.getTask(2));

        System.out.println("Получение подзадач эпика\n" + manager.getSubtasks(epic2));

        System.out.println("Обновление задачи\n");
        manager.updateTask(3, manager.updateInformation(manager.getTask(1), "description",
                "Он прячется в шкафу"));

        System.out.println(manager);

        manager.deleteAllTasks();

        System.out.println(manager);
    }
}
