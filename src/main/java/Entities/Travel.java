package Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="travel")
public class Travel {
	
	// Variáveis do Objeto
	private Long code;
	private String line;
	private String origin;
	private String destination;
	private double ticketValue;
	
	private int version;
	
	// Construtores
	public Travel() {}
	
	public Travel(String line, String origin, String destination, double ticketValue) {
		this.line = line;
		this.origin = origin;
		this.destination = destination;
		this.ticketValue = ticketValue;
	}
	
	// Getters
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CODE")
	private Long getCode() {
		return code;
	}
	public String getLine() {
		return line;
	}
	public String getOrigin() {
		return origin;
	}
	public String getDestination() {
		return destination;
	}
	public double getTicketValue() {
		return ticketValue;
	}
	
	@Version
	public int getVersion() {
		return version;
	}
	
	// Setters
	@SuppressWarnings("unused")
	private void setCode(Long code) {
		this.code = code;
	}
	
	public void setLine(String line) {
		this.line = line;
	}
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public void setTicketValue(double ticketValue) {
		this.ticketValue = ticketValue;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
}
