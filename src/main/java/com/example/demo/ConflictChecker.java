package com.example.demo;

import com.example.demo.data.Booking;
import com.example.demo.data.Curriculum;
import com.example.demo.data.Lecture;
import com.example.demo.data.TimeSlot;

import java.util.*;

public class ConflictChecker {
    private List<Lecture> lectures;
    private List<Curriculum> curricula;

    private Map<String, Map<String, List<TimeSlot>>> timeSlots;

    public ConflictChecker(List<Lecture> lectures, List<Curriculum> curricula) {
        this.lectures = lectures;
        this.curricula = curricula;
        this.timeSlots = new HashMap<>();
    }

    public void checkConflicts() {
        List<String> roomConflicts = checkRoomConflicts();
        List<String> curricularConflicts = checkCurricularConflicts();

        if (!roomConflicts.isEmpty()) {
            System.out.println("Room Conflicts:");
            roomConflicts.forEach(System.out::println);
        } else {
            System.out.println("No Room Conflicts.");
        }

        if (!curricularConflicts.isEmpty()) {
            System.out.println("Curricular Conflicts:");
            curricularConflicts.forEach(System.out::println);
        } else {
            System.out.println("No Curricular Conflicts.");
        }
    }

    private List<String> checkRoomConflicts() {
        List<String> conflicts = new ArrayList<>();

        for (Lecture lecture : lectures) {
            for (Booking booking : lecture.getRoomBookings()) {
                String roomKey = "room:" + booking.getRoom();
                String weekday = booking.getWeekday();
                TimeSlot newSlot = new TimeSlot(lecture.getId(), booking.getStartTime(), booking.getEndTime());

                conflicts.addAll(addAndCheckConflict(roomKey, weekday, newSlot, "Room"));
            }
        }
        return conflicts;
    }

    private List<String> checkCurricularConflicts() {
        List<String> conflicts = new ArrayList<>();

        for (Curriculum curriculum : curricula) {
            String curriculumKey = "curriculum:" + curriculum.getName();

            for (String lectureId : curriculum.getLectureId()) {
                Lecture lecture = findLectureById(lectureId);
                if (lecture == null) continue;

                for (Booking booking : lecture.getRoomBookings()) {
                    String weekday = booking.getWeekday();
                    TimeSlot newSlot = new TimeSlot(lecture.getId(), booking.getStartTime(), booking.getEndTime());

                    conflicts.addAll(addAndCheckConflict(curriculumKey, weekday, newSlot, "Curricular"));
                }
            }
        }

        return conflicts;
    }

    private List<String> addAndCheckConflict(String key, String weekday, TimeSlot newSlot, String conflictType) {
        List<String> conflicts = new ArrayList<>();

        timeSlots.putIfAbsent(key, new HashMap<>());
        Map<String, List<TimeSlot>> daySlots = timeSlots.get(key);

        daySlots.putIfAbsent(weekday, new ArrayList<>());
        List<TimeSlot> slots = daySlots.get(weekday);

        for (TimeSlot existingSlot : slots) {
            if (newSlot.overlaps(existingSlot)) {
                conflicts.add(conflictType + " conflict between lecture " +
                        newSlot.getLectureId() + " and lecture " +
                        existingSlot.getLectureId() + " on " + weekday +
                        " at " + newSlot.getStartTime());
            }
        }

        slots.add(newSlot);
        return conflicts;
    }

    private Lecture findLectureById(String id) {
        return lectures.stream().filter(lecture -> lecture.getId().equals(id)).findFirst().orElse(null);
    }
}
