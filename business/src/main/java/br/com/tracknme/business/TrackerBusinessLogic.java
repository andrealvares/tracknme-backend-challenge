package br.com.tracknme.business;

/**
 * Created by Cleberson on 20/11/2017.
 */
public interface TrackerBusinessLogic {

    boolean updateTrackerLocationAsync(long id, double lat, double lng);
}
