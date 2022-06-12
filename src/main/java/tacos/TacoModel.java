package tacos;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@EqualsAndHashCode(callSuper = false)
@Relation(itemRelation = "taco", collectionRelation = "tacos")
public class TacoModel extends RepresentationModel<TacoModel> {

    private Long id;
    private Date createdAt;
    private String name;
    private List<Ingredient> ingredients;

}
