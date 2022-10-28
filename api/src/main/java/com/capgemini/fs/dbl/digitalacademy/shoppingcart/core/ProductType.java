package com.capgemini.fs.dbl.digitalacademy.shoppingcart.core;

import java.util.Arrays;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ProductType {

	PREMIERE_NECESSITE("NEC", 0d), LIVRE("LIV", 0.1d), AUTRE("AUT", 0.2d);

	@Getter
	private String code;
	@Getter
	private double taxeRate;

	public static ProductType fromCode(final String thisCode) {
		Optional<ProductType> productType = Arrays.asList(ProductType.values()).stream().filter(p-> p.getCode().equals(thisCode)).findFirst();

		return productType.get();
	}

	@Override
	public String toString() {
		return this.getCode();
	}
}
