package br.com.tracknme.ws.service;

import java.util.NavigableMap;

public interface LocationService<T> {

	NavigableMap<String, T> getLocations();
	T getLocation(String id);
	T createLocation(T request);
	T updateLocation(String id, T request);
	Void deleteLocation(String id);
	
}
