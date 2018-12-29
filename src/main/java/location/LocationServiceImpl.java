package location;
import data.LocationsDataSource;
import exception.DistanceBetweenLocationsException;

import java.util.List;

public class LocationServiceImpl implements LocationService {

    private LocationsDataSource locationsDataSource;
    public LocationServiceImpl(LocationsDataSource locationsDataSource){
        this.locationsDataSource = locationsDataSource;
    }

    @Override
    public LocationEntity create(LocationEntity location) throws DistanceBetweenLocationsException {
        return locationsDataSource.create(location);
    }

    @Override
    public List<LocationEntity> listAll() {
        return locationsDataSource.getAll();
    }

    @Override
    public LocationEntity findById(int id) {
        return locationsDataSource.findById(id);
    }

    @Override
    public LocationEntity update(int id, String fieldsToBeUpdated) {
        return locationsDataSource.update(id, fieldsToBeUpdated);
    }

    @Override
    public LocationEntity delete(int id) {
        return locationsDataSource.delete(id);
    }
}
