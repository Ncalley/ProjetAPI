package fr.miage.m2.bankprojectcarte.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import fr.miage.m2.bankprojectcarte.model.Carte;
import fr.miage.m2.bankprojectcarte.repository.CarteRepository;
import fr.miage.m2.bankprojectcarte.controller.CarteController;
import fr.miage.m2.bankprojectcarte.model.Carte;

@RestController
@RequestMapping(value = "cartes", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Carte.class)
public class CarteController {

	@Autowired
	private CarteRepository repository;

	public CarteController(CarteRepository repository) {
		super();
		this.repository = repository;
	}
	
	//Get methods *****************************************************************************
	
	@GetMapping("{carteId}")
	public ResponseEntity<?> getCarte(@PathVariable("carteId") String id) {
		//return repository.findById(id).toString();
		return Optional.ofNullable(repository.findById(id))
				.filter(Optional::isPresent)
				.map(i -> new ResponseEntity<>(carteToResource(i.get(), true), HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@GetMapping
	public ResponseEntity<?> getAllCartes() {
		Iterable<Carte> allCartes = repository.findAll();
		return new ResponseEntity<>(carteToResource(allCartes), HttpStatus.OK);
	}
	
	@GetMapping("ofCompte/{id}")
	public ResponseEntity<?> getAllCartes(@PathVariable String id) {
		return new ResponseEntity<>(cartesOfCompte(id), HttpStatus.OK);
	}
	
	// Post methods ******************************************************************************
	
	@PostMapping
	public ResponseEntity<?> postCarte(@RequestBody Carte carte) {
		carte.setId(UUID.randomUUID().toString());
		Carte saved = repository.save(carte);
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.setLocation(linkTo(CarteController.class).slash(saved.getId()).toUri());
		return new ResponseEntity<>(null, responseHeader, HttpStatus.CREATED);
	}
	
	//Put methods *********************************************************************************
	
	@PutMapping("{id}")
	public ResponseEntity<?> putCarte(@RequestBody Carte carte, @PathVariable String id) {
		Optional<Carte> carteOptional = repository.findById(id);
		if(carteOptional.isPresent()) {
			carte.setId(id);
			repository.save(carte);
			return new ResponseEntity<>(carteToResource(carte,true),HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	//Delete methods ********************************************************************************
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteCarte(@PathVariable String id) {
		repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Internal methods *******************************************************************************
	
	private Resources<Resource<Carte>> carteToResource(Iterable<Carte> cartes){
		Link selfLink = linkTo(methodOn(CarteController.class).getAllCartes()).withSelfRel();
        List<Resource<Carte>> carteRessources = new ArrayList();
        cartes.forEach(carte
                -> carteRessources.add(carteToResource(carte, false)));
        return new Resources<>(carteRessources, selfLink);
	}
	
	private Resource<Carte> carteToResource(Carte carte, Boolean collection){
		Link selfLink = linkTo(CarteController.class)
				.slash(carte.getId())
				.withSelfRel();
		if(collection) {
			Link collectionLink = linkTo(methodOn(CarteController.class).getAllCartes())
					.withSelfRel();
			return new Resource<>(carte, selfLink, collectionLink);
		}else {
			return new Resource<>(carte, selfLink);
		}
	}
	
	private Iterable<Carte> cartesOfCompte(String idCompte){
		Iterable<Carte> cartes = repository.findAll();
		ArrayList<Carte> selectedCartes = new ArrayList();
		for (Carte elt : cartes) {
			if(elt.getIdCompte().equals(idCompte)) {
				selectedCartes.add(elt);
			}
		}
		return selectedCartes;
	}
}
