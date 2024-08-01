package it.adesso.awesomepizza.data.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ordine_pizza")
public class OrdinePizza implements Serializable{
	private static final long serialVersionUID = 7814196257358834394L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "ordine_id", insertable = true, updatable = false)
	private Ordine ordine;
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "pizza_id", insertable = true, updatable = false)
	private Pizza pizza;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ordine_pizza_aggiunta", joinColumns = {
			@JoinColumn(name = "ordine_pizza_id", nullable = false, updatable = false, insertable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "aggiunta_id", nullable = false, updatable = false, insertable = false) })
	private List<Aggiunta> aggiunte;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ordine getOrdine() {
		return ordine;
	}

	public void setOrdine(Ordine ordine) {
		this.ordine = ordine;
	}

	public Pizza getPizza() {
		return pizza;
	}

	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}

	public List<Aggiunta> getAggiunte() {
		return aggiunte;
	}

	public void setAggiunte(List<Aggiunta> aggiunte) {
		this.aggiunte = aggiunte;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aggiunte == null) ? 0 : aggiunte.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ordine == null) ? 0 : ordine.hashCode());
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
		OrdinePizza other = (OrdinePizza) obj;
		if (aggiunte == null) {
			if (other.aggiunte != null)
				return false;
		} else if (!aggiunte.equals(other.aggiunte))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ordine == null) {
			if (other.ordine != null)
				return false;
		} else if (!ordine.equals(other.ordine))
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
		return "OrdinePizza [id=" + id + ", ordine=" + ordine + ", pizza=" + pizza + ", aggiunte=" + aggiunte + "]";
	}
}
