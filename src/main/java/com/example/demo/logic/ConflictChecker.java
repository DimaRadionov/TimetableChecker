package com.example.demo.logic;

import com.example.demo.data.Booking;
import com.example.demo.data.Curriculum;
import com.example.demo.data.Lecture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConflictChecker {
    private List<Lecture> lectures;
    private List<Curriculum> curricula;

    public ConflictChecker(List<Lecture> lectures, List<Curriculum> curricula) {
        this.lectures = lectures;
        this.curricula = curricula;
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
        Map<String, List<BookingRecord>> roomBookings = new HashMap<>();

        for (Lecture lecture : lectures) {
            for (Booking booking : lecture.getRoomBookings()) {
                String key = booking.getRoom() + "-" + booking.getWeekday();

                for (BookingRecord existingRecord : roomBookings.getOrDefault(key, new ArrayList<>())) {
                    if (isTimeOverlap(booking, existingRecord.booking)) {
                        conflicts.add("Room conflict between lecture " + lecture.getId() +
                                " and lecture " + existingRecord.lectureId +
                                " in " + booking.getRoom() +
                                " on " + booking.getWeekday());
                    }
                }

                roomBookings.putIfAbsent(key, new ArrayList<>());
                roomBookings.get(key).add(new BookingRecord(lecture.getId(), booking));
            }
        }
        return conflicts;
    }

    private List<String> checkCurricularConflicts() {
        List<String> conflicts = new ArrayList<>();

        for (Curriculum curriculum : curricula) {
            Map<String, List<BookingRecord>> curriculumBookings = new HashMap<>();

            for (String lectureId : curriculum.getLectureId()) {
                Lecture lecture = findLectureById(lectureId);
                if (lecture == null) continue;

                for (Booking booking : lecture.getRoomBookings()) {
                    String key = booking.getWeekday();

                    for (BookingRecord existingRecord : curriculumBookings.getOrDefault(key, new ArrayList<>())) {
                        if (isTimeOverlap(booking, existingRecord.booking)) {
                            conflicts.add("Curricular conflict in " + curriculum.getName() +
                                    " between lecture " + lecture.getId() +
                                    " and lecture " + existingRecord.lectureId +
                                    " on " + booking.getWeekday() +
                                    " at " + booking.getStartTime());
                        }
                    }

                    curriculumBookings.putIfAbsent(key, new ArrayList<>());
                    curriculumBookings.get(key).add(new BookingRecord(lecture.getId(), booking));
                }
            }
        }

        return conflicts;
    }

    private Lecture findLectureById(String id) {
        return lectures.stream().filter(lecture -> lecture.getId().equals(id)).findFirst().orElse(null);
    }

    private boolean isTimeOverlap(Booking b1, Booking b2) {
        return b1.getEndTime().compareTo(b2.getStartTime()) > 0 &&
                b1.getStartTime().compareTo(b2.getEndTime()) < 0;
    }

    private static class BookingRecord {
        String lectureId;
        Booking booking;

        BookingRecord(String lectureId, Booking booking) {
            this.lectureId = lectureId;
            this.booking = booking;
        }
    }

}