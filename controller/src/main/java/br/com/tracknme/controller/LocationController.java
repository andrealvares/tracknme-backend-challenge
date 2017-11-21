package br.com.tracknme.controller;

import br.com.tracknme.business.LocationBusinessLogic;
import br.com.tracknme.business.dto.LocationDto;
import br.com.tracknme.business.exception.TrackNMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Cleberson on 18/11/2017.
 */
@RestController
public class LocationController {

    @Autowired
    private LocationBusinessLogic locationBusinessLogic;

    @GetMapping("/locations")
    public List<LocationDto> getLocations() {
        return locationBusinessLogic.getLocations();
    }

    @GetMapping("/locations/{id}")
    public LocationDto getLocation(@PathVariable Long id) {
        return locationBusinessLogic.getLocation(id);
    }

    @PostMapping("/locations")
    public ResponseEntity createLocation(@RequestBody LocationDto locationDto) {
        try {
            return ResponseEntity.ok().body(
                    locationBusinessLogic.createLocation(locationDto));
        } catch (TrackNMException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/locations/{id}")
    public ResponseEntity updateLocation(@RequestBody LocationDto locationDto, @PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(
                    locationBusinessLogic.updateLocation(locationDto, id));
        } catch (TrackNMException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @RequestMapping(value = "/locations/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteLocation(@PathVariable Long id) {
        try {
            locationBusinessLogic.deleteLocation(id);
            return ResponseEntity.ok().build();
        } catch (TrackNMException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
