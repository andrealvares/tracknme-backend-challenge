package br.com.tracknme.model.repository;

import br.com.tracknme.model.domain.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Cleberson on 18/11/2017.
 */
@Repository
public class LocationRepositoryImpl implements LocationRepository {

    private static final String KEY = Location.class.getSimpleName() + ":";

    @Autowired
    private BasicRepository<Location> basicRepository;

    @Override
    public List<Location> getLocations() {
        return basicRepository.retrieveAll(KEY);
    }

    @Override
    public Location getLocation(long id) {
        if (id <= 0) return null;
        return basicRepository.retrieve(KEY, id);
    }

    @Override
    public boolean isLocationExists(long id) {
        if (id <= 0) return false;
        return basicRepository.retrieve(KEY, id) != null;
    }

    @Override
    public void createLocation(Location location) {
        if (!isLocationValid(location)) return;
        basicRepository.create(KEY, location, location.getId());
    }

    @Override
    public Location updateLocation(Location location) {
        if (!isLocationValid(location)) return null;
        return basicRepository.update(KEY, location, location.getId());
    }

    private boolean isLocationValid(Location location) {
        return (location != null && location.getId() != null && location.getId() > 0);
    }

    @Override
    public void deleteLocation(long id) {
        if (id <= 0) return;
        basicRepository.delete(KEY, id);
    }
}
