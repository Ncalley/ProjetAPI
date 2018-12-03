package fr.miage.m2.bankprojecttransfert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.miage.m2.bankprojecttransfert.model.Transfert;

@Repository
public interface TransfertRepository extends JpaRepository<Transfert, String>{

}
