package br.com.tracknme.model;


import br.com.tracknme.model.repository.LocationRepository;
import br.com.tracknme.model.repository.LocationRepositoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;


/**
 * Created by Cleberson on 19/11/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class LocationRepositoryTest {

    @InjectMocks
    public LocationRepository locationRepository = new LocationRepositoryImpl();


    @Test
    public void getLocationWithInvalidIdTest() {
        assertNull(locationRepository.getLocation(-1));
    }

    @Test
    public void invalidLocationUpdateTest() {
        assertNull(locationRepository.updateLocation(null));
    }

    @Test
    public void invalidIdDeleteTest() {
        //if validation fail then will throw a NullPointerException
        locationRepository.deleteLocation(-1);
    }
}
