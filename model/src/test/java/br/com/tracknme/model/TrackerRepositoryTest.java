package br.com.tracknme.model;


import br.com.tracknme.model.repository.TrackerRepository;
import br.com.tracknme.model.repository.TrackerRepositoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNull;


/**
 * Created by Cleberson on 19/11/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class TrackerRepositoryTest {

    @InjectMocks
    public TrackerRepository trackerRepository = new TrackerRepositoryImpl();


    @Test
    public void getTrackerInvalidIdTest() {
        assertNull(trackerRepository.getTracker(-1));
    }

    @Test
    public void invalidTrackerUpdateTest() {
        assertNull(trackerRepository.updateTracker(null));
    }


}
