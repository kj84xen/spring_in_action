package tacos;

import java.util.List;

import lombok.Data;

/**
 * 타코 디자인
 */
@Data
public class Taco {
	private String name;
	private List<String> ingredients;
}
