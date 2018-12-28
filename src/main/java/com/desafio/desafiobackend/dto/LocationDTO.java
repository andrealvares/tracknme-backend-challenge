package com.desafio.desafiobackend.dto;

import com.desafio.desafiobackend.domain.Location;

import java.util.Date;

public class LocationDTO {

    private Integer id;
    private Date dateTime;
    private String name;
    private Integer trackerId;
    private double latitude;
    private double longitude;

    public LocationDTO() {

    }

    public LocationDTO(Location location) {
        this.id = location.getId();
        this.dateTime = location.getDateTime();
        this.name = location.getTrackerId().getName();
        this.trackerId = location.getTrackerId().getTrackerId();
        this.latitude = location.getTrackerId().getLatitude();
        this.longitude = location.getTrackerId().getLongitude();

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
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
}
