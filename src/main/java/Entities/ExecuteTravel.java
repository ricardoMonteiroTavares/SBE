package Entities;

import java.util.Date;

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
@Table(name="execute_travel")
public class ExecuteTravel {
	
	// Variáveis do Objeto
	private Long id;
	private Long id_travel;
	private String company;
	private String vehicleCode;
	private Date date;
	private String time;
	private String direction;
	private double ticketValue;
	
	private int version;
	
	// Construtores
	public ExecuteTravel() {}
	
	public ExecuteTravel(
			Long id_travel, 
			String company,
			String vehicleCode,
			Date date,
			String time,
			String direction,
			double ticketValue
		) {
		this.id_travel = id_travel;
		this.company = company;
		this.vehicleCode = vehicleCode;
		this.date = date;
		this.time = time;
		this.direction = direction;
		this.ticketValue = ticketValue;
	}
	
	// Getters
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	public Long getId() {
		return id;
	}
	
	public Long getId_travel() {
		return id_travel;
	}
	
	public String getCompany() {
		return company;
	}

	public String getVehicleCode() {
		return vehicleCode;
	}
	
	public Date getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}
	
	public String getDirection() {
		return direction;
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
	private void setId(Long id) {
		this.id = id;
	}
	
	public void setId_travel(Long id_travel) {
		this.id_travel = id_travel;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public void setVehicleCode(String vehicleCode) {
		this.vehicleCode = vehicleCode;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public void setTicketValue(double ticketValue) {
		this.ticketValue = ticketValue;
	}

}
