package com.desafio.desafiobackend.service;

import com.desafio.desafiobackend.domain.Location;
import com.desafio.desafiobackend.domain.Tracker;
import com.desafio.desafiobackend.dto.LocationDTO;
import com.desafio.desafiobackend.repository.LocationRepository;
import com.desafio.desafiobackend.repository.TrackerRepository;
import com.desafio.desafiobackend.service.utils.CalcularDistancia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private TrackerRepository trackerRepository;


    public Location recuperarPeloId(Integer id) {
        Optional<Location> location = locationRepository.findById(id);
        return location.orElseThrow(() -> new EmptyResultDataAccessException(id));
    }

    public Location removerLocation(Integer id) {
        Location location = recuperarPeloId(id);
        try {
            locationRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Erro na exclusão da Location");

        }
        return location;

    }

    public Location atualizar(LocationDTO location) {
        Location newLocation = recuperarPeloId(location.getId());
        newLocation.setDateTime(location.getDateTime());

        Tracker tracker = buscarTracker(location.getTrackerId());
        tracker.setLatitude(location.getLatitude());
        tracker.setLongitude(location.getLongitude());
        newLocation.setTrackerId(tracker);

        LocationDTO locationDTO = new LocationDTO(newLocation);
        return locationRepository.save(newLocation);
    }

    @Transactional
    public LocationDTO inserir(LocationDTO location){

        //VERIFICAR SE A NOVA LOCALIZAÇÃO É MAIS DISTANTE QUE A ANTERIOR
        boolean DistanciaMenorQue10metros = false;
        LocationDTO ultimaLocationDTO = (recuperacaoPersonalizada());// <-- RECEBER ULTIMA LOCALIZAÇÃO

       try {
           DistanciaMenorQue10metros = new CalcularDistancia().calcularDistancia(location, ultimaLocationDTO);

       }catch(NullPointerException n){
           
       }

       // SE A DISTANCIA ENTRE A NOVA POSIÇÃO E A ULTIMA FOR MENOR QUE 10 METROS
       if (DistanciaMenorQue10metros) {
            throw new IllegalAccessError("Distância menor que 10 metros");
       }

        Location loc = new Location();
        loc.setDateTime(location.getDateTime());

        // VERIFICANDO SE JÁ EXISTE A TRACKER PELO ID
        Tracker tracker = buscarTracker(location.getTrackerId());
        if (tracker == null) {
            tracker = new Tracker();
            tracker.setTrackerId(location.getTrackerId());
        }

        // ATUALIZANDO TRACKER
        tracker.setLongitude(location.getLongitude());
        tracker.setLatitude(location.getLatitude());

        //APONTANDO TRACKER PARA LOCATION
        loc.setTrackerId(tracker);

        //ADICIONANDO NOVA LOCALIZAÇÃO NA LISTA
        tracker.getLocations().add(loc);


        Tracker trackerSalvo = trackerRepository.save(tracker);
        Location locationSalvo = locationRepository.save(loc);

        //CRIANDO LOCATION DATA TRANSFER OBJECT(DTO) PARA RESPOSTA DO POST
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(locationSalvo.getId());
        locationDTO.setDateTime(locationSalvo.getDateTime());
        locationDTO.setTrackerId(trackerSalvo.getTrackerId());
        locationDTO.setLatitude(trackerSalvo.getLatitude());
        locationDTO.setLongitude(trackerSalvo.getLongitude());
        locationDTO.setName(trackerSalvo.getName());

        return locationDTO;
    }


    public Tracker buscarTracker(Integer trackerId) {
        Optional<Tracker> tracker = trackerRepository.findById(trackerId);
        return tracker.orElse(null);
    }

    public List<Location> recuperarTodos() {
        return locationRepository.findAll();
    }

    public LocationDTO recuperacaoPersonalizada() throws IndexOutOfBoundsException {
        List<Location> locations = locationRepository.findAll();

        if(locations.isEmpty())
            return null;
        Location location = new Location();
        location = ((List<Location>) locations).get(locations.size() > 0 ? locations.size() - 1 : locations.size());

        LocationDTO locationDTO= new LocationDTO(location);
        return locationDTO;
    }


}
