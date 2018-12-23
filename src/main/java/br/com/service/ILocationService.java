package br.com.service;

import java.util.List;

import br.com.entity.Location;

public interface ILocationService {
	public List<Location> getLocations();
	public Location getLocation(long id);
	public Location createLocation(Location location);
	public Location updateLocation(Location location);
	public void deletelocation(long id);
}
