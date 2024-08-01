package it.adesso.awesomepizza.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.adesso.awesomepizza.data.dto.AggiuntaDto;
import it.adesso.awesomepizza.data.dto.AggiuntaDtoListResponse;
import it.adesso.awesomepizza.data.dto.EsitoDto;
import it.adesso.awesomepizza.data.dto.PizzaDto;
import it.adesso.awesomepizza.data.dto.PizzaDtoListResponse;
import it.adesso.awesomepizza.exceptions.DataNotFoundException;
import it.adesso.awesomepizza.exceptions.ServiceException;
import it.adesso.awesomepizza.service.AggiuntaService;
import it.adesso.awesomepizza.service.PizzaService;

@RestController
@RequestMapping("/api")
public class CatalogoRestController {
	
	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private AggiuntaService aggiuntaService;
	
	private final Logger log = LoggerFactory.getLogger(CatalogoRestController.class);
	
	/**
     * GET  /catalogoPizze
     */
	@Operation(summary = "Restituisce le pizze disponibili a catalogo")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Catalogo delle pizze", 
			    content = { @Content(mediaType = "application/json", 
			      schema = @Schema(implementation = PizzaDtoListResponse.class)) }),
			  @ApiResponse(responseCode = "404", description = "Catalogo non trovato", 
			    content = @Content), 
			  @ApiResponse(responseCode = "304", description = "Errore e nessuna modifica apporta", 
			    content = @Content),
			  @ApiResponse(responseCode = "400", description = "Errore di sistema", 
			    content = @Content)})
	
    @RequestMapping(value = "/catalogoPizze",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PizzaDtoListResponse> catalogoPizze(final HttpServletRequest request) {
		try {
	    	List<PizzaDto> catalogo = pizzaService.getCatalogo();
	    	PizzaDtoListResponse response = new PizzaDtoListResponse(new EsitoDto(true));
	    	response.setList(catalogo);
			return new ResponseEntity<PizzaDtoListResponse>(response, HttpStatus.OK);
		}catch(DataNotFoundException e) {
			return new ResponseEntity<PizzaDtoListResponse>(new PizzaDtoListResponse(new EsitoDto(false, e.getMessage())), HttpStatus.NOT_FOUND);
		}catch(ServiceException e) {
			return new ResponseEntity<PizzaDtoListResponse>(new PizzaDtoListResponse(new EsitoDto(false, e.getMessage())), HttpStatus.NOT_MODIFIED);
		}catch(Exception e) {
			if(log.isDebugEnabled()) {
				log.debug(ExceptionUtils.getStackTrace(e));
			}
			return new ResponseEntity<PizzaDtoListResponse>(new PizzaDtoListResponse(new EsitoDto(false, "Errore imprevisto")), HttpStatus.BAD_REQUEST);
		}
    }
    
    /**
     * GET  /catalogoAggiunte
     */
	@Operation(summary = "Restituisce le aggiunte disponibili a catalogo")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Catalogo delle aggiunte", 
			    content = { @Content(mediaType = "application/json", 
			      schema = @Schema(implementation = AggiuntaDtoListResponse.class)) }),
			  @ApiResponse(responseCode = "404", description = "Catalogo non trovato", 
			    content = @Content), 
			  @ApiResponse(responseCode = "304", description = "Errore e nessuna modifica apporta", 
			    content = @Content),
			  @ApiResponse(responseCode = "400", description = "Errore di sistema", 
			    content = @Content)})
	
    @RequestMapping(value = "/catalogoAggiunte",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AggiuntaDtoListResponse> catalogoAggiunte(final HttpServletRequest request) {
    	try {
	    	List<AggiuntaDto> catalogo = aggiuntaService.getCatalogo();
	    	AggiuntaDtoListResponse response = new AggiuntaDtoListResponse(new EsitoDto(true));
	    	response.setList(catalogo);
			return new ResponseEntity<AggiuntaDtoListResponse>(response, HttpStatus.OK);
    	}catch(DataNotFoundException e) {
			return new ResponseEntity<AggiuntaDtoListResponse>(new AggiuntaDtoListResponse(new EsitoDto(false, e.getMessage())), HttpStatus.NOT_FOUND);
		}catch(ServiceException e) {
			return new ResponseEntity<AggiuntaDtoListResponse>(new AggiuntaDtoListResponse(new EsitoDto(false, e.getMessage())), HttpStatus.NOT_MODIFIED);
		}catch(Exception e) {
			if(log.isDebugEnabled()) {
				log.debug(ExceptionUtils.getStackTrace(e));
			}
			return new ResponseEntity<AggiuntaDtoListResponse>(new AggiuntaDtoListResponse(new EsitoDto(false, "Errore imprevisto")), HttpStatus.BAD_REQUEST);
		}
    }
}
