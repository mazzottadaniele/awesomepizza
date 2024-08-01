package it.adesso.awesomepizza.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.adesso.awesomepizza.data.dto.OrdineDtoRequest;
import it.adesso.awesomepizza.data.dto.OrdineDtoResponse;
import it.adesso.awesomepizza.data.dto.OrdineIdDto;
import it.adesso.awesomepizza.data.enumerations.StatoOrdineEnum;
import it.adesso.awesomepizza.data.persistence.entity.Ordine;
import it.adesso.awesomepizza.data.persistence.repository.OrdineRepository;
import it.adesso.awesomepizza.data.transformer.OrdineTransformer;
import it.adesso.awesomepizza.exceptions.DataNotFoundException;
import it.adesso.awesomepizza.exceptions.ServiceException;
import it.adesso.awesomepizza.service.OrdineService;

@Service
public class OrdineServiceImpl implements OrdineService{
	@Autowired
	private OrdineRepository ordineRepository;
	
	@Autowired
	private OrdineTransformer ordineTransformer;
	
	private final Logger log = LoggerFactory.getLogger(OrdineServiceImpl.class);
	
	@Override
	@Transactional
	public Long creaOrdine(OrdineDtoRequest dto) throws ServiceException{
		try {
			Ordine ordine = ordineTransformer.toEntity(dto);
			ordine = ordineRepository.save(ordine);
			return ordine.getId();
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
	
	@Override
	@Transactional(readOnly = true)
	public StatoOrdineEnum getStatoOrdine(Long idOrdine) throws ServiceException{
		StatoOrdineEnum stato = null;
		try {
			Optional<Ordine> ordine = ordineRepository.findById(idOrdine);
			if(ordine.isPresent()) {
				stato = ordine.get().getStato();
			}else {
				throw new DataNotFoundException("Ordine Inesistente");
			}
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
		return stato;
	}

	@Override
	@Transactional
	public void modificaStato(Long idOrdine, StatoOrdineEnum stato) throws ServiceException{
		try {
			if(stato==null) {
				throw new ServiceException("Lo stato non può essere vuoto");
			}
			Optional<Ordine> ordine = ordineRepository.findById(idOrdine);
			if(ordine.isPresent()) {
				if(stato.equals(StatoOrdineEnum.WORK_IN_PROGRESS) && !ordine.get().getStato().equals(StatoOrdineEnum.NUOVO)) {
					throw new ServiceException("Impossibile prendere in carico un ordine in stato " + ordine.get().getStato());
				}else if(stato.equals(StatoOrdineEnum.EVASO) && !ordine.get().getStato().equals(StatoOrdineEnum.WORK_IN_PROGRESS)) {
					throw new ServiceException("Impossibile evadere un ordine in stato " + ordine.get().getStato());
				}
				ordine.get().setStato(stato);
				ordineRepository.save(ordine.get());
			}else {
				throw new DataNotFoundException("Ordine Inesistente");
			}
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

	@Override
	@Transactional(readOnly = true)
	public List<OrdineDtoResponse> getOrdiniDaLavorare() throws ServiceException{
		try {
			Ordine ordine = new Ordine();
			ordine.setStato(StatoOrdineEnum.NUOVO);
			Example<Ordine> example = Example.of(ordine);
			List<Ordine> ordini = ordineRepository.findAll(example);
			List<OrdineDtoResponse> response = ordineTransformer.toDTOs(ordini);
			return response;
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
	
	@Override
	@Transactional(readOnly = true)
	public boolean esisteOrdineInStato(StatoOrdineEnum stato) throws ServiceException{
		try {
			Ordine ordine = new Ordine();
			ordine.setStato(stato);
			Example<Ordine> example = Example.of(ordine);
			boolean exists = ordineRepository.exists(example);
			return exists;
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

	@Override
	public OrdineDtoResponse getDettaglioOrdine(Long idOrdine) throws ServiceException{
		try {
			OrdineDtoResponse response = null;
			Optional<Ordine> ordine = ordineRepository.findById(idOrdine);
			if(ordine.isPresent()) {
				response = ordineTransformer.toDTO(ordine.get());
			}else {
				throw new ServiceException("Ordine Inesistente");
			}
			return response;
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
	
	@Override
	@Transactional
	public void eliminaOrdine(Long id) throws ServiceException{
		try {
			if(id==null) {
				throw new ServiceException("L'id non può essere vuoto");
			}
			Optional<Ordine> ordine = ordineRepository.findById(id);
			if(ordine.isPresent()) {
				ordineRepository.delete(ordine.get());
			}else {
				throw new DataNotFoundException("Ordine Inesistente");
			}
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
