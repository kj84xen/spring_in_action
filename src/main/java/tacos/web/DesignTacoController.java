package tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Taco;
import tacos.data.IngredientRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 식자재 요청 처리
 */
@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

	private final IngredientRepository ingredientRepository;

	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

	@GetMapping
	public String showDesignForm(Model model) {

		// 식자재 내역 생성
		List<Ingredient> ingredientList = new ArrayList<>();
		ingredientRepository.findAll().forEach(i -> ingredientList.add(i));

		Ingredient.Type[] types = Ingredient.Type.values();

		for(Ingredient.Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredientList, type));
		}

		model.addAttribute("taco", new Taco());

		return "design";
	}

	private List<Ingredient> filterByType(List<Ingredient> ingredientList, Ingredient.Type type) {
		return ingredientList
				.stream()
				.filter(x -> x.getType().equals(type))
				.collect(Collectors.toList());
	}

	@PostMapping
	public String processDesign(@Valid Taco design, Errors errors) {
		if(errors.hasErrors()) {
			return "design";
		}

		// TODO : 선택된 식자재 내역을 저장한다.
		log.info("Processing design:" + design);

		return "redirect:/orders/current";
	}
}
