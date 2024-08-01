package it.adesso.awesomepizza.service.impl;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.adesso.awesomepizza.data.dto.PizzaDto;
import it.adesso.awesomepizza.data.persistence.entity.Pizza;
import it.adesso.awesomepizza.data.persistence.repository.PizzaRepository;
import it.adesso.awesomepizza.data.transformer.PizzaTransformer;
import it.adesso.awesomepizza.exceptions.ServiceException;
import it.adesso.awesomepizza.service.PizzaService;

@Service
public class PizzaServiceImpl implements PizzaService{
	@Autowired
	private PizzaRepository pizzaRepository;
	
	@Autowired
	private PizzaTransformer pizzaTransformer;
	
	private final Logger log = LoggerFactory.getLogger(PizzaServiceImpl.class);

	@Override
	public List<PizzaDto> getCatalogo() throws ServiceException{
		try {
			List<Pizza> catalogo = pizzaRepository.findAll();
			return pizzaTransformer.toDTOs(catalogo);
		}catch(ServiceException e) {
			if(log.isDebugEnabled()) {
				log.debug(ExceptionUtils.getStackTrace(e));
			}
			throw e;
		}catch(Exception e) {
			if(log.isDebugEnabled()) {
				log.debug(ExceptionUtils.getStackTrace(e));
			}
			throw new ServiceException(e);
		}
	}
	
}
