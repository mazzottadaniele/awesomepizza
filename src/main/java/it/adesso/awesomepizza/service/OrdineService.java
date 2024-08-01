package it.adesso.awesomepizza.service;

import java.util.List;

import it.adesso.awesomepizza.data.dto.OrdineDtoRequest;
import it.adesso.awesomepizza.data.dto.OrdineDtoResponse;
import it.adesso.awesomepizza.data.dto.OrdineIdDto;
import it.adesso.awesomepizza.data.enumerations.StatoOrdineEnum;
import it.adesso.awesomepizza.exceptions.ServiceException;

public interface OrdineService {
	public Long creaOrdine(OrdineDtoRequest ordine) throws ServiceException;
	public void modificaStato(Long idOrdine, StatoOrdineEnum stato) throws ServiceException;
	public List<OrdineDtoResponse> getOrdiniDaLavorare() throws ServiceException;
	public OrdineDtoResponse getDettaglioOrdine(Long ordineId) throws ServiceException;
	public boolean esisteOrdineInStato(StatoOrdineEnum stato) throws ServiceException;
	public StatoOrdineEnum getStatoOrdine(Long id) throws ServiceException;
	public void eliminaOrdine(Long id) throws ServiceException;
}
