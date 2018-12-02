package fr.miage.m2.bankprojectcompte.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Compte implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id = UUID.randomUUID().toString();
	private String nom = "";
	private String prenom = "";
	private Date dateNaiss = new Date();
	private String pays = "";
	private String numPass = "";
	private String numTel = "";
	private String secret = "";
	private String iban = "";
	private Double solde;
	
	public Compte(String nom, String prenom, Date dateNaiss, String pays, String numPass, String numTel, String secret,
			String iBAN, Double solde) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaiss = dateNaiss;
		this.pays = pays;
		this.numPass = numPass;
		this.numTel = numTel;
		this.secret = secret;
		iban = iBAN;
		this.solde = solde;
	}

	public Compte() {
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Date getDateNaiss() {
		return dateNaiss;
	}

	public void setDateNaiss(Date dateNaiss) {
		this.dateNaiss = dateNaiss;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getNumPass() {
		return numPass;
	}

	public void setNumPass(String numPass) {
		this.numPass = numPass;
	}

	public String getNumTel() {
		return numTel;
	}

	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iBAN) {
		iban = iBAN;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public Double getSolde() {
		return solde;
	}

	public void setSolde(Double solde) {
		this.solde = solde;
	}

	@Override
	public String toString() {
		return "Compte [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", dateNaiss=" + dateNaiss + ", pays="
				+ pays + ", numPass=" + numPass + ", numTel=" + numTel + ", secret=" + secret + ", IBAN=" + iban + "]";
	}
	
	
}
