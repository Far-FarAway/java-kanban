package task;

import java.time.LocalDateTime;
import java.util.*;
import java.time.Duration;


public class Epic extends Task {
    protected Map<Integer, Subtask> subtasksMap = new HashMap<>();
    protected LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description, 0, "01.01.0001 00:00");
        this.endTime = LocalDateTime.of(1,1,1,0,0);
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status, 0, "01.01.0001 00:00");
        this.endTime = LocalDateTime.of(1,1,1,0,0);
    }

    public void findDurationAndStartEndTime() {
        LocalDateTime earliestTime = subtasksMap.values().stream().findFirst().get().getStartTime();
        LocalDateTime latestTime = subtasksMap.values().stream().findFirst().get().getStartTime();
        for (Subtask sub : subtasksMap.values()) {
            LocalDateTime subStartTime = sub.getStartTime();
            LocalDateTime subEndTime = sub.getEndTime();
            if (subStartTime.isBefore(earliestTime)){
                earliestTime = subStartTime;
            }
            if (subEndTime.isAfter(latestTime)){
                latestTime = subEndTime;
            }
        }
        this.startTime = earliestTime;
        this.endTime = latestTime;
        this.duration = Duration.between(earliestTime, latestTime);

    }

    public HashMap<Integer, Subtask> getSubtasksMap() {
        return new HashMap<>(subtasksMap);
    }

    public void addSubtask(int subId, Subtask subtask) {
        subtask.setId(subId);
        subtasksMap.put(subId, subtask);
        subtask.setEpicId(id);
        findDurationAndStartEndTime();
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("\nID: " + id);

        result.append(name == null ? "":"\n" + name);
        result.append(description == null ? "":"\nОписание: " + description);
        result.append("\nСтатус: ").append(status);

        if(startTime.getYear() != 1) {
            result.append("\nДата начала: ").append(startTime.format(FORMATTER));
            result.append("\nДата окончания: ").append(endTime.format(FORMATTER));
        }

        result.append(duration.toMinutes() == 0 ? "":"\nПродолжительность: " + duration.toMinutes());

        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Epic epic = (Epic) o;
        return Objects.equals(epic.subtasksMap, this.subtasksMap);
    }

    public void checkStatus() {
        int count = 0;
        for (Subtask subtask : subtasksMap.values()) {
            if (subtask.getSubtaskStatus() == Status.DONE) {
                count++;
            }
        }

        if (count == subtasksMap.size()) {
            setStatus(Status.DONE);
        } else if (count > 0) {
            setStatus(Status.IN_PROGRESS);
        } else {
            setStatus(Status.NEW);
        }

    }
}