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
@Table(name="card")
public class Card {
	
	// Variáveis do Objeto
	private Long code;
	private String name;
	private double balance;
	private int version;
	
	// Construtores
	public Card() {}
	
	public Card(String name) 
	{
		this.name = name;
		this.balance = 0;
	}
	
	public Card(String name, double balance) 
	{
		this.name = name;
		this.balance = balance;
	}
	
	// Getters
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CODE")
	public Long getCode() 
	{
		return this.code; 
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public double getBalance()
	{
		return this.balance;
	}
	
	@Version
	public int getVersion() {
		return version;
	}

	
	// Setters
	
	@SuppressWarnings("unused")
	private void setCode(Long code) 
	{
		this.code = code;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setBalance(double balance)
	{
		this.balance = balance;
	}
	

	public void setVersion(int version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
		return "\nCódigo do cartão: "+this.code+"\nNome: "+this.name+"\nSaldo: "+this.balance + "\nVersão:" + this.version;
	}
}