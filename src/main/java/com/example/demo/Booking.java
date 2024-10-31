package com.example.demo;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.DateFormat;

@Getter
@Setter
@XmlRootElement(name = "booking")
@XmlAccessorType(XmlAccessType.FIELD)
public class Booking {

    @XmlElement(name = "room")
    private String room;
    @XmlElement(name = "weekday")
    private String weekday;
    @XmlElement(name = "startTime")
    private String startTime;
    @XmlElement(name = "endTime")
    private String endTime;

}
