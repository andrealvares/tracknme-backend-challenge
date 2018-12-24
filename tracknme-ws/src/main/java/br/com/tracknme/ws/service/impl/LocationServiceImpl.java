package br.com.tracknme.ws.service.impl;

import java.util.NavigableMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tracknme.ws.exception.service.LocationServiceException;
import br.com.tracknme.ws.service.LocationService;
import br.com.tracknme.ws.shared.Utils;
import br.com.tracknme.ws.ui.model.dto.LocationDTO;

@Service
public class LocationServiceImpl implements LocationService<LocationDTO> {

	private NavigableMap<String, LocationDTO> locations;
	
	private Utils utils;	
	
	public LocationServiceImpl(){}  
	
	@Autowired
	public LocationServiceImpl(Utils utils) {
		this.utils = utils;
	} 

	@Override
	public NavigableMap<String, LocationDTO> getLocations() {
		
		if (locations != null && !locations.isEmpty()) {		
			return locations;
		} else {
			return null;	
		}
	}
	
	@Override
	public LocationDTO getLocation(String id) {
		
		if (locations.containsKey(id)) {
			return locations.get(id);
		} else {
			return null;
		}
	}

	@Override
	public LocationDTO createLocation(LocationDTO request) {

		if (locations == null) {
			locations = new TreeMap<String, LocationDTO>();
		} else {
			if(!utils.validationDistance(locations, request)) {
				throw new LocationServiceException("This location cannot be persist, must be greater than 10 meters away");
			}
		}
		LocationDTO dto = utils.setLocation(locations, request);		
				
		locations.put(dto.getId(), dto);
		
		utils.refreshTacker(dto);
		
		return dto;
	}

	@Override
	public LocationDTO updateLocation(String id, LocationDTO request) {

		request.setId(id);
		LocationDTO dto = utils.setLocation(locations, request);	
		
		locations.put(dto.getId(), dto);
		
		return dto;
	}

	@Override
	public Void deleteLocation(String id) {		
		locations.remove(id);
		return null;
	}

	
    
	
	
}
