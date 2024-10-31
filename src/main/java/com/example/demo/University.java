package com.example.demo;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.text.DateFormat;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name="university")
@XmlAccessorType(XmlAccessType.FIELD)
public class University {

    @XmlElementWrapper(name = "lectures")
    @XmlElement(name = "lecture")
    private List<Lecture> lectures;

    @XmlElementWrapper(name = "curricula")
    @XmlElement(name = "curriculum")
    private List<Curriculum> curricula;

}
