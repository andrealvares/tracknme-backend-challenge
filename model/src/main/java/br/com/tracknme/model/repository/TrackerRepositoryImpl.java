package br.com.tracknme.model.repository;

import br.com.tracknme.model.domain.Tracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Cleberson on 19/11/2017.
 */
@Repository
public class TrackerRepositoryImpl implements TrackerRepository {

    private static final String KEY = Tracker.class.getSimpleName() + ":";

    @Autowired
    private BasicRepository<Tracker> basicRepository;

    @Override
    public Tracker getTracker(long id) {
        if (id <= 0) return null;
        return basicRepository.retrieve(KEY, id);
    }

    @Override
    public Tracker updateTracker(Tracker tracker) {
        if (tracker == null || tracker.getId() == null || tracker.getId() <= 0) return null;
        return basicRepository.update(KEY, tracker, tracker.getId());
    }

    @Override
    public void createTracker(Tracker tracker) {
        basicRepository.create(KEY, tracker, tracker.getId());
    }
}
