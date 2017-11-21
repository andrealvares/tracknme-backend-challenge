package br.com.tracknme.business;

import br.com.tracknme.business.dto.LocationDto;
import br.com.tracknme.business.exception.TrackNMException;

import java.util.List;

/**
 * Created by Cleberson on 18/11/2017.
 */
public interface LocationBusinessLogic {

    /**
     * @return Retrieve all locations stored
     */
    List<LocationDto> getLocations();

    /**
     * Used to get particular Location by its primary key
     *
     * @param id, Primary key of Location
     * @return Particular Location
     */
    LocationDto getLocation(long id);

    /**
     * Used to store a location
     *
     * @param locationDto, All fields are mandatory
     * @return Whole Location
     * @throws TrackNMException
     */
    LocationDto createLocation(LocationDto locationDto) throws TrackNMException;

    /**
     * Used to updateLocation partially a location
     *
     * @param locationDto, trackerId is mandatory
     * @param id,          primary key of Location
     * @return Whole Location
     * @throws TrackNMException
     */
    LocationDto updateLocation(LocationDto locationDto, long id) throws TrackNMException;

    /**
     * Delete a particular location
     *
     * @param id Primary Key of Location
     * @throws TrackNMException
     */
    void deleteLocation(long id) throws TrackNMException;

}
