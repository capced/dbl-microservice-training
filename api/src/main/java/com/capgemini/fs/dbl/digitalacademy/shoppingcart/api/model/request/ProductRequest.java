package com.capgemini.fs.dbl.digitalacademy.shoppingcart.api.model.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class ProductRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String productCode;

    private Integer quantity = 1;
}
