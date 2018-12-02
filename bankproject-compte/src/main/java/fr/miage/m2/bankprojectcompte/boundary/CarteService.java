package fr.miage.m2.bankprojectcompte.boundary;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.miage.m2.bankprojectcompte.model.Carte;


@FeignClient(name="cartes")
@RibbonClient(name="cartes")
@RequestMapping(value="cartes")
public interface CarteService {

	//Get methods *************************************************
	
	@GetMapping
	public ResponseEntity<?> getAllCartes();
	
	@GetMapping("{id}")
	public ResponseEntity<?> getCarte(@PathVariable String id) ;
	
	@GetMapping("ofCompte/{id}")
	public ResponseEntity<?> getAllCartes(@PathVariable String id);
	
	//Post methods ************************************************
	
	@PostMapping
	public String postCarte(@RequestBody Carte carte) ;
	
}