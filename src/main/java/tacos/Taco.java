package tacos;

import java.util.List;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 타코 디자인
 */
@Data
public class Taco {
	@NotNull
	@Size(min = 5, message = "Name must be at least 5 characters long")
	private String name;

	@Size(min = 1, message = "You meus choose at least 1 ingredient")
	private List<String> ingredients;
}
