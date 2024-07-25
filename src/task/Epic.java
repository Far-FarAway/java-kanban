package task;

import java.time.LocalDateTime;
import java.util.*;
import java.time.Duration;


public class Epic extends Task {
    /*private final static Comparator<Subtask> comparator = (sub1, sub2) -> {
        LocalDateTime timeSub1 = sub1.getStartTime();
        LocalDateTime timeSub2 = sub2.getStartTime();
        if (timeSub1.isAfter(timeSub2)) {
            return 3;
        } else if (timeSub1.isBefore(timeSub2)) {
            return -3;
        } else {
            return 0;
        }
    };*/
    protected Map<Integer, Subtask> subtasksMap = new HashMap<>();
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    protected Duration duration;

    public Epic(String name, String description) {
        super(name, description);
        findDurationAndStartEndTime();
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);
        findDurationAndStartEndTime();
    }

    private void findDurationAndStartEndTime() {
        if (!subtasksMap.isEmpty()) {
            LocalDateTime earliestTime = subtasksMap.values().stream().findFirst().get().getStartTime();
            LocalDateTime latestTime = subtasksMap.values().stream().findFirst().get().getStartTime();
            Duration duration = Duration.ofMinutes(0);
            for (Subtask sub : subtasksMap.values()) {
                LocalDateTime subStartTime = sub.getStartTime();
                LocalDateTime subEndTime = sub.getEndTime();
                if (subStartTime.isBefore(earliestTime)){
                    earliestTime = subStartTime;
                }
                if (subEndTime.isAfter(latestTime)){
                    latestTime = subEndTime;
                }
                duration.plus(sub.getDuration());
            }
            this.startTime = earliestTime;
            this.endTime = latestTime;
            this.duration = duration;
        } else {
            startTime = LocalDateTime.of(0, 0, 0, 0, 0);
            endTime = LocalDateTime.of(0, 0, 0, 0, 0);
            duration = Duration.ofMinutes(0);
        }
    }
/*

    private Duration findDuration() {
        long durationSum = subtasksMap.values().stream().map(Subtask::getDuration)
                .map(Duration::toMinutes).mapToLong(i -> i).sum();

        return Duration.ofMinutes(durationSum);
    }

    private LocalDateTime findStartTime() {
        Optional<Subtask> subOptional = subtasksMap.values().stream().min(comparator);
        return subOptional.orElseGet(Subtask::new).getStartTime();
    }

    private LocalDateTime findEndTime() {
        Optional<Subtask> subOptional = subtasksMap.values().stream().max(comparator);
        return subOptional.orElseGet(Subtask::new).getEndTime();
    }
*/

    public HashMap<Integer, Subtask> getSubtasksMap() {
        return new HashMap<>(subtasksMap);
    }

    public void addSubtask(int subId, Subtask subtask) {
        subtask.setId(subId);
        subtasksMap.put(subId, subtask);
    }

    @Override
    public String toString() {
        String result = "\n\nID: " + id;
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

        result += "\nСтатус: " + status;

        return result;
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