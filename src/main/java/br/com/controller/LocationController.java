package br.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.entity.Location;
import br.com.service.LocationService;

@RestController
@RequestMapping("/locations")
public class LocationController {
		
	@Autowired
	LocationService locationService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Location> getLocations(){
		return locationService.getLocations();
	}
	
	@RequestMapping(method=RequestMethod.GET, path="/{id}")
	public Location getLocation(@PathVariable("id") int id) {
		return locationService.getLocation(id);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public Location createLocation(@RequestBody Location location) {
		return (Location) locationService.createLocation(location);
	}
	
	@RequestMapping(method=RequestMethod.PATCH, path="/{id}")
	public Location updateLocation(@RequestBody Location location, @PathVariable("id") long id) {
		return locationService.updateLocation(location);
	}

	@RequestMapping(method=RequestMethod.DELETE, path="/{id}")
	public void deleteLocation(@PathVariable("id") long id) {
		locationService.deletelocation(id);
	}
	
}

/*
 * GET /locations (Recupera as entidades persistidas.)

GET /locations/{ID} (Recupera apenas uma entidade persistida pelo ID.)

POST /locations (Cria ou atualiza uma entidade recebida em formato JSON no corpo da requisição. Retorna a entidade criada.)

PATCH /locations/{ID} (Atualiza apenas os atributos de uma entidade informados no corpo da requisição. Retorna a entidade atualizada.)

DELETE /locations/{ID} (Remove e retorna uma entidade por ID.)
*/
