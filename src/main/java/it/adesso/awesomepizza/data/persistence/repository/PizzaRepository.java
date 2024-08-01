package it.adesso.awesomepizza.data.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.adesso.awesomepizza.data.persistence.entity.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza,Long>{

}
