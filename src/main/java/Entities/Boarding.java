package Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@NamedQueries(
	{ 
		@NamedQuery(name = "Boarding.getAllBoardingsByDate", query = "select b from Boarding b where b.id_card= ?1 AND b.date = ?2 order by b.id"),
		@NamedQuery(name = "Boarding.getAllBoardingsByPeriod", query = "select b from Boarding b where b.id_card= ?1 AND b.date >= ?2 AND b.date <= ?3 order by b.id")
	}
)


@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="boarding")
public class Boarding {
	
	// Variáveis do Objeto
	private Long id;
	private Long id_executeTravel;
	private Long id_card;
	private String date;
	private String time;
	
	private int version;
	
	// Construtores
	public Boarding(){}
	
	public Boarding(Long id_executeTravel, Long id_card, String date, String time) {
		this.setId_executeTravel(id_executeTravel);
		this.setId_card(id_card);
		this.setDate(date);
		this.setTime(time);
	}

	// Getters
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	public Long getId() {
		return id;
	}
	
	public Long getId_executeTravel() {
		return id_executeTravel;
	}
	
	public Long getId_card() {
		return id_card;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}
	
	@Version
	public int getVersion() {
		return version;
	}

	// Setters
	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}	

	public void setId_executeTravel(Long id_executeTravel) {
		this.id_executeTravel = id_executeTravel;
	}

	public void setId_card(Long id_card) {
		this.id_card = id_card;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
}
