package fr.miage.m2.bankprojectcarte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.miage.m2.bankprojectcarte.model.Carte;

@Repository
public interface CarteRepository extends JpaRepository<Carte, String> {
	
	public Iterable<Carte> findByIdCompte(String idCompte);
}
