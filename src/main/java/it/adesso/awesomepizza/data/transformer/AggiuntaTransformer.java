package it.adesso.awesomepizza.data.transformer;

import org.springframework.stereotype.Component;

import it.adesso.awesomepizza.data.dto.AggiuntaDto;
import it.adesso.awesomepizza.data.persistence.entity.Aggiunta;

@Component
public class AggiuntaTransformer extends GenericTransformer<Aggiunta, AggiuntaDto>{

}
