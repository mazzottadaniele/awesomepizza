package it.adesso.awesomepizza.data.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OrdinePizzaDtoRequest{
	@NotNull
	@NotEmpty
	private Long idPizza;
	
	private List<Long> idsAggiunte;
	
	public Long getIdPizza() {
		return idPizza;
	}
	public void setIdPizza(Long idPizza) {
		this.idPizza = idPizza;
	}
	public List<Long> getIdsAggiunte() {
		return idsAggiunte;
	}
	public void setIdsAggiunte(List<Long> idsAggiunte) {
		this.idsAggiunte = idsAggiunte;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPizza == null) ? 0 : idPizza.hashCode());
		result = prime * result + ((idsAggiunte == null) ? 0 : idsAggiunte.hashCode());
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
		OrdinePizzaDtoRequest other = (OrdinePizzaDtoRequest) obj;
		if (idPizza == null) {
			if (other.idPizza != null)
				return false;
		} else if (!idPizza.equals(other.idPizza))
			return false;
		if (idsAggiunte == null) {
			if (other.idsAggiunte != null)
				return false;
		} else if (!idsAggiunte.equals(other.idsAggiunte))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "OrdinePizzaDTORequest [idPizza=" + idPizza + ", idsAggiunte=" + idsAggiunte + "]";
	}
}