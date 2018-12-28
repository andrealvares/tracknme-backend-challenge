package com.desafio.desafiobackend.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Location implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date dateTime;

    @ManyToOne
    @JoinColumn
    private Tracker trackerId;


    public Location() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Tracker getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(Tracker trackerId) {
        this.trackerId = trackerId;
    }
}


