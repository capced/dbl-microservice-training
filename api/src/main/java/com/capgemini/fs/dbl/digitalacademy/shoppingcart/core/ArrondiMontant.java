package com.capgemini.fs.dbl.digitalacademy.shoppingcart.core;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ArrondiMontant {

	private ArrondiMontant() {
		super();
	}

	public static BigDecimal cinqCentimeSuperieur(BigDecimal montant) {
		return montant.multiply(BigDecimal.valueOf(2)) //
				.setScale(1, RoundingMode.CEILING).setScale(2) //
				.divide(BigDecimal.valueOf(2));
	}

	public static BigDecimal centimeSuperieur(BigDecimal montant) {
		return montant //
				.setScale(2, RoundingMode.CEILING).setScale(2);
	}

}
