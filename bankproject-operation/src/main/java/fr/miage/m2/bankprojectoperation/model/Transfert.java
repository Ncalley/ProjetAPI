package fr.miage.m2.bankprojectoperation.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Transfert {

	@Id
	private String id = UUID.randomUUID().toString();
	
	private String idCompte;
	private Date date;
	private String IBANCible;
	private Double Montant;
	
	public Transfert(String idCompte, Date date, String iBANCible, Double montant) {
		super();
		this.idCompte = idCompte;
		this.date = date;
		IBANCible = iBANCible;
		Montant = montant;
	}

	public Transfert() {
		super();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getIBANCible() {
		return IBANCible;
	}

	public void setIBANCible(String iBANCible) {
		IBANCible = iBANCible;
	}

	public Double getMontant() {
		return Montant;
	}

	public void setMontant(Double montant) {
		Montant = montant;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdCompte() {
		return idCompte;
	}

	public void setIdCompte(String idCompte) {
		this.idCompte = idCompte;
	}
	
	
}
