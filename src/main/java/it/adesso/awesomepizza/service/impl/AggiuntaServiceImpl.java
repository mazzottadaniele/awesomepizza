package it.adesso.awesomepizza.service.impl;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.adesso.awesomepizza.data.dto.AggiuntaDto;
import it.adesso.awesomepizza.data.persistence.entity.Aggiunta;
import it.adesso.awesomepizza.data.persistence.repository.AggiuntaRepository;
import it.adesso.awesomepizza.data.transformer.AggiuntaTransformer;
import it.adesso.awesomepizza.exceptions.ServiceException;
import it.adesso.awesomepizza.service.AggiuntaService;

@Service
public class AggiuntaServiceImpl implements AggiuntaService{
	@Autowired
	private AggiuntaRepository aggiuntaRepository;
	
	@Autowired
	private AggiuntaTransformer aggiuntaTransformer;
	
	private final Logger log = LoggerFactory.getLogger(AggiuntaServiceImpl.class);
	
	@Override
	public List<AggiuntaDto> getCatalogo() throws ServiceException{
		try {
			List<Aggiunta> catalogo = aggiuntaRepository.findAll();
			return aggiuntaTransformer.toDTOs(catalogo);
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
