package it.adesso.awesomepizza.data.dto;

import java.util.List;

public class PizzaDtoListResponse extends SimpleResponseDto{
	public PizzaDtoListResponse(EsitoDto esito) {
		super(esito);
	}

	private List<PizzaDto> list;

	public List<PizzaDto> getList() {
		return list;
	}

	public void setList(List<PizzaDto> list) {
		this.list = list;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PizzaDtoListResponse other = (PizzaDtoListResponse) obj;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PizzaDtoListResponse [list=" + list + "]";
	}
}
