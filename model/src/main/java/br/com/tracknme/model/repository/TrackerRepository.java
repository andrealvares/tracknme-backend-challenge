package br.com.tracknme.model.repository;

import br.com.tracknme.model.domain.Tracker;

/**
 * Created by Cleberson on 19/11/2017.
 */
public interface TrackerRepository {

    Tracker getTracker(long id);

    Tracker updateTracker(Tracker tracker);

    void createTracker(Tracker tracker);
}
