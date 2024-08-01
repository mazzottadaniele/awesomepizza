package it.adesso.awesomepizza.test;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.adesso.awesomepizza.configuration.Application;
import it.adesso.awesomepizza.data.dto.AggiuntaDto;
import it.adesso.awesomepizza.data.dto.OrdineDtoRequest;
import it.adesso.awesomepizza.data.dto.OrdineDtoResponse;
import it.adesso.awesomepizza.data.dto.OrdineIdDto;
import it.adesso.awesomepizza.data.dto.OrdinePizzaDtoRequest;
import it.adesso.awesomepizza.data.dto.PizzaDto;
import it.adesso.awesomepizza.service.OrdineService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrdineTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private OrdineService ordineService;
	
	@Test
    public void verificaCreazioneOrdine() throws Exception {
    	
    	PizzaDto pizzaDto = getExamplePizza();
    	AggiuntaDto aggiuntaDto = getExampleAggiunta();
    	
    	List<OrdineDtoResponse> ordiniDaLavorare = getOrdiniDaLavorare();
    	
    	if(pizzaDto!=null) {
	    	OrdineDtoRequest ordine = new OrdineDtoRequest();
	    	ordine.setCliente("test");
	    	OrdinePizzaDtoRequest req = new OrdinePizzaDtoRequest();
	    	req.setIdPizza(pizzaDto.getId());
	    	if(aggiuntaDto!=null) {
	    		req.setIdsAggiunte(Arrays.asList(aggiuntaDto.getId()));
	    	}
	    	ordine.setPizze(Arrays.asList(req));
	    	
	    	Object ret = mockMvc.perform( MockMvcRequestBuilders
	                .post("/api/nuovoOrdine")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(gson.toJson(ordine))
	                ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
	    			.andExpect(MockMvcResultMatchers.jsonPath("esito.success").value(true))
	    			.andReturn().getResponse().getContentAsString();
	    	
	    	JsonElement je = gson.fromJson(ret.toString(), JsonElement.class);
	    	
	    	assertNotNull(je);
	    	
	    	assertTrue(je.isJsonObject());
	    	
	    	JsonObject ordineIdJson = je.getAsJsonObject();
    		
    		assertNotNull(ordineIdJson);
    		
    		OrdineIdDto ordineIdDto = gson.fromJson(ordineIdJson, OrdineIdDto.class);
    		
    		assertTrue(ordineIdDto.getIdOrdine()!=null && ordineIdDto.getIdOrdine() > 0);
    		
    		List<OrdineDtoResponse> ordiniDaLavorarePostCreazione = getOrdiniDaLavorare();
    		
    		assertTrue((ordiniDaLavorare.size() + 1) == ordiniDaLavorarePostCreazione.size());
	    	
    		ordineService.eliminaOrdine(ordineIdDto.getIdOrdine());
    		
    		ordiniDaLavorarePostCreazione = getOrdiniDaLavorare();
    		
    		assertTrue(ordiniDaLavorare.size() == ordiniDaLavorarePostCreazione.size());
    	}
    }
	
    private List<OrdineDtoResponse> getOrdiniDaLavorare() throws Exception {
    	List<OrdineDtoResponse> ordini = new ArrayList<OrdineDtoResponse>();
    	Object ret = mockMvc.perform( MockMvcRequestBuilders
                .get("/api/ordiniDaLavorare")
                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
    			.andExpect(MockMvcResultMatchers.jsonPath("esito.success").value(true))
    			.andReturn().getResponse().getContentAsString();
    	
    	assertNotNull(ret);
    	
    	JsonElement je = gson.fromJson(ret.toString(), JsonElement.class);
    	
    	assertNotNull(je);
    	
    	assertTrue(je.isJsonObject());
    	
    	assertTrue(je.getAsJsonObject().has("list"));
    	
    	assertTrue(je.getAsJsonObject().get("list").isJsonArray());
    	
    	if(je.getAsJsonObject().get("list").getAsJsonArray().size() > 0) {
    		for(JsonElement ordineJson : je.getAsJsonObject().get("list").getAsJsonArray()) {
    			OrdineDtoResponse ordineDto = gson.fromJson(ordineJson, OrdineDtoResponse.class);
    			assertNotNull(ordineDto);
    			ordini.add(ordineDto);
    		}
    	}
    	
    	return ordini;
    }
    
    private PizzaDto getExamplePizza() throws Exception {
    	PizzaDto pizzaDto = null;
    	Object ret = mockMvc.perform( MockMvcRequestBuilders
                .get("/api/catalogoPizze")
                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
    			.andExpect(MockMvcResultMatchers.jsonPath("esito.success").value(true))
    			.andReturn().getResponse().getContentAsString();
    	
    	assertNotNull(ret);
    	
    	JsonElement je = gson.fromJson(ret.toString(), JsonElement.class);
    	
    	assertNotNull(je);
    	
    	assertTrue(je.isJsonObject());
    	
    	assertTrue(je.getAsJsonObject().has("list"));
    	
    	assertTrue(je.getAsJsonObject().get("list").isJsonArray());
    	
    	if(je.getAsJsonObject().get("list").getAsJsonArray().size() > 0) {
    		assertTrue(je.getAsJsonObject().get("list").getAsJsonArray().get(0).isJsonObject());
    		
    		JsonObject pizza = je.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject();
    		
    		assertNotNull(pizza);
    		
    		pizzaDto = gson.fromJson(pizza, PizzaDto.class);
    		
    		assertNotNull(pizzaDto);
    	}
    	
    	return pizzaDto;
    }
    
    private AggiuntaDto getExampleAggiunta() throws Exception {
    	AggiuntaDto aggiuntaDto = null;
    	Object ret = mockMvc.perform( MockMvcRequestBuilders
                .get("/api/catalogoAggiunte")
                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
    			.andExpect(MockMvcResultMatchers.jsonPath("esito.success").value(true))
    			.andReturn().getResponse().getContentAsString();
    	
    	assertNotNull(ret);
    	
    	JsonElement je = gson.fromJson(ret.toString(), JsonElement.class);
    	
    	assertNotNull(je);
    	
    	assertTrue(je.isJsonObject());
    	
    	assertTrue(je.getAsJsonObject().has("list"));
    	
    	assertTrue(je.getAsJsonObject().get("list").isJsonArray());
    	
    	if(je.getAsJsonObject().get("list").getAsJsonArray().size() > 0) {
    		assertTrue(je.getAsJsonObject().get("list").getAsJsonArray().get(0).isJsonObject());
    		
    		JsonObject aggiunta = je.getAsJsonObject().get("list").getAsJsonArray().get(0).getAsJsonObject();
    		
    		assertNotNull(aggiunta);
    		
    		aggiuntaDto = gson.fromJson(aggiunta, AggiuntaDto.class);
    		
    		assertNotNull(aggiuntaDto);
    	}
    	
    	return aggiuntaDto;
    }
}
