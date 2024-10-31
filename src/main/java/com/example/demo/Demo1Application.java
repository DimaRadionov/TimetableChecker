package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;


public class Demo1Application {

    public static void main(String[] args) throws JAXBException {
      //  JAXBContext jc = JAXBContext.newInstance(University.class);
////
      //  Unmarshaller unmarshaller = jc.createUnmarshaller();
      //  University university = (University) unmarshaller.unmarshal(new File("timetable.xml"));
////
      //  Marshaller marshaller = jc.createMarshaller();
      //  marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
     //   System.out.println("Lectures:");
     //   for (Lecture lecture : university.getLectures()) {
     //       System.out.println("Lecture ID: " + lecture.getId());
     //       System.out.println("Name: " + lecture.getName());
     //       System.out.println("Room Bookings:");
     //       for (Booking booking : lecture.getRoomBookings()) {
     //           System.out.println("  Room: " + booking.getRoom());
     //           System.out.println("  Weekday: " + booking.getWeekday());
     //           System.out.println("  Start Time: " + booking.getStartTime());
     //           System.out.println("  End Time: " + booking.getEndTime());
     //       }
     //   }
        List<Lecture> lectures = loadLecturesFromXML();
        List<Curriculum> curricula = loadCurriculaFromXML();

        ConflictChecker checker = new ConflictChecker(lectures, curricula);
        checker.checkConflicts();

    }

    private static University loadUniversityFromXML(String filePath) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(University.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (University) unmarshaller.unmarshal(new File(filePath));
    }

    private static List<Lecture> loadLecturesFromXML() throws JAXBException {
        return loadUniversityFromXML("timetable.xml").getLectures();
    }

    private static List<Curriculum> loadCurriculaFromXML() throws JAXBException {
        return loadUniversityFromXML("timetable.xml").getCurricula();
    }
}
