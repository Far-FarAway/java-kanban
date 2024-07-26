package task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {
    protected int epicId;
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

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration.toMinutes());
    }

    @Override
    public void setStartTime(String time){
        this.startTime = LocalDateTime.parse(time, FORMATTER);

    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public void setDuration(int duration) {
        this.duration = Duration.ofMinutes(duration);

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

    public void setEpicId(int id) {
        this.epicId = id;
    }

    public int getEpicId(){
        return epicId;
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
        StringBuilder result = new StringBuilder("\n\tID: " + id);

        result.append(name == null ? "":"\n\t" + name);
        result.append(description == null ? "":"\n\tОписание: " + description);
        result.append("\n\tСтатус: ").append(status);

        if(startTime.getYear() != 1) {
            result.append("\n\tДата начала: ").append(startTime.format(FORMATTER));
            result.append("\n\tДата окончания: ").append(getEndTime().format(FORMATTER));
        }

        result.append(duration.toMinutes() == 0 ? "":"\n\tПродолжительность: " + duration.toMinutes());

        return result.toString();
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