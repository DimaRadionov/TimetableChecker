package com.example.demo.data;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "lecture")
@XmlAccessorType(XmlAccessType.FIELD)
public class Lecture {

      @XmlElement(name = "id")
      private String id;

      @XmlElement(name = "name")
      private String name;

      @XmlElementWrapper(name = "roombookings")
      @XmlElement(name = "booking")
      private List<Booking> roomBookings;

}
