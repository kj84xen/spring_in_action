package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.Order;
import tacos.Taco;
import tacos.data.TacoRepository;

import javax.validation.Valid;
import java.util.Optional;

/**
 * 식자재 요청 처리
 */
@Slf4j
@RestController
@RequestMapping(value = "/design", produces = {"application/json", "text/xml"})
@CrossOrigin(origins = "*")
public class DesignTacoController {

	private TacoRepository tacoRepository;

//	@Autowired
//	EntityLinks entityLinks;

	@Autowired
	public DesignTacoController(TacoRepository tacoRepository) {
		this.tacoRepository = tacoRepository;
	}

	@GetMapping("/recent")
	public Iterable<Taco> recentTacos() {
		PageRequest pageRequest = PageRequest.of(0, 12, Sort.by("createdAt").descending());
		return tacoRepository.findAll(pageRequest).getContent();
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
