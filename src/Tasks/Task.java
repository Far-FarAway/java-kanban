package Tasks;

import java.util.Objects;
import java.util.Random;


public class Task {
    protected String name;
    protected String description;
    protected final int id;
    protected Statuses status;
    protected Random random = new Random();

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Statuses.NEW;
        this.id = hashCode();
    }

    @Override
    public String toString() {
        String result = "ID: " + id;
        if (name == null){ result += ""; }
        else { result += "\n" + name; }

        if (description == null) { result += ""; }
        else { result += "\nОписание: " + description; }

        return result + "\nСтатус: " + status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status;
    }

    public void setStatus(Statuses status) {
        if (status != Statuses.DONE && status != Statuses.IN_PROGRESS && status != Statuses.NEW) {
            System.out.println("Такого статуса нет");
        } else {
            this.status = status;
        }
    }

    public String getName(){
        return name;
    }
}
