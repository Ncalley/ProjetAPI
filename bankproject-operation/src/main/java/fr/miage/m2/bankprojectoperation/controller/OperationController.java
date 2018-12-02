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

import fr.miage.m2.bankprojectoperation.model.Operation;
import fr.miage.m2.bankprojectoperation.repository.OperationRepository;


@RestController
@RequestMapping(value = "operations", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Operation.class)
public class OperationController {

	@Autowired
	private OperationRepository repository;

	public OperationController(OperationRepository repository) {
		super();
		this.repository = repository;
	}
	
	//Get methods *****************************************************************************
	
	@GetMapping("{OperationId}")
	public ResponseEntity<?> getOperation(@PathVariable("OperationId") String id) {
		//return repository.findById(id).toString();
		return Optional.ofNullable(repository.findById(id))
				.filter(Optional::isPresent)
				.map(i -> new ResponseEntity<>(OperationToResource(i.get(), true), HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@GetMapping
	public ResponseEntity<?> getAllOperations() {
		Iterable<Operation> allOperations = repository.findAll();
		return new ResponseEntity<>(OperationToResource(allOperations), HttpStatus.OK);
	}
	
	@GetMapping("ofCompte/{id}")
	public ResponseEntity<?> getAllOperations(@PathVariable String id) {
		return new ResponseEntity<>(OperationsOfCompte(id), HttpStatus.OK);
	}
	
	// Post methods ******************************************************************************
	
	@PostMapping
	public ResponseEntity<?> postOperation(@RequestBody Operation Operation) {
		Operation.setId(UUID.randomUUID().toString());
		Operation saved = repository.save(Operation);
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.setLocation(linkTo(OperationController.class).slash(saved.getId()).toUri());
		return new ResponseEntity<>(null, responseHeader, HttpStatus.CREATED);
	}
	
	//Put methods *********************************************************************************
	
	@PutMapping("{id}")
	public ResponseEntity<?> putOperation(@RequestBody Operation Operation, @PathVariable String id) {
		Optional<Operation> OperationOptional = repository.findById(id);
		if(OperationOptional.isPresent()) {
			Operation.setId(id);
			repository.save(Operation);
			return new ResponseEntity<>(OperationToResource(Operation,true),HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	//Delete methods ********************************************************************************
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteOperation(@PathVariable String id) {
		repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//Internal methods *******************************************************************************
	
	private Resources<Resource<Operation>> OperationToResource(Iterable<Operation> Operations){
		Link selfLink = linkTo(methodOn(OperationController.class).getAllOperations()).withSelfRel();
        List<Resource<Operation>> OperationRessources = new ArrayList();
        Operations.forEach(Operation
                -> OperationRessources.add(OperationToResource(Operation, false)));
        return new Resources<>(OperationRessources, selfLink);
	}
	
	private Resource<Operation> OperationToResource(Operation Operation, Boolean collection){
		Link selfLink = linkTo(OperationController.class)
				.slash(Operation.getId())
				.withSelfRel();
		if(collection) {
			Link collectionLink = linkTo(methodOn(OperationController.class).getAllOperations())
					.withSelfRel();
			return new Resource<>(Operation, selfLink, collectionLink);
		}else {
			return new Resource<>(Operation, selfLink);
		}
	}
	
	private Iterable<Operation> OperationsOfCompte(String idCompte){
		Iterable<Operation> Operations = repository.findAll();
		ArrayList<Operation> selectedOperations = new ArrayList();
		for (Operation elt : Operations) {
			if(elt.getIdCompte().equals(idCompte)) {
				selectedOperations.add(elt);
			}
		}
		return selectedOperations;
	}
}
