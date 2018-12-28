package com.desafio.desafiobackend.resource;

import com.desafio.desafiobackend.domain.Location;
import com.desafio.desafiobackend.dto.LocationDTO;
import com.desafio.desafiobackend.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/locations")
public class LocationResource {

    @Autowired
    LocationService locationService;

    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<LocationDTO> atualizar(@RequestBody LocationDTO locationUpdate, @PathVariable Integer id) {
        locationUpdate.setId(id);
        Location location= locationService.atualizar(locationUpdate);
        LocationDTO locationDTO= new LocationDTO(location);

        return ResponseEntity.ok().body(locationDTO);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Location> deletar(@PathVariable Integer id) {
       Location location= locationService.removerLocation(id);
        return ResponseEntity.ok().body(location);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<LocationDTO> inserir(@RequestBody LocationDTO locationDTO) {
        LocationDTO location = locationService.inserir(locationDTO);
        return ResponseEntity.ok().body(location);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<LocationDTO>> buscarTodos() {
        List<Location> lista = locationService.recuperarTodos();
        List<LocationDTO> listaDTO= lista.stream().map(obj -> new LocationDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listaDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<LocationDTO> buscarId(@PathVariable Integer id) {
        Location location = locationService.recuperarPeloId(id);
        LocationDTO locationDTO= new LocationDTO(location);
        return ResponseEntity.ok().body(locationDTO);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ultimo")
    public ResponseEntity<LocationDTO> pesquisaAvancada(){
        LocationDTO location= locationService.recuperacaoPersonalizada();
        return ResponseEntity.ok().body(location);
    }


}
