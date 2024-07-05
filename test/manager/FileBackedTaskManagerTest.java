package manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import task.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    String filename = "src/saveFile/savedTasks.txt";
    TaskManager manager;
    @BeforeEach
    public void beforeEach(){
        manager = Managers.getDefaultSave(filename);
    }

    @Test
    public void shouldFillFileAfterAddedTasks() {

        Task task1 = new Task("Тренировка", "Вот так вот");
        Epic epic1 = new Epic("Попасть в исекай", "Почему бы и нет");
        Subtask epic1Subtask1 = new Subtask("Найти белый грузовичок", "Самый надежный способ");

        manager.addTask(task1);
        manager.addTask(epic1, epic1Subtask1);

        String[] givenTypes = new String[3];

        try (BufferedReader br = new BufferedReader(new FileReader("src/saveFile/savedTasks.txt"))) {
            br.readLine();
            while (br.ready()) {
                String[] line = br.readLine().split(",");
                givenTypes[Integer.parseInt(line[0]) - 1] = line[1];
            }
        } catch (IOException ex) {
            System.out.println("Исключение во время теста на заполнение файла");
            ex.printStackTrace();
        }

        String[] correctTypes = new String[]{"TASK", "EPIC", "SUBTASK"};

        assertArrayEquals(correctTypes, givenTypes);
    }

    @Test
    public void shouldFillTasksMapFromFile(){
        ((FileBackedTaskManager)manager).loadFromFile();
        int[] givenIds = new int[3];

        for(Task task : manager.getTasksList()){
            givenIds[task.getId()-1] = task.getId();
        }

        int[] correctIds = new int[] {1, 2, 3};

        assertArrayEquals(correctIds, givenIds);
    }
}