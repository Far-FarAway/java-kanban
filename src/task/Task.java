package task;

import java.util.Objects;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    public final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    protected String name;
    protected String description;
    protected int id;
    protected Status status;
    protected Duration duration;
    protected LocalDateTime startTime;

    public Task(String name, String description, int minutes, String startTime) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        duration = Duration.ofMinutes(minutes);
        this.startTime = LocalDateTime.parse(startTime, FORMATTER);
    }

    public Task(String name, String description, Status status, int minutes, String startTime) {
        this.name = name;
        this.description = description;
        this.status = status;
        duration = Duration.ofMinutes(minutes);
        this.startTime = LocalDateTime.parse(startTime, FORMATTER);
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration.toMinutes());
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStatus(Status status) {
        if (status != Status.DONE && status != Status.IN_PROGRESS && status != Status.NEW) {
            System.out.println("Такого статуса нет");
        } else {
            this.status = status;
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        String result = "\n\nID: " + id;

        result += name == null ? "":"\n" + name;
        result += description == null ? "":"\nОписание: " + description;

        if(startTime.getYear() == 1) {
            result += "";
        } else {
            result += "\nДата начала: " + startTime.format(FORMATTER);
            result += "\nДата окончания: " + getEndTime();
        }

        result += "\nСтатус: " + status + "\n";
        result += duration.toMinutes() == 0 ? "":"\nПродолжительность: " + duration.toMinutes();

        return result;
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
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description)
                && status == task.status;
    }
}
