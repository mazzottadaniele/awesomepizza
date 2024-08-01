package it.adesso.awesomepizza.data.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.adesso.awesomepizza.data.enumerations.StatoOrdineEnum;

@Entity
@Table(name = "ordine")
public class Ordine implements Serializable{
	private static final long serialVersionUID = 7814196757358834394L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "stato")
	private StatoOrdineEnum stato;

	@Column(name = "cliente")
	private String cliente;
	
	@OneToMany(mappedBy = "ordine", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<OrdinePizza> dettaglio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StatoOrdineEnum getStato() {
		return stato;
	}

	public void setStato(StatoOrdineEnum stato) {
		this.stato = stato;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public List<OrdinePizza> getDettaglio() {
		return dettaglio;
	}

	public void setDettaglio(List<OrdinePizza> dettaglio) {
		this.dettaglio = dettaglio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((dettaglio == null) ? 0 : dettaglio.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((stato == null) ? 0 : stato.hashCode());
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
		Ordine other = (Ordine) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (dettaglio == null) {
			if (other.dettaglio != null)
				return false;
		} else if (!dettaglio.equals(other.dettaglio))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (stato != other.stato)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ordine [id=" + id + ", stato=" + stato + ", cliente=" + cliente + ", dettaglio=" + dettaglio + "]";
	}
}
