package fr.miage.m2.bankprojectoperation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.miage.m2.bankprojectoperation.model.Operation;

@Repository
public interface OperationRepository extends JpaRepository<Operation, String>{

}
