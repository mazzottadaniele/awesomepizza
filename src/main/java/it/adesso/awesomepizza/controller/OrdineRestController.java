package it.adesso.awesomepizza.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.adesso.awesomepizza.data.dto.EsitoDto;
import it.adesso.awesomepizza.data.dto.OrdineDtoListResponse;
import it.adesso.awesomepizza.data.dto.OrdineDtoRequest;
import it.adesso.awesomepizza.data.dto.OrdineDtoResponse;
import it.adesso.awesomepizza.data.dto.OrdineIdDto;
import it.adesso.awesomepizza.data.dto.StatoOrdineDto;
import it.adesso.awesomepizza.data.enumerations.StatoOrdineEnum;
import it.adesso.awesomepizza.exceptions.DataNotFoundException;
import it.adesso.awesomepizza.exceptions.ServiceException;
import it.adesso.awesomepizza.service.OrdineService;

@RestController
@RequestMapping("/api")
public class OrdineRestController {
	
	@Autowired
	private OrdineService ordineService;
	
	private final Logger log = LoggerFactory.getLogger(OrdineRestController.class);
	
	/**
     * POST  /nuovoOrdine
     */
	@Operation(summary = "Crea un nuovo ordine")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "201", description = "Ordine creato con successo", 
			    content = { @Content(mediaType = "application/json", 
			      schema = @Schema(implementation = OrdineIdDto.class)) }),
			  @ApiResponse(responseCode = "404", description = "Ordine non trovato", 
			    content = @Content), 
			  @ApiResponse(responseCode = "304", description = "Errore e nessuna modifica apporta", 
			    content = @Content),
			  @ApiResponse(responseCode = "400", description = "Errore di sistema", 
			    content = @Content)})
	
    @RequestMapping(value = "/nuovoOrdine",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrdineIdDto> creaOrdine(@Valid @RequestBody OrdineDtoRequest ordine, final HttpServletRequest request) {
    	try {
    		Long idOrdine = ordineService.creaOrdine(ordine);
			return new ResponseEntity<OrdineIdDto>(new OrdineIdDto(idOrdine, new EsitoDto(true)), HttpStatus.CREATED);
    	}catch(DataNotFoundException e) {
			return new ResponseEntity<OrdineIdDto>(new OrdineIdDto(new EsitoDto(false, e.getMessage())), HttpStatus.NOT_FOUND);
		}catch(ServiceException e) {
			return new ResponseEntity<OrdineIdDto>(new OrdineIdDto(new EsitoDto(false, e.getMessage())), HttpStatus.NOT_MODIFIED);
		}catch(Exception e) {
			if(log.isDebugEnabled()) {
				log.debug(ExceptionUtils.getStackTrace(e));
			}
			return new ResponseEntity<OrdineIdDto>(new OrdineIdDto(new EsitoDto(false, "Errore imprevisto")), HttpStatus.BAD_REQUEST);
		}
    }
    
    /**
     * GET  /ordiniDaLavorare
     */
	@Operation(summary = "Restituisce gli ordini ancora da lavorare")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Lista degli ordini da lavorare", 
			    content = { @Content(mediaType = "application/json", 
			      schema = @Schema(implementation = OrdineDtoListResponse.class)) }),
			  @ApiResponse(responseCode = "404", description = "Lista ordini non trovata", 
			    content = @Content), 
			  @ApiResponse(responseCode = "304", description = "Errore e nessuna modifica apporta", 
			    content = @Content),
			  @ApiResponse(responseCode = "400", description = "Errore di sistema", 
			    content = @Content)})
	
    @RequestMapping(value = "/ordiniDaLavorare",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrdineDtoListResponse> ordiniDaLavorare(final HttpServletRequest request) {
    	try {
	    	List<OrdineDtoResponse> toWorkList = ordineService.getOrdiniDaLavorare();
	    	OrdineDtoListResponse response = new OrdineDtoListResponse(new EsitoDto(true));
	    	response.setList(toWorkList);
			return new ResponseEntity<OrdineDtoListResponse>(response, HttpStatus.OK);
    	}catch(DataNotFoundException e) {
			return new ResponseEntity<OrdineDtoListResponse>(new OrdineDtoListResponse(new EsitoDto(false, e.getMessage())), HttpStatus.NOT_FOUND);
		}catch(ServiceException e) {
			return new ResponseEntity<OrdineDtoListResponse>(new OrdineDtoListResponse(new EsitoDto(false, e.getMessage())), HttpStatus.NOT_MODIFIED);
		}catch(Exception e) {
			if(log.isDebugEnabled()) {
				log.debug(ExceptionUtils.getStackTrace(e));
			}
			return new ResponseEntity<OrdineDtoListResponse>(new OrdineDtoListResponse(new EsitoDto(false, "Errore imprevisto")), HttpStatus.BAD_REQUEST);
		}
    }
    
    /**
     * PUT  /prendiInCarico
     */
	@Operation(summary = "Modifica lo stato dell'ordine in Work in progress")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Esito positivo della modifica", 
			    content = { @Content(mediaType = "application/json", 
			      schema = @Schema(implementation = EsitoDto.class)) }),
			  @ApiResponse(responseCode = "404", description = "Ordine non trovato", 
			    content = @Content), 
			  @ApiResponse(responseCode = "304", description = "Errore e nessuna modifica apporta", 
			    content = @Content),
			  @ApiResponse(responseCode = "400", description = "Errore di sistema", 
			    content = @Content)})
	
    @RequestMapping(value = "/prendiInCarico",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EsitoDto> prendiInCarico(final HttpServletRequest request,
    		@RequestParam(value = "idOrdine" , required = true) final Long idOrdine) {
    	boolean existsWIP = ordineService.esisteOrdineInStato(StatoOrdineEnum.WORK_IN_PROGRESS);
    	if(!existsWIP) {
    		try {
    			ordineService.modificaStato(idOrdine, StatoOrdineEnum.WORK_IN_PROGRESS);
    			return new ResponseEntity<EsitoDto>(new EsitoDto(true, "Operazione eseguita con successo"), HttpStatus.OK);
    		}catch(DataNotFoundException e) {
    			return new ResponseEntity<EsitoDto>(new EsitoDto(false, e.getMessage()), HttpStatus.NOT_FOUND);
    		}catch(ServiceException e) {
    			return new ResponseEntity<EsitoDto>(new EsitoDto(false, e.getMessage()), HttpStatus.NOT_MODIFIED);
    		}catch(Exception e) {
    			if(log.isDebugEnabled()) {
    				log.debug(ExceptionUtils.getStackTrace(e));
    			}
    			return new ResponseEntity<EsitoDto>(new EsitoDto(false, "Errore imprevisto"), HttpStatus.BAD_REQUEST);
    		}
    	}else {
    		return new ResponseEntity<EsitoDto>(new EsitoDto(false, "Occorre prima evadere l'ordine attualmente in lavorazione."), HttpStatus.BAD_REQUEST);
    	}
    }
    
    /**
     * PUT  /ordineEvaso
     */
	@Operation(summary = "Modifica lo stato dell'ordine in Evaso")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Esito positivo della modifica", 
			    content = { @Content(mediaType = "application/json", 
			      schema = @Schema(implementation = EsitoDto.class)) }),
			  @ApiResponse(responseCode = "404", description = "Ordine non trovato", 
			    content = @Content), 
			  @ApiResponse(responseCode = "304", description = "Errore e nessuna modifica apporta", 
			    content = @Content),
			  @ApiResponse(responseCode = "400", description = "Errore di sistema", 
			    content = @Content)})
	
    @RequestMapping(value = "/ordineEvaso",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EsitoDto> ordineEvaso(final HttpServletRequest request,
    		@RequestParam(value = "idOrdine" , required = true) final Long idOrdine) {
		try {
			ordineService.modificaStato(idOrdine, StatoOrdineEnum.EVASO);
			return new ResponseEntity<EsitoDto>(new EsitoDto(true, "Operazione eseguita con successo"), HttpStatus.OK);
		}catch(DataNotFoundException e) {
			return new ResponseEntity<EsitoDto>(new EsitoDto(false, e.getMessage()), HttpStatus.NOT_FOUND);
		}catch(ServiceException e) {
			return new ResponseEntity<EsitoDto>(new EsitoDto(false, e.getMessage()), HttpStatus.NOT_MODIFIED);
		}catch(Exception e) {
			if(log.isDebugEnabled()) {
				log.debug(ExceptionUtils.getStackTrace(e));
			}
			return new ResponseEntity<EsitoDto>(new EsitoDto(false, "Errore imprevisto"), HttpStatus.BAD_REQUEST);
		}
    }
	
	/**
     * GET  /statoOrdine
     */
	@Operation(summary = "Verifica lo stato di uno specifico ordine")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Stato dell'ordine", 
			    content = { @Content(mediaType = "application/json", 
			      schema = @Schema(implementation = StatoOrdineDto.class)) }),
			  @ApiResponse(responseCode = "404", description = "Ordine non trovato", 
			    content = @Content), 
			  @ApiResponse(responseCode = "304", description = "Errore e nessuna modifica apporta", 
			    content = @Content),
			  @ApiResponse(responseCode = "400", description = "Errore di sistema", 
			    content = @Content)})
	
    @RequestMapping(value = "/statoOrdine",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatoOrdineDto> statoOrdine(final HttpServletRequest request,
    		@RequestParam(value = "idOrdine" , required = true) final Long idOrdine) {
    	try {
    		StatoOrdineEnum stato = ordineService.getStatoOrdine(idOrdine);
    		StatoOrdineDto response = new StatoOrdineDto(new EsitoDto(true));
	    	response.setStato(stato);
			return new ResponseEntity<StatoOrdineDto>(response, HttpStatus.OK);
    	}catch(DataNotFoundException e) {
			return new ResponseEntity<StatoOrdineDto>(new StatoOrdineDto(new EsitoDto(false, e.getMessage())), HttpStatus.NOT_FOUND);
		}catch(ServiceException e) {
			return new ResponseEntity<StatoOrdineDto>(new StatoOrdineDto(new EsitoDto(false, e.getMessage())), HttpStatus.NOT_MODIFIED);
		}catch(Exception e) {
			if(log.isDebugEnabled()) {
				log.debug(ExceptionUtils.getStackTrace(e));
			}
			return new ResponseEntity<StatoOrdineDto>(new StatoOrdineDto(new EsitoDto(false, "Errore imprevisto")), HttpStatus.BAD_REQUEST);
		}
    }
}
