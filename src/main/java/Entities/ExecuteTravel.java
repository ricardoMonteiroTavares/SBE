package Entities;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@NamedQueries(
	{ 
		@NamedQuery(name = "ExecuteTravel.getAllExTravelsByDate", query = "select e from ExecuteTravel e where e.id_travel= ?1 AND e.date= ?2 order by e.id"),
		@NamedQuery(name = "ExecuteTravel.getAllExTravelsByPeriod", query = "select e from ExecuteTravel e where e.id_travel= ?1 AND e.date>= ?2 AND e.date <= ?3 order by e.id"),
		@NamedQuery(name = "ExecuteTravel.getAllExTravelsByDateAndDirection", query = "select e from ExecuteTravel e where e.id_travel= ?1 AND e.date= ?2 AND e.direction LIKE ?3 order by e.id"),
		@NamedQuery(name = "ExecuteTravel.getAllExTravelsByPeriodAndDirection", query = "select e from ExecuteTravel e where e.id_travel= ?1 AND e.date>= ?2 AND e.date <= ?3 AND e.direction LIKE ?4 order by e.id")
	}
)

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
	private Calendar date;
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
			Calendar date,
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
	
	@Temporal(TemporalType.DATE)
	public Calendar getDate() {
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
	
	public void setDate(Calendar date) {
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
	
	public void setVersion(int version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
	return "\nID do Intinerário: " + this.id + "\nID da Viagem: " + this.id_travel + "\nEmpresa: " + this.company + "\nCód. do Veículo:" + this.vehicleCode + "\nTarifa: " + this.ticketValue + "\nDia: " + this.date + "\nHora: " + this.time + "\nDireção: " + this.direction;
	}

}
