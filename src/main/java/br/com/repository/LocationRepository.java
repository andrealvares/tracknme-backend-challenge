package br.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{

}
	