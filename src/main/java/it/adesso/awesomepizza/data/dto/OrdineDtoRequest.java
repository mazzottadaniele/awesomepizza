package it.adesso.awesomepizza.data.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OrdineDtoRequest {
	@NotNull
	@NotEmpty
	private String cliente;
	
	@NotNull
	@NotEmpty
	private List<OrdinePizzaDtoRequest> pizze;

	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public List<OrdinePizzaDtoRequest> getPizze() {
		return pizze;
	}
	public void setPizze(List<OrdinePizzaDtoRequest> pizze) {
		this.pizze = pizze;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((pizze == null) ? 0 : pizze.hashCode());
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
		OrdineDtoRequest other = (OrdineDtoRequest) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (pizze == null) {
			if (other.pizze != null)
				return false;
		} else if (!pizze.equals(other.pizze))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "OrdineDTO [cliente=" + cliente + ", pizze=" + pizze + "]";
	}
}
