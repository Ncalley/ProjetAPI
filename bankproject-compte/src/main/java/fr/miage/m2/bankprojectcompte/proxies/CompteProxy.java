package fr.miage.m2.bankprojectcompte.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.miage.m2.bankprojectcompte.model.Compte;

@FeignClient("compte-service")
public interface CompteProxy {

	@GetMapping("{id}")
	public String getCompte(@PathVariable String id);
	
	@GetMapping
	public Iterable<Compte> getAllComptes();
}
