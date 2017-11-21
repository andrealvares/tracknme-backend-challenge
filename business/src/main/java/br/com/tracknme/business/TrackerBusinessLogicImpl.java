package br.com.tracknme.business;

import br.com.tracknme.model.domain.Tracker;
import br.com.tracknme.model.repository.TrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by Cleberson on 20/11/2017.
 */
@Component
public class TrackerBusinessLogicImpl implements TrackerBusinessLogic {

    static final Logger logger = Logger.getLogger(TrackerBusinessLogicImpl.class.getSimpleName());

    @PostConstruct
    public void init() {
        //load track used in the challenge
        Tracker tracker = new Tracker();
        tracker.setId(654321l);
        tracker.setName("Meu rastreador");
        tracker.setLatitude(0);
        tracker.setLatitude(0);
        trackerRepository.createTracker(tracker);
        logger.log(Level.INFO, "Tracker created or updated successfully  ID: 654321l");
    }

    @Autowired
    private TrackerRepository trackerRepository;

    @Async
    public boolean updateTrackerLocationAsync(long id, double lat, double lng) {
        if (id <= 0) return false;
        Tracker tracker = trackerRepository.getTracker(id);
        if (tracker == null) return false;
        tracker.setLatitude(lat);
        tracker.setLongitude(lng);
        try {
            trackerRepository.updateTracker(tracker);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while trying to update the position of tracker", e);
        }
        logger.log(Level.INFO, "Tracker position successfully updated");
        return true;
    }
}
