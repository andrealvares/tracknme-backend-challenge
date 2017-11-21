package br.com.tracknme.model.repository;

import br.com.tracknme.model.domain.Location;

import java.util.List;

/**
 * Created by Cleberson on 18/11/2017.
 */
public interface LocationRepository {

    List<Location> getLocations();

    Location getLocation(long id);

    boolean isLocationExists(long id);

    void createLocation(Location location);

    Location updateLocation(Location location);

    void deleteLocation(long id);

}
