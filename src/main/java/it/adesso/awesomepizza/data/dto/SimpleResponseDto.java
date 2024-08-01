package it.adesso.awesomepizza.data.dto;

public class SimpleResponseDto {
	public SimpleResponseDto(EsitoDto esito) {
		this.esito = esito;
	}
	
	private EsitoDto esito;

	public EsitoDto getEsito() {
		return esito;
	}

	public void setEsito(EsitoDto esito) {
		this.esito = esito;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((esito == null) ? 0 : esito.hashCode());
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
		SimpleResponseDto other = (SimpleResponseDto) obj;
		if (esito == null) {
			if (other.esito != null)
				return false;
		} else if (!esito.equals(other.esito))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SimpleResponseDto [esito=" + esito + "]";
	}
}
