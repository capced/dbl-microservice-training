package com.capgemini.fs.dbl.digitalacademy.shoppingcart.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.fs.dbl.digitalacademy.shoppingcart.persistence.domain.Product;
import com.capgemini.fs.dbl.digitalacademy.shoppingcart.persistence.domain.ShoppingCartProductEntry;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.RequestHeader;

@RepositoryRestResource(collectionResourceRel = "carts", path = "carts")
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartProductEntry, String> {

	List<ShoppingCartProductEntry> findBySessionId(String sessionId);

	// @Query("SELECT s FROM ShoppingCartProductEntry s WHERE s.sessionId = ?1 AND s.product.code = ?2")
	ShoppingCartProductEntry findBySessionIdAndProduct(String sessionId, Product product);
}
