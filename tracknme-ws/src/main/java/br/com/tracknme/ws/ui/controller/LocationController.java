package br.com.tracknme.ws.ui.controller;

import java.util.NavigableMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tracknme.ws.service.LocationService;
import br.com.tracknme.ws.ui.model.dto.LocationDTO;

@RestController
@RequestMapping("/locations")
public class LocationController {

	
	@Autowired
	private LocationService<LocationDTO> locationService;
	
	private LocationDTO dto;
		
	private NavigableMap<String, LocationDTO> locations;
	
	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, 
							 MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<NavigableMap<String, LocationDTO>> getLocations() {

		this.locations = locationService.getLocations();
		
		if (locations != null) {
			return new ResponseEntity<>(locations, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);			
		}		
	}
	
	@GetMapping(path = "/{id}", 
			produces = { MediaType.APPLICATION_XML_VALUE, 
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<LocationDTO> getLocation(@PathVariable String id) {
		
		this.dto = locationService.getLocation(id);
		
		if (dto != null) {
			return new ResponseEntity<LocationDTO>(dto, HttpStatus.OK);
		} else {
			return new ResponseEntity<LocationDTO>(HttpStatus.NO_CONTENT);			
		}		
	}
	
	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, 
			 				  MediaType.APPLICATION_JSON_VALUE },
				 produces = { MediaType.APPLICATION_XML_VALUE, 
						 	  MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<LocationDTO> createLocation(@Valid @RequestBody LocationDTO request) {
				
		this.dto = locationService.createLocation(request);
		
		return new ResponseEntity<LocationDTO>(dto, HttpStatus.OK);			
	}
	
	@PatchMapping(path = "/{id}",
				  consumes = { MediaType.APPLICATION_XML_VALUE, 
							   MediaType.APPLICATION_JSON_VALUE },
				  produces = { MediaType.APPLICATION_XML_VALUE, 
							   MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<LocationDTO> updateLocation(@PathVariable String id, @Valid @RequestBody LocationDTO request) {
		
		this.dto = locationService.updateLocation(id, request);		
		
		return new ResponseEntity<LocationDTO>(dto, HttpStatus.OK);		
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deleteLocation(@PathVariable String id) {
		
		locationService.deleteLocation(id);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}