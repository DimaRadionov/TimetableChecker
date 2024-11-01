package com.example.demo.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeSlot {
    private String lectureId;
    private String startTime;
    private String endTime;

    public TimeSlot(String lectureId, String startTime, String endTime) {
        this.lectureId = lectureId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean overlaps(TimeSlot other) {
        return this.endTime.compareTo(other.startTime) > 0 &&
                this.startTime.compareTo(other.endTime) < 0;
    }
}

