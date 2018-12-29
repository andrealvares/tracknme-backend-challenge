package location;

import exception.DistanceBetweenLocationsException;

import java.util.List;

public interface LocationService {
    LocationEntity create(LocationEntity location) throws DistanceBetweenLocationsException;
    List<LocationEntity> listAll();
    LocationEntity findById(int id);
    LocationEntity update(int id, String fieldsToBeUpdated);
    LocationEntity delete(int id);
}
