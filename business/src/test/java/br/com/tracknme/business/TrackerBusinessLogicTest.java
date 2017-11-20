package br.com.tracknme.business;

import br.com.tracknme.model.repository.TrackerRepository;
import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * Created by Cleberson on 20/11/2017.
 */
public class TrackerBusinessLogicTest {

    @InjectMocks
    public TrackerBusinessLogicImpl trackerBusinessLogic = new TrackerBusinessLogicImpl();

    @Mock
    private TrackerRepository trackerRepository;

    @Test
    public void validateUpdateAsyncInvalidIdTest() {
        assertFalse(trackerBusinessLogic.updateTrackerLocationAsync(-1, 0,0));
    }
}
