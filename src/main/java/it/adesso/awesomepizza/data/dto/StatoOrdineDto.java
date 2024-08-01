package it.adesso.awesomepizza.data.dto;

import it.adesso.awesomepizza.data.enumerations.StatoOrdineEnum;

public class StatoOrdineDto extends SimpleResponseDto{
	public StatoOrdineDto(StatoOrdineEnum stato, EsitoDto esito) {
		super(esito);
		this.stato = stato;
	}
	
	public StatoOrdineDto(EsitoDto esito) {
		super(esito);
	}
	
	private StatoOrdineEnum stato;

	public StatoOrdineEnum getStato() {
		return stato;
	}

	public void setStato(StatoOrdineEnum stato) {
		this.stato = stato;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((stato == null) ? 0 : stato.hashCode());
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
		StatoOrdineDto other = (StatoOrdineDto) obj;
		if (stato != other.stato)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StatoOrdineDto [stato=" + stato + "]";
	}
}
