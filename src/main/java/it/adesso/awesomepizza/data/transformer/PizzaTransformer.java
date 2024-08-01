package it.adesso.awesomepizza.data.transformer;

import org.springframework.stereotype.Component;

import it.adesso.awesomepizza.data.dto.PizzaDto;
import it.adesso.awesomepizza.data.persistence.entity.Pizza;

@Component
public class PizzaTransformer extends GenericTransformer<Pizza, PizzaDto>{

}
