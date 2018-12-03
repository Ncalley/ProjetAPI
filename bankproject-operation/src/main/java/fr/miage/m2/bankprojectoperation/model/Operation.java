package fr.miage.m2.bankprojectoperation.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import fr.miage.m2.bankprojectoperation.model.Categorie;

@Entity
public class Operation {

	@Id
	private String id = UUID.randomUUID().toString();

	private String idCompte;
	private Date date;
	private String libelle;
	private Double montant;
	private Double taux;
	private String commercant;
	private Categorie categorie;
	private String pays;
	
	public Operation(String idCompte, Date date, String libelle, double montant, double taux, String commercant, Categorie categorie,
			String pays) {
		super();
		this.idCompte = idCompte;
		this.date = date;
		this.libelle = libelle;
		this.montant = montant;
		this.taux = taux;
		this.commercant = commercant;
		this.categorie = categorie;
		this.pays = pays;
	}

	public Operation() {
		super();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public double getTaux() {
		return taux;
	}

	public void setTaux(double taux) {
		this.taux = taux;
	}

	public String getCommercant() {
		return commercant;
	}

	public void setCommercant(String commercant) {
		this.commercant = commercant;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
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
