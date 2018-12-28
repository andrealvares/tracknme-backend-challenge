package com.desafio.desafiobackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tracker implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer trackerId;
    private String name="Meu Rastreador";
    private double latitude;
    private double longitude;

    @JsonIgnore
    @OneToMany(mappedBy = "trackerId")
    private List<Location> locations= new ArrayList<>();

    public Tracker() {

    }

    public Integer getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(Integer trackerId) {
        this.trackerId = trackerId;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
