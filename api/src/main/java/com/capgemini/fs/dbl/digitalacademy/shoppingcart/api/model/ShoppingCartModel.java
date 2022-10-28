package com.capgemini.fs.dbl.digitalacademy.shoppingcart.api.model;

import java.util.concurrent.atomic.DoubleAdder;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "cart")
@JsonInclude(Include.NON_NULL)
@AllArgsConstructor
public class ShoppingCartModel extends RepresentationModel<ShoppingCartModel> {

	@Getter @Builder.Default
	private CollectionModel<ShoppingCartEntryModel> productEntries = CollectionModel.empty();


	@Getter @NonNull
	private String sessionId;

	@Getter
	private final DoubleAdder totalPrice = new DoubleAdder();

	@Getter
	private final DoubleAdder totalTaxes = new DoubleAdder();

	public static ShoppingCartModelBuilder builder() {
		return new CustomShoppingCartModelBuilder();
	}

	private static class CustomShoppingCartModelBuilder extends ShoppingCartModelBuilder {
        @Override
        public ShoppingCartModel build() {
        	ShoppingCartModel model = super.build();
        	model.computeTotals();

        	return model;
        }
    }

	private void computeTotals() {
		productEntries.forEach(p -> updateAmounts(p));
	}

	private void updateAmounts(final ShoppingCartEntryModel productEntry) {
		totalPrice.add(productEntry.getFullTaxesPrice().doubleValue());
		totalTaxes.add(productEntry.getFullTaxesAmount().doubleValue());
	}
}
