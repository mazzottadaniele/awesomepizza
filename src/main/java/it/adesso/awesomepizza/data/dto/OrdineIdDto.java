package it.adesso.awesomepizza.data.dto;

public class OrdineIdDto extends SimpleResponseDto{
	public OrdineIdDto(Long idOrdine, EsitoDto esito) {
		super(esito);
		this.idOrdine = idOrdine;
	}
	
	public OrdineIdDto(EsitoDto esito) {
		super(esito);
	}
	
	private Long idOrdine;
	
	public Long getIdOrdine() {
		return idOrdine;
	}
	public void setIdOrdine(Long idOrdine) {
		this.idOrdine = idOrdine;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idOrdine == null) ? 0 : idOrdine.hashCode());
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
		OrdineIdDto other = (OrdineIdDto) obj;
		if (idOrdine == null) {
			if (other.idOrdine != null)
				return false;
		} else if (!idOrdine.equals(other.idOrdine))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "OrdineIdDTOResponse [idOrdine=" + idOrdine + "]";
	}
}
