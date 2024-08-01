package it.adesso.awesomepizza.service;

import java.util.List;

import it.adesso.awesomepizza.data.dto.AggiuntaDto;
import it.adesso.awesomepizza.exceptions.ServiceException;

public interface AggiuntaService {
	public List<AggiuntaDto> getCatalogo() throws ServiceException;
}
