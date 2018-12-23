package br.com.utils;

import br.com.entity.Location;

public class LocationUtils {
	public static Location updateLocationProperties(Location locationUpdate, Location location) {
		location.setDateTime(locationUpdate.getDateTime());
		location.setLatitude(locationUpdate.getLatitude());
		location.setLongitude(locationUpdate.getLongitude());
		location.setTrackerId(locationUpdate.getTrackerId());
		return location;
	}
}
