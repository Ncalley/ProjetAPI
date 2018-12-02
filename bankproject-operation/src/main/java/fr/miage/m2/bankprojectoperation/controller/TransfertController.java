package fr.miage.m2.bankprojectoperation.controller;

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

import fr.miage.m2.bankprojectoperation.model.Transfert;
import fr.miage.m2.bankprojectoperation.repository.TransfertRepository;


@RestController
@RequestMapping(value = "transferts", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Transfert.class)
public class TransfertController {

	@Autowired
	private TransfertRepository repository;

	public TransfertController(TransfertRepository repository) {
		super();
		this.repository = repository;
	}
	
	//Get methods *****************************************************************************
	
	@GetMapping("{TransfertId}")
	public ResponseEntity<?> getTransfert(@PathVariable("TransfertId") String id) {
		//return repository.findById(id).toString();
		return Optional.ofNullable(repository.findById(id))
				.filter(Optional::isPresent)
				.map(i -> new ResponseEntity<>(TransfertToResource(i.get(), true), HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@GetMapping
	public ResponseEntity<?> getAllTransferts() {
		Iterable<Transfert> allTransferts = repository.findAll();
		return new ResponseEntity<>(TransfertToResource(allTransferts), HttpStatus.OK);
	}
	
	@GetMapping("ofCompte/{id}")
	public ResponseEntity<?> getAllTransferts(@PathVariable String id) {
		return new ResponseEntity<>(TransfertsOfCompte(id), HttpStatus.OK);
	}
	
	// Post methods ******************************************************************************
	
	@PostMapping
	public ResponseEntity<?> postTransfert(@RequestBody Transfert Transfert) {
		Transfert.setId(UUID.randomUUID().toString());
		Transfert saved = repository.save(Transfert);
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.setLocation(linkTo(TransfertController.class).slash(saved.getId()).toUri());
		return new ResponseEntity<>(null, responseHeader, HttpStatus.CREATED);
	}
	
	//Put methods *********************************************************************************
	
	@PutMapping("{id}")
	public ResponseEntity<?> putTransfert(@RequestBody Transfert Transfert, @PathVariable String id) {
		Optional<Transfert> TransfertOptional = repository.findById(id);
		if(TransfertOptional.isPresent()) {
			Transfert.setId(id);
			repository.save(Transfert);
			return new ResponseEntity<>(TransfertToResource(Transfert,true),HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	//Delete methods ********************************************************************************
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteTransfert(@PathVariable String id) {
		repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Internal methods *******************************************************************************
	
	private Resources<Resource<Transfert>> TransfertToResource(Iterable<Transfert> Transferts){
		Link selfLink = linkTo(methodOn(TransfertController.class).getAllTransferts()).withSelfRel();
        List<Resource<Transfert>> TransfertRessources = new ArrayList();
        Transferts.forEach(Transfert
                -> TransfertRessources.add(TransfertToResource(Transfert, false)));
        return new Resources<>(TransfertRessources, selfLink);
	}
	
	private Resource<Transfert> TransfertToResource(Transfert Transfert, Boolean collection){
		Link selfLink = linkTo(TransfertController.class)
				.slash(Transfert.getId())
				.withSelfRel();
		if(collection) {
			Link collectionLink = linkTo(methodOn(TransfertController.class).getAllTransferts())
					.withSelfRel();
			return new Resource<>(Transfert, selfLink, collectionLink);
		}else {
			return new Resource<>(Transfert, selfLink);
		}
	}
	
	private Iterable<Transfert> TransfertsOfCompte(String idCompte){
		Iterable<Transfert> Transferts = repository.findAll();
		ArrayList<Transfert> selectedTransferts = new ArrayList();
		for (Transfert elt : Transferts) {
			if(elt.getIdCompte().equals(idCompte)) {
				selectedTransferts.add(elt);
			}
		}
		return selectedTransferts;
	}
}
