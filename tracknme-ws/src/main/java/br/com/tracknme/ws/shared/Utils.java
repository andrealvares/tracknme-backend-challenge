package br.com.tracknme.ws.shared;

import java.util.Date;
import java.util.NavigableMap;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.google.maps.DirectionsApi;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import br.com.tracknme.ws.ui.model.dto.LocationDTO;
import br.com.tracknme.ws.ui.model.dto.TrackerDTO;

@Service
public class Utils {

	private static final String API_KEY = "AIzaSyCub7VwZeE6TZR_sXF83ETR8b3R7Nvh270";
	private static final GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);
	
	public String generateId() {
		String id = UUID.randomUUID().toString();
		return id;
	}
	
	public LocationDTO setLocation(NavigableMap<String, LocationDTO> locations, LocationDTO request) {
		
		LocationDTO dto;
		
		if (request.getId() == null) {
			dto = new LocationDTO();
			dto.setId(generateId());
		} else {
			dto = locations.get(request.getId());
		}
		
		if (request.getTrackerId() != null) {
			dto.setTrackerId(request.getTrackerId());			
		}
		
		dto.setDateTime(new Date());

		if (request.getLatitude() != null) {
			dto.setLatitude(request.getLatitude());			
		} 
		
		if (request.getLongitude() != null) {
			dto.setLongitude(request.getLongitude());			
		}
		
		return dto;		
	}
	
	public boolean validationDistance(NavigableMap<String, LocationDTO> locations, LocationDTO request) {

		LatLng lastLocation = new LatLng(locations.lastEntry().getValue().getLatitude(), locations.lastEntry().getValue().getLongitude());
		LatLng newLocation = new LatLng(request.getLatitude(), request.getLongitude());
		
		if(getDistance(lastLocation, newLocation) < 10)
			return false;
			
		return true;
	}
	
	public Long getDistance(LatLng lastLocation, LatLng... newLocation) {
	    try {
	        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);
	       
	        DistanceMatrix trix = req.origins(lastLocation)
					                 .destinations(newLocation)
					                 .mode(TravelMode.DRIVING)
					                 .avoid(DirectionsApi.RouteRestriction.TOLLS)
					                 .language("pt-BR")
					                 .await();
	        return trix.rows[0].elements[0].distance.inMeters;

	    } catch (ApiException e) {
	        System.out.println(e.getMessage());
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    }
	    return null;
	}
	
	public TrackerDTO refreshTacker(LocationDTO dto) {
		
		TrackerDTO trackerDTO = new TrackerDTO();
		trackerDTO.setLatitude(dto.getLatitude());
		trackerDTO.setLongitude(dto.getLongitude());
		
		return trackerDTO;
	}
}
