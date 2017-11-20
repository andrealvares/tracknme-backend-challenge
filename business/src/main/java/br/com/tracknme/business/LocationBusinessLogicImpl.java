package br.com.tracknme.business;

import br.com.tracknme.business.dto.LocationDto;
import br.com.tracknme.business.exception.TrackNMException;
import br.com.tracknme.model.domain.Tracker;
import br.com.tracknme.model.repository.LocationRepository;
import br.com.tracknme.model.domain.Location;
import br.com.tracknme.model.repository.TrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by Cleberson on 18/11/2017.
 */
@Component
public class LocationBusinessLogicImpl implements LocationBusinessLogic {

    static final Logger logger = Logger.getLogger(LocationBusinessLogicImpl.class.getSimpleName());

    @Autowired
    private LocationRepository locationRepository;

    @Value("${min.distance.between.locations.meters}")
    public Integer minDistanceLocations;

    @Autowired
    private TrackerRepository trackerRepository;

    @Autowired
    private TrackerBusinessLogic trackerBusinessLogic;


    @Override
    public List<LocationDto> getLocations() {
        List<Location> locationsEntity = locationRepository.getLocations();
        return locationsEntity.stream()
                .map(locationEntity -> convert2Dto(locationEntity))
                .collect(Collectors.toList());
    }


    public LocationDto getLocation(long id) {
        if (id <= 0) return null;
        Location locationEntity = locationRepository.getLocation(id);
        if (locationEntity == null) return null;
        return convert2Dto(locationEntity);
    }


    public LocationDto createLocation(LocationDto locationDto) throws TrackNMException {
        validateRequiredFields(locationDto);
        if (!isDistanceValid(
                locationDto,
                trackerRepository.getTracker(locationDto.getTrackerId()))) {
            logger.log(Level.INFO,
                    "Location wasn't created because is lesser then {0}"
                            + " meters from the last position"
                    , new Object[]{minDistanceLocations});
            return null;
        }
        Location locationEntity = convert2Entity(locationDto);
        locationRepository.createLocation(locationEntity);
        replicateLocationAsync(locationEntity);
        return locationDto;
    }

    public LocationDto updateLocation(LocationDto locationDto, long id) throws TrackNMException {
        if (locationDto == null || id <= 0) return null;
        Location locationEntity = locationRepository.getLocation(id);
        if (locationEntity == null) return null;
        setFieldsFromLocationDto(locationEntity, locationDto);
        locationEntity = locationRepository.updateLocation(locationEntity);
        return convert2Dto(locationEntity);
    }


    public void deleteLocation(long id) throws TrackNMException {
        if (!locationRepository.isLocationExists(id)) throw new TrackNMException("Location no longer exists!");
        locationRepository.deleteLocation(id);
    }

    /**
     * Used to copy actual location position to tracker position asynchronous
     *
     * @param locationEntity
     */
    public void replicateLocationAsync(Location locationEntity) {
        trackerBusinessLogic.updateTrackerLocationAsync(
                locationEntity.getTrackerId(),
                locationEntity.getLatitude(),
                locationEntity.getLongitude());
    }

    /**
     * Used to set fields not nulls from Dto in the entity fields
     *
     * @param locationEntity
     * @param locationDto
     */
    public void setFieldsFromLocationDto(Location locationEntity, LocationDto locationDto) {
        if (locationDto.getLatitude() != null) locationEntity.setLatitude(locationDto.getLatitude());
        if (locationDto.getLongitude() != null) locationEntity.setLongitude(locationDto.getLongitude());
        if (locationDto.getDateTime() != null) locationEntity.setDateTime(locationDto.getDateTime());
        if (locationDto.getTrackerId() > 0) locationEntity.setTrackerId(locationDto.getTrackerId());
    }

    /**
     * Utility method to validate mandatory fields
     *
     * @param locationDto
     * @throws TrackNMException
     */
    private void validateRequiredFields(LocationDto locationDto) throws TrackNMException {
        if (locationDto == null) throw new TrackNMException("Location can't be NULL");
        if (locationDto.getId() == null || locationDto.getId() <= 0) throw new TrackNMException("Invalid Location ID");
        if (locationDto.getDateTime() == null) throw new TrackNMException("Date time can't be NULL");
        if (locationDto.getTrackerId() <= 0) throw new TrackNMException("Invalid Tracker ID");
        if (locationDto.getLatitude() == null) throw new TrackNMException("Invalid Latitude value");
        if (locationDto.getLongitude() == null) throw new TrackNMException("Invalid Longitude value");
    }

    /**
     * Check the distance of last position and new position
     *
     * @param locationDto
     * @return true in case of distance in meter is higher than minimum distance configured in the system
     */
    public boolean isDistanceValid(LocationDto locationDto, Tracker tracker) {
        if (tracker == null || locationDto == null) return false;
        double oldLat = tracker.getLatitude();
        double oldLng = tracker.getLongitude();

        double newLat = locationDto.getLatitude();
        double newLng = locationDto.getLongitude();
        return isDistanceValid(oldLat, newLat, oldLng, newLng, minDistanceLocations);
    }

    /**
     * Check if the distance between 2 points is valid
     *
     * @param lat1
     * @param lat2
     * @param lon1
     * @param lon2
     * @param minDistance
     * @return boolean
     */
    public boolean isDistanceValid(double lat1, double lat2, double lon1,
                                   double lon2, int minDistance) {

        int distanceInMeters = (int) distance(lat1, lat2, lon1, lon2, 0, 0);
        return (distanceInMeters > minDistance);
    }


    /**
     * Utility method to convert Location entity to DTO
     */
    private LocationDto convert2Dto(Location locationEntity) {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(locationEntity.getId());
        locationDto.setTrackerId(locationEntity.getTrackerId());
        locationDto.setDateTime(locationEntity.getDateTime());
        locationDto.setLatitude(locationEntity.getLatitude());
        locationDto.setLongitude(locationEntity.getLongitude());
        return locationDto;
    }


    /**
     * Utility method to convert Location DTO to Location Entity
     */
    private Location convert2Entity(LocationDto locationDto) {
        Location locationEntity = new Location();
        locationEntity.setId(locationDto.getId());
        locationEntity.setTrackerId(locationDto.getTrackerId());
        locationEntity.setDateTime(locationDto.getDateTime());
        locationEntity.setLatitude(locationDto.getLatitude());
        locationEntity.setLongitude(locationDto.getLongitude());
        return locationEntity;
    }

    /**
     * https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi
     * <p>
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * <p>
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     *
     * @returns Distance in Meters
     */
    public double distance(double lat1, double lat2, double lon1,
                           double lon2, double el1, double el2) {

        final int radius = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = radius * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

}
