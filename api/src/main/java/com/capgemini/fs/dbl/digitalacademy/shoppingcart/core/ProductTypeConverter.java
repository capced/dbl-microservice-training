package com.capgemini.fs.dbl.digitalacademy.shoppingcart.core;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.util.StringUtils;

@Converter(autoApply = true)
public class ProductTypeConverter implements AttributeConverter<ProductType, String> {

	@Override
	public String convertToDatabaseColumn(ProductType type) {
		 if (type == null) {
	            return null;
	        }
	        return type.getCode();
	}

	@Override
	public ProductType convertToEntityAttribute(String typeCode) {
		if (StringUtils.isEmpty(typeCode)) {
			return null;
		}

		return ProductType.fromCode(typeCode);
	}

}
