package com.capgemini.fs.dbl.digitalacademy.shoppingcart.api.model.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.capgemini.fs.dbl.digitalacademy.shoppingcart.persistence.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.mvc.BasicLinkBuilder;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.capgemini.fs.dbl.digitalacademy.shoppingcart.api.ShoppingCartController;
import com.capgemini.fs.dbl.digitalacademy.shoppingcart.api.model.ShoppingCartEntryModel;
import com.capgemini.fs.dbl.digitalacademy.shoppingcart.persistence.domain.ShoppingCartProductEntry;

@Component
public class ShoppingCartEntryModelAssembler extends RepresentationModelAssemblerSupport<ShoppingCartProductEntry, ShoppingCartEntryModel>{

	public ShoppingCartEntryModelAssembler() {
        super(ShoppingCartController.class, ShoppingCartEntryModel.class);
    }

	@Override
	public ShoppingCartEntryModel toModel(ShoppingCartProductEntry entity) {
		ShoppingCartEntryModel model = ShoppingCartEntryModel.builder() //
				.quantity(entity.getQuantity()) //
				.sessionId(entity.getSessionId()) //
				.quantity(entity.getQuantity()) //
				.fullTaxesAmount(entity.getFullTaxesAmount()) //
				.fullTaxesPrice(entity.getFullTaxesPrice()) //
				.fullTaxesRate(entity.getFullTaxesRate())
                .product(entity.getProduct()).build();

        String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
        StringBuilder str = new StringBuilder(baseUri);
        str.append("/carts");
        str.append("/findBySessionId");
        model.add(Link.of(str.toString(), IanaLinkRelations.SELF));

		return model;
	}

	@Override
	public CollectionModel<ShoppingCartEntryModel> toCollectionModel(Iterable<? extends ShoppingCartProductEntry> entities) {
		return super.toCollectionModel(entities);
	}
}
