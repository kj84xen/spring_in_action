package tacos.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Taco;

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

	@GetMapping
	public String showDesignForm(Model model) {

		// 식자재 내역 생성
		List<Ingredient> ingredientList = Arrays.asList(
				new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
				new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
				new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
				new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
				new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
				new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
				new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
				new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
				new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
				new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
		);

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
	public String processDesign(Taco design) {

		// TODO : 선택된 식자재 내역을 저장한다.
		log.info("Processing design:" + design);

		return "redirect:/orders/current";
	}
}
