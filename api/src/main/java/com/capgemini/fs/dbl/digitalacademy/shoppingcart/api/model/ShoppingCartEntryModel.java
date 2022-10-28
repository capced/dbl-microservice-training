package com.capgemini.fs.dbl.digitalacademy.shoppingcart.api.model;

import java.math.BigDecimal;

import com.capgemini.fs.dbl.digitalacademy.shoppingcart.persistence.domain.Product;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "entry")
@Relation(collectionRelation = "entries")
@JsonInclude(Include.NON_NULL)
public class ShoppingCartEntryModel extends RepresentationModel<ShoppingCartEntryModel>
{
	@Getter
	private String sessionId;

	@Getter
	private BigDecimal fullTaxesPrice;

	@Getter
	private BigDecimal fullTaxesAmount;

	@Getter
	private Double fullTaxesRate;

	@Builder.Default
	private Integer quantity = 1;

	@Getter
	private Product product;
}

