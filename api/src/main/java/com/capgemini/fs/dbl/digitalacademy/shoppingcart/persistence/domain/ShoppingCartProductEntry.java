package com.capgemini.fs.dbl.digitalacademy.shoppingcart.persistence.domain;

import java.math.BigDecimal;

import javax.persistence.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capgemini.fs.dbl.digitalacademy.shoppingcart.core.ArrondiMontant;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "SHOPPING_CARTS")
public class ShoppingCartProductEntry {

	@Transient
	Logger logger = LoggerFactory.getLogger(ShoppingCartProductEntry.class);

    private @Version Long version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "cart_session_id")
	@NonNull
	private String sessionId;

	@Column(name = "cart_product_fulltaxes_price")
	private BigDecimal fullTaxesPrice;

	@Column(name = "cart_product_fulltaxes_amount")
	private BigDecimal fullTaxesAmount;

	@Column(name = "cart_product_fulltaxes_rate")
	private Double fullTaxesRate;

	@NonNull
	@ManyToOne
    @JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "cart_product_quantity")
	@NonNull
	private Integer quantity;

	public static ShoppingCartProductEntry with(String sessionId, Product thisProduct, Integer thisQuantity) {
		ShoppingCartProductEntry scpe = new ShoppingCartProductEntry(sessionId, thisProduct, thisQuantity);
		scpe.caculerTaxesEtPrixTTC();

		return scpe;
	}

	public void calculerMontantToutesTaxes() {
		this.fullTaxesAmount = BigDecimal.ZERO;
		this.fullTaxesRate = this.product.getType().getTaxeRate();
		if (this.product.isImported()) {
			this.fullTaxesRate += Double.valueOf(0.05d);
		}

		this.fullTaxesAmount = ArrondiMontant
				.centimeSuperieur(this.product.getRawPrice().multiply(BigDecimal.valueOf(this.quantity)).multiply(BigDecimal.valueOf(this.fullTaxesRate)));
	}

	public void caculerTaxesEtPrixTTC() {
		this.calculerMontantToutesTaxes();

		this.fullTaxesPrice = ArrondiMontant
				.cinqCentimeSuperieur(this.product.getRawPrice().multiply(BigDecimal.valueOf(this.quantity)).add(this.fullTaxesAmount));
	}

	/**
	 * Increase product quantity
	 *
	 * @return new quantity
	 */
	public int increaseQuantity(int plus) {
		this.quantity += plus;
		this.caculerTaxesEtPrixTTC();

		return this.quantity;
	}

	/**
	 * Decrease product quantity if value is not Zero
	 *
	 * @return new quantity
	 */
	public int decreaseQuantity(int minus) {
		if (this.quantity == 1) {
			return 1;
		}

		this.quantity -= minus;
		this.caculerTaxesEtPrixTTC();

		return this.quantity;
	}
}
