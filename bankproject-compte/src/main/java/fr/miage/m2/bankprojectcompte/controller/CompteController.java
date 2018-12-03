package fr.miage.m2.bankprojectcompte.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.hateoas.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.client.RestTemplate;

import fr.miage.m2.bankprojectcompte.model.Carte;
import fr.miage.m2.bankprojectcompte.model.Compte;
import fr.miage.m2.bankprojectcompte.model.Operation;
import fr.miage.m2.bankprojectcompte.model.Transfert;
import fr.miage.m2.bankprojectcompte.repository.CompteRepository;
import fr.miage.m2.bankprojectcompte.repository.Config;

@RestController
@RequestMapping(value="comptes", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Compte.class)
public class CompteController {

	@Autowired
	private CompteRepository repository;
	
	@Autowired
	private RestTemplate restTemplate;
	private Config config;
	
	public CompteController(CompteRepository repository, RestTemplate restTemplate, Config config) {
		this.repository = repository;
		this.restTemplate = restTemplate;
		this.config = config;
	}

	
	// Get methods ***********************************************************
	
	@GetMapping("{compteId}")
	public ResponseEntity<?> getCompte(@PathVariable("compteId") String id) {
		//return repository.findById(id).toString();
		return Optional.ofNullable(repository.findById(id))
				.filter(Optional::isPresent)
				.map(i -> new ResponseEntity<>(compteToResource(i.get(), true), HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@GetMapping("{id}/cartes")
	public ResponseEntity<?> getAllCartes(@PathVariable("compteId") String id){
		String URI = config.getUrl()+":"+config.getPort()+"/cartes/ofCompte/"+id;
		ResponseEntity<?> response = restTemplate.getForEntity(URI, Object.class);
		
		return response;
	}
	
	@GetMapping("{id}/operations")
	public ResponseEntity<?> getAllOperations(@PathVariable("compteId") String id){
		String URI = config.getUrl()+":"+config.getPort()+"/operations/ofCompte/"+id;
		ResponseEntity<?> response = restTemplate.getForEntity(URI, Object.class);
		
		return response;
	}
	
	@GetMapping("{id}/transferts")
	public ResponseEntity<?> getAllTransferts(@PathVariable("compteId") String id){
		String URI = config.getUrl()+":"+config.getPort()+"/transferts/ofCompte/"+id;
		ResponseEntity<?> response = restTemplate.getForEntity(URI, Object.class);
		
		return response;
	}
	
	@GetMapping
	public ResponseEntity<?> getAllComptes() {
		Iterable<Compte> allComptes = repository.findAll();
		return new ResponseEntity<>(compteToResource(allComptes), HttpStatus.OK);
	}

	@GetMapping("{id}/solde")
	public ResponseEntity<?> getSolde(@PathVariable String id) {
		return Optional.ofNullable(repository.findById(id))
				.filter(Optional::isPresent)
				.map(i -> new ResponseEntity<>(compteToResource(i.get(),true).getContent().getSolde(),HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	//Post methods ***************************************************************
	
	@PostMapping("{compteId}/cartes")
	public ResponseEntity<?> postCarte(@PathVariable("compteId") String id, @RequestBody Carte carte){
		String URI = config.getUrl()+":"+config.getPort()+"/cartes";
		carte.setIdCompte(id);
		
		ResponseEntity<?> response = restTemplate.postForEntity(URI, carte, Carte.class);
		
		return response;
	}
	
	@PostMapping("{compteId}/operations")
	public ResponseEntity<?> postOperation(@PathVariable("compteId") String id, @RequestBody Operation operation){
		String URI = config.getUrl()+":"+config.getPort()+"/operations";
		operation.setIdCompte(id);
		
		ResponseEntity<?> response = restTemplate.postForEntity(URI, operation, Carte.class);
		
		return response;
	}
	
	@PostMapping("{compteId}/transferts")
	public ResponseEntity<?> postTransfert(@PathVariable("compteId") String id, @RequestBody Transfert transfert){
		String URI = config.getUrl()+":"+config.getPort()+"/cartes";
		transfert.setIdCompte(id);
		
		ResponseEntity<?> response = restTemplate.postForEntity(URI, transfert, Carte.class);
		
		return response;
	}
	
	@PostMapping
	public ResponseEntity<?> postCompte(@RequestBody Compte compte) {
		compte.setId(UUID.randomUUID().toString());
		Compte saved = repository.save(compte);
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.setLocation(linkTo(CompteController.class).slash(saved.getId()).toUri());
		return new ResponseEntity<>(null, responseHeader, HttpStatus.CREATED);
	}
	
	//Put methods ********************************************************************
	
	@PutMapping("{id}")
	public ResponseEntity<?> putCompte(@RequestBody Compte compte, @PathVariable String id) {
		Optional<Compte> compteOptional = repository.findById(id);
		if(compteOptional.isPresent()) {
			compte.setId(id);
			repository.save(compte);
			return new ResponseEntity<>(compteToResource(compte,true),HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("{id}/cartes/{idCarte}")
	public ResponseEntity<?> putCarte(@RequestBody Carte carte, @PathVariable String id, @PathVariable String idCarte){
		String URI = config.getUrl()+":"+config.getPort()+"/cartes/"+idCarte;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Carte> updatedCarte = new HttpEntity<>(carte, headers);
		ResponseEntity<?> response = restTemplate.exchange(URI, HttpMethod.PUT, updatedCarte, Object.class);
		return response;
	}
	
	@PutMapping("{id}/operations/{idOperation}")
	public ResponseEntity<?> putOperation(@RequestBody Operation Operation, @PathVariable String id, @PathVariable String idOperation){
		String URI = config.getUrl()+":"+config.getPort()+"/Operations/"+idOperation;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Operation> updatedOperation = new HttpEntity<>(Operation, headers);
		ResponseEntity<?> response = restTemplate.exchange(URI, HttpMethod.PUT, updatedOperation, Object.class);
		return response;
	}
	
	@PutMapping("{id}/Transferts/{idTransfert}")
	public ResponseEntity<?> putTransfert(@RequestBody Transfert Transfert, @PathVariable String id, @PathVariable String idTransfert){
		String URI = config.getUrl()+":"+config.getPort()+"/Transferts/"+idTransfert;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Transfert> updatedTransfert = new HttpEntity<>(Transfert, headers);
		ResponseEntity<?> response = restTemplate.exchange(URI, HttpMethod.PUT, updatedTransfert, Object.class);
		return response;
	}
	
	//delete methods **********************************************************************
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteCompte(@PathVariable String id) {
		repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("{id}/cartes/{idCarte}")
	public ResponseEntity<?> DeleteCarte(@PathVariable String id, @PathVariable String idCarte){
		String URI = config.getUrl()+":"+config.getPort()+"/cartes/"+idCarte;
		restTemplate.delete(URI);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("{id}/Operations/{idOperation}")
	public ResponseEntity<?> DeleteOperation(@PathVariable String id, @PathVariable String idOperation){
		String URI = config.getUrl()+":"+config.getPort()+"/Operations/"+idOperation;
		restTemplate.delete(URI);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("{id}/Transferts/{idTransfert}")
	public ResponseEntity<?> DeleteTransfert(@PathVariable String id, @PathVariable String idTransfert){
		String URI = config.getUrl()+":"+config.getPort()+"/Transferts/"+idTransfert;
		restTemplate.delete(URI);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Internal methods **********************************************************************
	
	private Resources<Resource<Compte>> compteToResource(Iterable<Compte> comptes){
		Link selfLink = linkTo(methodOn(CompteController.class).getAllComptes()).withSelfRel();
        List<Resource<Compte>> compteRessources = new ArrayList();
        comptes.forEach(compte
                -> compteRessources.add(compteToResource(compte, false)));
        return new Resources<>(compteRessources, selfLink);
	}
	
	private Resource<Compte> compteToResource(Compte compte, Boolean collection){
		Link selfLink = linkTo(CompteController.class)
				.slash(compte.getId())
				.withSelfRel();
		if(collection) {
			Link collectionLink = linkTo(methodOn(CompteController.class).getAllComptes())
					.withSelfRel();
			return new Resource<>(compte, selfLink, collectionLink);
		}else {
			return new Resource<>(compte, selfLink);
		}
	}
	
}
