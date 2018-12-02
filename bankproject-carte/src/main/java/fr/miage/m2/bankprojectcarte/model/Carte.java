package fr.miage.m2.bankprojectcarte.model;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Carte implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id = UUID.randomUUID().toString();
	private String idCompte ;
	private String numCarte;
	private String code;
	private String cryptogramme;
	private boolean bloquee = false;
	private boolean localisation = false;
	private Double plafond = 9999999999D;
	private boolean sansContact = false;
	private boolean virtuelle = false;
	
	public Carte(String idCompte, String numCarte, String code, String cryptogramme, boolean bloquee, boolean localisation,
			double plafond, boolean sansContact, boolean virtuelle) {
		super();
		this.idCompte = idCompte;
		this.numCarte = numCarte;
		this.code = code;
		this.cryptogramme = cryptogramme;
		this.bloquee = bloquee;
		this.localisation = localisation;
		this.plafond = plafond;
		this.sansContact = sansContact;
		this.virtuelle = virtuelle;
	}
	
	
	
	public Carte(String idCompte, String numCarte) {
		super();
		this.idCompte = idCompte;
		this.numCarte = numCarte;
		this.code = "0000";
		this.cryptogramme = "000";
	}



	public Carte() {
		super();
	}


	public String getIdCompte() {
		return idCompte;
	}

	public void setIdCompte(String idCompte) {
		this.idCompte = idCompte;
	}

	public void setPlafond(Double plafond) {
		this.plafond = plafond;
	}

	
	public String getNumCarte() {
		return numCarte;
	}

	public void setNumCarte(String numCarte) {
		this.numCarte = numCarte;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCryptogramme() {
		return cryptogramme;
	}

	public void setCryptogramme(String cryptogramme) {
		this.cryptogramme = cryptogramme;
	}

	public boolean isBloquee() {
		return bloquee;
	}

	public void setBloquee(boolean bloquee) {
		this.bloquee = bloquee;
	}

	public boolean isLocalisation() {
		return localisation;
	}

	public void setLocalisation(boolean localisation) {
		this.localisation = localisation;
	}

	public double getPlafond() {
		return plafond;
	}

	public void setPlafond(double plafond) {
		this.plafond = plafond;
	}

	public boolean isSansContact() {
		return sansContact;
	}

	public void setSansContact(boolean sansContact) {
		this.sansContact = sansContact;
	}

	public boolean isVirtuelle() {
		return virtuelle;
	}

	public void setVirtuelle(boolean virtuelle) {
		this.virtuelle = virtuelle;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id=id;
	}
}
