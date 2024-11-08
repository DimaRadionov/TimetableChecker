package com.example.demo;

import com.example.demo.data.Curriculum;
import com.example.demo.data.Lecture;
import com.example.demo.data.University;
import com.example.demo.logic.ConflictChecker;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;


public class Main {

    public static void main(String[] args) throws JAXBException {
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
