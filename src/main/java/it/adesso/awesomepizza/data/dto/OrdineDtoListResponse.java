package it.adesso.awesomepizza.data.dto;

import java.util.List;

public class OrdineDtoListResponse extends SimpleResponseDto{
	public OrdineDtoListResponse(EsitoDto esito) {
		super(esito);
	}
	
	private List<OrdineDtoResponse> list;
	
	public List<OrdineDtoResponse> getList() {
		return list;
	}
	public void setList(List<OrdineDtoResponse> list) {
		this.list = list;
	}
}
