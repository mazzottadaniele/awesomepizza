package it.adesso.awesomepizza.data.dto;

import java.util.List;

public class OrdinePizzaDtoResponse{
	private PizzaDto pizza;
	private List<AggiuntaDto> aggiunte;
	
	public PizzaDto getPizza() {
		return pizza;
	}
	public void setPizza(PizzaDto pizza) {
		this.pizza = pizza;
	}
	public List<AggiuntaDto> getAggiunte() {
		return aggiunte;
	}
	public void setAggiunte(List<AggiuntaDto> aggiunte) {
		this.aggiunte = aggiunte;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aggiunte == null) ? 0 : aggiunte.hashCode());
		result = prime * result + ((pizza == null) ? 0 : pizza.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdinePizzaDtoResponse other = (OrdinePizzaDtoResponse) obj;
		if (aggiunte == null) {
			if (other.aggiunte != null)
				return false;
		} else if (!aggiunte.equals(other.aggiunte))
			return false;
		if (pizza == null) {
			if (other.pizza != null)
				return false;
		} else if (!pizza.equals(other.pizza))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "OrdinePizzaDTOResponse [pizza=" + pizza + ", aggiunte=" + aggiunte + "]";
	}
}
