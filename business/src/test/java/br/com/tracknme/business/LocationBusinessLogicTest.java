package br.com.tracknme.business;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import br.com.tracknme.business.dto.LocationDto;
import br.com.tracknme.business.exception.TrackNMException;
import br.com.tracknme.model.domain.Location;
import br.com.tracknme.model.domain.Tracker;
import br.com.tracknme.model.repository.LocationRepository;
import br.com.tracknme.model.repository.TrackerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

/**
 * Created by Cleberson on 19/11/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class LocationBusinessLogicTest {

    @InjectMocks
    public LocationBusinessLogicImpl locationBusinessLogic = new LocationBusinessLogicImpl();

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private TrackerRepository trackerRepository;

    @Mock
    private TrackerBusinessLogic trackerBusinessLogic;

    @Test
    public void invalidLocationTest() {
        assertNull(locationBusinessLogic.getLocation(-1));
    }

    @Test
    public void validLocationTest() {
        when(locationRepository.getLocation(1)).thenReturn(new Location(1l));
        LocationDto locationDto = locationBusinessLogic.getLocation(1);
        assertNotNull(locationDto);
        assertEquals(locationDto.getId().longValue(), 1l);
    }

    @Test
    public void distanceBetween2pointsTest() {
        int distanceRequiredMeter = 2;
        int distanceCalculatedMeter = (int) locationBusinessLogic
                .distance(
                        -23.985813333333333, -23.985813333333333,
                        -46.3034735, -46.30344683333333, 0, 0);
        assertEquals(distanceRequiredMeter, distanceCalculatedMeter);
    }

    @Test
    public void validateDistanceTest() {
        assertFalse(locationBusinessLogic.isDistanceValid(
                -23.985813333333333, -23.985813333333333,
                -46.3034735, -46.30344683333333, 10));

        locationBusinessLogic.minDistanceLocations = 10;

        Tracker tracker = new Tracker();
        tracker.setId(1l);
        tracker.setLatitude(-23.985813333333333);
        tracker.setLongitude(-46.3034735);

        LocationDto locationDto = new LocationDto();
        locationDto.setLatitude(-23.985813333333333);
        locationDto.setLongitude(-45.31868516666667);
        assertTrue(locationBusinessLogic.isDistanceValid(locationDto, tracker));
    }

    @Test
    public void nullLocationOrTrackValidateDistanceTest() {
        assertFalse(locationBusinessLogic.isDistanceValid(new LocationDto(), null));
        assertFalse(locationBusinessLogic.isDistanceValid(null, new Tracker()));
    }

    @Test(expected = TrackNMException.class)
    public void validateRequiredFieldsCreateTest() throws TrackNMException {
        locationBusinessLogic.createLocation(new LocationDto());
    }

    @Test(expected = TrackNMException.class)
    public void validateRequiredIdCreateTest() throws TrackNMException {
        LocationDto location = new LocationDto();
        location.setDateTime(new Date());
        location.setLatitude(0d);
        location.setLongitude(0d);
        location.setTrackerId(1);
        locationBusinessLogic.createLocation(location);
    }


    @Test(expected = TrackNMException.class)
    public void validateRequiredTrackerIdCreateTest() throws TrackNMException {
        LocationDto location = new LocationDto();
        location.setId(1l);
        location.setDateTime(new Date());
        location.setLatitude(0d);
        location.setLongitude(0d);
        locationBusinessLogic.createLocation(location);
    }

    @Test(expected = TrackNMException.class)
    public void validateRequiredDateTimeCreateTest() throws TrackNMException {
        LocationDto location = new LocationDto();
        location.setId(1l);
        location.setTrackerId(1);
        location.setLatitude(0d);
        location.setLongitude(0d);
        locationBusinessLogic.createLocation(location);
    }

    @Test(expected = TrackNMException.class)
    public void validateRequiredLatCreateTest() throws TrackNMException {
        LocationDto location = new LocationDto();
        location.setId(1l);
        location.setTrackerId(1);
        location.setDateTime(new Date());
        location.setLongitude(0d);
        locationBusinessLogic.createLocation(location);
    }

    @Test(expected = TrackNMException.class)
    public void validateRequiredLngCreateTest() throws TrackNMException {
        LocationDto location = new LocationDto();
        location.setId(1l);
        location.setTrackerId(1);
        location.setDateTime(new Date());
        location.setLatitude(0d);
        locationBusinessLogic.createLocation(location);
    }

    @Test
    public void validateSuccessfullyCreateTest() throws TrackNMException {

        Tracker tracker = new Tracker();
        tracker.setId(1l);
        tracker.setLatitude(-23.985813333333333);
        tracker.setLongitude(-46.3034735);

        LocationDto location = new LocationDto();
        location.setId(1l);
        location.setTrackerId(1);
        location.setDateTime(new Date());
        location.setLatitude(-23.985813333333333);
        location.setLongitude(-45.31868516666667);

        when(trackerRepository.getTracker(1)).thenReturn(tracker);
        locationBusinessLogic.minDistanceLocations = 10;
        assertNotNull(locationBusinessLogic.createLocation(location));
    }

    @Test
    public void validateSetupLocationForUpdateTest() {
        Date oldDate = new Date();
        Date newDate = new Date();
        Location location = new Location();
        location.setTrackerId(0);
        location.setDateTime(oldDate);
        location.setLatitude(0d);
        location.setLongitude(0d);

        LocationDto locationDto = new LocationDto();
        locationDto.setTrackerId(1);
        locationDto.setDateTime(newDate);
        locationDto.setLatitude(1d);
        locationDto.setLongitude(2d);

        locationBusinessLogic.setFieldsFromLocationDto(location, locationDto);
        assertEquals(location.getTrackerId(), locationDto.getTrackerId());
        assertEquals(location.getDateTime(), locationDto.getDateTime());
        assertTrue(locationDto.getLatitude().equals(location.getLatitude()));
        assertTrue(locationDto.getLongitude().equals(location.getLongitude()));
    }


    @Test
    public void validateUpdateTest() throws TrackNMException {
        Location location = new Location();
        location.setId(1l);
        location.setTrackerId(0);
        location.setDateTime(new Date());
        location.setLatitude(0d);
        location.setLongitude(0d);

        LocationDto locationDto = new LocationDto();
        locationDto.setId(1l);
        locationDto.setDateTime(new Date());
        locationDto.setDateTime(new Date());
        locationDto.setLatitude(1d);
        locationDto.setLongitude(2d);

        when(locationRepository.getLocation(1l)).thenReturn(location);
        when(locationRepository.updateLocation(location)).thenReturn(location);
        assertNotNull(locationBusinessLogic.updateLocation(locationDto, 1l));
    }

    @Test(expected = TrackNMException.class)
    public void deleteInvalidLocationTest() throws TrackNMException{
        when(locationRepository.isLocationExists(1)).thenReturn(true);
        locationBusinessLogic.deleteLocation(2l);
    }
}
