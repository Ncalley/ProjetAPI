package fr.miage.m2.bankprojectcompte.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.miage.m2.bankprojectcompte.model.Compte;

public interface CompteRepository extends JpaRepository<Compte, String>{

}
