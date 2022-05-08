package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.Ingredient;
import tacos.Order;
import tacos.Taco;
import tacos.User;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.data.UserRepository;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 식자재 요청 처리
 */
@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

	private final IngredientRepository ingredientRepository;

	private TacoRepository tacoRepository;

	private UserRepository userRepository;

	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository, UserRepository userRepository) {
		this.ingredientRepository = ingredientRepository;
		this.tacoRepository = tacoRepository;
		this.userRepository = userRepository;
	}

	private List<Ingredient> filterByType(List<Ingredient> ingredientList, Ingredient.Type type) {
		return ingredientList
				.stream()
				.filter(x -> x.getType().equals(type))
				.collect(Collectors.toList());
	}

	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}

	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}

	@GetMapping
	public String showDesignForm(Model model, Principal principal) {

		// 식자재 내역 생성
		List<Ingredient> ingredientList = new ArrayList<>();
		ingredientRepository.findAll().forEach(i -> ingredientList.add(i));

		Ingredient.Type[] types = Ingredient.Type.values();

		for(Ingredient.Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredientList, type));
		}

//		model.addAttribute("taco", new Taco());

		String username = principal.getName();
		User user = userRepository.findByUsername(username);
		model.addAttribute("user", user);

		return "design";
	}

	@PostMapping
	public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
		if(errors.hasErrors()) {
			return "design";
		}

		Taco saved = tacoRepository.save(design);
		order.addDesign(saved);

		return "redirect:/orders/current";
	}
}
