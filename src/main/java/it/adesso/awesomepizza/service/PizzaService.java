package it.adesso.awesomepizza.service;

import java.util.List;

import it.adesso.awesomepizza.data.dto.PizzaDto;
import it.adesso.awesomepizza.exceptions.ServiceException;

public interface PizzaService {
	public List<PizzaDto> getCatalogo() throws ServiceException;
}
