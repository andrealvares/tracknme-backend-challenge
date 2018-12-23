package br.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.entity.Location;
import br.com.repository.LocationRepository;

@Service
public class LocationService implements ILocationService {

	@Autowired
	LocationRepository locationRepository;
	
	@Override
	public List<Location> getLocations() {
		return locationRepository.findAll();
	}

	@Override
	public Location getLocation(long id) {
		return locationRepository.getOne(id);
	}

	@Override
	public Location createLocation(Location location) {
		return (Location) locationRepository.save(location);
	}

	@Override
	public Location updateLocation(Location location) {
		return (Location) locationRepository.saveAndFlush(location);
	}

	@Override
	public void deletelocation(long id) {
		locationRepository.deleteById(id);
	}
	
	
}