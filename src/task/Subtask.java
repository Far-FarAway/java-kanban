package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Subtask extends Task {
    protected String name;
    protected Status status;
    protected String description;
    protected Duration duration;
    protected LocalDateTime startTime;

    public Subtask(String name, String description, int minutes, String startTime) {
        super("", "", -3, "01.01.0001 00:00");
        this.description = description;
        this.name = name;
        status = Status.NEW;
        duration = Duration.ofMinutes(minutes);
        this.startTime = LocalDateTime.parse(startTime, FORMATTER);
    }

    public Subtask(String name, String description, Status status, int minutes, String startTime) {
        super("", "", -3, "01.01.0001 00:00");
        this.name = name;
        this.description = description;
        this.status = status;
        duration = Duration.ofMinutes(minutes);
        this.startTime = LocalDateTime.parse(startTime, FORMATTER);
    }

    public Subtask() {
        super("", "", -3, "01.01.0001 00:00");
        this.startTime = LocalDateTime.of(1,1,1, 0, 0);
        this.duration = Duration.ofMinutes(0);
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration.toMinutes());
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public Status getSubtaskStatus() {
        return status;
    }

    public void setSubtaskStatus(Status status) {
        if (status != Status.DONE && status != Status.IN_PROGRESS && status != Status.NEW) {
            System.out.println("Такого статуса нет");
        } else {
            this.status = status;
        }
    }

    public void setSubtaskName(String name) {
        this.name = name;
    }

    public String getSubtaskName() {
        return name;
    }

    public void setSubtaskDescription(String description) {
        this.description = description;
    }

    public String getSubtaskDescription() {
        return description;
    }

    @Override
    public String toString() {
        String result = "\nID: " + id;
        if (name == null) {
            result += "";
        } else {
            result += "\n" + name;
        }

        if (description == null) {
            result += "";
        } else {
            result += "\nОписание: " + description;
        }

        return result + "\nСтатус:" + status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, status);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Subtask sub = (Subtask) o;
        return Objects.equals(sub.name, this.name) && Objects.equals(sub.status, this.status);
    }
}