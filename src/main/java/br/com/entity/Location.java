package br.com.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * 
	{
	    "id": 123456,
	    "trackerId": 654321,
	    "dateTime": "2017-10-12T21:34:15",
	    "latitude": -23.9626767,
	    "longitude": -46.3884785
	}
 */

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})	
public class Location{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private long trackerId;
	private String dateTime;
	private float latitude;
	private float longitude;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTrackerId() {
		return trackerId;
	}
	public void setTrackerId(long trackerId) {
		this.trackerId = trackerId;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
}
