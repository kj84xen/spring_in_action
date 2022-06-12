package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.domain.Taco;
import tacos.dto.TacoModelAssembler;
import tacos.data.TacoRepository;

import java.util.List;
import java.util.Optional;

/**
 * 식자재 요청 처리
 */
@Slf4j
@RestController
@RequestMapping(value = "/design", produces = {MediaTypes.HAL_JSON_VALUE})
@CrossOrigin(origins = "*")
public class DesignTacoController {

	private TacoRepository tacoRepository;
	private TacoModelAssembler tacoModelAssembler;

	@Autowired
	public DesignTacoController(TacoRepository tacoRepository, TacoModelAssembler tacoModelAssembler) {
		this.tacoRepository = tacoRepository;
		this.tacoModelAssembler = tacoModelAssembler;
	}

	@GetMapping("/recent")
	public ResponseEntity recentTacos() {
		PageRequest pageRequest = PageRequest.of(0, 12, Sort.by("createdAt").descending());
		List<Taco> tacoList = tacoRepository.findAll(pageRequest).getContent();
		return ResponseEntity.ok(tacoModelAssembler.toCollectionModel(tacoList));
	}

	@GetMapping("/{id}")
	public ResponseEntity tacoById(@PathVariable("id") Long id) {
		Optional<Taco> optTaco = tacoRepository.findById(id);
		if(optTaco.isPresent()) {
			return ResponseEntity.ok(tacoModelAssembler.toModel(optTaco.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity postTaco(@RequestBody Taco taco) {
		return ResponseEntity.ok(tacoModelAssembler.toModel(tacoRepository.save(taco)));
	}

}
