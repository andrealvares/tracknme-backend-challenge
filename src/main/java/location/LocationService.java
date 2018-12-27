package location;

import java.util.List;

public interface LocationService {
    LocationEntity create(LocationEntity location);
    List<LocationEntity> listAll();
    LocationEntity findById(int id);
    LocationEntity update(int id, String fieldsToBeUpdated);
    LocationEntity delete(int id);
}
