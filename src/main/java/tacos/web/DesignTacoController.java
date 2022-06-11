package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.Taco;
import tacos.TacoModel;
import tacos.data.TacoRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * 식자재 요청 처리
 */
@Slf4j
@RestController
@RequestMapping(value = "/design", produces = {"application/json", "text/xml", MediaTypes.HAL_JSON_VALUE})
@CrossOrigin(origins = "*")
public class DesignTacoController {

	private TacoRepository tacoRepository;

	@Autowired
	public DesignTacoController(TacoRepository tacoRepository) {
		this.tacoRepository = tacoRepository;
	}

	@GetMapping("/recent")
	public ResponseEntity recentTacos() {
		PageRequest pageRequest = PageRequest.of(0, 12, Sort.by("createdAt").descending());

		List<Taco> tacoList = tacoRepository.findAll(pageRequest).getContent();
		CollectionModel collectionModel = CollectionModel.of(tacoList, linkTo(methodOn(this.getClass()).recentTacos()).withRel("recents"));
		return new ResponseEntity(collectionModel, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
		Optional<Taco> optTaco = tacoRepository.findById(id);
		if(optTaco.isPresent()) {
			return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@PostMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Taco postTaco(@RequestBody Taco taco) {
		return tacoRepository.save(taco);
	}

}
