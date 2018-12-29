package tracker;

import data.TrackerDataSource;

public class TrackerServiceImpl implements TrackerService {

    private TrackerDataSource trackerDataSource;
    public TrackerServiceImpl(TrackerDataSource trackerDataSource){
        this.trackerDataSource = trackerDataSource;
    }
    @Override
    public void updateTracker(TrackerEntity tracker) {
        trackerDataSource.updateTrackerCoordinates(tracker);
    }
}
