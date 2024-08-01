package it.adesso.awesomepizza.data.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.adesso.awesomepizza.data.dto.OrdineDtoRequest;
import it.adesso.awesomepizza.data.dto.OrdineDtoResponse;
import it.adesso.awesomepizza.data.dto.OrdinePizzaDtoRequest;
import it.adesso.awesomepizza.data.dto.OrdinePizzaDtoResponse;
import it.adesso.awesomepizza.data.enumerations.StatoOrdineEnum;
import it.adesso.awesomepizza.data.persistence.entity.Aggiunta;
import it.adesso.awesomepizza.data.persistence.entity.Ordine;
import it.adesso.awesomepizza.data.persistence.entity.OrdinePizza;
import it.adesso.awesomepizza.data.persistence.entity.Pizza;
import it.adesso.awesomepizza.data.persistence.repository.AggiuntaRepository;
import it.adesso.awesomepizza.data.persistence.repository.PizzaRepository;

@Component
public class OrdineTransformer {
	
	@Autowired
	private AggiuntaTransformer aggiuntaTransformer;
	
	@Autowired
	private PizzaTransformer pizzaTransformer;
	
	@Autowired
	private PizzaRepository pizzaRepository;
	
	@Autowired
	private AggiuntaRepository aggiuntaRepository;
	
	public Ordine toEntity(OrdineDtoRequest dto) {
		Ordine entity = null;
		
		if(dto!=null) {
			entity = new Ordine();
			entity.setCliente(dto.getCliente());
			entity.setStato(StatoOrdineEnum.NUOVO);
			if(dto.getPizze()!=null) {
				entity.setDettaglio(new ArrayList<OrdinePizza>());
				for(OrdinePizzaDtoRequest pizzaDto : dto.getPizze()) {
					OrdinePizza ordinePizza = new OrdinePizza();
					Optional<Pizza> pizza = pizzaRepository.findById(pizzaDto.getIdPizza());
					if(pizza.isPresent()) {
						ordinePizza.setPizza(pizza.get());
					}else {
						throw new RuntimeException("Pizza con id "+pizzaDto.getIdPizza()+" non presente a catalogo");
					}
					if(pizzaDto.getIdsAggiunte()!=null) {
						ordinePizza.setAggiunte(new ArrayList<Aggiunta>());
						for(Long idAggiunta : pizzaDto.getIdsAggiunte()) {
							if(idAggiunta!=null) {
								Optional<Aggiunta> aggiunta = aggiuntaRepository.findById(idAggiunta);
								if(aggiunta.isPresent()) {
									ordinePizza.getAggiunte().add(aggiunta.get());
								}else {
									throw new RuntimeException("Aggiunta con id "+idAggiunta+" non presente a catalogo");
								}
							}
						}
						ordinePizza.setOrdine(entity);
					}
					entity.getDettaglio().add(ordinePizza);
				}
			}
		}
		
		return entity;
	}
	
	public OrdineDtoResponse toDTO(Ordine entity) {
		OrdineDtoResponse dto = null;
		if(entity!=null) {
			dto = new OrdineDtoResponse();
			dto.setCliente(entity.getCliente());
			dto.setIdOrdine(entity.getId());
			if(entity.getDettaglio()!=null) {
				dto.setPizze(new ArrayList<OrdinePizzaDtoResponse>());
				for(OrdinePizza ordinePizza : entity.getDettaglio()) {
					if(ordinePizza!=null) {
						OrdinePizzaDtoResponse ordinePizzaDTO = new OrdinePizzaDtoResponse();
						ordinePizzaDTO.setAggiunte(aggiuntaTransformer.toDTOs(ordinePizza.getAggiunte()));
						ordinePizzaDTO.setPizza(pizzaTransformer.toDTO(ordinePizza.getPizza()));
						dto.getPizze().add(ordinePizzaDTO);
					}
				}
			}
		}
		return dto;
	}
	
	public List<OrdineDtoResponse> toDTOs(Collection<Ordine> entities) {
		List<OrdineDtoResponse> dtos = null;
		if(entities!=null) {
			dtos = new ArrayList<OrdineDtoResponse>();
			for(Ordine ordine : entities) {
				if(ordine!=null) {
					dtos.add(this.toDTO(ordine));
				}
			}
		}
		return dtos;
	}
}
