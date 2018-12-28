package com.desafio.desafiobackend.repository;

import com.desafio.desafiobackend.domain.Tracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackerRepository extends JpaRepository<Tracker, Integer> {
}

