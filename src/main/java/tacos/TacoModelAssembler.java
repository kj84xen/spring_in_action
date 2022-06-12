package tacos;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import tacos.web.DesignTacoController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TacoModelAssembler implements RepresentationModelAssembler<Taco, TacoModel> {

    @Override
    public TacoModel toModel(Taco entity) {
        TacoModel tacoModel = TacoModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .ingredients(entity.getIngredientList())
                .build();

        tacoModel.add(linkTo(methodOn(DesignTacoController.class).tacoById(entity.getId())).withSelfRel());
        tacoModel.add(linkTo(methodOn(DesignTacoController.class).recentTacos()).withRel("recents"));

        return tacoModel;
    }

    @Override
    public CollectionModel<TacoModel> toCollectionModel(Iterable<? extends Taco> entities) {
        CollectionModel collectionModel = RepresentationModelAssembler.super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(DesignTacoController.class).recentTacos()).withSelfRel());
        return collectionModel;
    }
}
