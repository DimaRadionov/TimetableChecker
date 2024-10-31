package com.example.demo.data;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "curriculum")
@XmlAccessorType(XmlAccessType.FIELD)
public class Curriculum {

    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "lecture")
    private List<String> lectureId;

}
