package it.adesso.awesomepizza.data.dto;

import java.util.List;

public class OrdineDtoResponse {
	private Long idOrdine;
	private String cliente;
	private List<OrdinePizzaDtoResponse> pizze;
	
	public Long getIdOrdine() {
		return idOrdine;
	}
	public void setIdOrdine(Long idOrdine) {
		this.idOrdine = idOrdine;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public List<OrdinePizzaDtoResponse> getPizze() {
		return pizze;
	}
	public void setPizze(List<OrdinePizzaDtoResponse> pizze) {
		this.pizze = pizze;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((idOrdine == null) ? 0 : idOrdine.hashCode());
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
		OrdineDtoResponse other = (OrdineDtoResponse) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (idOrdine == null) {
			if (other.idOrdine != null)
				return false;
		} else if (!idOrdine.equals(other.idOrdine))
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
		return "OrdineDTOResponse [idOrdine=" + idOrdine + ", cliente=" + cliente + ", pizze=" + pizze + "]";
	}
}
