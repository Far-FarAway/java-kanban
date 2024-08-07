package manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import task.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    File tempFile;
    TaskManager manager;

    @BeforeEach
    public void beforeEach() throws IOException {
        tempFile = File.createTempFile("test", ".txt", new File("src/saveFile"));
        manager = Managers.getDefaultSave(tempFile.getPath());
    }

    @AfterEach
    public void afterEach() {
        tempFile.deleteOnExit();
    }

    @Test
    public void shouldFillFileAfterAddedTasks() {

        Task task1 = new Task("Тренировка", "Вот так вот", 90, "13.10.2024 12:00");
        Epic epic1 = new Epic("Попасть в исекай", "Почему бы и нет");
        Subtask epic1Subtask1 = new Subtask("Найти белый грузовичок", "Самый надежный способ",
                10, "31.12.2024 23:50");
        manager.addTask(task1);
        manager.addTask(epic1, epic1Subtask1);

        String[] givenTypes = new String[3];

        try (BufferedReader br = new BufferedReader(new FileReader(tempFile.getPath()))) {
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
    public void shouldFillTasksMapFromFile() throws IOException {
        try (FileWriter fw = new FileWriter(tempFile.getPath())) {
            fw.write("id,type,name,status,description,epic,startTime,duration\n" +
                    "1,TASK,Тренировка,NEW,Вот так вот,,13.10.2024 12:00,90\n" +
                    "2,EPIC,Попасть в исекай,NEW,Почему бы и нет,,31.12.2024 23:50,10\n" +
                    "3,SUBTASK,Найти белый грузовичок,NEW,Самый надежный способ,2,31.12.2024 23:50,10");
        }

        ((FileBackedTaskManager) manager).loadFromFile();
        int[] givenIds = new int[3];

        for (Task task : manager.getTasksList()) {
            givenIds[task.getId() - 1] = task.getId();

            if (task instanceof Epic epic) {
                for (Subtask subtask : epic.getSubtasksMap().values()) {
                    givenIds[subtask.getId() - 1] = subtask.getId();
                }
            }
        }

        int[] correctIds = new int[]{1, 2, 3};

        assertArrayEquals(correctIds, givenIds);
    }
}