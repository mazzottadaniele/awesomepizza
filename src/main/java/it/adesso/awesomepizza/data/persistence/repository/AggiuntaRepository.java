package it.adesso.awesomepizza.data.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.adesso.awesomepizza.data.persistence.entity.Aggiunta;

public interface AggiuntaRepository extends JpaRepository<Aggiunta,Long>{

}
