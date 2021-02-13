package Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Card")
public class Card {
	
	// Variáveis do Objeto
	private String code;
	private String name;
	private float balance;
	
	
	// Construtores
	public Card() {}
	
	public Card(String name) 
	{
		this.name = name;
		this.balance = 0;
	}
	
	public Card(String name, float balance) 
	{
		this.name = name;
		this.balance = balance;
	}
	
	// Getters
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CODE")
	public String getCode() 
	{
		return this.code; 
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public float getBalance()
	{
		return this.balance;
	}
	
	// Setters
	
	@SuppressWarnings("unused")
	private void setCode(String code) 
	{
		this.code = code;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setBalance(float balance)
	{
		this.balance = balance;
	}
}