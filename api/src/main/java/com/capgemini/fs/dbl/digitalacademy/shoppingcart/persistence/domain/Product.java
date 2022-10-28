package com.capgemini.fs.dbl.digitalacademy.shoppingcart.persistence.domain;

import java.math.BigDecimal;

import javax.persistence.*;


import com.capgemini.fs.dbl.digitalacademy.shoppingcart.core.ProductType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCTS")
@Builder
@EqualsAndHashCode(callSuper = false)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private @Version Long version;

	@Column(name = "product_code", unique = true)
	private String code;

	@Column(name = "product_name")
	private String name;

	@Column(name = "product_is_imported") @Builder.Default
 	private boolean imported = Boolean.FALSE;

	@Column(name = "product_raw_price") @Builder.Default
	private BigDecimal rawPrice = BigDecimal.ZERO;

	@Column(name = "product_type") @Builder.Default
	private ProductType type = ProductType.AUTRE;

}
